package org.example.transfersv6.delme;

public class MyMergeSort {

    public static void main(String[] args) {
        int[] myArray = {3, 1, 5, 2, 9, 6, 8, 7};

        mergeSort(myArray);

        System.out.println(myArray);
    }

    private static void mergeSort(int[] myArray) {
        mergeSort(myArray, 0, myArray.length - 1, new int[2]);
    }

    private static void mergeSort(int[] myArray, int left, int right, int[] aux) {
        if (left == right) {
            return;
        }
        if (left + 1 == right) {
            if (myArray[left] > myArray[right]) {
                aux[0] = myArray[left];
                myArray[left] = myArray[right];
                myArray[right] = aux[left];
            }
            return;
        }

        //sort left
        mergeSort(myArray, left, (left + right) / 2, aux);
        //sort right
        mergeSort(myArray, ((left + right) / 2) + 1, right, aux);
        //combine
        merge(myArray, left, right, aux);
    }

    private static void merge(int[] myArray, int left, int right, int[] aux) {
        //needs a notion of midpoint
        aux[1] = ((left + right) / 2) + 1;
        while (left != ((left + right) / 2) + 1) {
            //this needs to be a while too
            if (myArray[left] > myArray[aux[1]]) {
                aux[0] = myArray[left];
                myArray[left] = myArray[aux[1]];
                myArray[right] = aux[0];
            }
            left++;

        }

    }
}
