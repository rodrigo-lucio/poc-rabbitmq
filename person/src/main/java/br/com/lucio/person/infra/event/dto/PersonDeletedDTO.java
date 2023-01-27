package br.com.lucio.person.infra.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PersonDeletedDTO {

    private UUID id;

}
