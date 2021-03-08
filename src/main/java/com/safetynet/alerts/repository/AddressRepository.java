package com.safetynet.alerts.repository;

import com.safetynet.alerts.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    public Address findTopByName(String name);
}
