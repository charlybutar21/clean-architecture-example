package com.charly.demospringbootjsp.controller.contact;

import com.charly.demospringbootjsp.dto.contact.response.DetailContactResponse;
import com.charly.demospringbootjsp.presenter.contact.DetailContactPresenter;
import com.charly.demospringbootjsp.usecase.contact.detail.DetailContactInputBoundary;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
@AllArgsConstructor
public class DetailContactController {
    private final DetailContactInputBoundary useCase;
    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public DetailContactResponse getContactDetails(@PathVariable Long id) {
        DetailContactPresenter presenter = new DetailContactPresenter(modelMapper);
        useCase.execute(id, presenter);
        return presenter.getResponse();
    }

}
