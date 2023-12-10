package io.arrogantprogrammer.domain;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    static final Logger LOGGER = LoggerFactory.getLogger(OrderRepository.class);

    public Uni<List<OrderRecord>> listAllUni() {
        return Uni.createFrom().item(listAll()).map(orders -> {
            LOGGER.debug("allOrders");
            return orders.stream().map(order -> {
                return new OrderRecord(order.getName(), order.getMenuItem(), order.getOrderStatus(), order.getPaymentStatus(), order.getId());
            }).toList();
        }).runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

}