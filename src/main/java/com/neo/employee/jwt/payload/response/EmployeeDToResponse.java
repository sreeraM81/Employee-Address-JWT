package com.neo.employee.jwt.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeDToResponse {
    private boolean status;
    private String message;
    private List<EmployeeResponse> employeesList;
}
