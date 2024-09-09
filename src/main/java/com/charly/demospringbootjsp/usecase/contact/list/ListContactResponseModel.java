package com.charly.demospringbootjsp.usecase.contact.list;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ListContactResponseModel {
    private List<ContactResponseModel> data;
    private int totalPages;
    private long totalElements;

}
