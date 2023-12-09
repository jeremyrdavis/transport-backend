package io.arrogantprogrammer.graphql;

import io.arrogantprogrammer.OrderService;
import io.arrogantprogrammer.domain.Order;
import io.arrogantprogrammer.domain.OrderCommand;
import io.arrogantprogrammer.domain.OrderRecord;
import io.arrogantprogrammer.domain.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
    public List<OrderRecord> getAllFilms() {
        return orderRepository.listAll().stream().map(order -> {
            return new OrderRecord(order.getName(), order.getMenuItem(), order.getOrderStatus(), order.getPaymentStatus(), order.getId());
        }).toList();
    }

    @Mutation("placeOrder")
    @Description("Add a new order")
    public OrderRecord placeOrder(OrderCommand orderCommand){
        return orderService.createOrder(orderCommand);
    }

}
