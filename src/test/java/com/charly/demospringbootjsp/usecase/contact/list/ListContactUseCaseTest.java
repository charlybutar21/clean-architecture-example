package com.charly.demospringbootjsp.usecase.contact.list;

import com.charly.demospringbootjsp.entity.Contact;
import com.charly.demospringbootjsp.repository.ContactRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.mockito.Mockito.*;

class ListContactUseCaseTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ListContactOutputBoundary presenter;

    @InjectMocks
    private ListContactUseCase listContactUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listContactUseCase = new ListContactUseCase(contactRepository, new ModelMapper());
    }

    @Test
    void testExecute_ShouldReturnPagedContacts_WhenContactsExist() {
        ListContactRequestModel requestModel = ListContactRequestModel.builder()
                .page(0)
                .size(10)
                .sortBy("name")
                .sortOrientation("asc")
                .phone("123456789")
                .email("example@example.com")
                .build();

        Contact contact1 = new Contact(1L, "John Doe", "example@example.com", "USA", "123456789", true);
        Contact contact2 = new Contact(2L, "Jane Doe", "jane@example.com", "USA", "987654321", true);
        Page<Contact> contactsPage = new PageImpl<>(List.of(contact1, contact2), PageRequest.of(0, 10), 2);

        when(contactRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(contactsPage);

        // Act
        listContactUseCase.execute(requestModel, presenter);

        // Assert
        verify(contactRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(presenter, times(1)).present(any(ListContactResponseModel.class));
    }


    @Test
    void testExecute_ShouldReturnEmpty_WhenNoContactsExist() {
        ListContactRequestModel requestModel = ListContactRequestModel.builder()
                .page(0)
                .size(10)
                .sortBy("name")
                .sortOrientation("desc")
                .keyword("keyword")
                .phone("123456789")
                .email("example@example.com")
                .build();
        Page<Contact> contactsPage = Page.empty(PageRequest.of(0, 10));

        when(contactRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(contactsPage);

        listContactUseCase.execute(requestModel, presenter);

        verify(contactRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(presenter, times(1)).present(any(ListContactResponseModel.class));
    }

    @Test
    void testBuildSpecification_ShouldBuildCorrectPredicate_WhenFiltersAreApplied() {
        ListContactRequestModel requestModel = ListContactRequestModel.builder()
                .page(0)
                .size(10)
                .sortBy("name")
                .sortOrientation("desc")
                .keyword("keyword")
                .phone("123456789")
                .email("example@example.com")
                .build();

        Specification<Contact> spec = listContactUseCase.buildSpecification(requestModel);

        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<Contact> query = mock(CriteriaQuery.class);
        Root<Contact> root = mock(Root.class);
        spec.toPredicate(root, query, criteriaBuilder);

        verify(criteriaBuilder).equal(root.get("phone"), "123456789");
        verify(criteriaBuilder).equal(root.get("email"), "example@example.com");
    }

}