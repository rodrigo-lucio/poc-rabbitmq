package br.com.lucio.order.application.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class OrderItemDTO {

    @JsonIgnore
    @JsonProperty(value = "id")
    private UUID id;
    private Integer quantity;
    private String description;
}
