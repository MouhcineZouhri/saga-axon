package com.mouhcine.orderservice.web;

import com.mouhcine.orderservice.aggregate.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.AggregateMember;
import org.example.common.OrderLineItem;

import java.util.ArrayList;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class OrderRequest {
    private String customerId;
    private String restaurantId;
    private List<OrderLineItem> lineItems= new ArrayList<>();
}
