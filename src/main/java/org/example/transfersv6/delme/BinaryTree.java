package org.example.transfersv6.delme;

import java.util.Arrays;

public class BinaryTree {

    private final int[] nodes = new int[64];
    private int next = 0;

    public void insert(int i) {
        nodes[next] = i;
        next++;
        bubbleUp();
        System.out.println("finished insertion");
        System.out.println(this);
    }

    public int poll() {
        if (next == 0) {
            throw new RuntimeException("empty you dummy");
        }
        int polled = nodes[0];
        nodes[0] = nodes[next - 1];
        next--;
        bubbleDown();
        System.out.println("finished polling");
        System.out.println(this);
        return polled;
    }

    private void bubbleDown() {
        int nextCopy = 0;

        int l = nextCopy * 2 + 1;
        int r = nextCopy * 2 + 2;
        int leftChildValue = nodes[l];
        int rightChildValue = nodes[r];
        while (nodes[nextCopy] > leftChildValue || nodes[nextCopy] > rightChildValue) {
            if (leftChildValue < rightChildValue) {
                System.out.println("swapping on the left " + nodes[nextCopy] + " for " + leftChildValue);
                nodes[l] = nodes[nextCopy];
                nodes[nextCopy] = leftChildValue;
                nextCopy = l;
            } else {
                System.out.println("swapping on the right " + nodes[nextCopy] + " for " + rightChildValue);
                nodes[r] = nodes[nextCopy];
                nodes[nextCopy] = rightChildValue;
                nextCopy = r;
            }
            l = nextCopy * 2 + 1;
            r = nextCopy * 2 + 2;
            leftChildValue = nodes[l];
            rightChildValue = nodes[r];
            System.out.println(this);
        }

    }

    private void bubbleUp() {
        if (next < 2) {
            return;
        }
        int nextCopy = next - 1;
        int parent = (int) Math.ceil((nextCopy / 2.0) - 1);
        while (parent >= 0 && nodes[nextCopy] < nodes[parent]) {
            System.out.println("bubbling up " + nodes[nextCopy] + " from " + nodes[parent]);
            int aux = nodes[nextCopy];
            nodes[nextCopy] = nodes[parent];
            nodes[parent] = aux;
            nextCopy = parent;
            parent = (int) Math.ceil((nextCopy / 2.0) - 1);
            System.out.println(this);
        }
    }

    @Override
    public String toString() {
        int index = 0;
        int nextBreak = 0;
        StringBuilder sb = new StringBuilder();
        while (index < next) {
            sb.append(nodes[index] + " ");
            if (index == nextBreak) {
                sb.append("\n");
                nextBreak = index * 2 + 2;
            }
            index++;

        }
        return sb.toString();
    }

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();

        binaryTree.insert(10);
        binaryTree.insert(1);
        binaryTree.insert(9);
        binaryTree.insert(2);
        binaryTree.insert(8);
        binaryTree.insert(3);
        binaryTree.insert(7);
        binaryTree.insert(4);
        binaryTree.insert(6);
        binaryTree.insert(5);

        System.out.println("polled " + binaryTree.poll());
        System.out.println("polled " + binaryTree.poll());
        System.out.println("polled " + binaryTree.poll());
        System.out.println("polled " + binaryTree.poll());
        System.out.println("polled " + binaryTree.poll());
        System.out.println("polled " + binaryTree.poll());
        System.out.println("polled " + binaryTree.poll());
        System.out.println("polled " + binaryTree.poll());
        System.out.println("polled " + binaryTree.poll());
        System.out.println("polled " + binaryTree.poll());
    }


}
