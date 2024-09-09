package com.charly.demospringbootjsp.usecase.contact.create;

import com.charly.demospringbootjsp.entity.Contact;
import com.charly.demospringbootjsp.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateContactUseCase implements CreateContactInputBoundary{

    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    @Override
    public void execute(CreateContactRequestModel requestModel) {
        Contact newContact = modelMapper.map(requestModel, Contact.class);
        contactRepository.save(newContact);
    }

}
