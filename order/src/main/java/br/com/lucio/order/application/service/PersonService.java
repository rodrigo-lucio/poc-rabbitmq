package br.com.lucio.order.application.service;

import br.com.lucio.order.domain.entity.Person;
import br.com.lucio.order.domain.repository.PersonRepository;
import br.com.lucio.order.infra.event.dto.PersonCrudEventDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.lucio.order.application.dto.PersonDTO;

import java.util.UUID;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ModelMapper modelMapper;

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
        personRepository.deleteById(id);
        personRepository.flush();
    }

    @Transactional
    public void crudEvent(PersonCrudEventDTO personCrudEventDTO) {
        switch (personCrudEventDTO.getType()) {
            case CREATE -> this.create(personCrudEventDTO.getPerson());
            case UPDATE -> this.update(personCrudEventDTO.getPerson());
            case DELETE -> this.delete(personCrudEventDTO.getPerson().getId());
        }
    }
}
