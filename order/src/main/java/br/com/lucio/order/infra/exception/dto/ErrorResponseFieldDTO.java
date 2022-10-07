package br.com.lucio.order.infra.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class ErrorResponseFieldDTO {

    private String message;
    private List<InvalidFieldDTO> invalidFields;

    public static ErrorResponseFieldDTO buildMessagesInvalidFields(Set<ConstraintViolation<?>> constraintViolations) {
        List<InvalidFieldDTO> mensagens = new ArrayList<>();
        constraintViolations.forEach(fieldError -> {
            mensagens.add(InvalidFieldDTO.builder()
                    .field(fieldError.getPropertyPath().toString())
                    .message(fieldError.getMessage())
                    .build());
        });
        return new ErrorResponseFieldDTO("Invalid fields", mensagens);
    }

}
