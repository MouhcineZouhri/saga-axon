package org.example.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.common.OrderLineItem;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class TicketCreatedEvent {
    private String ticketId;
    private String orderId;
    private String customerId;
    private String restaurantId;
    private List<OrderLineItem> lineItems;
}
