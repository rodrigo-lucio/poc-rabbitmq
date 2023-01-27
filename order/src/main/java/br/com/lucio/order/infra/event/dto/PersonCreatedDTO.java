package br.com.lucio.order.infra.event.dto;

import br.com.lucio.order.application.dto.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonCreatedDTO {

    private PersonDTO person;

}
