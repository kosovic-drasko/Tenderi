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
import tender.service.criteria.HvalePonudeCriteria;

/**
 * Integration tests for the {@link HvalePonudeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HvalePonudeResourceIT {

    private static final Integer DEFAULT_SIFRA_POSTUPKA = 1;
    private static final Integer UPDATED_SIFRA_POSTUPKA = 2;
    private static final Integer SMALLER_SIFRA_POSTUPKA = 1 - 1;

    private static final Integer DEFAULT_BROJ_PARTIJE = 1;
    private static final Integer UPDATED_BROJ_PARTIJE = 2;
    private static final Integer SMALLER_BROJ_PARTIJE = 1 - 1;

    private static final String DEFAULT_INN = "AAAAAAAAAA";
    private static final String UPDATED_INN = "BBBBBBBBBB";

    private static final String DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA = "AAAAAAAAAA";
    private static final String UPDATED_FARMACEUTSKI_OBLIK_LIJEKA = "BBBBBBBBBB";

    private static final String DEFAULT_PAKOVANJE = "AAAAAAAAAA";
    private static final String UPDATED_PAKOVANJE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRAZENA_KOLICINA = 1;
    private static final Integer UPDATED_TRAZENA_KOLICINA = 2;
    private static final Integer SMALLER_TRAZENA_KOLICINA = 1 - 1;

    private static final Double DEFAULT_PROCIJENJENA_VRIJEDNOST = 1D;
    private static final Double UPDATED_PROCIJENJENA_VRIJEDNOST = 2D;
    private static final Double SMALLER_PROCIJENJENA_VRIJEDNOST = 1D - 1D;

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
    void getHvalePonudesByIdFiltering() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        Long id = hvalePonude.getId();

        defaultHvalePonudeShouldBeFound("id.equals=" + id);
        defaultHvalePonudeShouldNotBeFound("id.notEquals=" + id);

        defaultHvalePonudeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHvalePonudeShouldNotBeFound("id.greaterThan=" + id);

        defaultHvalePonudeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHvalePonudeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllHvalePonudesBySifraPostupkaIsEqualToSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where sifraPostupka equals to DEFAULT_SIFRA_POSTUPKA
        defaultHvalePonudeShouldBeFound("sifraPostupka.equals=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the hvalePonudeList where sifraPostupka equals to UPDATED_SIFRA_POSTUPKA
        defaultHvalePonudeShouldNotBeFound("sifraPostupka.equals=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllHvalePonudesBySifraPostupkaIsInShouldWork() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where sifraPostupka in DEFAULT_SIFRA_POSTUPKA or UPDATED_SIFRA_POSTUPKA
        defaultHvalePonudeShouldBeFound("sifraPostupka.in=" + DEFAULT_SIFRA_POSTUPKA + "," + UPDATED_SIFRA_POSTUPKA);

        // Get all the hvalePonudeList where sifraPostupka equals to UPDATED_SIFRA_POSTUPKA
        defaultHvalePonudeShouldNotBeFound("sifraPostupka.in=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllHvalePonudesBySifraPostupkaIsNullOrNotNull() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where sifraPostupka is not null
        defaultHvalePonudeShouldBeFound("sifraPostupka.specified=true");

        // Get all the hvalePonudeList where sifraPostupka is null
        defaultHvalePonudeShouldNotBeFound("sifraPostupka.specified=false");
    }

    @Test
    @Transactional
    void getAllHvalePonudesBySifraPostupkaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where sifraPostupka is greater than or equal to DEFAULT_SIFRA_POSTUPKA
        defaultHvalePonudeShouldBeFound("sifraPostupka.greaterThanOrEqual=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the hvalePonudeList where sifraPostupka is greater than or equal to UPDATED_SIFRA_POSTUPKA
        defaultHvalePonudeShouldNotBeFound("sifraPostupka.greaterThanOrEqual=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllHvalePonudesBySifraPostupkaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where sifraPostupka is less than or equal to DEFAULT_SIFRA_POSTUPKA
        defaultHvalePonudeShouldBeFound("sifraPostupka.lessThanOrEqual=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the hvalePonudeList where sifraPostupka is less than or equal to SMALLER_SIFRA_POSTUPKA
        defaultHvalePonudeShouldNotBeFound("sifraPostupka.lessThanOrEqual=" + SMALLER_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllHvalePonudesBySifraPostupkaIsLessThanSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where sifraPostupka is less than DEFAULT_SIFRA_POSTUPKA
        defaultHvalePonudeShouldNotBeFound("sifraPostupka.lessThan=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the hvalePonudeList where sifraPostupka is less than UPDATED_SIFRA_POSTUPKA
        defaultHvalePonudeShouldBeFound("sifraPostupka.lessThan=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllHvalePonudesBySifraPostupkaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where sifraPostupka is greater than DEFAULT_SIFRA_POSTUPKA
        defaultHvalePonudeShouldNotBeFound("sifraPostupka.greaterThan=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the hvalePonudeList where sifraPostupka is greater than SMALLER_SIFRA_POSTUPKA
        defaultHvalePonudeShouldBeFound("sifraPostupka.greaterThan=" + SMALLER_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByBrojPartijeIsEqualToSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where brojPartije equals to DEFAULT_BROJ_PARTIJE
        defaultHvalePonudeShouldBeFound("brojPartije.equals=" + DEFAULT_BROJ_PARTIJE);

        // Get all the hvalePonudeList where brojPartije equals to UPDATED_BROJ_PARTIJE
        defaultHvalePonudeShouldNotBeFound("brojPartije.equals=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByBrojPartijeIsInShouldWork() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where brojPartije in DEFAULT_BROJ_PARTIJE or UPDATED_BROJ_PARTIJE
        defaultHvalePonudeShouldBeFound("brojPartije.in=" + DEFAULT_BROJ_PARTIJE + "," + UPDATED_BROJ_PARTIJE);

        // Get all the hvalePonudeList where brojPartije equals to UPDATED_BROJ_PARTIJE
        defaultHvalePonudeShouldNotBeFound("brojPartije.in=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByBrojPartijeIsNullOrNotNull() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where brojPartije is not null
        defaultHvalePonudeShouldBeFound("brojPartije.specified=true");

        // Get all the hvalePonudeList where brojPartije is null
        defaultHvalePonudeShouldNotBeFound("brojPartije.specified=false");
    }

    @Test
    @Transactional
    void getAllHvalePonudesByBrojPartijeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where brojPartije is greater than or equal to DEFAULT_BROJ_PARTIJE
        defaultHvalePonudeShouldBeFound("brojPartije.greaterThanOrEqual=" + DEFAULT_BROJ_PARTIJE);

        // Get all the hvalePonudeList where brojPartije is greater than or equal to UPDATED_BROJ_PARTIJE
        defaultHvalePonudeShouldNotBeFound("brojPartije.greaterThanOrEqual=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByBrojPartijeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where brojPartije is less than or equal to DEFAULT_BROJ_PARTIJE
        defaultHvalePonudeShouldBeFound("brojPartije.lessThanOrEqual=" + DEFAULT_BROJ_PARTIJE);

        // Get all the hvalePonudeList where brojPartije is less than or equal to SMALLER_BROJ_PARTIJE
        defaultHvalePonudeShouldNotBeFound("brojPartije.lessThanOrEqual=" + SMALLER_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByBrojPartijeIsLessThanSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where brojPartije is less than DEFAULT_BROJ_PARTIJE
        defaultHvalePonudeShouldNotBeFound("brojPartije.lessThan=" + DEFAULT_BROJ_PARTIJE);

        // Get all the hvalePonudeList where brojPartije is less than UPDATED_BROJ_PARTIJE
        defaultHvalePonudeShouldBeFound("brojPartije.lessThan=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByBrojPartijeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where brojPartije is greater than DEFAULT_BROJ_PARTIJE
        defaultHvalePonudeShouldNotBeFound("brojPartije.greaterThan=" + DEFAULT_BROJ_PARTIJE);

        // Get all the hvalePonudeList where brojPartije is greater than SMALLER_BROJ_PARTIJE
        defaultHvalePonudeShouldBeFound("brojPartije.greaterThan=" + SMALLER_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByInnIsEqualToSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where inn equals to DEFAULT_INN
        defaultHvalePonudeShouldBeFound("inn.equals=" + DEFAULT_INN);

        // Get all the hvalePonudeList where inn equals to UPDATED_INN
        defaultHvalePonudeShouldNotBeFound("inn.equals=" + UPDATED_INN);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByInnIsInShouldWork() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where inn in DEFAULT_INN or UPDATED_INN
        defaultHvalePonudeShouldBeFound("inn.in=" + DEFAULT_INN + "," + UPDATED_INN);

        // Get all the hvalePonudeList where inn equals to UPDATED_INN
        defaultHvalePonudeShouldNotBeFound("inn.in=" + UPDATED_INN);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByInnIsNullOrNotNull() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where inn is not null
        defaultHvalePonudeShouldBeFound("inn.specified=true");

        // Get all the hvalePonudeList where inn is null
        defaultHvalePonudeShouldNotBeFound("inn.specified=false");
    }

    @Test
    @Transactional
    void getAllHvalePonudesByInnContainsSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where inn contains DEFAULT_INN
        defaultHvalePonudeShouldBeFound("inn.contains=" + DEFAULT_INN);

        // Get all the hvalePonudeList where inn contains UPDATED_INN
        defaultHvalePonudeShouldNotBeFound("inn.contains=" + UPDATED_INN);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByInnNotContainsSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where inn does not contain DEFAULT_INN
        defaultHvalePonudeShouldNotBeFound("inn.doesNotContain=" + DEFAULT_INN);

        // Get all the hvalePonudeList where inn does not contain UPDATED_INN
        defaultHvalePonudeShouldBeFound("inn.doesNotContain=" + UPDATED_INN);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByFarmaceutskiOblikLijekaIsEqualToSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where farmaceutskiOblikLijeka equals to DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA
        defaultHvalePonudeShouldBeFound("farmaceutskiOblikLijeka.equals=" + DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA);

        // Get all the hvalePonudeList where farmaceutskiOblikLijeka equals to UPDATED_FARMACEUTSKI_OBLIK_LIJEKA
        defaultHvalePonudeShouldNotBeFound("farmaceutskiOblikLijeka.equals=" + UPDATED_FARMACEUTSKI_OBLIK_LIJEKA);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByFarmaceutskiOblikLijekaIsInShouldWork() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where farmaceutskiOblikLijeka in DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA or UPDATED_FARMACEUTSKI_OBLIK_LIJEKA
        defaultHvalePonudeShouldBeFound(
            "farmaceutskiOblikLijeka.in=" + DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA + "," + UPDATED_FARMACEUTSKI_OBLIK_LIJEKA
        );

        // Get all the hvalePonudeList where farmaceutskiOblikLijeka equals to UPDATED_FARMACEUTSKI_OBLIK_LIJEKA
        defaultHvalePonudeShouldNotBeFound("farmaceutskiOblikLijeka.in=" + UPDATED_FARMACEUTSKI_OBLIK_LIJEKA);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByFarmaceutskiOblikLijekaIsNullOrNotNull() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where farmaceutskiOblikLijeka is not null
        defaultHvalePonudeShouldBeFound("farmaceutskiOblikLijeka.specified=true");

        // Get all the hvalePonudeList where farmaceutskiOblikLijeka is null
        defaultHvalePonudeShouldNotBeFound("farmaceutskiOblikLijeka.specified=false");
    }

    @Test
    @Transactional
    void getAllHvalePonudesByFarmaceutskiOblikLijekaContainsSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where farmaceutskiOblikLijeka contains DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA
        defaultHvalePonudeShouldBeFound("farmaceutskiOblikLijeka.contains=" + DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA);

        // Get all the hvalePonudeList where farmaceutskiOblikLijeka contains UPDATED_FARMACEUTSKI_OBLIK_LIJEKA
        defaultHvalePonudeShouldNotBeFound("farmaceutskiOblikLijeka.contains=" + UPDATED_FARMACEUTSKI_OBLIK_LIJEKA);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByFarmaceutskiOblikLijekaNotContainsSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where farmaceutskiOblikLijeka does not contain DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA
        defaultHvalePonudeShouldNotBeFound("farmaceutskiOblikLijeka.doesNotContain=" + DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA);

        // Get all the hvalePonudeList where farmaceutskiOblikLijeka does not contain UPDATED_FARMACEUTSKI_OBLIK_LIJEKA
        defaultHvalePonudeShouldBeFound("farmaceutskiOblikLijeka.doesNotContain=" + UPDATED_FARMACEUTSKI_OBLIK_LIJEKA);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByPakovanjeIsEqualToSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where pakovanje equals to DEFAULT_PAKOVANJE
        defaultHvalePonudeShouldBeFound("pakovanje.equals=" + DEFAULT_PAKOVANJE);

        // Get all the hvalePonudeList where pakovanje equals to UPDATED_PAKOVANJE
        defaultHvalePonudeShouldNotBeFound("pakovanje.equals=" + UPDATED_PAKOVANJE);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByPakovanjeIsInShouldWork() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where pakovanje in DEFAULT_PAKOVANJE or UPDATED_PAKOVANJE
        defaultHvalePonudeShouldBeFound("pakovanje.in=" + DEFAULT_PAKOVANJE + "," + UPDATED_PAKOVANJE);

        // Get all the hvalePonudeList where pakovanje equals to UPDATED_PAKOVANJE
        defaultHvalePonudeShouldNotBeFound("pakovanje.in=" + UPDATED_PAKOVANJE);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByPakovanjeIsNullOrNotNull() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where pakovanje is not null
        defaultHvalePonudeShouldBeFound("pakovanje.specified=true");

        // Get all the hvalePonudeList where pakovanje is null
        defaultHvalePonudeShouldNotBeFound("pakovanje.specified=false");
    }

    @Test
    @Transactional
    void getAllHvalePonudesByPakovanjeContainsSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where pakovanje contains DEFAULT_PAKOVANJE
        defaultHvalePonudeShouldBeFound("pakovanje.contains=" + DEFAULT_PAKOVANJE);

        // Get all the hvalePonudeList where pakovanje contains UPDATED_PAKOVANJE
        defaultHvalePonudeShouldNotBeFound("pakovanje.contains=" + UPDATED_PAKOVANJE);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByPakovanjeNotContainsSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where pakovanje does not contain DEFAULT_PAKOVANJE
        defaultHvalePonudeShouldNotBeFound("pakovanje.doesNotContain=" + DEFAULT_PAKOVANJE);

        // Get all the hvalePonudeList where pakovanje does not contain UPDATED_PAKOVANJE
        defaultHvalePonudeShouldBeFound("pakovanje.doesNotContain=" + UPDATED_PAKOVANJE);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByTrazenaKolicinaIsEqualToSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where trazenaKolicina equals to DEFAULT_TRAZENA_KOLICINA
        defaultHvalePonudeShouldBeFound("trazenaKolicina.equals=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the hvalePonudeList where trazenaKolicina equals to UPDATED_TRAZENA_KOLICINA
        defaultHvalePonudeShouldNotBeFound("trazenaKolicina.equals=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByTrazenaKolicinaIsInShouldWork() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where trazenaKolicina in DEFAULT_TRAZENA_KOLICINA or UPDATED_TRAZENA_KOLICINA
        defaultHvalePonudeShouldBeFound("trazenaKolicina.in=" + DEFAULT_TRAZENA_KOLICINA + "," + UPDATED_TRAZENA_KOLICINA);

        // Get all the hvalePonudeList where trazenaKolicina equals to UPDATED_TRAZENA_KOLICINA
        defaultHvalePonudeShouldNotBeFound("trazenaKolicina.in=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByTrazenaKolicinaIsNullOrNotNull() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where trazenaKolicina is not null
        defaultHvalePonudeShouldBeFound("trazenaKolicina.specified=true");

        // Get all the hvalePonudeList where trazenaKolicina is null
        defaultHvalePonudeShouldNotBeFound("trazenaKolicina.specified=false");
    }

    @Test
    @Transactional
    void getAllHvalePonudesByTrazenaKolicinaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where trazenaKolicina is greater than or equal to DEFAULT_TRAZENA_KOLICINA
        defaultHvalePonudeShouldBeFound("trazenaKolicina.greaterThanOrEqual=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the hvalePonudeList where trazenaKolicina is greater than or equal to UPDATED_TRAZENA_KOLICINA
        defaultHvalePonudeShouldNotBeFound("trazenaKolicina.greaterThanOrEqual=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByTrazenaKolicinaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where trazenaKolicina is less than or equal to DEFAULT_TRAZENA_KOLICINA
        defaultHvalePonudeShouldBeFound("trazenaKolicina.lessThanOrEqual=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the hvalePonudeList where trazenaKolicina is less than or equal to SMALLER_TRAZENA_KOLICINA
        defaultHvalePonudeShouldNotBeFound("trazenaKolicina.lessThanOrEqual=" + SMALLER_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByTrazenaKolicinaIsLessThanSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where trazenaKolicina is less than DEFAULT_TRAZENA_KOLICINA
        defaultHvalePonudeShouldNotBeFound("trazenaKolicina.lessThan=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the hvalePonudeList where trazenaKolicina is less than UPDATED_TRAZENA_KOLICINA
        defaultHvalePonudeShouldBeFound("trazenaKolicina.lessThan=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByTrazenaKolicinaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where trazenaKolicina is greater than DEFAULT_TRAZENA_KOLICINA
        defaultHvalePonudeShouldNotBeFound("trazenaKolicina.greaterThan=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the hvalePonudeList where trazenaKolicina is greater than SMALLER_TRAZENA_KOLICINA
        defaultHvalePonudeShouldBeFound("trazenaKolicina.greaterThan=" + SMALLER_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByProcijenjenaVrijednostIsEqualToSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where procijenjenaVrijednost equals to DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultHvalePonudeShouldBeFound("procijenjenaVrijednost.equals=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the hvalePonudeList where procijenjenaVrijednost equals to UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultHvalePonudeShouldNotBeFound("procijenjenaVrijednost.equals=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByProcijenjenaVrijednostIsInShouldWork() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where procijenjenaVrijednost in DEFAULT_PROCIJENJENA_VRIJEDNOST or UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultHvalePonudeShouldBeFound(
            "procijenjenaVrijednost.in=" + DEFAULT_PROCIJENJENA_VRIJEDNOST + "," + UPDATED_PROCIJENJENA_VRIJEDNOST
        );

        // Get all the hvalePonudeList where procijenjenaVrijednost equals to UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultHvalePonudeShouldNotBeFound("procijenjenaVrijednost.in=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByProcijenjenaVrijednostIsNullOrNotNull() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where procijenjenaVrijednost is not null
        defaultHvalePonudeShouldBeFound("procijenjenaVrijednost.specified=true");

        // Get all the hvalePonudeList where procijenjenaVrijednost is null
        defaultHvalePonudeShouldNotBeFound("procijenjenaVrijednost.specified=false");
    }

    @Test
    @Transactional
    void getAllHvalePonudesByProcijenjenaVrijednostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where procijenjenaVrijednost is greater than or equal to DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultHvalePonudeShouldBeFound("procijenjenaVrijednost.greaterThanOrEqual=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the hvalePonudeList where procijenjenaVrijednost is greater than or equal to UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultHvalePonudeShouldNotBeFound("procijenjenaVrijednost.greaterThanOrEqual=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByProcijenjenaVrijednostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where procijenjenaVrijednost is less than or equal to DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultHvalePonudeShouldBeFound("procijenjenaVrijednost.lessThanOrEqual=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the hvalePonudeList where procijenjenaVrijednost is less than or equal to SMALLER_PROCIJENJENA_VRIJEDNOST
        defaultHvalePonudeShouldNotBeFound("procijenjenaVrijednost.lessThanOrEqual=" + SMALLER_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByProcijenjenaVrijednostIsLessThanSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where procijenjenaVrijednost is less than DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultHvalePonudeShouldNotBeFound("procijenjenaVrijednost.lessThan=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the hvalePonudeList where procijenjenaVrijednost is less than UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultHvalePonudeShouldBeFound("procijenjenaVrijednost.lessThan=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllHvalePonudesByProcijenjenaVrijednostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList where procijenjenaVrijednost is greater than DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultHvalePonudeShouldNotBeFound("procijenjenaVrijednost.greaterThan=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the hvalePonudeList where procijenjenaVrijednost is greater than SMALLER_PROCIJENJENA_VRIJEDNOST
        defaultHvalePonudeShouldBeFound("procijenjenaVrijednost.greaterThan=" + SMALLER_PROCIJENJENA_VRIJEDNOST);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHvalePonudeShouldBeFound(String filter) throws Exception {
        restHvalePonudeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
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

        // Check, that the count call also returns 1
        restHvalePonudeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHvalePonudeShouldNotBeFound(String filter) throws Exception {
        restHvalePonudeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHvalePonudeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingHvalePonude() throws Exception {
        // Get the hvalePonude
        restHvalePonudeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
