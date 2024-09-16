package com.luccas.schedulerpii.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class UserDTO {
    @Id
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

