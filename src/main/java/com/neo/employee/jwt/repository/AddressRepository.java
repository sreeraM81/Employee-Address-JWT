package com.neo.employee.jwt.repository;

import com.neo.employee.jwt.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
