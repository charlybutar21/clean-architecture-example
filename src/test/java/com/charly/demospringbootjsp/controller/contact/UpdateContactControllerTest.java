package com.charly.demospringbootjsp.controller.contact;

import com.charly.demospringbootjsp.dto.contact.request.UpdateContactRequest;
import com.charly.demospringbootjsp.usecase.contact.update.UpdateContactInputBoundary;
import com.charly.demospringbootjsp.usecase.contact.update.UpdateContactRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UpdateContactControllerTest {

    @Mock
    private UpdateContactInputBoundary useCase;

    @InjectMocks
    private UpdateContactController updateContactController;

    private MockMvc mockMvc;

    private UpdateContactRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        updateContactController = new UpdateContactController(useCase, new ModelMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(updateContactController).build();
        request = new UpdateContactRequest();
        request.setId(1L);
        request.setName("New Name");
    }

    @Test
    void testUpdateContactSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/contacts")
                        .flashAttr("updateContactRequest", request)  // Simulate @ModelAttribute
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())  // Expect 3xx redirection status
                .andExpect(redirectedUrl("/contacts"))  // Expect to be redirected to /contacts
                .andExpect(flash().attribute("successMessage", "Contact updated successfully!"));  // Flash message for success

        verify(useCase, times(1)).execute(any(UpdateContactRequestModel.class));
    }

    @Test
    void testUpdateContactFailure() throws Exception {
        doThrow(new RuntimeException("Test Exception")).when(useCase).execute(any(UpdateContactRequestModel.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/contacts")
                        .flashAttr("updateContactRequest", request)  // Simulate @ModelAttribute
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())  // Expect 3xx redirection status
                .andExpect(redirectedUrl("/contacts"))  // Expect to be redirected to /contacts
                .andExpect(flash().attribute("errorMessage", "Failed to update contact."));  // Flash message for error

          verify(useCase, times(1)).execute(any(UpdateContactRequestModel.class));
    }
}