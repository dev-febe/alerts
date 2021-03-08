package com.safetynet.alerts.service;

import com.safetynet.alerts.entity.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class PersonService {

    EntityManager em;
    PersonRepository repository;

    PersonService(EntityManager em, PersonRepository repository) {
        this.em = em;
        this.repository = repository;
    }

    public List<Person> findPersons() {
        return repository.findAll();
    }

    public List<Person> findChildren(String address) {
        TypedQuery<Person> query = em
                .createQuery("SELECT p FROM Person as p, Address as ad WHERE p.address = ad AND ad.name = :address", Person.class)
                .setParameter("address", address);

        return query.getResultList();
    }

    public List<Person> findPersonPhones(Long fireStationNumber) {
        TypedQuery<Person> query = em
                .createQuery("SELECT p.phone FROM FireStation as f, Person as p WHERE p.address = f.address AND f.station = :station", Person.class)
                .setParameter("station", fireStationNumber)
                ;

        return query.getResultList();
    }

    public List<Person> findPersonsFireStations(String address) {
        TypedQuery<Person> query = em
                .createQuery("SELECT p.phone FROM FireStation as f JOIN f.address ad, Person as p WHERE p.address = f.address AND ad.name = :address", Person.class)
                .setParameter("address", address)
                ;

        return query.getResultList();
    }

    public List<Person> findPersonsGroupFireStations(String address) {
        TypedQuery<Person> query = em
                .createQuery("SELECT p.phone FROM FireStation as f JOIN f.address ad, Person as p WHERE p.address = f.address AND ad.name = :address", Person.class)
                .setParameter("address", address)
                ;

        return query.getResultList();
    }

    public List<Person> findPersonsEmailByCity(String city) {
        TypedQuery<Person> query = em
                .createQuery("SELECT p FROM Person as p, City as c WHERE p.address = c AND c.name = :city", Person.class)
                .setParameter("city", city);

        return query.getResultList();
    }

    public List<Person> findPersonInfo(String firstName, String lastName) {
        TypedQuery<Person> query = em
                .createQuery("SELECT p FROM Person as p, MedicalRecord as m WHERE m.person = p AND p.firstName = :firstName AND p.lastName = :lastName", Person.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName);

        return query.getResultList();
    }


    public Person findPerson(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Person createPerson(Person person) {
        return repository.save(person);
    }

    public Person updatePerson(Long id, Person personToUpdate) {
        return repository
                .findById(id)
                .map((person -> {
                    person.setAddress(personToUpdate.getAddress());
                    person.setEmail(personToUpdate.getEmail());
                    return repository.save(person);
                }))
                .orElseThrow();
    }

    public void deletePerson(Long id) {
        repository.deleteById(id);
    }
}
