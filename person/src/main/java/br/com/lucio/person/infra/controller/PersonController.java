package br.com.lucio.person.infra.controller;

import br.com.lucio.person.application.dto.PersonDTO;
import br.com.lucio.person.application.service.PersonService;
import br.com.lucio.person.infra.event.dto.PersonCreatedDTO;
import br.com.lucio.person.infra.event.dto.PersonDeletedDTO;
import br.com.lucio.person.infra.event.dto.PersonUpdatedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> get(@PathVariable UUID id) {
        PersonDTO person = personService.getPerson(id);
        return ResponseEntity.ok(person);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody @Valid PersonDTO dto, UriComponentsBuilder uriBuilder) {
        PersonDTO personCreated = personService.create(dto);
        URI uri = uriBuilder.path("/person/{id}").buildAndExpand(personCreated.getId()).toUri();
        publisher.publishEvent(new PersonCreatedDTO(personCreated));
        return ResponseEntity.created(uri).body(personCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> update(@PathVariable @NotNull UUID id, @RequestBody @Valid PersonDTO dto) {
        PersonDTO personUpdated = personService.update(id, dto);
        publisher.publishEvent(new PersonUpdatedDTO(personUpdated));
        return ResponseEntity.ok(personUpdated);
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<PersonDTO> updatePatch(@PathVariable @NotNull UUID id, @RequestBody PersonDTO dto) {
//        PersonDTO personUpdated = personService.updatePatch(id, dto);
//        publisher.publishEvent(new PersonUpdatedDTO(personUpdated));
//        return ResponseEntity.ok(personUpdated);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull UUID id) {
        personService.delete(id);
        publisher.publishEvent(new PersonDeletedDTO(id));
        return ResponseEntity.noContent().build();
    }

}
