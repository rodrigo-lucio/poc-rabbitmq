package br.com.lucio.order.application.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.lucio.order.domain.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private UUID id;
    private Status status;
    private List<OrderItemDTO> items = new ArrayList<>();

}
