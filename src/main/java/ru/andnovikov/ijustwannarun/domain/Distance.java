package ru.andnovikov.ijustwannarun.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Distance.
 */
@Document(collection = "distance")
public class Distance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("length")
    private Float length;

    @Field("limit")
    private Integer limit;

    @DBRef
    @Field("events")
    private Set<Events> events = new HashSet<>();

    @DBRef
    @Field("userRegistration")
    @JsonIgnoreProperties("events")
    private UserRegistration userRegistration;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getLength() {
        return length;
    }

    public Distance length(Float length) {
        this.length = length;
        return this;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Integer getLimit() {
        return limit;
    }

    public Distance limit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Set<Events> getEvents() {
        return events;
    }

    public Distance events(Set<Events> events) {
        this.events = events;
        return this;
    }

    public Distance addEvents(Events events) {
        this.events.add(events);
        events.setDistances(this);
        return this;
    }

    public Distance removeEvents(Events events) {
        this.events.remove(events);
        events.setDistances(null);
        return this;
    }

    public void setEvents(Set<Events> events) {
        this.events = events;
    }

    public UserRegistration getUserRegistration() {
        return userRegistration;
    }

    public Distance userRegistration(UserRegistration userRegistration) {
        this.userRegistration = userRegistration;
        return this;
    }

    public void setUserRegistration(UserRegistration userRegistration) {
        this.userRegistration = userRegistration;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Distance)) {
            return false;
        }
        return id != null && id.equals(((Distance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Distance{" +
            "id=" + getId() +
            ", length=" + getLength() +
            ", limit=" + getLimit() +
            "}";
    }
}
