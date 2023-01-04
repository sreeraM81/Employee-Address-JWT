package com.neo.employee.jwt.payload.response;

import com.neo.employee.jwt.models.Address;
import com.neo.employee.jwt.models.Role;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class EmployeeResponse {

    private Long id;
    @NotNull
    private String employeeName;


    private Integer employeeAge;


    private String gender;

    private Long mobileNumber;

    private Long alternateMobileNumber;

    private Double employeeSalary;
    private String email;

    private String password;

    private String department;


    private Set<Role> roles;

    private List<AddressResponse> address;
}
