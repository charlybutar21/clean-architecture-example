package com.charly.demospringbootjsp.controller.contact;

import com.charly.demospringbootjsp.dto.contact.request.UpdateContactRequest;
import com.charly.demospringbootjsp.usecase.contact.update.UpdateContactInputBoundary;
import com.charly.demospringbootjsp.usecase.contact.update.UpdateContactRequestModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class UpdateContactController {
    private final UpdateContactInputBoundary useCase;
    private final ModelMapper modelMapper;

    @PutMapping("/contacts")
    public String updateContact(@ModelAttribute UpdateContactRequest request, RedirectAttributes redirectAttributes) {
        try {
            UpdateContactRequestModel requestModel = modelMapper.map(request, UpdateContactRequestModel.class);
            useCase.execute(requestModel);
            redirectAttributes.addFlashAttribute("successMessage", "Contact updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update contact.");
        }
        return "redirect:/contacts";
    }
}
