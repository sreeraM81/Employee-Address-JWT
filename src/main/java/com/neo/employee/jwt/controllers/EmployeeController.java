package com.neo.employee.jwt.controllers;

import com.neo.employee.jwt.payload.request.AddressRequest;
import com.neo.employee.jwt.payload.request.EmployeeRequest;
import com.neo.employee.jwt.payload.response.EmployeeDToResponse;
import com.neo.employee.jwt.payload.response.EmployeeResponse;
import com.neo.employee.jwt.payload.response.Response;
import com.neo.employee.jwt.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emp")
@Slf4j
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/getEmployee")
    public ResponseEntity<EmployeeResponse> getEmployeeDetails(@RequestParam("employeeId") Long employeeId){
        log.info("enter getEmployee api");
        return employeeService.getEmployee(employeeId);
    }

    @DeleteMapping("/deleteEmployee")
    public ResponseEntity<Response> deleteEmployee(@RequestParam("employeeId") Long employeeId) {
        return employeeService.delete(employeeId);
    }

    @PutMapping("/updateEmployee")
    public ResponseEntity<Response> updateEmployee(@RequestBody EmployeeRequest employeeRequest){
        return employeeService.update(employeeRequest);
    }

    @PutMapping("/editAddress")
    public ResponseEntity<Response> editAddress(@RequestParam("mail") String employeeEmail,
                                                @RequestBody AddressRequest addressRequest){
        return employeeService.addAddress(employeeEmail,addressRequest);
    }
    @GetMapping("/fetchEmployees")
    public ResponseEntity<EmployeeDToResponse> getEmployeesByDepartment(@RequestParam("department") String department){
        return employeeService.getEmployees(department);
    }
}
