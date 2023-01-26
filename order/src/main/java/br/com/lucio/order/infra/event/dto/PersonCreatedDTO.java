package br.com.lucio.order.infra.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonCreatedDTO {

    private PersonDTO personDTO;

}
