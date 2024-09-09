package com.charly.demospringbootjsp.controller.contact;

import com.charly.demospringbootjsp.dto.contact.request.CreateContactRequest;
import com.charly.demospringbootjsp.usecase.contact.create.CreateContactInputBoundary;
import com.charly.demospringbootjsp.usecase.contact.create.CreateContactRequestModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class CreateContactController {
    private final CreateContactInputBoundary useCase;
    private final ModelMapper modelMapper;

    @PostMapping("/contacts")
    public String createContact(@ModelAttribute CreateContactRequest request) {
        CreateContactRequestModel requestModel = modelMapper.map(request, CreateContactRequestModel.class);
        useCase.execute(requestModel);
        return "redirect:/contacts";
    }
}
