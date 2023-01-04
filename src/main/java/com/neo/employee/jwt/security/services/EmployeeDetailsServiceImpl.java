package com.neo.employee.jwt.security.services;

import com.neo.employee.jwt.models.Employee;
import com.neo.employee.jwt.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class EmployeeDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepository.findByEmployeeName(username);
               // .orElseThrow(() -> new UsernameNotFoundException("Employee Not Found with username: " + email));
        return EmployeeDetailsImpl.build(employee);
    }
}
