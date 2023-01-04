package com.neo.employee.jwt.payload.response;

import lombok.Data;

@Data
public class AddressResponse {

    private Long id;
    private String addressType;
    private String address;
    private String city;
    private String state;
    private Long pincode;
    private String country;
}
