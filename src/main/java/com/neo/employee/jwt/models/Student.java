package com.neo.employee.jwt.models;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="Student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentName;
    private Long phone;
    private String department;


}
