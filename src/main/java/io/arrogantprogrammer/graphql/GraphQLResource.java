package io.arrogantprogrammer.graphql;

import io.arrogantprogrammer.OrderService;
import io.arrogantprogrammer.domain.*;
import io.smallrye.graphql.api.Subscription;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@GraphQLApi
@ApplicationScoped
public class GraphQLResource {

    static final Logger LOGGER = LoggerFactory.getLogger(GraphQLResource.class);

    BroadcastProcessor<OrderRecord> broadcastProcessor = BroadcastProcessor.create();

    @Inject
    OrderRepository orderRepository;

    @Inject
    OrderService orderService;

    @Query("allOrders")
    @Description("Get all orders placed in the current day")
    public List<OrderRecord> allOrders(@Name("name") String name, @Name("menuItem")MenuItem menuItem, @Name("orderStatus") OrderStatus orderStatus, @Name("paymentStats") PaymentStatus paymentStatus) {
        return orderService.orderQuery(
                new OrderParams.Builder()
                .withName(name)
                .withMenuItem(menuItem)
                .withOrderStatus(orderStatus)
                .withPaymentStatus(paymentStatus)
                .build());
    }

    @Mutation("placeOrder")
    @Description("Add a new order")
    public OrderRecord placeOrder(OrderCommand orderCommand){
        OrderRecord orderRecord = orderService.createOrder(orderCommand);
        broadcastProcessor.onNext(orderRecord);
        return orderRecord;
    }

    @Subscription
    @Description("In progress orders")
    public Multi<OrderRecord> inProgressOrders() {
        return broadcastProcessor;
    }
}
