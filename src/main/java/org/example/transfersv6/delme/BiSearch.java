package org.example.transfersv6.delme;

import java.util.ArrayList;
import java.util.List;

public class BiSearch {

    public static void main(String[] args) {

        Node l = new Node("l");
        Node m = new Node("m");
        Node r = new Node("r");

        //left search
        Node l1 = new Node("l1");
        Node l2 = new Node("l2");
        l.addAdjacent(l1);
        l.addAdjacent(l2);
        Node l11 = new Node("l11");
        Node l12 = new Node("l12");
        l1.addAdjacent(l11);
        l1.addAdjacent(l12);
        Node l121 = new Node("l121");
        Node l122 = new Node("l122");
        l12.addAdjacent(l121);
        l12.addAdjacent(l122);
        l122.addAdjacent(m);

        //right search

        Node r1 = new Node("r1");
        Node r2 = new Node("r2");
        Node r3 = new Node("r3");
        r.addAdjacent(r1);
        r.addAdjacent(r2);
        r.addAdjacent(r3);
        Node r21 = new Node("r21");
        Node r22 = new Node("r22");
        Node r23 = new Node("r23");
        r2.addAdjacent(r21);
        r2.addAdjacent(r22);
        r2.addAdjacent(r23);
        Node r231 = new Node("r231");
        Node r232 = new Node("r232");
        Node r233 = new Node("r233");
        r23.addAdjacent(r231);
        r23.addAdjacent(r232);
        r23.addAdjacent(r233);
        r231.addAdjacent(m);
        int d = biSearch(l, r);
        System.out.println(d);
    }

    private static int biSearch(Node l, Node r) {
        MyQueue q1 = new MyQueue();
        MyQueue q1_c = new MyQueue();
        MyQueue q2 = new MyQueue();
        MyQueue q2_c = new MyQueue();
        l.l_v = true;
        r.r_v = true;
        q1.push(l);
        q2.push(r);
        while (!q1.isEmpty() && !q2.isEmpty()) {
            while (!q1.isEmpty()) {
                Node pop = q1.pop();
                System.out.println("visited " + pop.name);
                if (pop.r_v) {
                    return pop.l_d + pop.r_d;
                }
                pop.l_v = true;
                for (Node node : pop.adjacentNodes) {
                    if (!node.l_v) {
                        node.l_d = pop.l_d + 1;
                        q1_c.push(node);
                    }
                }
            }
            q1 = q1_c;
            q1_c = new MyQueue();

            while (!q2.isEmpty()) {
                Node pop = q2.pop();
                System.out.println("visited " + pop.name);
                if (pop.l_v) {
                    return pop.l_d + pop.r_d;
                }
                pop.r_v = true;
                for (Node node : pop.adjacentNodes) {
                    if (!node.r_v) {
                        node.r_d = pop.r_d + 1;
                        q2_c.push(node);
                    }
                }
            }
            q2 = q2_c;
            q2_c = new MyQueue();
        }
        return -1;
    }


    public static class Node {
        private final String name;
        private final List<Node> adjacentNodes;
        private boolean l_v;
        private boolean r_v;
        private int l_d;
        private int r_d;

        public Node(String name) {
            this.name = name;
            this.adjacentNodes = new ArrayList<>();
        }

        public void addAdjacent(Node node) {
            this.adjacentNodes.add(node);
            node.adjacentNodes.add(this);
        }
    }

    public static class MyQueue {

        private org.example.transfersv6.delme.Node<Node> head;
        private org.example.transfersv6.delme.Node<Node> tail;
        private int index = -1;

        public void push(Node val) {
            if (head == null) {
                head = new org.example.transfersv6.delme.Node(val);
                tail = head;
            } else {
                org.example.transfersv6.delme.Node newTail = new org.example.transfersv6.delme.Node(val);
                tail.next = newTail;
                tail = newTail;
            }
        }

        public Node pop() {
            Node val = head.val;
            head = head.next;
//        if (head == null) {
//            tail = null;
//        }
            return val;
        }

        public boolean isEmpty() {
            return head == null;
        }
    }
}
