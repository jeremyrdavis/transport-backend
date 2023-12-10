package io.arrogantprogrammer.grpc;

import io.arrogantprogrammer.OrderService;
import io.arrogantprogrammer.domain.MenuItem;
import io.arrogantprogrammer.domain.OrderCommand;
import io.arrogantprogrammer.proto.Empty;
import io.arrogantprogrammer.proto.OrderRecordProto;
import io.arrogantprogrammer.proto.PlaceOrderProto;
import io.quarkus.grpc.GrpcService;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class GRPCResource implements io.arrogantprogrammer.proto.gRPCService {

    static final Logger LOGGER = LoggerFactory.getLogger(GRPCResource.class);

    @Inject
    OrderService orderService;

    @Inject
    EventBus eventBus;


    @Override
    public Uni<OrderRecordProto> placeOrder(PlaceOrderProto request) {

        OrderCommand orderCommand = new OrderCommand(request.getOrderName(), MenuItem.values()[request.getMenuItemValue()]);
        LOGGER.debug("placing order {}", orderCommand);

        return Uni.createFrom().item(orderService.createOrder(orderCommand))
                .onItem().transform(orderRecord -> OrderRecordProto.newBuilder()
                            .setName(orderRecord.name())
                            .setId(orderRecord.id().intValue())
                            .setMenuItemValue(orderRecord.menuItem().ordinal())
                            .setOrderStatusValue(orderRecord.orderStatus().ordinal())
                            .setPaymentStatusValue(orderRecord.paymentStatus().ordinal())
                            .build())
                        .invoke(orderRecordProto -> {
                            eventBus.publish("order", orderRecordProto);
                        })
                        .map(orderRecordProto -> orderRecordProto);

    }

    @Override
    public Multi<OrderRecordProto> allOrders(Empty request) {

        return Multi.createFrom()
                .iterable(orderService.allOrders())
                .map(orderRecord -> OrderRecordProto.newBuilder()
                        .setName(orderRecord.name())
                        .setId(orderRecord.id().intValue())
                        .setMenuItemValue(orderRecord.menuItem().ordinal())
                        .setOrderStatusValue(orderRecord.orderStatus().ordinal())
                        .setPaymentStatusValue(orderRecord.paymentStatus().ordinal())
                        .build());
    }

    @ConsumeEvent
    public Uni<OrderRecordProto> consumeOrder(OrderRecordProto orderRecordProto) {
        return Uni.createFrom().item(orderRecordProto);
    }

    @Override
    public Multi<OrderRecordProto> inProgressOrders(Empty request) {
        return eventBus.<OrderRecordProto>consumer("order").bodyStream().toMulti();
    }
}
