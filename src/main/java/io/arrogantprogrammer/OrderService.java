package io.arrogantprogrammer;

import io.arrogantprogrammer.domain.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class OrderService {
    static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    @Transactional
    public static OrderRecord createOrder(OrderCommand orderCommand) {
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
}
