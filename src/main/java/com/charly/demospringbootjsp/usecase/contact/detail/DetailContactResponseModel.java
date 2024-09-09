package com.charly.demospringbootjsp.usecase.contact.detail;

import lombok.Data;

@Data
public class DetailContactResponseModel {
    private Long id;
    private String name;
    private String email;
    private String country;
    private String phone;
}
