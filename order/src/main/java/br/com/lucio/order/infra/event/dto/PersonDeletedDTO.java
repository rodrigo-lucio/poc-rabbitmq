package br.com.lucio.order.infra.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDeletedDTO {

    private UUID idPersonDeleted;

}
