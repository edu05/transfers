package org.example.urlshortening;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UrlPoolTest {

    private UrlPool urlPool = new UrlPool(List.of("first", "second"));

    @Test
    void shouldGetAllSavedShortUrlsAndThenFailAftwards() {
        assertThat(urlPool.getNextShortUrl(), equalTo("first"));
        assertThat(urlPool.getNextShortUrl(), equalTo("second"));
        assertThrows(Exception.class, () -> urlPool.getNextShortUrl());
    }
}