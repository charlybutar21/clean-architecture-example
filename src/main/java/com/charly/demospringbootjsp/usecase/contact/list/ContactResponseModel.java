package com.charly.demospringbootjsp.usecase.contact.list;

import lombok.Data;

@Data
public class ContactResponseModel {
    private Long id;
    private String name;
    private String email;
    private String country;
    private String phone;
}