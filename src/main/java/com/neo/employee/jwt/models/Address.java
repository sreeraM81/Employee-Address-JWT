package com.neo.employee.jwt.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="Address_Details")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String addressType;
    private String address;
    private String city;
    private String state;
    private Long pincode;
    private String country;

   /* @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="emp_id",referencedColumnName = "id")
    private Employee employee;*/

}
