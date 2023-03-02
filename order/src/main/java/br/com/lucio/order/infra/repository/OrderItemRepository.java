package br.com.lucio.order.infra.repository;


import br.com.lucio.order.application.dto.OrderItemDTO;
import br.com.lucio.order.domain.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    Page<OrderItem> findAll(Specification<OrderItemDTO> specification, Pageable pageable);

    Optional<OrderItem> findByIdAndOrderId(UUID id, UUID orderId);
}
