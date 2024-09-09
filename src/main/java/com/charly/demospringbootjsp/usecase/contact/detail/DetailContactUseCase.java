package com.charly.demospringbootjsp.usecase.contact.detail;

import com.charly.demospringbootjsp.entity.Contact;
import com.charly.demospringbootjsp.exception.ContactNotFoundException;
import com.charly.demospringbootjsp.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DetailContactUseCase implements DetailContactInputBoundary {
    private ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    @Override
    public void execute(Long id, DetailContactOutputBoundary presenter) {
        Optional<Contact> contactOptional = contactRepository.findById(id);

        if (contactOptional.isEmpty()) {
            throw new ContactNotFoundException("Contact not found with id: " + id);
        }

        Contact contact = contactOptional.get();
        presenter.present(modelMapper.map(contact, DetailContactResponseModel.class));
    }
}
