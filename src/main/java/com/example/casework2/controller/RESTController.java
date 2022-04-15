package com.example.casework2.controller;

import com.example.casework2.model.Order;
import com.example.casework2.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RESTController {

    @Autowired
    private OrderRepository orderRepository;

    //Get all orders or a specific order within the title
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam(required = false) String search) {
        List<Order> orders = new ArrayList<>();
        try {
            if (search == null) {
                orderRepository.findAll().forEach(order -> orders.add(order));
            } else {
                orderRepository.findAllByDescriptionContainsIgnoreCase(search).forEach(order -> orders.add(order));;
            }
            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Get an order by id
    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable("id") long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return new ResponseEntity<>(order.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Create an order
    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order o) {
        try {
            Order order = orderRepository.save(o);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Update an order
    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") long id, @RequestBody Order o) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            Order order1 = order.get();
            order1.setDescription(o.getDescription());
            order1.setAmount(o.getAmount());
            order1.setDelivered(o.isDelivered());
            return new ResponseEntity<>(orderRepository.save(order1), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Delete and order
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable("id") long id) {
        try {
            orderRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Find all with delivery
    @GetMapping("/orders/delivery")
    public ResponseEntity<List<Order>> findWithDelivery() {
        List<Order> orders = new ArrayList<Order>();
        try {
            orderRepository.findAllByDeliveredTrue().forEach(order -> orders.add(order));
            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders,HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
