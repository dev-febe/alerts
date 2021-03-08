package com.safetynet.alerts.configuration;

import com.safetynet.alerts.entity.*;
import com.safetynet.alerts.helper.ReadFileHelper;
import com.safetynet.alerts.repository.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PersonRepository personRepository, AddressRepository addressRepository, CityRepository cityRepository, FireStationRepository fireStationRepository, MedicalRecordRepository medicalRecordRepository) throws IOException, ParseException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("data.json");
        ReadFileHelper fileReader = new ReadFileHelper(inputStream);
        JSONObject data = fileReader.readFile();
        return args -> {
            loadPersonEntity(data, personRepository, addressRepository, cityRepository);
            loadFireStationEntity(data, fireStationRepository, addressRepository);
            loadMedicalRecordEntity(data, medicalRecordRepository, personRepository);
        };
    }

    private void loadPersonEntity(JSONObject json, PersonRepository repository, AddressRepository addressRepository, CityRepository cityRepository) {
        JSONArray jsonPersons = (JSONArray) json.get("persons");
        Iterator<JSONObject> iterator = (Iterator) jsonPersons.iterator();
        while (iterator.hasNext()) {
            JSONObject object = iterator.next();
            City city = new City(
                    (String) object.get("city"),
                    (String) object.get("zip")
            );
            city = cityRepository.save(city);
            Address address = new Address((String) object.get("address"));
            address = addressRepository.save(address);
            Person person = new Person(
                    (String) object.get("firstName"),
                    (String) object.get("lastName"),
                    address,
                    (String) object.get("birthdate"),
                    (String) object.get("phone"),
                    (String) object.get("email"),
                    city
            );
            log.info("Preloading " + repository.save(person));
        }
    }

    private void loadFireStationEntity(JSONObject json, FireStationRepository repository, AddressRepository addressRepository) {
        JSONArray jsonPersons = (JSONArray) json.get("firestations");
        Iterator<JSONObject> iterator = (Iterator) jsonPersons.iterator();
        while (iterator.hasNext()) {
            JSONObject object = iterator.next();
            Address address = addressRepository.findTopByName((String) object.get("address"));
            FireStation fireStation = new FireStation(
                    address,
                    Long.parseLong((String) object.get("station"))
            );
            log.info("Preloading " + repository.save(fireStation));

        }
    }

    private void loadMedicalRecordEntity(JSONObject json, MedicalRecordRepository medicalRecordRepository, PersonRepository personRepository) {
        JSONArray jsonPersons = (JSONArray) json.get("medicalrecords");
        Iterator<JSONObject> iterator = (Iterator) jsonPersons.iterator();

        while (iterator.hasNext()) {
            JSONObject object = iterator.next();
            Person person = personRepository.findTopByFirstNameAndLastName(
                    (String) object.get("firstName"),
                    (String) object.get("lastName")
            );
            MedicalRecord medicalRecord = new MedicalRecord(person);
            log.info("Preloading " + medicalRecordRepository.save(medicalRecord));
        }
    }
}
