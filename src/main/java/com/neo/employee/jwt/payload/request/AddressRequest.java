package com.neo.employee.jwt.payload.request;

import lombok.Data;

@Data
public class AddressRequest {

    private String addressType;
    private String address;
    private String city;
    private String state;
    private Long pincode;
    private String country;
}
