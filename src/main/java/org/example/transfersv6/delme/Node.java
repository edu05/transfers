package org.example.transfersv6.delme;

import java.util.Hashtable;


public class Node<T> {

    public T val;
    public Node<T> next;
    public Node(T val) {
        this.val = val;
    }

    public void add(int val) {
        addTo(this, new Node(val));

    }

    public T kthLast(int reversedPosition) {
//        new HashTable();
        new Hashtable();
        int count = 0;
        Node<T> n = this;
        while (n != null) {
            count++;
            n = n.next;
        }

        int forwardPosition = count - 1 - reversedPosition;
        n = this;
        for (int i = 0; i < forwardPosition; i++) {
            n = n.next;
        }
        return n.val;
    }

    private void addTo(Node head, Node newNode) {
        if (head.next == null) {
            head.next = newNode;
        } else {
            addTo(head.next, newNode);
        }
    }

    public void removeDuplicates() {
        Node<T> n = this;
        while (n != null) {
            T test = n.val;
            Node<T> m = n;
            while (m.next != null) {
                if (m.next.val == test) {
                    m.next = m.next.next;
                } else {
                    m = m.next;
                }
            }
            n = n.next;
        }
    }

    @Override
    public String toString() {
        StringBuilder append = new StringBuilder()
                .append("[");
        Node<T> n = this;
        do {
            append.append(n.val);
            n = n.next;
        } while (n != null);
        return append
                .append("]")
                .toString();
    }

    public static void main(String[] args) {
        Node<Integer> node = new Node<>(3);
        node.add(2);
        node.add(1);
        node.add(1);
        node.add(4);
        node.add(5);
        node.add(3);
        node.add(3);
        node.add(3);
        node.add(6);
        node.add(6);
        node.add(2);
        node.add(2);
        node.add(8);
        node.add(7);
        node.add(7);
        System.out.println(node);
        node.removeDuplicates();
        System.out.println(node);
        System.out.println(node.kthLast(0));
        System.out.println(node.kthLast(1));
        System.out.println(node.kthLast(2));
        System.out.println(node.kthLast(3));
        System.out.println(node.kthLast(4));
    }
}
