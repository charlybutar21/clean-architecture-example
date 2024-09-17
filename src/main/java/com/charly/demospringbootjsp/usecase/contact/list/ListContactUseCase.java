package com.charly.demospringbootjsp.usecase.contact.list;

import com.charly.demospringbootjsp.entity.Contact;
import com.charly.demospringbootjsp.repository.ContactRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ListContactUseCase implements ListContactInputBoundary{
    private ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    @Override
    public void execute(ListContactRequestModel requestModel, ListContactOutputBoundary presenter) {
        Specification<Contact> spec = buildSpecification(requestModel);

        Pageable pageable = createPageable(requestModel);

        Page<Contact> contactsPage = contactRepository.findAll(spec, pageable);

        presenter.present(constructResponseModel(contactsPage));
    }

    Specification<Contact> buildSpecification(ListContactRequestModel requestModel) {
        return (Root<Contact> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (isNotNullAndEmpty(requestModel.getPhone())) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("phone"), requestModel.getPhone()));
            }

            if (isNotNullAndEmpty(requestModel.getEmail())) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("email"), requestModel.getEmail()));
            }

            if (isNotNullAndEmpty(requestModel.getKeyword())) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(root.get("name"), "%" + requestModel.getKeyword() + "%"));
            }

            return predicate;
        };
    }

    private boolean isNotNullAndEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    private Pageable createPageable(ListContactRequestModel requestModel) {
        Sort.Direction direction = determineSortDirection(requestModel.getSortOrientation());
        return PageRequest.of(
                requestModel.getPage(),
                requestModel.getSize(),
                Sort.by(direction, requestModel.getSortBy()));
    }

    private Sort.Direction determineSortDirection(String sortOrientation) {
        return "desc".equalsIgnoreCase(sortOrientation) ? Sort.Direction.DESC : Sort.Direction.ASC;
    }

    private ListContactResponseModel constructResponseModel(Page<Contact> contactsPage) {
        List<ContactResponseModel> contactResponseModels = contactsPage.getContent().stream()
                .map(contact -> modelMapper.map(contact, ContactResponseModel.class))
                .toList();

        return ListContactResponseModel.builder()
                .data(contactResponseModels)
                .totalPages(contactsPage.getTotalPages())
                .totalElements(contactsPage.getTotalElements())
                .build();
    }
}
