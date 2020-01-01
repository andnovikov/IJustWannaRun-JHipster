package ru.andnovikov.ijustwannarun.web.rest;

import ru.andnovikov.ijustwannarun.domain.UserRegistration;
import ru.andnovikov.ijustwannarun.service.UserRegistrationService;
import ru.andnovikov.ijustwannarun.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ru.andnovikov.ijustwannarun.domain.UserRegistration}.
 */
@RestController
@RequestMapping("/api")
public class UserRegistrationResource {

    private final Logger log = LoggerFactory.getLogger(UserRegistrationResource.class);

    private static final String ENTITY_NAME = "userRegistration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserRegistrationService userRegistrationService;

    public UserRegistrationResource(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    /**
     * {@code POST  /user-registrations} : Create a new userRegistration.
     *
     * @param userRegistration the userRegistration to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userRegistration, or with status {@code 400 (Bad Request)} if the userRegistration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-registrations")
    public ResponseEntity<UserRegistration> createUserRegistration(@RequestBody UserRegistration userRegistration) throws URISyntaxException {
        log.debug("REST request to save UserRegistration : {}", userRegistration);
        if (userRegistration.getId() != null) {
            throw new BadRequestAlertException("A new userRegistration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserRegistration result = userRegistrationService.save(userRegistration);
        return ResponseEntity.created(new URI("/api/user-registrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-registrations} : Updates an existing userRegistration.
     *
     * @param userRegistration the userRegistration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userRegistration,
     * or with status {@code 400 (Bad Request)} if the userRegistration is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userRegistration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-registrations")
    public ResponseEntity<UserRegistration> updateUserRegistration(@RequestBody UserRegistration userRegistration) throws URISyntaxException {
        log.debug("REST request to update UserRegistration : {}", userRegistration);
        if (userRegistration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserRegistration result = userRegistrationService.save(userRegistration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userRegistration.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-registrations} : get all the userRegistrations.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userRegistrations in body.
     */
    @GetMapping("/user-registrations")
    public List<UserRegistration> getAllUserRegistrations() {
        log.debug("REST request to get all UserRegistrations");
        return userRegistrationService.findAll();
    }

    /**
     * {@code GET  /user-registrations/:id} : get the "id" userRegistration.
     *
     * @param id the id of the userRegistration to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userRegistration, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-registrations/{id}")
    public ResponseEntity<UserRegistration> getUserRegistration(@PathVariable String id) {
        log.debug("REST request to get UserRegistration : {}", id);
        Optional<UserRegistration> userRegistration = userRegistrationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userRegistration);
    }

    /**
     * {@code DELETE  /user-registrations/:id} : delete the "id" userRegistration.
     *
     * @param id the id of the userRegistration to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-registrations/{id}")
    public ResponseEntity<Void> deleteUserRegistration(@PathVariable String id) {
        log.debug("REST request to delete UserRegistration : {}", id);
        userRegistrationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
