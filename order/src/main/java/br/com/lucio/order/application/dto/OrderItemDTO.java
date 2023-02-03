package br.com.lucio.order.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemDTO {

    @JsonIgnore
    @JsonProperty(value = "id")
    private UUID id;
    private String description;
    private Integer quantity;
    private BigDecimal unitaryValue;
    private BigDecimal amount;
    private LocalDate expectedDeliveryDate;
}
