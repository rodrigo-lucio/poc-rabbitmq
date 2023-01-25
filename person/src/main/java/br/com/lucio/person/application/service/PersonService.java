package br.com.lucio.person.application.service;

import br.com.lucio.person.application.dto.PersonDTO;
import br.com.lucio.person.domain.entity.Document;
import br.com.lucio.person.domain.entity.Person;
import br.com.lucio.person.domain.entity.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public PersonDTO create(PersonDTO personDTO) {
        Person person = modelMapper.map(personDTO, Person.class);
        person.setDocument(Document.of(personDTO.getDocument()));
        personRepository.saveAndFlush(person);
        return modelMapper.map(person, PersonDTO.class);
    }


}
