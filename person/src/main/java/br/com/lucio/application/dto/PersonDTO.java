package br.com.lucio.application.dto;

import br.com.lucio.domain.entity.Document;
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
