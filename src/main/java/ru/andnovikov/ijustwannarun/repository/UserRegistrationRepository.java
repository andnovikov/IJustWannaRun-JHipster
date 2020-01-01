package ru.andnovikov.ijustwannarun.repository;

import ru.andnovikov.ijustwannarun.domain.UserRegistration;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the UserRegistration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRegistrationRepository extends MongoRepository<UserRegistration, String> {

}
