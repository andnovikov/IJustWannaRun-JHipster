package ru.andnovikov.ijustwannarun.service;

import ru.andnovikov.ijustwannarun.domain.Distance;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Distance}.
 */
public interface DistanceService {

    /**
     * Save a distance.
     *
     * @param distance the entity to save.
     * @return the persisted entity.
     */
    Distance save(Distance distance);

    /**
     * Get all the distances.
     *
     * @return the list of entities.
     */
    List<Distance> findAll();


    /**
     * Get the "id" distance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Distance> findOne(String id);

    /**
     * Delete the "id" distance.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
