package com.example.casework2.controller;

import com.example.casework2.model.Order;
import com.example.casework2.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/makeOrder")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/{id}")
    public ResponseEntity<Order> makeAnOrder(@PathVariable("id") long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            rabbitTemplate.convertAndSend("fanout", "", order.get().asJson());
            return new ResponseEntity<>(order.get(), HttpStatus.OK);
        }
    }
}
