package br.com.lucio.order.infra.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import br.com.lucio.order.application.dto.PersonDTO;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class PersonCrudEventDTO {

    private PersonDTO person;
    private EventType type;

}
