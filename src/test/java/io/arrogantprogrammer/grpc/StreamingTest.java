package io.arrogantprogrammer.grpc;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.core.eventbus.EventBus;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class StreamingTest {

    @Inject
    EventBus eventBus;

    @ConsumeEvent("order")
    public String consumeOrder(String order) {
        return order;
    }


    @Test
    public void testStreaming() {



    }
}
