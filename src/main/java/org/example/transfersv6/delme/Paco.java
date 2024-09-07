package org.example.transfersv6.delme;

public class Paco {

    public static void main(String[] args) {
        System.out.println(doIt("Mr John Smith    ".toCharArray(), 13));
    }

    private static String doIt(char[] string, int size) {
        var totalMove = string.length - size;

        for (int i = size - 1; i >= 0; i--) {
            if (string[i] == ' ') {
                string[i + totalMove] = '0';
                string[i + totalMove - 1] = '2';
                string[i + totalMove - 2] = '%';
                totalMove--;
                totalMove--;
            } else {
                string[i + totalMove] = string[i];
            }
        }

        return new String(string);
    }
}
