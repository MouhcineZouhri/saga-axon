package com.mouhcine.orderservice.saga;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.example.command.ApproveOrderCommand;
import org.example.command.CreateTicketCommand;
import org.example.command.RejectOrderCommand;
import org.example.events.OrderCreatedEvent;
import org.example.events.TicketCreatedEvent;
import org.springframework.beans.BeanUtils;

import javax.inject.Inject;
import java.util.Random;
import java.util.UUID;

@Saga
@Slf4j
public class OrderSagaHandler {

    @Inject
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderCreatedEvent event){
        log.info("OrderCreatedEvent " + event);
        CreateTicketCommand command = new CreateTicketCommand();
        BeanUtils.copyProperties(event, command);
        command.setTicketId(UUID.randomUUID().toString());
        commandGateway.send(command);
    }
    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void on(TicketCreatedEvent event){
        log.info("TicketCreatedEvent" + event);
        int number = new Random().nextInt(10);
        if (number > 5){
            RejectOrderCommand command = new RejectOrderCommand(event.getOrderId());
            commandGateway.send(command);
        }else {
            ApproveOrderCommand command = new ApproveOrderCommand(event.getOrderId());
            commandGateway.send(command);
        }
    }
}

