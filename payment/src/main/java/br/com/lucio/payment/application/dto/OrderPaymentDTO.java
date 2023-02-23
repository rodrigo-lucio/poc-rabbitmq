package br.com.lucio.payment.application.dto;

import br.com.lucio.payment.domain.entity.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderPaymentDTO {

    private UUID id;
    private String creditCardNumber;
    private String cardHolderName;
    private Integer validityMonth;
    private Integer validityYear;
    private Integer securityCode;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime dateConfirmed;

}
