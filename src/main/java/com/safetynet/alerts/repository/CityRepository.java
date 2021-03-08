package com.safetynet.alerts.repository;

import com.safetynet.alerts.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
