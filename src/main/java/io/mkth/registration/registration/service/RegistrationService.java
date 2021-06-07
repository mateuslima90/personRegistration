package io.mkth.registration.registration.service;

import io.mkth.registration.registration.model.ConfirmationToken;
import io.mkth.registration.registration.model.Person;
import io.mkth.registration.registration.model.PersonRole;
import io.mkth.registration.registration.model.RegistrationRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RegistrationService {

    private final PersonService personService;
    private final ConfirmationTokenService confirmationTokenService;

    public RegistrationService(PersonService personService, ConfirmationTokenService confirmationTokenService) {
        this.personService = personService;
        this.confirmationTokenService = confirmationTokenService;
    }

    public String register(RegistrationRequest request) {
        Person person = new Person(request.getUsername(),
                                   request.getFirstName(),
                                   request.getLastName(),
                                   request.getEmail(),
                                   request.getPassword(),
                                   PersonRole.USER);
        return generateTokenToVerifyEmail(personService.signUp(person));
    }

    public String generateTokenToVerifyEmail(Person person) {
        String tokenId = UUID.randomUUID().toString();
        ConfirmationToken token = new ConfirmationToken(tokenId,
                                                        LocalDateTime.now(),
                                                        LocalDateTime.now().plusMinutes(15),
                                                        person);
        return confirmationTokenService.save(token).getTokenId();
    }

    public String verifyEmail(String token) {
        ConfirmationToken cToken = confirmationTokenService
                .setConfirmDate(confirmationTokenService.getConfirmationToken(token));

        if(cToken != null){
            personService.enablePerson(cToken.getPerson().getId());
        }
        return "Confirmed";
    }
}
