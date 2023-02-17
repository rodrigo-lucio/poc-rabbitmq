package br.com.lucio.order.application.dto;

import br.com.lucio.order.domain.entity.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderPaymentDTO {

    @JsonIgnore
    @JsonProperty(value = "id")
    private UUID id;

    private String creditCardNumber;
    private String cardHolderName;
    private Integer validityMonth;
    private Integer validityYear;
    private Integer securityCode;
    private BigDecimal amount;

    @JsonIgnore
    @JsonProperty(value = "status")
    private PaymentStatus status;

    @JsonIgnore
    @JsonProperty(value = "dateConfirmed")
    private LocalDateTime dateConfirmed;

}
