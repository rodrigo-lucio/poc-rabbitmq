package br.com.lucio.order.application.dto;

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
