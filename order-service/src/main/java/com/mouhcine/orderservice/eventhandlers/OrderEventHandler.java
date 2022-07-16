package com.mouhcine.orderservice.eventhandlers;

import com.mouhcine.orderservice.aggregate.OrderStatus;
import com.mouhcine.orderservice.domain.OrderEntity;
import com.mouhcine.orderservice.domain.OrderLineItemEntity;
import com.mouhcine.orderservice.domain.OrderLineItemRepository;
import com.mouhcine.orderservice.domain.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.example.common.OrderLineItem;
import org.example.events.OrderApprovedEvent;
import org.example.events.OrderCreatedEvent;
import org.example.events.OrderRejectedEvent;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventHandler {

    private final OrderJpaRepository orderRepository;

    private final OrderLineItemRepository lineItemRepository;

    @EventHandler
    public void on(OrderCreatedEvent event){
        OrderEntity order = new OrderEntity();
        BeanUtils.copyProperties(event , order);
        order = orderRepository.save(order);

        for (OrderLineItem lineItem : event.getLineItems()){
            OrderLineItemEntity orderLineItem = new OrderLineItemEntity();
            BeanUtils.copyProperties(lineItem , orderLineItem);
            orderLineItem.setOrder(order);

            order = orderRepository.save(order);
            lineItemRepository.save(orderLineItem);
        }
    }

    @EventHandler
    public void on(OrderApprovedEvent event){
        OrderEntity order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order Not Found"));

        order.setOrderStatus(OrderStatus.APPROVED);

        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderRejectedEvent event){
        OrderEntity order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order Not Found"));

        order.setOrderStatus(OrderStatus.REJECTED);

        orderRepository.save(order);
    }

}
