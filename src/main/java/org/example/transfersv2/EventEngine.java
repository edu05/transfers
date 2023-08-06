package org.example.transfersv2;

import java.util.ArrayList;
import java.util.List;

public class EventEngine {

    private final List<Actor> actors;
    private final List<Message> messages = new ArrayList<>();

    public EventEngine(List<Actor> actors) {
        this.actors = actors;
    }

    public void start() {

    }
}