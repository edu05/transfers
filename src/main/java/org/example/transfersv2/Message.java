package org.example.transfersv2;

import java.util.List;
import java.util.function.Function;

public class Message<T> {

    private final Actor actor;
    private final T object;
    private final Function<T, List<Message<T>>> function;

    public Message(Actor actor, T object, Function<T, List<Message<T>>> function) {
        this.actor = actor;
        this.object = object;
        this.function = function;
    }
}
