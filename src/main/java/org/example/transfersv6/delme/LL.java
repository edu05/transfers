package org.example.transfersv6.delme;

public class LL {

    public static void main(String[] args) {
        System.out.println(check("pale", "pales"));
        System.out.println(check("pale", "ple"));
        System.out.println(check("pale", "bale"));
        System.out.println(check("pale", "bake"));

    }

    public static boolean check(String first, String second) {
        if (first.length() == second.length()) {
            boolean madeAMistake = false;
            for (int i = 0; i < first.length(); i++) {
                if (first.charAt(i) != second.charAt(i)) {
                    if (madeAMistake) {
                        return false;
                    } else {
                        madeAMistake = true;
                    }
                }
            }
            return true;
        } else if (first.length() == second.length() - 1) {
            int offset = 0;
            for (int i = 0; i < first.length(); i++) {
                if (first.charAt(i) != second.charAt(i + offset)) {
                    if (offset == 1) {
                        return false;
                    } else {
                        offset++;
                    }
                }
            }
            return true;
        } else if (first.length() == second.length() + 1) {
            return check(second, first);
        }
        return false;
    }
}
