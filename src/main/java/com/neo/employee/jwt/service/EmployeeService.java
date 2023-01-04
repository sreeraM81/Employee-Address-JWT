package com.neo.employee.jwt.service;

import com.neo.employee.jwt.exceptions.EmployeeNotFoundException;
import com.neo.employee.jwt.models.Address;
import com.neo.employee.jwt.models.ERole;
import com.neo.employee.jwt.models.Employee;
import com.neo.employee.jwt.models.Role;
import com.neo.employee.jwt.payload.request.AddressRequest;
import com.neo.employee.jwt.payload.request.EmployeeRequest;
import com.neo.employee.jwt.payload.request.EmployeeSignUpRequest;
import com.neo.employee.jwt.payload.response.*;
import com.neo.employee.jwt.repository.AddressRepository;
import com.neo.employee.jwt.repository.EmployeeRepository;
import com.neo.employee.jwt.repository.RoleRepository;
import com.neo.employee.jwt.utils.MessageConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private AddressRepository addressRepository;

    public ResponseEntity<MessageResponse> saveEmployee(EmployeeSignUpRequest signUpRequest) {

        if (signUpRequest != null) {
            if (employeeRepository.existsByEmployeeName(signUpRequest.getEmployeeName())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Username is already taken!"));
            }

            if (employeeRepository.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email is already in use!"));
            }
            // Create new Employee  account
            Set<Role> roles = employeeRoles(signUpRequest.getRole());
            Employee employee = constructEmployee(signUpRequest, roles);
            employeeRepository.save(employee);
            return ResponseEntity.ok(new MessageResponse("Employee registered successfully!"));
        }
        return ResponseEntity.ok((new MessageResponse(MessageConstants.EMPLOYEE_ADDED_FAILED)));
    }


    public ResponseEntity<Response> delete(Long employeeId) {

        Response response = new Response();
        if (employeeId != null) {
            boolean existEmployee = employeeRepository.existsById(employeeId);
            if (existEmployee) {
                log.info("employee is present ");
                employeeRepository.deleteById(employeeId);
                response.setStatus(true);
                response.setErrorMessage(employeeId + MessageConstants.EMPLOYEE_DELETED);
            } else {
                response.setStatus(false);
                response.setErrorMessage(MessageConstants.EMPLOYEE_NOT_FOUND);
            }
        }
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<EmployeeResponse> getEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).
                orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found "));
        EmployeeResponse employeeResponse = constructEmployeeResponse(employee);
        return ResponseEntity.ok().body(employeeResponse);
    }

    private EmployeeResponse constructEmployeeResponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setId(employee.getId());
        employeeResponse.setEmployeeName(employee.getEmployeeName());
        employeeResponse.setEmployeeSalary(employee.getEmployeeSalary());
        employeeResponse.setEmail(employee.getEmail());
        employeeResponse.setEmployeeAge(employee.getEmployeeAge());
        employeeResponse.setDepartment(employeeResponse.getDepartment());
        employeeResponse.setMobileNumber(employee.getMobileNumber());
        employeeResponse.setAlternateMobileNumber(employee.getAlternateMobileNumber());
        employeeResponse.setAddress(constructAddressResponse(employee.getAddress()));
        employeeResponse.setRoles(employee.getRoles());
        return employeeResponse;
    }

    private List<AddressResponse> constructAddressResponse(List<Address> address) {
        List<AddressResponse> addressResponseList = new ArrayList<>();
        for (Address address1 : address) {
            AddressResponse addressResponse = new AddressResponse();
            addressResponse.setId(address1.getId());
            addressResponse.setAddressType(address1.getAddressType());
            addressResponse.setCity(address1.getCity());
            addressResponse.setPincode(address1.getPincode());
            addressResponse.setState(address1.getState());
            addressResponse.setCountry(address1.getCountry());
            addressResponseList.add(addressResponse);
        }
        return addressResponseList;
    }

    private Set<Role> employeeRoles(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        return roles;
    }

    private Employee constructEmployee(EmployeeSignUpRequest signUpRequest, Set<Role> roles) {
        Employee employee = new Employee();
        employee.setEmployeeName(signUpRequest.getEmployeeName());
        employee.setEmployeeAge(signUpRequest.getEmployeeAge());
        employee.setEmployeeSalary(signUpRequest.getEmployeeSalary());
        employee.setEmail(signUpRequest.getEmail());
        employee.setRoles(roles);
        employee.setGender(signUpRequest.getGender());
        employee.setDepartment(signUpRequest.getDepartment());
        employee.setMobileNumber(signUpRequest.getMobileNumber());
        employee.setAlternateMobileNumber(signUpRequest.getAlternateMobileNumber());
        employee.setPassword(encoder.encode(signUpRequest.getPassword()));
        employee.setAddress(constructAddress(signUpRequest.getAddress()));
        return employee;
    }

    private List<Address> constructAddress(List<AddressRequest> addresses) {
        List<Address> addressList = new ArrayList<>();
        for (AddressRequest address1 : addresses) {
            Address address = new Address();
            address.setAddressType(address1.getAddressType());
            address.setPincode(address1.getPincode());
            address.setCountry(address1.getCountry());
            address.setState(address1.getState());
            address.setCity(address1.getCity());
            addressList.add(address);
        }
        return addressList;
    }

    public ResponseEntity<Response> update(EmployeeRequest employeeRequest) {
        Response response=new Response();
        if (employeeRequest != null) {
            Optional<Employee> existEmployee = employeeRepository.findByEmail(employeeRequest.getEmail());
            if (existEmployee.isPresent()) {
                log.info("existing employee is present " + existEmployee.get().getId());
                Employee updateEmployee = existEmployee.get();
                updateEmployee.setEmployeeName(employeeRequest.getEmployeeName());
                updateEmployee.setEmployeeSalary(employeeRequest.getEmployeeSalary());
                updateEmployee.setAlternateMobileNumber(employeeRequest.getAlternateMobileNumber());
                employeeRepository.save(updateEmployee);
                log.info("employee details updated..");
                response.setStatus(true);
                response.setErrorMessage(MessageConstants.EMPLOYEE_UPDATED);
            } else {
                response.setStatus(false);
                response.setErrorMessage(MessageConstants.EMPLOYEE_NOT_FOUND);
            }
        }
        return ResponseEntity.ok().body(response);
    }
    public ResponseEntity<Response> addAddress(String employeeEmail, AddressRequest addressRequest) {
        Response response = new Response();
        if (employeeEmail != null && addressRequest != null) {
            Optional<Employee> existEmployee = employeeRepository.findByEmail(employeeEmail);
            if (existEmployee.isPresent()) {
                Address address = new Address();
                address.setCity(addressRequest.getCity());
                address.setAddressType(addressRequest.getAddressType());
                address.setCountry(addressRequest.getCountry());
                address.setState(addressRequest.getState());
                address.setPincode(addressRequest.getPincode());
                // address.setEmployee(constrctEmployee(existEmployee.get()));
                addressRepository.save(address);
                response.setStatus(true);
                response.setErrorMessage(MessageConstants.ADDRESS_ADDED);
            } else {
                response.setStatus(false);
                response.setErrorMessage(MessageConstants.EMPLOYEE_NOT_FOUND);
            }
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<EmployeeDToResponse> getEmployees(String department) {
        EmployeeDToResponse employeeDToResponse =new EmployeeDToResponse();
        if(department != null){
            List<Employee> existEmployee=employeeRepository.findByDepartment(department);
            if(existEmployee.size() > 0){
                List<EmployeeResponse> employeeResponseList = new ArrayList<>();
                for (Employee employee : existEmployee) {
                    EmployeeResponse employeeResponse = constructEmployeeResponse(employee);
                    employeeResponseList.add(employeeResponse);
                }
                employeeDToResponse.setStatus(true);
                employeeDToResponse.setMessage("Fetched Employee Details");
                employeeDToResponse.setEmployeesList(employeeResponseList);
            }else{
                employeeDToResponse.setStatus(false);
                employeeDToResponse.setMessage("Employees Not present for given this department");
            }
        }

        return ResponseEntity.ok().body(employeeDToResponse);
    }
}
