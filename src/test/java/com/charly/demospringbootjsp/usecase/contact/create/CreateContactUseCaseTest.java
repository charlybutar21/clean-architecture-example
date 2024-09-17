package com.charly.demospringbootjsp.usecase.contact.create;

import com.charly.demospringbootjsp.entity.Contact;
import com.charly.demospringbootjsp.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CreateContactUseCaseTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private CreateContactUseCase createContactUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createContactUseCase = new CreateContactUseCase(contactRepository, new ModelMapper());
    }

    @Test
    void testExecute_SuccessfulCreation() {
        CreateContactRequestModel requestModel = new CreateContactRequestModel();
        requestModel.setName("John Doe");
        requestModel.setEmail("john.doe@example.com");
        requestModel.setCountry("USA");
        requestModel.setPhone("123456789");

        Contact contactEntity = new Contact(null, "John Doe", "john.doe@example.com", "USA", "123456789", true);

        createContactUseCase.execute(requestModel);

        verify(contactRepository, times(1)).save(contactEntity);
    }
}