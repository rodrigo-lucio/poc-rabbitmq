package br.com.lucio.order.domain.repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lucio.order.domain.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

}
