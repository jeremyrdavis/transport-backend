package io.arrogantprogrammer.graphql;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueryOptionsTest {

    @Test
    public void testQueryParams() {
        Optional<String> name = Optional.ofNullable(null);
        assertTrue(name.isPresent(), "name should be present");
        assertFalse(name.isPresent());
        assertTrue(name.isEmpty());
    }
}
