package br.com.lucio.person.infra.event.dto;

import br.com.lucio.person.application.dto.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PersonDeletedDTO {

    private UUID idPersonDeleted;

}
