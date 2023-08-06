package org.example.urlshortening;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UrlPool {

    private final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

    public UrlPool(List<String> initialShortUrls) {
        queue.addAll(initialShortUrls);
    }

    public String getNextShortUrl() {
        return queue.remove();
    }
}
