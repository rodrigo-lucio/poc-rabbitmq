package br.com.lucio.person.infra.event.dto;

import br.com.lucio.person.application.dto.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonUpdatedDTO {

    private PersonDTO personDTO;

}
