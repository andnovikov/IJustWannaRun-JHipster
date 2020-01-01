package ru.andnovikov.ijustwannarun.web.rest;

import ru.andnovikov.ijustwannarun.IJustWannaRunApp;
import ru.andnovikov.ijustwannarun.domain.Distance;
import ru.andnovikov.ijustwannarun.repository.DistanceRepository;
import ru.andnovikov.ijustwannarun.service.DistanceService;
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


import java.util.List;

import static ru.andnovikov.ijustwannarun.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DistanceResource} REST controller.
 */
@SpringBootTest(classes = IJustWannaRunApp.class)
public class DistanceResourceIT {

    private static final Float DEFAULT_LENGTH = 1F;
    private static final Float UPDATED_LENGTH = 2F;

    private static final Integer DEFAULT_LIMIT = 1;
    private static final Integer UPDATED_LIMIT = 2;

    @Autowired
    private DistanceRepository distanceRepository;

    @Autowired
    private DistanceService distanceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restDistanceMockMvc;

    private Distance distance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DistanceResource distanceResource = new DistanceResource(distanceService);
        this.restDistanceMockMvc = MockMvcBuilders.standaloneSetup(distanceResource)
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
    public static Distance createEntity() {
        Distance distance = new Distance()
            .length(DEFAULT_LENGTH)
            .limit(DEFAULT_LIMIT);
        return distance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Distance createUpdatedEntity() {
        Distance distance = new Distance()
            .length(UPDATED_LENGTH)
            .limit(UPDATED_LIMIT);
        return distance;
    }

    @BeforeEach
    public void initTest() {
        distanceRepository.deleteAll();
        distance = createEntity();
    }

    @Test
    public void createDistance() throws Exception {
        int databaseSizeBeforeCreate = distanceRepository.findAll().size();

        // Create the Distance
        restDistanceMockMvc.perform(post("/api/distances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(distance)))
            .andExpect(status().isCreated());

        // Validate the Distance in the database
        List<Distance> distanceList = distanceRepository.findAll();
        assertThat(distanceList).hasSize(databaseSizeBeforeCreate + 1);
        Distance testDistance = distanceList.get(distanceList.size() - 1);
        assertThat(testDistance.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testDistance.getLimit()).isEqualTo(DEFAULT_LIMIT);
    }

    @Test
    public void createDistanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = distanceRepository.findAll().size();

        // Create the Distance with an existing ID
        distance.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistanceMockMvc.perform(post("/api/distances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(distance)))
            .andExpect(status().isBadRequest());

        // Validate the Distance in the database
        List<Distance> distanceList = distanceRepository.findAll();
        assertThat(distanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllDistances() throws Exception {
        // Initialize the database
        distanceRepository.save(distance);

        // Get all the distanceList
        restDistanceMockMvc.perform(get("/api/distances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(distance.getId())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].limit").value(hasItem(DEFAULT_LIMIT)));
    }
    
    @Test
    public void getDistance() throws Exception {
        // Initialize the database
        distanceRepository.save(distance);

        // Get the distance
        restDistanceMockMvc.perform(get("/api/distances/{id}", distance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(distance.getId()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.doubleValue()))
            .andExpect(jsonPath("$.limit").value(DEFAULT_LIMIT));
    }

    @Test
    public void getNonExistingDistance() throws Exception {
        // Get the distance
        restDistanceMockMvc.perform(get("/api/distances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDistance() throws Exception {
        // Initialize the database
        distanceService.save(distance);

        int databaseSizeBeforeUpdate = distanceRepository.findAll().size();

        // Update the distance
        Distance updatedDistance = distanceRepository.findById(distance.getId()).get();
        updatedDistance
            .length(UPDATED_LENGTH)
            .limit(UPDATED_LIMIT);

        restDistanceMockMvc.perform(put("/api/distances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDistance)))
            .andExpect(status().isOk());

        // Validate the Distance in the database
        List<Distance> distanceList = distanceRepository.findAll();
        assertThat(distanceList).hasSize(databaseSizeBeforeUpdate);
        Distance testDistance = distanceList.get(distanceList.size() - 1);
        assertThat(testDistance.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testDistance.getLimit()).isEqualTo(UPDATED_LIMIT);
    }

    @Test
    public void updateNonExistingDistance() throws Exception {
        int databaseSizeBeforeUpdate = distanceRepository.findAll().size();

        // Create the Distance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistanceMockMvc.perform(put("/api/distances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(distance)))
            .andExpect(status().isBadRequest());

        // Validate the Distance in the database
        List<Distance> distanceList = distanceRepository.findAll();
        assertThat(distanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteDistance() throws Exception {
        // Initialize the database
        distanceService.save(distance);

        int databaseSizeBeforeDelete = distanceRepository.findAll().size();

        // Delete the distance
        restDistanceMockMvc.perform(delete("/api/distances/{id}", distance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Distance> distanceList = distanceRepository.findAll();
        assertThat(distanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
