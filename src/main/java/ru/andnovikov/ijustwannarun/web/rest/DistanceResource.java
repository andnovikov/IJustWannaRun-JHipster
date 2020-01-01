package ru.andnovikov.ijustwannarun.web.rest;

import ru.andnovikov.ijustwannarun.domain.Distance;
import ru.andnovikov.ijustwannarun.service.DistanceService;
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
 * REST controller for managing {@link ru.andnovikov.ijustwannarun.domain.Distance}.
 */
@RestController
@RequestMapping("/api")
public class DistanceResource {

    private final Logger log = LoggerFactory.getLogger(DistanceResource.class);

    private static final String ENTITY_NAME = "distance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DistanceService distanceService;

    public DistanceResource(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    /**
     * {@code POST  /distances} : Create a new distance.
     *
     * @param distance the distance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new distance, or with status {@code 400 (Bad Request)} if the distance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/distances")
    public ResponseEntity<Distance> createDistance(@RequestBody Distance distance) throws URISyntaxException {
        log.debug("REST request to save Distance : {}", distance);
        if (distance.getId() != null) {
            throw new BadRequestAlertException("A new distance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Distance result = distanceService.save(distance);
        return ResponseEntity.created(new URI("/api/distances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /distances} : Updates an existing distance.
     *
     * @param distance the distance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated distance,
     * or with status {@code 400 (Bad Request)} if the distance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the distance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/distances")
    public ResponseEntity<Distance> updateDistance(@RequestBody Distance distance) throws URISyntaxException {
        log.debug("REST request to update Distance : {}", distance);
        if (distance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Distance result = distanceService.save(distance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, distance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /distances} : get all the distances.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of distances in body.
     */
    @GetMapping("/distances")
    public List<Distance> getAllDistances() {
        log.debug("REST request to get all Distances");
        return distanceService.findAll();
    }

    /**
     * {@code GET  /distances/:id} : get the "id" distance.
     *
     * @param id the id of the distance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the distance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/distances/{id}")
    public ResponseEntity<Distance> getDistance(@PathVariable String id) {
        log.debug("REST request to get Distance : {}", id);
        Optional<Distance> distance = distanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(distance);
    }

    /**
     * {@code DELETE  /distances/:id} : delete the "id" distance.
     *
     * @param id the id of the distance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/distances/{id}")
    public ResponseEntity<Void> deleteDistance(@PathVariable String id) {
        log.debug("REST request to delete Distance : {}", id);
        distanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
