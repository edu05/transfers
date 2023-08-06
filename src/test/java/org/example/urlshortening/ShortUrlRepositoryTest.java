package org.example.urlshortening;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ShortUrlRepositoryTest {

    private final ShortUrlRepository repository = new ShortUrlRepository();


    @Test
    void shouldRetrievePreviouslySavedUrls() {

        repository.save("longUrl", "shortUrl");
        assertThat(repository.getLongUrl("shortUrl"), equalTo("longUrl"));
    }
}