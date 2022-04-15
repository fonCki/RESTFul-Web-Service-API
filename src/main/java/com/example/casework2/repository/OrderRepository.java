package com.example.casework2.repository;

import com.example.casework2.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByDescriptionContainsIgnoreCase(String search);
    List<Order> findAllByDeliveredTrue();

}
