package br.com.lucio.order.application.dto;

import br.com.lucio.order.domain.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProcessedDTO {

    private UUID id;
    private PaymentStatus status;
    private LocalDateTime dateConfirmed;

}
