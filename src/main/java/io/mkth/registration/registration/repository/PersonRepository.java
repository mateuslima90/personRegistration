package io.mkth.registration.registration.repository;

import io.mkth.registration.registration.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByUsername(String username);

    Person findByEmail(String email);
}
