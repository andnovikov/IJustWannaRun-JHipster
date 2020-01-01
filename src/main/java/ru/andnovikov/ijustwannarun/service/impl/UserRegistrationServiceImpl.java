package ru.andnovikov.ijustwannarun.service.impl;

import ru.andnovikov.ijustwannarun.service.UserRegistrationService;
import ru.andnovikov.ijustwannarun.domain.UserRegistration;
import ru.andnovikov.ijustwannarun.repository.UserRegistrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link UserRegistration}.
 */
@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final Logger log = LoggerFactory.getLogger(UserRegistrationServiceImpl.class);

    private final UserRegistrationRepository userRegistrationRepository;

    public UserRegistrationServiceImpl(UserRegistrationRepository userRegistrationRepository) {
        this.userRegistrationRepository = userRegistrationRepository;
    }

    /**
     * Save a userRegistration.
     *
     * @param userRegistration the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UserRegistration save(UserRegistration userRegistration) {
        log.debug("Request to save UserRegistration : {}", userRegistration);
        return userRegistrationRepository.save(userRegistration);
    }

    /**
     * Get all the userRegistrations.
     *
     * @return the list of entities.
     */
    @Override
    public List<UserRegistration> findAll() {
        log.debug("Request to get all UserRegistrations");
        return userRegistrationRepository.findAll();
    }


    /**
     * Get one userRegistration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<UserRegistration> findOne(String id) {
        log.debug("Request to get UserRegistration : {}", id);
        return userRegistrationRepository.findById(id);
    }

    /**
     * Delete the userRegistration by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete UserRegistration : {}", id);
        userRegistrationRepository.deleteById(id);
    }
}
