package br.com.lucio.order.application.validator;

import br.com.lucio.order.application.dto.PersonDTO;
import br.com.lucio.order.application.exception.ResourceNotFoundException;
import br.com.lucio.order.application.exception.ServiceException;
import br.com.lucio.order.application.service.PersonService;
import br.com.lucio.order.domain.entity.Person;
import br.com.lucio.order.shared.translation.TranslationComponent;
import br.com.lucio.order.shared.translation.TranslationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PersonValidator {

    @Autowired
    private PersonService personService;

    @Autowired
    private TranslationComponent translation;

    public Person validate(PersonDTO personDTO) {
        if (Objects.isNull(personDTO) || Objects.isNull(personDTO.getId())) {
            throw new ResourceNotFoundException(translation.getMessage(TranslationConstants.PERSON_CANNOT_BE_NULL));
        }

        Person person = personService.findPerson(personDTO.getId());
        validateActive(person);
        return person;
    }

    private void validateActive(Person person) {
        if (Boolean.FALSE.equals(person.getActive())) {
            throw new ServiceException(translation.getMessage(TranslationConstants.PERSON_IS_NOT_ACTIVE));
        }
    }

}
