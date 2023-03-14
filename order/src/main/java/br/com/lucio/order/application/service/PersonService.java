package br.com.lucio.order.application.service;

import br.com.lucio.order.application.dto.PersonDTO;
import br.com.lucio.order.application.exception.ResourceNotFoundException;
import br.com.lucio.order.domain.entity.Person;
import br.com.lucio.order.infra.repository.OrderRepository;
import br.com.lucio.order.infra.repository.PersonRepository;
import br.com.lucio.order.infra.event.dto.PersonCrudEventDTO;
import br.com.lucio.order.shared.translation.TranslationComponent;
import br.com.lucio.order.shared.translation.TranslationConstants;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    private final TranslationComponent translation;

    public PersonService(PersonRepository personRepository, OrderRepository orderRepository, ModelMapper modelMapper, TranslationComponent translation) {
        this.personRepository = personRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.translation = translation;
    }

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

    public Person findPerson(UUID id) {
        return personRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(translation.getMessage(TranslationConstants.PERSON_NOT_FOUND_WITH_ID, id)));
    }


}
