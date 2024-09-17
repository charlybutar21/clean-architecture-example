package com.charly.demospringbootjsp.usecase.contact.update;

import com.charly.demospringbootjsp.entity.Contact;
import com.charly.demospringbootjsp.exception.ContactNotFoundException;
import com.charly.demospringbootjsp.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateContactUseCase implements UpdateContactInputBoundary{

    private final ContactRepository contactRepository;

    @Override
    public void execute(UpdateContactRequestModel requestModel) {
        Contact contact = contactRepository.findById(requestModel.getId())
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with id: " + requestModel.getId()));

        Optional.ofNullable(requestModel.getName()).ifPresent(contact::setName);
        Optional.ofNullable(requestModel.getEmail()).ifPresent(contact::setEmail);
        Optional.ofNullable(requestModel.getPhone()).ifPresent(contact::setPhone);
        Optional.ofNullable(requestModel.getCountry()).ifPresent(contact::setCountry);

        contactRepository.save(contact);
    }
}
