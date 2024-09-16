package com.luccas.poc_rotation_key.model;

import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String userId;
    private String firstname;
    private String lastname;
    private String email;
    private String cellphone;
    private String city;
    private String country;
    private String zipcode;

    public UserResponse() {
    }

    public UserResponse(final String id,
                        final String userId,
                        final String firstname,
                        final String lastname,
                        final String email,
                        final String cellphone,
                        final String city,
                        final String country,
                        final String zipcode) {
        this.id = id;
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.cellphone = cellphone;
        this.city = city;
        this.country = country;
        this.zipcode = zipcode;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(final String cellphone) {
        this.cellphone = cellphone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(final String zipcode) {
        this.zipcode = zipcode;
    }
}