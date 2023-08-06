package org.example.urlshortening;

public class IdHasher {

    public String hash(long id) {

        return hash(id, 62);
    }

    private String hash(long remainder, int base) {
        long cotient = remainder / base;
//        if ()
        return null;
    }
}
