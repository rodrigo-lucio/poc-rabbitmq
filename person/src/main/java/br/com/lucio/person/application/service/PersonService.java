package br.com.lucio.person.application.service;

import br.com.lucio.person.application.dto.PersonDTO;
import br.com.lucio.person.application.exception.ResourceNotFoundException;
import br.com.lucio.person.domain.entity.Document;
import br.com.lucio.person.domain.entity.Person;
import br.com.lucio.person.domain.entity.PersonRepository;
import br.com.lucio.person.shared.translation.TranslationComponent;
import br.com.lucio.person.shared.translation.TranslationConstants;
import br.com.lucio.person.shared.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TranslationComponent translation;

    public PersonDTO getPerson(UUID id) {
        Person person = findPerson(id);
        return toPersonDto(person);
    }

    private Person findPerson(UUID id) {
        return personRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException(translation.getMessage(TranslationConstants.PERSON_NOT_FOUND_WITH_ID , id)));
    }

    @Transactional
    public PersonDTO create(PersonDTO personDTO) {
        Person person = modelMapper.map(personDTO, Person.class);
        person.setDocument(Document.of(personDTO.getDocument()));
        personRepository.saveAndFlush(person);
        return toPersonDto(person);
    }

    @Transactional
    public PersonDTO update(UUID id, PersonDTO personDTO) {
        Person person = findPerson(id);
        person.setDocument(Document.of(personDTO.getDocument()));
        Utils.copyProperties(toPersonEntity(personDTO), person, "id");
        personRepository.saveAndFlush(person);
        return toPersonDto(person);
    }

    @Transactional
    public PersonDTO updatePatch(UUID id, PersonDTO dto) {
        Person person = findPerson(id);
        Utils.copyNonNullAndIdNotProperties(toPersonEntity(dto), person);
        personRepository.saveAndFlush(person);
        return toPersonDto(person);
    }

    @Transactional
    public void delete(UUID id) {
        if(!personRepository.existsById(id)) {
            throw new ResourceNotFoundException(translation.getMessage(TranslationConstants.PERSON_NOT_FOUND_WITH_ID , id));
        }
        personRepository.deleteById(id);
        personRepository.flush();
    }

    private Person toPersonEntity(PersonDTO personDTO) {
        Person map = modelMapper.map(personDTO, Person.class);
        Document document = Document.of(personDTO.getDocument());
        map.setDocument(document);
        return map;
    }

    private PersonDTO toPersonDto(Person person) {
        PersonDTO map = modelMapper.map(person, PersonDTO.class);
        map.setDocument(person.getDocument().toString());
        return map;
    }

}
