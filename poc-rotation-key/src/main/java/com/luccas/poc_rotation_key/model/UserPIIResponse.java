package com.luccas.poc_rotation_key.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPIIResponse {
    private String id;
    private String userId;
    private String firstname;
    private String lastname;
    private String email;
    private String cellphone;
    private String city;
    private String country;
    private String zipcode;
}