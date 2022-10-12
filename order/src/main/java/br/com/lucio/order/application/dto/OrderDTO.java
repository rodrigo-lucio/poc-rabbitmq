package br.com.lucio.order.application.dto;

import br.com.lucio.order.domain.entity.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDTO {

    @JsonIgnore
    @JsonProperty(value = "id")
    private UUID id;

    @JsonIgnore
    @JsonProperty(value = "status")
    private Status status;

    private List<OrderItemDTO> items = new ArrayList<>();

}
