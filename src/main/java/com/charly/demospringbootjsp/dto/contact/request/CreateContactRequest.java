package com.charly.demospringbootjsp.dto.contact.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateContactRequest {
    private String name;
    private String email;
    private String country;
    private String phone;
}
