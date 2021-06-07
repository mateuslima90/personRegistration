package io.mkth.registration.registration.service;

import io.mkth.registration.registration.exception.DuplicatedPersonException;
import io.mkth.registration.registration.model.Person;
import io.mkth.registration.registration.repository.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService implements UserDetailsService {

    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository, BCryptPasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personRepository.findByUsername(username);
    }

    public Person signUp(Person person) {
        verifyUsernameExists(person.getUsername());
        verifyEmailExists(person.getEmail());

        person.setPassword(passwordEncoder.encode(person.getPassword()));

        return personRepository.save(person);
    }

    public Person enablePerson(Long id) {
        Optional<Person> person = personRepository.findById(id);
        person.ifPresent(p -> p.setEnable(true));
        return personRepository.save(person.get());
    }

    private void verifyUsernameExists(String username) {
        Person person = personRepository.findByUsername(username);

        if(person != null){
            throw new DuplicatedPersonException(String.format("Duplicated value %s", username));
        }
    }

    private void verifyEmailExists(String email) {
        Person person = personRepository.findByEmail(email);

        if(person != null){
            throw new DuplicatedPersonException(String.format("Duplicated value %s", email));
        }
    }
}
