package io.arrogantprogrammer;

import io.arrogantprogrammer.domain.*;
import io.arrogantprogrammer.graphql.OrderParams;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class OrderService {

    static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Inject
    OrderRepository orderRepository;

    public List<OrderRecord> allOrders() {
        return orderRepository.listAll().stream().map(order -> {
            return new OrderRecord(order.getName(), order.getMenuItem(), order.getOrderStatus(), order.getPaymentStatus(), order.getId());
        }).toList();
    }
    @Transactional
    public OrderRecord createOrder(OrderCommand orderCommand) {
        LOGGER.debug("Received order for {}.", orderCommand);
        Order order = new Order(orderCommand.name(), orderCommand.menuItem(), OrderStatus.PENDING, PaymentStatus.PENDING);
        order.persist();
        LOGGER.debug("persisted order {}.", order);
        return new OrderRecord(order.getName(), order.getMenuItem(), order.getOrderStatus(), order.getPaymentStatus(), order.getId());
    }

    @Transactional
    public static void createOrderAsync(OrderCommand orderCommand) {
        LOGGER.debug("Received order for {}.", orderCommand);
        Order order = new Order(orderCommand.name(), orderCommand.menuItem(), OrderStatus.PENDING, PaymentStatus.PENDING);
        order.persist();
        LOGGER.debug("persisted order {}.", order);
    }

    @Transactional
    public static void updateOrderAsync(UpdateOrderCommand updateOrderCommand) {
        LOGGER.debug("updating order {}.", updateOrderCommand);
        Order order = Order.findById(updateOrderCommand.orderRecord());
        OrderRecord orderRecord = order.updateOrder(updateOrderCommand.orderRecord());
        LOGGER.debug("updated order {}.", orderRecord);
    }

    @Transactional
    public static OrderRecord updateOrder(UpdateOrderCommand updateOrderCommand) {
        LOGGER.debug("updating order {}.", updateOrderCommand);
        Order order = Order.findById(updateOrderCommand.orderRecord());
        OrderRecord orderRecord = order.updateOrder(updateOrderCommand.orderRecord());
        LOGGER.debug("updated order {}.", orderRecord);
        return orderRecord;
    }

    public List<OrderRecord> orderQuery(OrderParams orderParams) {
        if (orderParams.hasParams()) {
            return orderRepository.find(orderParams.toQueryString(), orderParams.toMap()).stream().map(order -> {
                return new OrderRecord(order.getName(), order.getMenuItem(), order.getOrderStatus(), order.getPaymentStatus(), order.getId());
            }).toList();
        }else{
            return allOrders();
        }
    }
}
