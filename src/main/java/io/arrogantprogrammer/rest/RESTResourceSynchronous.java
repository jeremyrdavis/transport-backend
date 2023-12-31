package io.arrogantprogrammer.rest;

import io.arrogantprogrammer.OrderService;
import io.arrogantprogrammer.domain.OrderCommand;
import io.arrogantprogrammer.domain.OrderRecord;
import io.arrogantprogrammer.domain.UpdateOrderCommand;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/rest-synchronous")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RESTResourceSynchronous {
    static final Logger LOGGER = LoggerFactory.getLogger(RESTResourceSynchronous.class);
    @Inject
    OrderService orderService;

    @POST
    public OrderRecord placeOrder(OrderCommand orderCommand) {
        LOGGER.debug("Received order for {}.", orderCommand);
        OrderRecord orderRecord = orderService.createOrder(orderCommand);
        return orderRecord;
    }

    @PUT
    public OrderRecord updateOrder(UpdateOrderCommand updateOrderCommand) {
        LOGGER.debug("Received order for {}.", updateOrderCommand);
        OrderRecord orderRecord = orderService.updateOrder(updateOrderCommand);
        return orderRecord;
    }

    @GET
    public List<OrderRecord> allOrders() {
        return orderService.allOrders();
    }

}
