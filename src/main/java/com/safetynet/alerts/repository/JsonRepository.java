package com.safetynet.alerts.repository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public interface JsonRepository<T, K> {
    /**
     * Fetch all resources from specify entity
     */
    List<T> findAll() throws IOException, URISyntaxException;

    /**
     * Fetch resource by id
     */
    Optional<T> findById(K id) throws IOException;

    /**
     * Save resource in specific key of json file
     * @return
     */
    boolean save(T var) throws IOException, URISyntaxException;

    /**
     * Update resource
     */
    T update(K var, T varToUpdate) throws IOException;

    /**
     * Delete resource by id
     */
    void deleteById(K var) throws IOException;

}
