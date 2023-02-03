package br.com.lucio.order.application.service;

import br.com.lucio.order.application.exception.ResourceNotFoundException;
import br.com.lucio.order.domain.entity.Person;
import br.com.lucio.order.domain.repository.OrderRepository;
import br.com.lucio.order.domain.repository.PersonRepository;
import br.com.lucio.order.infra.event.dto.PersonCrudEventDTO;
import br.com.lucio.order.shared.translation.TranslationComponent;
import br.com.lucio.order.shared.translation.TranslationConstants;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.lucio.order.application.dto.PersonDTO;

import java.util.UUID;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TranslationComponent translation;

    @Transactional
    public void crudEvent(PersonCrudEventDTO personCrudEventDTO) {
        switch (personCrudEventDTO.getType()) {
            case CREATE -> this.create(personCrudEventDTO.getPerson());
            case UPDATE -> this.update(personCrudEventDTO.getPerson());
            case DELETE -> this.delete(personCrudEventDTO.getPerson().getId());
        }
    }

    @Transactional
    public PersonDTO create(PersonDTO personDTO) {
        Person person = modelMapper.map(personDTO, Person.class);
        personRepository.saveAndFlush(person);
        return modelMapper.map(person, PersonDTO.class);
    }

    @Transactional
    public PersonDTO update(PersonDTO personDTO) {
        Person person = modelMapper.map(personDTO, Person.class);
        personRepository.saveAndFlush(person);
        return modelMapper.map(person, PersonDTO.class);
    }

    @Transactional
    public void delete(UUID id) {
        if (orderRepository.existsByPersonId(id)) {
            Person person = findPerson(id);
            person.setDeleted(Boolean.TRUE);
            personRepository.saveAndFlush(person);
            return;
        }
        personRepository.deleteById(id);
        personRepository.flush();
    }

    private Person findPerson(UUID id) {
        return personRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(translation.getMessage(TranslationConstants.PERSON_NOT_FOUND_WITH_ID, id)));
    }


}
