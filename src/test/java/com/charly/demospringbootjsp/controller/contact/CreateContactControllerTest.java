package com.charly.demospringbootjsp.controller.contact;

import com.charly.demospringbootjsp.dto.contact.request.CreateContactRequest;
import com.charly.demospringbootjsp.usecase.contact.create.CreateContactInputBoundary;
import com.charly.demospringbootjsp.usecase.contact.create.CreateContactRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateContactControllerTest {

    @Mock
    private CreateContactInputBoundary useCase;

    @InjectMocks
    private CreateContactController createContactController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createContactController = new CreateContactController(useCase, new ModelMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(createContactController).build();
    }

    @Test
    void testCreateContact_ShouldRedirectToContactsPage_WhenRequestIsValid() throws Exception {
        CreateContactRequest request = new CreateContactRequest();
        request.setName("name");
        request.setEmail("email@email.com");
        request.setCountry("country");
        request.setPhone("phone");

        doNothing().when(useCase).execute(any(CreateContactRequestModel.class));

        mockMvc.perform(post("/contacts")
                        .flashAttr("request", request))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/contacts"));
    }
}