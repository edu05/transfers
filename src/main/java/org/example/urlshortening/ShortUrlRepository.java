package org.example.urlshortening;

import java.util.concurrent.ConcurrentHashMap;

public class ShortUrlRepository {

    private final ConcurrentHashMap<String, String> db = new ConcurrentHashMap<>();

    public void save(String longUrl, String shortUrl) {
        db.put(shortUrl, longUrl);
    }

    public String getLongUrl(String shortUrl) {
        return db.get(shortUrl);
    }
}
