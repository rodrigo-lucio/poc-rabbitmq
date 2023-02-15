package br.com.lucio.order.application.dto;

import br.com.lucio.order.domain.entity.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    @JsonIgnore
    @JsonProperty(value = "id")
    private UUID id;

    @JsonIgnore
    @JsonProperty(value = "status")
    private Status status;

    @JsonProperty(value = "expectedDeliveryDate")
    private LocalDate expectedDeliveryDate;

    @JsonProperty(value = "person")
    private PersonDTO person;

    private BigDecimal amount;

    private List<OrderItemDTO> items = new ArrayList<>();

    private List<OrderPaymentDTO> payments = new ArrayList<>();

}
