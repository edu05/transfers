package org.example.play;

public class Impl1 extends MyAbstractClass {

    public void doIt(String string) {
        System.out.println(string);
    }

    public void doIt(Integer number) {
        System.out.println(number);
    }

    public static void main(String[] args) {
        Impl1 q = new Impl1();

        Object t = "hey";

        q.doIt(t);
        q.doIt(1);
        q.doIt(3.2);
    }
}
