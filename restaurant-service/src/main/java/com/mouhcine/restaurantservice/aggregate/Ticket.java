package com.mouhcine.restaurantservice.aggregate;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.command.CreateTicketCommand;
import org.example.common.OrderLineItem;
import org.example.events.OrderCreatedEvent;
import org.example.events.TicketCreatedEvent;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Aggregate
@Slf4j
public class Ticket {
    @AggregateIdentifier
    private String ticketId;
    private String orderId;
    private String restaurantId;

    private String customerId;
    @AggregateMember
    private List<OrderLineItem> lineItems = new ArrayList<>();

    public Ticket(){}

    @CommandHandler
    public Ticket(CreateTicketCommand command) {
        log.info("CreateTicketCommand " +command);
        TicketCreatedEvent event = new TicketCreatedEvent();
        BeanUtils.copyProperties(command , event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(TicketCreatedEvent event){
        log.info("TicketCreatedEvent " + event);
        this.ticketId = event.getTicketId();
        this.customerId =event.getCustomerId();
        this.orderId = event.getOrderId();
        this.restaurantId = event.getRestaurantId();
        this.lineItems  = event.getLineItems();
    }

}
