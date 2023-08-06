package org.example;

public class MyClass {

    private final NumberGiver numberGiver;

    public MyClass(NumberGiver numberGiver) {
        this.numberGiver = numberGiver;
    }

    public MyNumber doIt() {
        return numberGiver.giveNumber(new MyNumber(2));
    }
}
