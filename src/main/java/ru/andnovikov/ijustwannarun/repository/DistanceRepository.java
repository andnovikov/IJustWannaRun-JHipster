package ru.andnovikov.ijustwannarun.repository;

import ru.andnovikov.ijustwannarun.domain.Distance;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Distance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistanceRepository extends MongoRepository<Distance, String> {

}
