package org.example.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.common.OrderLineItem;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class OrderCreatedEvent {
    private String orderId;
    private String customerId;
    private String restaurantId;
    private List<OrderLineItem> lineItems;
}
