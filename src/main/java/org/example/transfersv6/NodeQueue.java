/*
 * copy of https://github.com/akka/akka/blob/main/akka-actor/src/main/java/akka/dispatch/AbstractNodeQueue.java
 */

package org.example.transfersv6;

import akka.util.Unsafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Lock-free MPSC linked queue implementation based on Dmitriy Vyukov's non-intrusive MPSC queue:
 * https://www.1024cores.net/home/lock-free-algorithms/queues/non-intrusive-mpsc-node-based-queue
 * <p>
 * This queue could be wait-free (i.e. without the spinning loops in peekNode and pollNode) if
 * it were permitted to return null while the queue is not quite empty anymore but the enqueued
 * element is not yet visible. This would break actor scheduling, though.
 */
@SuppressWarnings("serial")
public class NodeQueue<T> extends AtomicReference<NodeQueue.Node<T>> implements Inbox<T> {

    /*
     * Extends AtomicReference for the "head" slot (which is the one that is appended to) since
     * there is nothing to be gained by going to all-out Unsafe usage—we’d have to do
     * cache-line padding ourselves.
     */

    @SuppressWarnings("unused")
    private volatile Node<T> _tailDoNotCallMeDirectly;

    protected NodeQueue() {
        final Node<T> n = new Node<T>();
        _tailDoNotCallMeDirectly = n;
        set(n);
    }

    /**
     * Add an element to the head of the queue.
     * <p>
     * This method can be used from any thread.
     *
     * @param value the element to be added; must not be null
     */
    public final void add(final T value) {
        final Node<T> n = new Node<>(value);
        getAndSet(n).setNext(n);
    }

    /**
     * Pull one item from the queue’s tail if there is one.
     * <p>
     * Use this method only from the consumer thread!
     *
     * @return element if there was one, or null if there was none
     */
    public final T poll() {
        final Node<T> next = pollNode();
        if (next == null) {
            return null;
        } else {
            T value = next.value;
            next.value = null;
            return value;
        }
    }

    /**
     * Pull one item from the queue, returning it within a queue node.
     * <p>
     * Use this method only from the consumer thread!
     *
     * @return queue node with element inside if there was one, or null if there was none
     */
    @SuppressWarnings("unchecked")
    public final Node<T> pollNode() {
        final Node<T> tail = (Node<T>) Unsafe.instance.getObjectVolatile(this, tailOffset);
        Node<T> next = tail.next();
        if (next == null && get() != tail) {
            // if tail != head this is not going to change until producer makes progress
            // we can avoid reading the head and just spin on next until it shows up
            do {
                next = tail.next();
            } while (next == null);
        }
        if (next == null) {
            return null;
        } else {
            tail.value = next.value;
            next.value = null;
            Unsafe.instance.putOrderedObject(this, tailOffset, next);
            tail.setNext(null);
            return tail;
        }
    }

    private final static long tailOffset;

    static {
        try {
            tailOffset = Unsafe.instance.objectFieldOffset(NodeQueue.class.getDeclaredField("_tailDoNotCallMeDirectly"));
        } catch (Throwable t) {
            throw new ExceptionInInitializerError(t);
        }
    }

    public static class Node<T> {
        public T value;
        @SuppressWarnings("unused")
        private volatile Node<T> _nextDoNotCallMeDirectly;

        public Node() {
            this(null);
        }

        public Node(final T value) {
            this.value = value;
        }

        @SuppressWarnings("unchecked")
        public final Node<T> next() {
            return (Node<T>) Unsafe.instance.getObjectVolatile(this, nextOffset);
        }

        protected final void setNext(final Node<T> newNext) {
            Unsafe.instance.putOrderedObject(this, nextOffset, newNext);
        }

        private final static long nextOffset;

        static {
            try {
                nextOffset = Unsafe.instance.objectFieldOffset(Node.class.getDeclaredField("_nextDoNotCallMeDirectly"));
            } catch (Throwable t) {
                throw new ExceptionInInitializerError(t);
            }
        }
    }
}