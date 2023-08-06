package org.example.urlshortening;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

    private final AtomicLong idCounter = new AtomicLong(0);
    public long getUniqueId() {
        return idCounter.getAndIncrement();
    }
}
