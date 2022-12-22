package tender.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tender.IntegrationTest;
import tender.domain.TenderiHome;
import tender.repository.TenderiHomeRepository;

/**
 * Integration tests for the {@link TenderiHomeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TenderiHomeResourceIT {

    private static final String ENTITY_API_URL = "/api/tenderi-homes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TenderiHomeRepository tenderiHomeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTenderiHomeMockMvc;

    private TenderiHome tenderiHome;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TenderiHome createEntity(EntityManager em) {
        TenderiHome tenderiHome = new TenderiHome();
        return tenderiHome;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TenderiHome createUpdatedEntity(EntityManager em) {
        TenderiHome tenderiHome = new TenderiHome();
        return tenderiHome;
    }

    @BeforeEach
    public void initTest() {
        tenderiHome = createEntity(em);
    }

    @Test
    @Transactional
    void getAllTenderiHomes() throws Exception {
        // Initialize the database
        tenderiHomeRepository.saveAndFlush(tenderiHome);

        // Get all the tenderiHomeList
        restTenderiHomeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tenderiHome.getId().intValue())));
    }

    @Test
    @Transactional
    void getTenderiHome() throws Exception {
        // Initialize the database
        tenderiHomeRepository.saveAndFlush(tenderiHome);

        // Get the tenderiHome
        restTenderiHomeMockMvc
            .perform(get(ENTITY_API_URL_ID, tenderiHome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tenderiHome.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTenderiHome() throws Exception {
        // Get the tenderiHome
        restTenderiHomeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
