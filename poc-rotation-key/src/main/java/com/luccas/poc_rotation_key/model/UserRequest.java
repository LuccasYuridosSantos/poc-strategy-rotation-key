package com.luccas.poc_rotation_key.model;

public record UserRequest(
        String firstname,
        String lastname,
        String email,
        String cellphone,
        String city,
        String country,
        String zipcode
) {
}
