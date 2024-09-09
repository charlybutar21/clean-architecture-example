package com.charly.demospringbootjsp.dto.contact.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateContactRequest {
    private Long id;
    private String name;
    private String email;
    private String country;
    private String phone;
}
