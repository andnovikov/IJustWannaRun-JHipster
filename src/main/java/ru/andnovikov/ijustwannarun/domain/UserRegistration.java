package ru.andnovikov.ijustwannarun.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A UserRegistration.
 */
@Document(collection = "user_registration")
public class UserRegistration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("reg_date")
    private ZonedDateTime regDate;

    @Field("reg_number")
    private Integer regNumber;

    @DBRef
    @Field("event")
    private Set<Distance> events = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getRegDate() {
        return regDate;
    }

    public UserRegistration regDate(ZonedDateTime regDate) {
        this.regDate = regDate;
        return this;
    }

    public void setRegDate(ZonedDateTime regDate) {
        this.regDate = regDate;
    }

    public Integer getRegNumber() {
        return regNumber;
    }

    public UserRegistration regNumber(Integer regNumber) {
        this.regNumber = regNumber;
        return this;
    }

    public void setRegNumber(Integer regNumber) {
        this.regNumber = regNumber;
    }

    public Set<Distance> getEvents() {
        return events;
    }

    public UserRegistration events(Set<Distance> distances) {
        this.events = distances;
        return this;
    }

    public UserRegistration addEvent(Distance distance) {
        this.events.add(distance);
        distance.setUserRegistration(this);
        return this;
    }

    public UserRegistration removeEvent(Distance distance) {
        this.events.remove(distance);
        distance.setUserRegistration(null);
        return this;
    }

    public void setEvents(Set<Distance> distances) {
        this.events = distances;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRegistration)) {
            return false;
        }
        return id != null && id.equals(((UserRegistration) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserRegistration{" +
            "id=" + getId() +
            ", regDate='" + getRegDate() + "'" +
            ", regNumber=" + getRegNumber() +
            "}";
    }
}
