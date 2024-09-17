package com.charly.demospringbootjsp.dto.contact.response;

import lombok.Data;
import java.util.List;

@Data
public class ListContactResponse {
    private List<ContactResponse> data;
    private int totalPages;
    private long totalElements;
}
