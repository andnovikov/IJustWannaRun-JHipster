package ru.andnovikov.ijustwannarun.web.rest;

import ru.andnovikov.ijustwannarun.IJustWannaRunApp;
import ru.andnovikov.ijustwannarun.domain.UserRegistration;
import ru.andnovikov.ijustwannarun.repository.UserRegistrationRepository;
import ru.andnovikov.ijustwannarun.service.UserRegistrationService;
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

/**
 * Integration tests for the {@link UserRegistrationResource} REST controller.
 */
@SpringBootTest(classes = IJustWannaRunApp.class)
public class UserRegistrationResourceIT {

    private static final ZonedDateTime DEFAULT_REG_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REG_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_REG_NUMBER = 1;
    private static final Integer UPDATED_REG_NUMBER = 2;

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restUserRegistrationMockMvc;

    private UserRegistration userRegistration;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserRegistrationResource userRegistrationResource = new UserRegistrationResource(userRegistrationService);
        this.restUserRegistrationMockMvc = MockMvcBuilders.standaloneSetup(userRegistrationResource)
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
    public static UserRegistration createEntity() {
        UserRegistration userRegistration = new UserRegistration()
            .regDate(DEFAULT_REG_DATE)
            .regNumber(DEFAULT_REG_NUMBER);
        return userRegistration;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRegistration createUpdatedEntity() {
        UserRegistration userRegistration = new UserRegistration()
            .regDate(UPDATED_REG_DATE)
            .regNumber(UPDATED_REG_NUMBER);
        return userRegistration;
    }

    @BeforeEach
    public void initTest() {
        userRegistrationRepository.deleteAll();
        userRegistration = createEntity();
    }

    @Test
    public void createUserRegistration() throws Exception {
        int databaseSizeBeforeCreate = userRegistrationRepository.findAll().size();

        // Create the UserRegistration
        restUserRegistrationMockMvc.perform(post("/api/user-registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userRegistration)))
            .andExpect(status().isCreated());

        // Validate the UserRegistration in the database
        List<UserRegistration> userRegistrationList = userRegistrationRepository.findAll();
        assertThat(userRegistrationList).hasSize(databaseSizeBeforeCreate + 1);
        UserRegistration testUserRegistration = userRegistrationList.get(userRegistrationList.size() - 1);
        assertThat(testUserRegistration.getRegDate()).isEqualTo(DEFAULT_REG_DATE);
        assertThat(testUserRegistration.getRegNumber()).isEqualTo(DEFAULT_REG_NUMBER);
    }

    @Test
    public void createUserRegistrationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userRegistrationRepository.findAll().size();

        // Create the UserRegistration with an existing ID
        userRegistration.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserRegistrationMockMvc.perform(post("/api/user-registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userRegistration)))
            .andExpect(status().isBadRequest());

        // Validate the UserRegistration in the database
        List<UserRegistration> userRegistrationList = userRegistrationRepository.findAll();
        assertThat(userRegistrationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllUserRegistrations() throws Exception {
        // Initialize the database
        userRegistrationRepository.save(userRegistration);

        // Get all the userRegistrationList
        restUserRegistrationMockMvc.perform(get("/api/user-registrations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRegistration.getId())))
            .andExpect(jsonPath("$.[*].regDate").value(hasItem(sameInstant(DEFAULT_REG_DATE))))
            .andExpect(jsonPath("$.[*].regNumber").value(hasItem(DEFAULT_REG_NUMBER)));
    }
    
    @Test
    public void getUserRegistration() throws Exception {
        // Initialize the database
        userRegistrationRepository.save(userRegistration);

        // Get the userRegistration
        restUserRegistrationMockMvc.perform(get("/api/user-registrations/{id}", userRegistration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userRegistration.getId()))
            .andExpect(jsonPath("$.regDate").value(sameInstant(DEFAULT_REG_DATE)))
            .andExpect(jsonPath("$.regNumber").value(DEFAULT_REG_NUMBER));
    }

    @Test
    public void getNonExistingUserRegistration() throws Exception {
        // Get the userRegistration
        restUserRegistrationMockMvc.perform(get("/api/user-registrations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUserRegistration() throws Exception {
        // Initialize the database
        userRegistrationService.save(userRegistration);

        int databaseSizeBeforeUpdate = userRegistrationRepository.findAll().size();

        // Update the userRegistration
        UserRegistration updatedUserRegistration = userRegistrationRepository.findById(userRegistration.getId()).get();
        updatedUserRegistration
            .regDate(UPDATED_REG_DATE)
            .regNumber(UPDATED_REG_NUMBER);

        restUserRegistrationMockMvc.perform(put("/api/user-registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserRegistration)))
            .andExpect(status().isOk());

        // Validate the UserRegistration in the database
        List<UserRegistration> userRegistrationList = userRegistrationRepository.findAll();
        assertThat(userRegistrationList).hasSize(databaseSizeBeforeUpdate);
        UserRegistration testUserRegistration = userRegistrationList.get(userRegistrationList.size() - 1);
        assertThat(testUserRegistration.getRegDate()).isEqualTo(UPDATED_REG_DATE);
        assertThat(testUserRegistration.getRegNumber()).isEqualTo(UPDATED_REG_NUMBER);
    }

    @Test
    public void updateNonExistingUserRegistration() throws Exception {
        int databaseSizeBeforeUpdate = userRegistrationRepository.findAll().size();

        // Create the UserRegistration

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRegistrationMockMvc.perform(put("/api/user-registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userRegistration)))
            .andExpect(status().isBadRequest());

        // Validate the UserRegistration in the database
        List<UserRegistration> userRegistrationList = userRegistrationRepository.findAll();
        assertThat(userRegistrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteUserRegistration() throws Exception {
        // Initialize the database
        userRegistrationService.save(userRegistration);

        int databaseSizeBeforeDelete = userRegistrationRepository.findAll().size();

        // Delete the userRegistration
        restUserRegistrationMockMvc.perform(delete("/api/user-registrations/{id}", userRegistration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserRegistration> userRegistrationList = userRegistrationRepository.findAll();
        assertThat(userRegistrationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
