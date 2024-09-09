package com.charly.demospringbootjsp.controller.contact;

import com.charly.demospringbootjsp.presenter.contact.ListContactPresenter;
import com.charly.demospringbootjsp.dto.contact.response.ListContactResponse;
import com.charly.demospringbootjsp.usecase.contact.list.ListContactInputBoundary;
import com.charly.demospringbootjsp.usecase.contact.list.ListContactRequestModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/contacts")
@AllArgsConstructor
public class ListContactController {
    private final ListContactInputBoundary useCase;
    private final ModelMapper modelMapper;

    @GetMapping
    public String  getContacts(
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrientation,
            Model model) {

        ListContactRequestModel requestModel = ListContactRequestModel.builder()
                .phone(phone)
                .email(email)
                .keyword(keyword)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortOrientation(sortOrientation)
                .build();

        ListContactPresenter presenter = new ListContactPresenter(modelMapper);
        useCase.execute(requestModel, presenter);
        ListContactResponse response = presenter.getResponse();

        model.addAttribute("data", response.getData());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", response.getTotalPages() > 0 ? response.getTotalPages() : 1);
        model.addAttribute("pageSize", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrientation", sortOrientation);

        return "contact-management";
    }
}
