package br.com.lucio.order.infra.repository;


import br.com.lucio.order.domain.entity.OrderPayment;
import br.com.lucio.order.domain.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface OrderPaymentRepository extends JpaRepository<OrderPayment, UUID> {
    @Modifying
    @Transactional
    @Query("update OrderPayment payment set payment.status = :status where payment.id = :id")
    void updateStatusPayment(@Param("status") PaymentStatus status, @Param("id") UUID id);

}
