package com.mouhcine.orderservice.web;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.command.CreateOrderCommand;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders/")
@RequiredArgsConstructor
public class OrderController {
    private final CommandGateway commandGateway;

    @PostMapping
    public void createOrder(@RequestBody OrderRequest orderRequest){
        CreateOrderCommand command = new CreateOrderCommand();
        BeanUtils.copyProperties(orderRequest , command);
        command.setOrderId(UUID.randomUUID().toString());
        commandGateway.send(command);
    }

}
