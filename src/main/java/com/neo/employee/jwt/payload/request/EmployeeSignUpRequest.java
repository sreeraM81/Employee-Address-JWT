package com.neo.employee.jwt.payload.request;

import com.neo.employee.jwt.models.Address;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Data
public class EmployeeSignUpRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String employeeName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;


    private Set<String> role;
    @Min(value = 18, message = "Employee Age should not be less than 18")
    @Max(value = 150, message = "Employee Age should not be greater than 150")
    private Integer employeeAge;

    @NotBlank
    private String gender;

    private Long mobileNumber;

    private Long alternateMobileNumber;

    private Double employeeSalary;

    @NotBlank
    private String department;


    private List<AddressRequest> address;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
