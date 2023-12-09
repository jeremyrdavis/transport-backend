package io.arrogantprogrammer.rest;

import io.arrogantprogrammer.OrderService;
import io.arrogantprogrammer.domain.OrderCommand;
import io.arrogantprogrammer.domain.UpdateOrderCommand;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/rest-asynchronous")
public class RESTResourceAsynchronous {

    static final Logger LOGGER = LoggerFactory.getLogger(RESTResourceSynchronous.class);
    @Inject
    OrderService orderService;

    @POST
    public Response placeOrder(OrderCommand orderCommand) {
        LOGGER.debug("Received order for {}.", orderCommand);
        OrderService.createOrderAsync(orderCommand);
        return Response.accepted().build();
    }

    @PUT
    public Response updateOrder(UpdateOrderCommand updateOrderCommand) {
        LOGGER.debug("Received order for {}.", updateOrderCommand);
        OrderService.updateOrderAsync(updateOrderCommand);
        return Response.accepted().build();
    }
}
