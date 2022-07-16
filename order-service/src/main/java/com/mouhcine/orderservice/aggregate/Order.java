package com.mouhcine.orderservice.aggregate;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.command.ApproveOrderCommand;
import org.example.command.CreateOrderCommand;
import org.example.command.RejectOrderCommand;
import org.example.common.OrderLineItem;
import org.example.events.OrderApprovedEvent;
import org.example.events.OrderCreatedEvent;
import org.example.events.OrderRejectedEvent;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Aggregate
@Slf4j
public class Order {
    @AggregateIdentifier
    private String orderId;

    private String customerId;

    private String restaurantId;

    private OrderStatus orderStatus;
    @AggregateMember
    private List<OrderLineItem> lineItems= new ArrayList<>();

    public Order(){}

    @CommandHandler
    public Order(CreateOrderCommand command){
        log.info("CreateOrderCommand " + command);
        OrderCreatedEvent event = new OrderCreatedEvent();
        BeanUtils.copyProperties(command , event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event){
        this.customerId =event.getCustomerId();
        this.orderId = event.getOrderId();
        this.restaurantId = event.getRestaurantId();
        this.lineItems  = event.getLineItems();
        this.orderStatus = OrderStatus.CREATED;
    }

    @CommandHandler
    public Order(ApproveOrderCommand command){
        log.info("ApproveOrderCommand " + command);
        OrderApprovedEvent event = new OrderApprovedEvent();
        event.setOrderId(command.getOrderId());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(OrderApprovedEvent event){
        log.info("OrderApprovedEvent " + event);
        this.orderStatus = OrderStatus.APPROVED;
    }

    @CommandHandler
    public Order(RejectOrderCommand command){
        log.info("RejectOrderCommand " + command);
        OrderRejectedEvent event = new OrderRejectedEvent();
        event.setOrderId(command.getOrderId());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(OrderRejectedEvent event){
        log.info("OrderRejectedEvent " + event);
        this.orderStatus = OrderStatus.REJECTED;
    }

}
