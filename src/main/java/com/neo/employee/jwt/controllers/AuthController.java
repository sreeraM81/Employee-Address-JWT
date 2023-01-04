package com.neo.employee.jwt.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.neo.employee.jwt.models.Address;
import com.neo.employee.jwt.models.Employee;
import com.neo.employee.jwt.payload.request.AddressRequest;
import com.neo.employee.jwt.payload.request.EmployeeSignInRequest;
import com.neo.employee.jwt.payload.request.EmployeeSignUpRequest;
import com.neo.employee.jwt.payload.response.EmployeeTokenResponse;
import com.neo.employee.jwt.repository.EmployeeRepository;
import com.neo.employee.jwt.security.jwt.JwtUtil;
import com.neo.employee.jwt.security.services.EmployeeDetailsImpl;
import com.neo.employee.jwt.models.ERole;
import com.neo.employee.jwt.models.Role;
import com.neo.employee.jwt.payload.response.MessageResponse;
import com.neo.employee.jwt.repository.RoleRepository;
import com.neo.employee.jwt.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtUtil jwtUtils;

  @Autowired
  EmployeeService employeeService;


  @PostMapping("/signIn")
  public ResponseEntity<?> authenticateEmployee(@Valid @RequestBody EmployeeSignInRequest loginRequest) {

    log.info("enter into authenticateEmployee");
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    log.info("enter into authentication"+authentication);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    log.info("jwt "+jwt);

    EmployeeDetailsImpl userDetails = (EmployeeDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

    return ResponseEntity.ok(new EmployeeTokenResponse(jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles));
  }

  @PostMapping("/signUp")
  public ResponseEntity<MessageResponse> registerEmployee(@Valid @RequestBody EmployeeSignUpRequest signUpRequest) {
    return  employeeService.saveEmployee(signUpRequest);
  }



}
