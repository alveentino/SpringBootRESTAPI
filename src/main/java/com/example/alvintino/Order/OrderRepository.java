package com.example.alvintino.Order;

import org.springframework.data.jpa.repository.JpaRepository;

interface OrderRepository extends JpaRepository<OrderInfo,Integer> {
}