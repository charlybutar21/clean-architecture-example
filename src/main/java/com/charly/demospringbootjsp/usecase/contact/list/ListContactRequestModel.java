package com.charly.demospringbootjsp.usecase.contact.list;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListContactRequestModel {
    private String keyword;
    private String email;
    private String phone;
    private int page;
    private int size;
    private String sortBy;
    private String sortOrientation;
}
