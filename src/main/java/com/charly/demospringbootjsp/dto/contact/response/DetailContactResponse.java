package com.charly.demospringbootjsp.dto.contact.response;

import lombok.Data;

@Data
public class DetailContactResponse {
    private Long id;
    private String name;
    private String email;
    private String country;
    private String phone;
}
