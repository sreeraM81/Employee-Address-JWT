package com.neo.employee.jwt.payload.response;

import lombok.Data;

@Data
public class Response {
    private boolean status;
    private String errorMessage;
}
