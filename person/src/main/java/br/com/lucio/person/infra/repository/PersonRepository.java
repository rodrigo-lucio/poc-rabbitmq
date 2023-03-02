package br.com.lucio.person.infra.repository;

import br.com.lucio.person.domain.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

}
