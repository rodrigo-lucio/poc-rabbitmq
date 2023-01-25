package br.com.lucio.person.infra.exception.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvalidFieldDTO {

    private String field;
    private String message;

}
