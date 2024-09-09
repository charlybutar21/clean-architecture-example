package com.charly.demospringbootjsp.usecase.contact.create;

import lombok.Data;

@Data
public class CreateContactRequestModel {
    private String name;
    private String email;
    private String country;
    private String phone;
}
