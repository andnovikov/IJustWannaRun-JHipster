package ru.andnovikov.ijustwannarun.service;

import ru.andnovikov.ijustwannarun.domain.UserRegistration;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link UserRegistration}.
 */
public interface UserRegistrationService {

    /**
     * Save a userRegistration.
     *
     * @param userRegistration the entity to save.
     * @return the persisted entity.
     */
    UserRegistration save(UserRegistration userRegistration);

    /**
     * Get all the userRegistrations.
     *
     * @return the list of entities.
     */
    List<UserRegistration> findAll();


    /**
     * Get the "id" userRegistration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserRegistration> findOne(String id);

    /**
     * Delete the "id" userRegistration.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
