package io.arrogantprogrammer.graphql;

import io.arrogantprogrammer.domain.MenuItem;
import io.arrogantprogrammer.domain.OrderStatus;
import io.arrogantprogrammer.domain.PaymentStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record OrderParams(Optional<String> name, Optional<MenuItem> menuItem, Optional<OrderStatus> orderStatus, Optional<PaymentStatus> paymentStatus) {

    public boolean hasParams() {
        if(name.isPresent() || menuItem.isPresent() || orderStatus.isPresent() || paymentStatus.isPresent()) {
            return true;
        }else {
            return false;
        }
    }
    public Builder builder() {
        return new Builder();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> params = new HashMap<>();
        if (this.name().isPresent()) {
            params.put("name", this.name().get());
        }
        if (this.menuItem().isPresent()) {
            params.put("menuItem", this.menuItem().get());
        }
        if (this.orderStatus().isPresent()) {
            params.put("orderStatus", this.orderStatus().get());
        }
        if (this.paymentStatus().isPresent()) {
            params.put("paymentStatus", this.paymentStatus().get());
        }
        return params;
    }

    public static class Builder {

        String name;
        MenuItem menuItem;
        OrderStatus orderStatus;
        PaymentStatus paymentStatus;

        public OrderParams build() {
            return new OrderParams(Optional.ofNullable(name), Optional.ofNullable(menuItem), Optional.ofNullable(orderStatus), Optional.ofNullable(paymentStatus));
        }
        public Builder() {
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }
        public Builder withMenuItem(MenuItem menuItem) {
            this.menuItem = menuItem;
            return this;
        }
        public Builder withOrderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }
        public Builder withPaymentStatus(PaymentStatus paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }
    }

    public String toQueryString() {
        Map<String, Object> params = new HashMap<>();
        if (this.name().isPresent()) {
            params.put("name", this.name().get());
        }
        if (this.menuItem().isPresent()) {
            params.put("menuItem", this.menuItem().get());
        }
        if (this.orderStatus().isPresent()) {
            params.put("orderStatus", this.orderStatus().get());
        }
        if (this.paymentStatus().isPresent()) {
            params.put("paymentStatus", this.paymentStatus().get());
        }
        StringBuilder sb = new StringBuilder();
        params.keySet().forEach(k -> {
            if (sb.length() > 1) {
                sb.append("and ");
                sb.append(k);
                sb.append(" = :");
                sb.append(k);
            }else {
                sb.append(k);
                sb.append(" = :");
                sb.append(k);
            }
        });
        return sb.toString();
    }
}
