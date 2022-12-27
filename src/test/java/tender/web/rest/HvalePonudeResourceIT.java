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
import tender.domain.HvalePonude;
import tender.repository.HvalePonudeRepository;

/**
 * Integration tests for the {@link HvalePonudeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HvalePonudeResourceIT {

    private static final Integer DEFAULT_SIFRA_POSTUPKA = 1;
    private static final Integer UPDATED_SIFRA_POSTUPKA = 2;

    private static final Integer DEFAULT_BROJ_PARTIJE = 1;
    private static final Integer UPDATED_BROJ_PARTIJE = 2;

    private static final String DEFAULT_INN = "AAAAAAAAAA";
    private static final String UPDATED_INN = "BBBBBBBBBB";

    private static final String DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA = "AAAAAAAAAA";
    private static final String UPDATED_FARMACEUTSKI_OBLIK_LIJEKA = "BBBBBBBBBB";

    private static final String DEFAULT_PAKOVANJE = "AAAAAAAAAA";
    private static final String UPDATED_PAKOVANJE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRAZENA_KOLICINA = 1;
    private static final Integer UPDATED_TRAZENA_KOLICINA = 2;

    private static final Double DEFAULT_PROCIJENJENA_VRIJEDNOST = 1D;
    private static final Double UPDATED_PROCIJENJENA_VRIJEDNOST = 2D;

    private static final String ENTITY_API_URL = "/api/hvale-ponudes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HvalePonudeRepository hvalePonudeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHvalePonudeMockMvc;

    private HvalePonude hvalePonude;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HvalePonude createEntity(EntityManager em) {
        HvalePonude hvalePonude = new HvalePonude()
            .sifraPostupka(DEFAULT_SIFRA_POSTUPKA)
            .brojPartije(DEFAULT_BROJ_PARTIJE)
            .inn(DEFAULT_INN)
            .farmaceutskiOblikLijeka(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA)
            .pakovanje(DEFAULT_PAKOVANJE)
            .trazenaKolicina(DEFAULT_TRAZENA_KOLICINA)
            .procijenjenaVrijednost(DEFAULT_PROCIJENJENA_VRIJEDNOST);
        return hvalePonude;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HvalePonude createUpdatedEntity(EntityManager em) {
        HvalePonude hvalePonude = new HvalePonude()
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .inn(UPDATED_INN)
            .farmaceutskiOblikLijeka(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA)
            .pakovanje(UPDATED_PAKOVANJE)
            .trazenaKolicina(UPDATED_TRAZENA_KOLICINA)
            .procijenjenaVrijednost(UPDATED_PROCIJENJENA_VRIJEDNOST);
        return hvalePonude;
    }

    @BeforeEach
    public void initTest() {
        hvalePonude = createEntity(em);
    }

    @Test
    @Transactional
    void getAllHvalePonudes() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList
        restHvalePonudeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hvalePonude.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].brojPartije").value(hasItem(DEFAULT_BROJ_PARTIJE)))
            .andExpect(jsonPath("$.[*].inn").value(hasItem(DEFAULT_INN)))
            .andExpect(jsonPath("$.[*].farmaceutskiOblikLijeka").value(hasItem(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA)))
            .andExpect(jsonPath("$.[*].pakovanje").value(hasItem(DEFAULT_PAKOVANJE)))
            .andExpect(jsonPath("$.[*].trazenaKolicina").value(hasItem(DEFAULT_TRAZENA_KOLICINA)))
            .andExpect(jsonPath("$.[*].procijenjenaVrijednost").value(hasItem(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue())));
    }

    @Test
    @Transactional
    void getHvalePonude() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get the hvalePonude
        restHvalePonudeMockMvc
            .perform(get(ENTITY_API_URL_ID, hvalePonude.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hvalePonude.getId().intValue()))
            .andExpect(jsonPath("$.sifraPostupka").value(DEFAULT_SIFRA_POSTUPKA))
            .andExpect(jsonPath("$.brojPartije").value(DEFAULT_BROJ_PARTIJE))
            .andExpect(jsonPath("$.inn").value(DEFAULT_INN))
            .andExpect(jsonPath("$.farmaceutskiOblikLijeka").value(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA))
            .andExpect(jsonPath("$.pakovanje").value(DEFAULT_PAKOVANJE))
            .andExpect(jsonPath("$.trazenaKolicina").value(DEFAULT_TRAZENA_KOLICINA))
            .andExpect(jsonPath("$.procijenjenaVrijednost").value(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingHvalePonude() throws Exception {
        // Get the hvalePonude
        restHvalePonudeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
