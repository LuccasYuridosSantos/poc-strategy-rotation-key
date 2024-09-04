package com.luccas.poc_rotation_key.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
public class UserPII {
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
