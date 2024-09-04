package com.luccas.poc_rotation_key.model;

public record UserPIIInsertRequest(
        String userId,
        String firstname,
        String lastname,
        String email,
        String cellphone,
        String city,
        String country,
        String zipcode
) {
}
