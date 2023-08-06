package org.example.urlshortening;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class UrlShortenerTest {

    private IdHasher idHasher = mock(IdHasher.class);
    private IdGenerator idGenerator = mock(IdGenerator.class);
    private ShortUrlRepository shortUrlRepository = mock(ShortUrlRepository.class);
    private final UrlShortener urlShortener = new UrlShortener(idHasher, idGenerator, shortUrlRepository);

    @Test
    void shouldReturnNullOnNullUrl() {
        assertNull(urlShortener.shorten(null));
    }

    @Test
    void shouldHashUrl() {
        when(idGenerator.getUniqueId()).thenReturn(0l);
        when(idHasher.hash(0)).thenReturn("shortUrl");
        assertThat(urlShortener.shorten("https://apache.org"), equalTo("shortUrl"));
    }

    @Test
    void shouldPersistShortUrls() {
        when(idGenerator.getUniqueId()).thenReturn(0l);
        when(idHasher.hash(0)).thenReturn("shortUrl");
        assertThat(urlShortener.shorten("https://apache.org"), equalTo("shortUrl"));

        verify(shortUrlRepository).save("https://apache.org", "shortUrl");
    }

}