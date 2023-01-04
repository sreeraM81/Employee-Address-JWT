package com.neo.employee.jwt.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTokenResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String employeeName;
    private String email;
    private List<String> roles;

    public EmployeeTokenResponse(String accessToken, Long id, String employeeName, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.employeeName = employeeName;
        this.email = email;
        this.roles = roles;
    }
}
