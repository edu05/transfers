package org.example.urlshortening;

public class UrlShortener {

    private final IdHasher idHasher;
    private final IdGenerator idGenerator;
    private final ShortUrlRepository shortUrlRepository;

    public UrlShortener(IdHasher idHasher, IdGenerator idGenerator, ShortUrlRepository shortUrlRepository) {
        this.idHasher = idHasher;
        this.idGenerator = idGenerator;
        this.shortUrlRepository = shortUrlRepository;
    }

    public String shorten(String url) {
        if (url == null) {
            return null;
        }
        String shortUrl = idHasher.hash(idGenerator.getUniqueId());
        shortUrlRepository.save(url, shortUrl);
        return shortUrl;
    }
}
