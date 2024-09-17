package com.charly.demospringbootjsp.usecase.contact.detail;

import com.charly.demospringbootjsp.entity.Contact;
import com.charly.demospringbootjsp.exception.ContactNotFoundException;
import com.charly.demospringbootjsp.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DetailContactUseCaseTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private DetailContactOutputBoundary presenter;

    @InjectMocks
    private DetailContactUseCase detailContactUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        detailContactUseCase = new DetailContactUseCase(contactRepository, new ModelMapper());
    }


    @Test
    void testExecute_ShouldPresentDetailContact_WhenContactFound() {
        Long contactId = 1L;
        Contact contact = new Contact(contactId, "name", "email", "country", "phone",true);
        when(contactRepository.findById(contactId)).thenReturn(Optional.of(contact));

        detailContactUseCase.execute(contactId, presenter);

        // Capture the argument passed to presenter
        ArgumentCaptor<DetailContactResponseModel> responseCaptor = ArgumentCaptor.forClass(DetailContactResponseModel.class);
        verify(presenter).present(responseCaptor.capture());

        // Assert
        DetailContactResponseModel actualResponseModel = responseCaptor.getValue();
        assertEquals(contactId, actualResponseModel.getId());
        assertEquals("name", actualResponseModel.getName());
        assertEquals("email", actualResponseModel.getEmail());
        assertEquals("country", actualResponseModel.getCountry());
        assertEquals("phone", actualResponseModel.getPhone());

    }

    @Test
    void execute_ShouldThrowContactNotFoundException_WhenContactNotFound() {
        Long contactId = 1L;

        when(contactRepository.findById(contactId)).thenReturn(Optional.empty());

        ContactNotFoundException exception = assertThrows(ContactNotFoundException.class,
                () -> detailContactUseCase.execute(contactId, presenter));

        assertEquals("Contact not found with id: " + contactId, exception.getMessage());
    }
}