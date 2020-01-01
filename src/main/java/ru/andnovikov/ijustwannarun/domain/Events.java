package ru.andnovikov.ijustwannarun.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import ru.andnovikov.ijustwannarun.domain.enumeration.EventStatus;

import ru.andnovikov.ijustwannarun.domain.enumeration.EventKind;

/**
 * A Events.
 */
@Document(collection = "events")
public class Events implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("date")
    private ZonedDateTime date;

    @Field("reg_start")
    private ZonedDateTime regStart;

    @Field("reg_end")
    private ZonedDateTime regEnd;

    @Field("status")
    private EventStatus status;

    @Field("kind")
    private EventKind kind;

    @Field("url")
    private String url;

    @DBRef
    @Field("location")
    private Set<Location> locations = new HashSet<>();

    @DBRef
    @Field("distances")
    @JsonIgnoreProperties("events")
    private Distance distances;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Events name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Events date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public ZonedDateTime getRegStart() {
        return regStart;
    }

    public Events regStart(ZonedDateTime regStart) {
        this.regStart = regStart;
        return this;
    }

    public void setRegStart(ZonedDateTime regStart) {
        this.regStart = regStart;
    }

    public ZonedDateTime getRegEnd() {
        return regEnd;
    }

    public Events regEnd(ZonedDateTime regEnd) {
        this.regEnd = regEnd;
        return this;
    }

    public void setRegEnd(ZonedDateTime regEnd) {
        this.regEnd = regEnd;
    }

    public EventStatus getStatus() {
        return status;
    }

    public Events status(EventStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public EventKind getKind() {
        return kind;
    }

    public Events kind(EventKind kind) {
        this.kind = kind;
        return this;
    }

    public void setKind(EventKind kind) {
        this.kind = kind;
    }

    public String getUrl() {
        return url;
    }

    public Events url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public Events locations(Set<Location> locations) {
        this.locations = locations;
        return this;
    }

    public Events addLocation(Location location) {
        this.locations.add(location);
        location.setEvents(this);
        return this;
    }

    public Events removeLocation(Location location) {
        this.locations.remove(location);
        location.setEvents(null);
        return this;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public Distance getDistances() {
        return distances;
    }

    public Events distances(Distance distance) {
        this.distances = distance;
        return this;
    }

    public void setDistances(Distance distance) {
        this.distances = distance;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Events)) {
            return false;
        }
        return id != null && id.equals(((Events) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Events{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            ", regStart='" + getRegStart() + "'" +
            ", regEnd='" + getRegEnd() + "'" +
            ", status='" + getStatus() + "'" +
            ", kind='" + getKind() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
