package com.mouhcine.orderservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineItemRepository extends JpaRepository<OrderLineItemEntity , Long> {
}
