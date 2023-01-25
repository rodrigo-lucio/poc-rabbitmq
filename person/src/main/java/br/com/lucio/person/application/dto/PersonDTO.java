package br.com.lucio.person.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class PersonDTO {

    @JsonIgnore
    @JsonProperty(value = "id")
    private UUID id;
    private String name;
    private String document;
    private String email;

}
