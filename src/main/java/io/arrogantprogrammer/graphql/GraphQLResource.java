package io.arrogantprogrammer.graphql;

import io.arrogantprogrammer.OrderService;
import io.arrogantprogrammer.domain.*;
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
        return orderService.createOrder(orderCommand);
    }

}
