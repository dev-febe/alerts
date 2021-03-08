package com.safetynet.alerts.service;

import com.safetynet.alerts.entity.FireStation;
import com.safetynet.alerts.entity.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class FireStationService {

    FireStationRepository repository;

    EntityManager em;

    FireStationService(EntityManager em, FireStationRepository repository) {
        this.repository = repository;
        this.em = em;
    }

    public List<Person> findPersonByFireStations(Long stationNumber) {
        TypedQuery<Person> query = em
                .createQuery("SELECT p FROM FireStation as f, Person as p WHERE p.address = f.address AND f.station = :station", Person.class)
                .setParameter("station", stationNumber)
                ;

        return query.getResultList();
    }

    public List<FireStation> findFiresStations() {
        return repository.findAll();
    }

    public FireStation findFiresStation(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public FireStation createFiresStation(FireStation person) {
        return repository.save(person);
    }

    public FireStation updateFiresStation(Long id, FireStation personToUpdate) {
        return repository
                .findById(id)
                .map((person -> {
                    person.setAddress(personToUpdate.getAddress());
                    person.setStation(personToUpdate.getStation());
                    return repository.save(person);
                }))
                .orElseThrow();
    }

    public void deleteFireStation(Long id) {
        repository.deleteById(id);
    }
}
