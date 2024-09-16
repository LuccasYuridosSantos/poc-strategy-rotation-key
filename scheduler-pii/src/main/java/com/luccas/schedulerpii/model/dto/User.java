package com.luccas.schedulerpii.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String userId;
    private String firstname;
    private String lastname;
    private String email;
    private String cellphone;
    private String city;
    private String country;
    private String zipcode;
    private EncryptedData encrypted;
}

