package com.mouhcine.orderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class OrderLineItemEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productId;
    private Integer quantity;

    @ManyToOne
    private OrderEntity order;
}
