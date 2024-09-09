package com.charly.demospringbootjsp.usecase.contact.update;

import lombok.Data;

@Data
public class UpdateContactRequestModel {
    private Long id;
    private String name;
    private String email;
    private String country;
    private String phone;
}
