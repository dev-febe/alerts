package com.safetynet.alerts.repository;

import com.safetynet.alerts.entity.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FireStationRepository extends JpaRepository<FireStation, Long> {
}
