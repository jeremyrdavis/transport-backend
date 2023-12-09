package io.arrogantprogrammer.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;


@Entity @Table(name = "COFFEE_ORDER")
public class Order extends PanacheEntity {

    String name;

    MenuItem menuItem;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus;

    public OrderRecord updateOrder(OrderRecord orderRecord) {
        this.orderStatus = orderRecord.orderStatus();
        this.paymentStatus = orderRecord.paymentStatus();
        this.persist();
        return new OrderRecord(this.name, this.menuItem, this.orderStatus, this.paymentStatus, this.id);
    }


    public Order() {
    }
    public Order(String name, MenuItem MenuItem, OrderStatus OrderStatus, PaymentStatus PaymentStatus) {
        this.name = name;
        this.menuItem = MenuItem;
        this.orderStatus = OrderStatus;
        this.paymentStatus = PaymentStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "name='" + name + '\'' +
                ", menuItem=" + menuItem +
                ", orderStatus=" + orderStatus +
                ", paymentStatus=" + paymentStatus +
                ", id=" + id +
                '}';
    }

    public String getName() {
        return name;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Long getId() {
        return id;
    }

}
