package org.example.urlshortening;

public class UrlPooler {

    private final UrlPool urlPool;
    private final ShortUrlRepository shortUrlRepository;

    public UrlPooler(UrlPool urlPool, ShortUrlRepository shortUrlRepository) {
        this.urlPool = urlPool;
        this.shortUrlRepository = shortUrlRepository;
    }

    public String shorten(String url) {
        String nextShortUrl = urlPool.getNextShortUrl();
        shortUrlRepository.save(url, nextShortUrl);
        return nextShortUrl;
    }
}
