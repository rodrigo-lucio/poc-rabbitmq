package br.com.lucio.payment.infra.repository;


import br.com.lucio.payment.domain.entity.OrderPayment;
import br.com.lucio.payment.domain.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface OrderPaymentRepository extends JpaRepository<OrderPayment, UUID> {

    boolean existsByIdAndStatus(UUID id, PaymentStatus status);

    @Modifying
    @Transactional
    @Query("update OrderPayment payment set payment.status = :status where payment.id = :id")
    void updateStatusPayment(@Param("status") PaymentStatus status, @Param("id") UUID id);

    default void updateToProcessing(UUID id) {
        updateStatusPayment(PaymentStatus.PROCESSING, id);
    }

    default void updateToReceived(UUID id) {
        updateStatusPayment(PaymentStatus.RECEIVED, id);
    }
    @Modifying
    @Transactional
    @Query("update OrderPayment payment set payment.status = :status, payment.dateConfirmed = :dateConfirmed where payment.id = :id")
    void updateStatusAndDateConfirmed(@Param("status") PaymentStatus status,
                           @Param("dateConfirmed") LocalDateTime dateConfirmed,
                           @Param("id") UUID id);
    default void updateToUnauthorized(UUID id) {
        updateStatusPayment(PaymentStatus.UNAUTHORIZED, id);
    }

    default void updateToErro(UUID id) {
        updateStatusPayment(PaymentStatus.ERROR, id);
    }


    default boolean notExistsByIdAndStatusConfirmed(UUID id) {
        return !existsByIdAndStatus(id, PaymentStatus.CONFIRMED);
    }
}
