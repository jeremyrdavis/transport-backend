package io.arrogantprogrammer.graphql;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class QueryOptionsTest {

    @Test
    public void testQueryParams() {
        Optional<String> name = Optional.ofNullable(null);
        assertTrue(name.isPresent(), "name should be present");
        assertFalse(name.isPresent());
        assertTrue(name.isEmpty());
    }

    @Test
    public void testOrderParamsObject() {
        OrderParams orderParams = new OrderParams.Builder()
                .withName("Jeremy").build();
        assertTrue(orderParams.name().isPresent());
        assertFalse(orderParams.name().isEmpty());
        assertEquals("Jeremy", orderParams.name().get());
        assertFalse(orderParams.menuItem().isPresent());
        assertTrue(orderParams.menuItem().isEmpty());

    }

}
