package com.mouhcine.orderservice.domain;

import com.mouhcine.orderservice.aggregate.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class OrderEntity {
    @Id
    private String orderId;

    private String customerId;

    private String restaurantId;

    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL)
    private Set<OrderLineItemEntity> lineItems = new HashSet<>();

}

