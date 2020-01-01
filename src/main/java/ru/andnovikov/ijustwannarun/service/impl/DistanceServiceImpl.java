package ru.andnovikov.ijustwannarun.service.impl;

import ru.andnovikov.ijustwannarun.service.DistanceService;
import ru.andnovikov.ijustwannarun.domain.Distance;
import ru.andnovikov.ijustwannarun.repository.DistanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Distance}.
 */
@Service
public class DistanceServiceImpl implements DistanceService {

    private final Logger log = LoggerFactory.getLogger(DistanceServiceImpl.class);

    private final DistanceRepository distanceRepository;

    public DistanceServiceImpl(DistanceRepository distanceRepository) {
        this.distanceRepository = distanceRepository;
    }

    /**
     * Save a distance.
     *
     * @param distance the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Distance save(Distance distance) {
        log.debug("Request to save Distance : {}", distance);
        return distanceRepository.save(distance);
    }

    /**
     * Get all the distances.
     *
     * @return the list of entities.
     */
    @Override
    public List<Distance> findAll() {
        log.debug("Request to get all Distances");
        return distanceRepository.findAll();
    }


    /**
     * Get one distance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Distance> findOne(String id) {
        log.debug("Request to get Distance : {}", id);
        return distanceRepository.findById(id);
    }

    /**
     * Delete the distance by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Distance : {}", id);
        distanceRepository.deleteById(id);
    }
}
