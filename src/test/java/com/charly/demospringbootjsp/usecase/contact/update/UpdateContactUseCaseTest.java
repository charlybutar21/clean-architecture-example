package com.charly.demospringbootjsp.usecase.contact.update;

import com.charly.demospringbootjsp.entity.Contact;
import com.charly.demospringbootjsp.exception.ContactNotFoundException;
import com.charly.demospringbootjsp.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UpdateContactUseCaseTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private UpdateContactUseCase updateContactUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_ShouldUpdateContact_WhenContactExists() {
        Long contactId = 1L;
        UpdateContactRequestModel requestModel = new UpdateContactRequestModel();
        requestModel.setId(contactId);
        requestModel.setName("new name");
        requestModel.setEmail("new email");
        requestModel.setCountry("new country");
        requestModel.setPhone("new phone");

        Contact contactEntity = new Contact(null, "name", "email", "country", "phone", true);
        when(contactRepository.findById(contactId)).thenReturn(Optional.of(contactEntity));

        updateContactUseCase.execute(requestModel);

        verify(contactRepository, times(1)).save(contactEntity);
        assertEquals("new name", contactEntity.getName());
        assertEquals("new email", contactEntity.getEmail());
        assertEquals("new phone", contactEntity.getPhone());
        assertEquals("new country", contactEntity.getCountry());
    }

    @Test
    void testExecute_ContactNotFound() {
        Long contactId = 1L;
        UpdateContactRequestModel requestModel = new UpdateContactRequestModel();
        requestModel.setId(contactId);

        when(contactRepository.findById(contactId)).thenReturn(Optional.empty());

        ContactNotFoundException exception = assertThrows(ContactNotFoundException.class, () -> {
            updateContactUseCase.execute(requestModel);
        });

        // Verify the exception message
        assertEquals("Contact not found with id: " + contactId, exception.getMessage());

        // Verify that no save operation was performed
        verify(contactRepository, times(1)).findById(contactId);
        verify(contactRepository, times(0)).save(any(Contact.class));
    }

}