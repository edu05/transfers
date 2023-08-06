package org.example.urlshortening;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UrlPoolerTest {

    private UrlPool urlPool = mock(UrlPool.class);
    private ShortUrlRepository shortUrlRepository = mock(ShortUrlRepository.class);
    private UrlPooler urlPooler = new UrlPooler(urlPool, shortUrlRepository);


    @Test
    void shouldReturnUrlFromPool() {
        when(urlPool.getNextShortUrl()).thenReturn("shortUrl");
        assertThat(urlPooler.shorten("longUrl"), equalTo("shortUrl"));
    }

    @Test
    void shouldSaveRelationship() {
        when(urlPool.getNextShortUrl()).thenReturn("shortUrl");
        assertThat(urlPooler.shorten("longUrl"), equalTo("shortUrl"));
        verify(shortUrlRepository).save("longUrl", "shortUrl");
    }
}