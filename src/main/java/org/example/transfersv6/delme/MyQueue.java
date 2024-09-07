package org.example.transfersv6.delme;

public class MyQueue {

    private Node<Integer> head;
    private Node<Integer> tail;
    private int index = -1;

    public void push(int val) {
        if (head == null) {
            head = new Node(val);
            tail = head;
        } else {
            Node newTail = new Node(val);
            tail.next = newTail;
            tail = newTail;
        }
    }

    public int pop() {
        int val = head.val;
        head = head.next;
//        if (head == null) {
//            tail = null;
//        }
        return val;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public static void main(String[] args) {
        MyQueue myQueue = new MyQueue();
        myQueue.push(1);
        myQueue.push(2);
        myQueue.push(3);
        myQueue.push(4);
        myQueue.push(5);
        myQueue.push(6);
        System.out.println(myQueue.pop());
        System.out.println(myQueue.pop());
        System.out.println(myQueue.pop());
        System.out.println(myQueue.pop());
        System.out.println(myQueue.pop());
        System.out.println(myQueue.pop());
        myQueue.push(7);
        myQueue.push(8);
        myQueue.push(9);
        System.out.println(myQueue.pop());
        System.out.println(myQueue.pop());
        System.out.println(myQueue.pop());
    }
}
