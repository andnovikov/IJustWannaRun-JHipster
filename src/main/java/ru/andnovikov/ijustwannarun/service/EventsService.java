package ru.andnovikov.ijustwannarun.service;

import ru.andnovikov.ijustwannarun.domain.Events;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Events}.
 */
public interface EventsService {

    /**
     * Save a events.
     *
     * @param events the entity to save.
     * @return the persisted entity.
     */
    Events save(Events events);

    /**
     * Get all the events.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Events> findAll(Pageable pageable);


    /**
     * Get the "id" events.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Events> findOne(String id);

    /**
     * Delete the "id" events.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
