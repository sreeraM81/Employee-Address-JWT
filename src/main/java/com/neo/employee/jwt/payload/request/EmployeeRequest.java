package com.neo.employee.jwt.payload.request;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Data
public class EmployeeRequest {


    private String employeeName;

    private String email;

    private Integer employeeAge;

    @NotBlank
    private String gender;

    private Long mobileNumber;

    private Long alternateMobileNumber;

    private Double employeeSalary;

    @NotBlank
    private String department;

    private List<AddressRequest> address;
}
