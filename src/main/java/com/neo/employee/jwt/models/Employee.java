package com.neo.employee.jwt.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Employee_Details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String employeeName;

    @Min(value = 18, message = "Employee Age should not be less than 18")
    @Max(value = 150, message = "Employee Age should not be greater than 150")
    private Integer employeeAge;

    @NotNull
    private String gender;

    private Long mobileNumber;

    private Long alternateMobileNumber;

    private Double employeeSalary;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;

    @NotNull
    private String department;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "employee_roles",
            joinColumns = @JoinColumn(name = "emp_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="emp_id",referencedColumnName = "id")
    private List<Address> address;

    public Employee(String employeeName, String email, String password) {
        this.employeeName=employeeName;
        this.email=email;
        this.password=password;
    }
}
