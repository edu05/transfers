package org.example.urlshortening;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class IdHasherTest {

    private final IdHasher idHasher = new IdHasher();

    @Test
    void shouldHashString() {
        assertThat(idHasher.hash(16), equalTo("f"));
    }
}