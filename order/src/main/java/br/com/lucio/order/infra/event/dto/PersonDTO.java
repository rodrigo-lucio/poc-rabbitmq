package br.com.lucio.order.infra.event.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private UUID id;
    private String name;
    private String document;
    private String email;

}
