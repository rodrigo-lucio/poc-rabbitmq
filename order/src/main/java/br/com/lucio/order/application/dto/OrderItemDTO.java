package br.com.lucio.order.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class OrderItemDTO {

    @JsonIgnore
    @JsonProperty(value = "id")
    private UUID id;
    private Integer quantity;
    private String description;

    @JsonProperty(value = "expectedDeliveryDate")
    private LocalDate expectedDeliveryDate;
}
