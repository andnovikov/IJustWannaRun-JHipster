package ru.andnovikov.ijustwannarun.web.rest;

import ru.andnovikov.ijustwannarun.IJustWannaRunApp;
import ru.andnovikov.ijustwannarun.domain.Events;
import ru.andnovikov.ijustwannarun.repository.EventsRepository;
import ru.andnovikov.ijustwannarun.service.EventsService;
import ru.andnovikov.ijustwannarun.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static ru.andnovikov.ijustwannarun.web.rest.TestUtil.sameInstant;
import static ru.andnovikov.ijustwannarun.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ru.andnovikov.ijustwannarun.domain.enumeration.EventStatus;
import ru.andnovikov.ijustwannarun.domain.enumeration.EventKind;
/**
 * Integration tests for the {@link EventsResource} REST controller.
 */
@SpringBootTest(classes = IJustWannaRunApp.class)
public class EventsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_REG_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REG_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_REG_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REG_END = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final EventStatus DEFAULT_STATUS = EventStatus.PLAN;
    private static final EventStatus UPDATED_STATUS = EventStatus.OPEN;

    private static final EventKind DEFAULT_KIND = EventKind.RUNNING;
    private static final EventKind UPDATED_KIND = EventKind.SWIMMING;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private EventsService eventsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restEventsMockMvc;

    private Events events;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventsResource eventsResource = new EventsResource(eventsService);
        this.restEventsMockMvc = MockMvcBuilders.standaloneSetup(eventsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Events createEntity() {
        Events events = new Events()
            .name(DEFAULT_NAME)
            .date(DEFAULT_DATE)
            .regStart(DEFAULT_REG_START)
            .regEnd(DEFAULT_REG_END)
            .status(DEFAULT_STATUS)
            .kind(DEFAULT_KIND)
            .url(DEFAULT_URL);
        return events;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Events createUpdatedEntity() {
        Events events = new Events()
            .name(UPDATED_NAME)
            .date(UPDATED_DATE)
            .regStart(UPDATED_REG_START)
            .regEnd(UPDATED_REG_END)
            .status(UPDATED_STATUS)
            .kind(UPDATED_KIND)
            .url(UPDATED_URL);
        return events;
    }

    @BeforeEach
    public void initTest() {
        eventsRepository.deleteAll();
        events = createEntity();
    }

    @Test
    public void createEvents() throws Exception {
        int databaseSizeBeforeCreate = eventsRepository.findAll().size();

        // Create the Events
        restEventsMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(events)))
            .andExpect(status().isCreated());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeCreate + 1);
        Events testEvents = eventsList.get(eventsList.size() - 1);
        assertThat(testEvents.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEvents.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEvents.getRegStart()).isEqualTo(DEFAULT_REG_START);
        assertThat(testEvents.getRegEnd()).isEqualTo(DEFAULT_REG_END);
        assertThat(testEvents.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEvents.getKind()).isEqualTo(DEFAULT_KIND);
        assertThat(testEvents.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    public void createEventsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventsRepository.findAll().size();

        // Create the Events with an existing ID
        events.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventsMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(events)))
            .andExpect(status().isBadRequest());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllEvents() throws Exception {
        // Initialize the database
        eventsRepository.save(events);

        // Get all the eventsList
        restEventsMockMvc.perform(get("/api/events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(events.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].regStart").value(hasItem(sameInstant(DEFAULT_REG_START))))
            .andExpect(jsonPath("$.[*].regEnd").value(hasItem(sameInstant(DEFAULT_REG_END))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].kind").value(hasItem(DEFAULT_KIND.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }
    
    @Test
    public void getEvents() throws Exception {
        // Initialize the database
        eventsRepository.save(events);

        // Get the events
        restEventsMockMvc.perform(get("/api/events/{id}", events.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(events.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.regStart").value(sameInstant(DEFAULT_REG_START)))
            .andExpect(jsonPath("$.regEnd").value(sameInstant(DEFAULT_REG_END)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.kind").value(DEFAULT_KIND.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }

    @Test
    public void getNonExistingEvents() throws Exception {
        // Get the events
        restEventsMockMvc.perform(get("/api/events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEvents() throws Exception {
        // Initialize the database
        eventsService.save(events);

        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();

        // Update the events
        Events updatedEvents = eventsRepository.findById(events.getId()).get();
        updatedEvents
            .name(UPDATED_NAME)
            .date(UPDATED_DATE)
            .regStart(UPDATED_REG_START)
            .regEnd(UPDATED_REG_END)
            .status(UPDATED_STATUS)
            .kind(UPDATED_KIND)
            .url(UPDATED_URL);

        restEventsMockMvc.perform(put("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEvents)))
            .andExpect(status().isOk());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
        Events testEvents = eventsList.get(eventsList.size() - 1);
        assertThat(testEvents.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEvents.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEvents.getRegStart()).isEqualTo(UPDATED_REG_START);
        assertThat(testEvents.getRegEnd()).isEqualTo(UPDATED_REG_END);
        assertThat(testEvents.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEvents.getKind()).isEqualTo(UPDATED_KIND);
        assertThat(testEvents.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    public void updateNonExistingEvents() throws Exception {
        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();

        // Create the Events

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventsMockMvc.perform(put("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(events)))
            .andExpect(status().isBadRequest());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteEvents() throws Exception {
        // Initialize the database
        eventsService.save(events);

        int databaseSizeBeforeDelete = eventsRepository.findAll().size();

        // Delete the events
        restEventsMockMvc.perform(delete("/api/events/{id}", events.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
