package org.example.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.example.common.OrderLineItem;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class CreateTicketCommand {
    @TargetAggregateIdentifier
    private String ticketId;
    private String orderId;
    private String customerId;
    private String restaurantId;
    private List<OrderLineItem> lineItems;
}
