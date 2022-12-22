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
import tender.domain.Specifikacije;
import tender.repository.SpecifikacijeRepository;
import tender.service.criteria.SpecifikacijeCriteria;

/**
 * Integration tests for the {@link SpecifikacijeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SpecifikacijeResourceIT {

    private static final Integer DEFAULT_SIFRA_POSTUPKA = 1;
    private static final Integer UPDATED_SIFRA_POSTUPKA = 2;
    private static final Integer SMALLER_SIFRA_POSTUPKA = 1 - 1;

    private static final Integer DEFAULT_BROJ_PARTIJE = 1;
    private static final Integer UPDATED_BROJ_PARTIJE = 2;
    private static final Integer SMALLER_BROJ_PARTIJE = 1 - 1;

    private static final String DEFAULT_ATC = "AAAAAAAAAA";
    private static final String UPDATED_ATC = "BBBBBBBBBB";

    private static final String DEFAULT_INN = "AAAAAAAAAA";
    private static final String UPDATED_INN = "BBBBBBBBBB";

    private static final String DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA = "AAAAAAAAAA";
    private static final String UPDATED_FARMACEUTSKI_OBLIK_LIJEKA = "BBBBBBBBBB";

    private static final String DEFAULT_JACINA_LIJEKA = "AAAAAAAAAA";
    private static final String UPDATED_JACINA_LIJEKA = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRAZENA_KOLICINA = 1;
    private static final Integer UPDATED_TRAZENA_KOLICINA = 2;
    private static final Integer SMALLER_TRAZENA_KOLICINA = 1 - 1;

    private static final String DEFAULT_PAKOVANJE = "AAAAAAAAAA";
    private static final String UPDATED_PAKOVANJE = "BBBBBBBBBB";

    private static final String DEFAULT_JEDINICA_MJERE = "AAAAAAAAAA";
    private static final String UPDATED_JEDINICA_MJERE = "BBBBBBBBBB";

    private static final Double DEFAULT_PROCIJENJENA_VRIJEDNOST = 1D;
    private static final Double UPDATED_PROCIJENJENA_VRIJEDNOST = 2D;
    private static final Double SMALLER_PROCIJENJENA_VRIJEDNOST = 1D - 1D;

    private static final Double DEFAULT_JEDINICNA_CIJENA = 1D;
    private static final Double UPDATED_JEDINICNA_CIJENA = 2D;
    private static final Double SMALLER_JEDINICNA_CIJENA = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/specifikacijes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SpecifikacijeRepository specifikacijeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpecifikacijeMockMvc;

    private Specifikacije specifikacije;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specifikacije createEntity(EntityManager em) {
        Specifikacije specifikacije = new Specifikacije()
            .sifraPostupka(DEFAULT_SIFRA_POSTUPKA)
            .brojPartije(DEFAULT_BROJ_PARTIJE)
            .atc(DEFAULT_ATC)
            .inn(DEFAULT_INN)
            .farmaceutskiOblikLijeka(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA)
            .jacinaLijeka(DEFAULT_JACINA_LIJEKA)
            .trazenaKolicina(DEFAULT_TRAZENA_KOLICINA)
            .pakovanje(DEFAULT_PAKOVANJE)
            .jedinicaMjere(DEFAULT_JEDINICA_MJERE)
            .procijenjenaVrijednost(DEFAULT_PROCIJENJENA_VRIJEDNOST)
            .jedinicnaCijena(DEFAULT_JEDINICNA_CIJENA);
        return specifikacije;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specifikacije createUpdatedEntity(EntityManager em) {
        Specifikacije specifikacije = new Specifikacije()
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .atc(UPDATED_ATC)
            .inn(UPDATED_INN)
            .farmaceutskiOblikLijeka(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA)
            .jacinaLijeka(UPDATED_JACINA_LIJEKA)
            .trazenaKolicina(UPDATED_TRAZENA_KOLICINA)
            .pakovanje(UPDATED_PAKOVANJE)
            .jedinicaMjere(UPDATED_JEDINICA_MJERE)
            .procijenjenaVrijednost(UPDATED_PROCIJENJENA_VRIJEDNOST)
            .jedinicnaCijena(UPDATED_JEDINICNA_CIJENA);
        return specifikacije;
    }

    @BeforeEach
    public void initTest() {
        specifikacije = createEntity(em);
    }

    @Test
    @Transactional
    void createSpecifikacije() throws Exception {
        int databaseSizeBeforeCreate = specifikacijeRepository.findAll().size();
        // Create the Specifikacije
        restSpecifikacijeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specifikacije)))
            .andExpect(status().isCreated());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeCreate + 1);
        Specifikacije testSpecifikacije = specifikacijeList.get(specifikacijeList.size() - 1);
        assertThat(testSpecifikacije.getSifraPostupka()).isEqualTo(DEFAULT_SIFRA_POSTUPKA);
        assertThat(testSpecifikacije.getBrojPartije()).isEqualTo(DEFAULT_BROJ_PARTIJE);
        assertThat(testSpecifikacije.getAtc()).isEqualTo(DEFAULT_ATC);
        assertThat(testSpecifikacije.getInn()).isEqualTo(DEFAULT_INN);
        assertThat(testSpecifikacije.getFarmaceutskiOblikLijeka()).isEqualTo(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA);
        assertThat(testSpecifikacije.getJacinaLijeka()).isEqualTo(DEFAULT_JACINA_LIJEKA);
        assertThat(testSpecifikacije.getTrazenaKolicina()).isEqualTo(DEFAULT_TRAZENA_KOLICINA);
        assertThat(testSpecifikacije.getPakovanje()).isEqualTo(DEFAULT_PAKOVANJE);
        assertThat(testSpecifikacije.getJedinicaMjere()).isEqualTo(DEFAULT_JEDINICA_MJERE);
        assertThat(testSpecifikacije.getProcijenjenaVrijednost()).isEqualTo(DEFAULT_PROCIJENJENA_VRIJEDNOST);
        assertThat(testSpecifikacije.getJedinicnaCijena()).isEqualTo(DEFAULT_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void createSpecifikacijeWithExistingId() throws Exception {
        // Create the Specifikacije with an existing ID
        specifikacije.setId(1L);

        int databaseSizeBeforeCreate = specifikacijeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecifikacijeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specifikacije)))
            .andExpect(status().isBadRequest());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSifraPostupkaIsRequired() throws Exception {
        int databaseSizeBeforeTest = specifikacijeRepository.findAll().size();
        // set the field null
        specifikacije.setSifraPostupka(null);

        // Create the Specifikacije, which fails.

        restSpecifikacijeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specifikacije)))
            .andExpect(status().isBadRequest());

        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBrojPartijeIsRequired() throws Exception {
        int databaseSizeBeforeTest = specifikacijeRepository.findAll().size();
        // set the field null
        specifikacije.setBrojPartije(null);

        // Create the Specifikacije, which fails.

        restSpecifikacijeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specifikacije)))
            .andExpect(status().isBadRequest());

        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProcijenjenaVrijednostIsRequired() throws Exception {
        int databaseSizeBeforeTest = specifikacijeRepository.findAll().size();
        // set the field null
        specifikacije.setProcijenjenaVrijednost(null);

        // Create the Specifikacije, which fails.

        restSpecifikacijeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specifikacije)))
            .andExpect(status().isBadRequest());

        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkJedinicnaCijenaIsRequired() throws Exception {
        int databaseSizeBeforeTest = specifikacijeRepository.findAll().size();
        // set the field null
        specifikacije.setJedinicnaCijena(null);

        // Create the Specifikacije, which fails.

        restSpecifikacijeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specifikacije)))
            .andExpect(status().isBadRequest());

        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSpecifikacijes() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList
        restSpecifikacijeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specifikacije.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].brojPartije").value(hasItem(DEFAULT_BROJ_PARTIJE)))
            .andExpect(jsonPath("$.[*].atc").value(hasItem(DEFAULT_ATC)))
            .andExpect(jsonPath("$.[*].inn").value(hasItem(DEFAULT_INN)))
            .andExpect(jsonPath("$.[*].farmaceutskiOblikLijeka").value(hasItem(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA)))
            .andExpect(jsonPath("$.[*].jacinaLijeka").value(hasItem(DEFAULT_JACINA_LIJEKA)))
            .andExpect(jsonPath("$.[*].trazenaKolicina").value(hasItem(DEFAULT_TRAZENA_KOLICINA)))
            .andExpect(jsonPath("$.[*].pakovanje").value(hasItem(DEFAULT_PAKOVANJE)))
            .andExpect(jsonPath("$.[*].jedinicaMjere").value(hasItem(DEFAULT_JEDINICA_MJERE)))
            .andExpect(jsonPath("$.[*].procijenjenaVrijednost").value(hasItem(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].jedinicnaCijena").value(hasItem(DEFAULT_JEDINICNA_CIJENA.doubleValue())));
    }

    @Test
    @Transactional
    void getSpecifikacije() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get the specifikacije
        restSpecifikacijeMockMvc
            .perform(get(ENTITY_API_URL_ID, specifikacije.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(specifikacije.getId().intValue()))
            .andExpect(jsonPath("$.sifraPostupka").value(DEFAULT_SIFRA_POSTUPKA))
            .andExpect(jsonPath("$.brojPartije").value(DEFAULT_BROJ_PARTIJE))
            .andExpect(jsonPath("$.atc").value(DEFAULT_ATC))
            .andExpect(jsonPath("$.inn").value(DEFAULT_INN))
            .andExpect(jsonPath("$.farmaceutskiOblikLijeka").value(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA))
            .andExpect(jsonPath("$.jacinaLijeka").value(DEFAULT_JACINA_LIJEKA))
            .andExpect(jsonPath("$.trazenaKolicina").value(DEFAULT_TRAZENA_KOLICINA))
            .andExpect(jsonPath("$.pakovanje").value(DEFAULT_PAKOVANJE))
            .andExpect(jsonPath("$.jedinicaMjere").value(DEFAULT_JEDINICA_MJERE))
            .andExpect(jsonPath("$.procijenjenaVrijednost").value(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue()))
            .andExpect(jsonPath("$.jedinicnaCijena").value(DEFAULT_JEDINICNA_CIJENA.doubleValue()));
    }

    @Test
    @Transactional
    void getSpecifikacijesByIdFiltering() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        Long id = specifikacije.getId();

        defaultSpecifikacijeShouldBeFound("id.equals=" + id);
        defaultSpecifikacijeShouldNotBeFound("id.notEquals=" + id);

        defaultSpecifikacijeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSpecifikacijeShouldNotBeFound("id.greaterThan=" + id);

        defaultSpecifikacijeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSpecifikacijeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesBySifraPostupkaIsEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where sifraPostupka equals to DEFAULT_SIFRA_POSTUPKA
        defaultSpecifikacijeShouldBeFound("sifraPostupka.equals=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the specifikacijeList where sifraPostupka equals to UPDATED_SIFRA_POSTUPKA
        defaultSpecifikacijeShouldNotBeFound("sifraPostupka.equals=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesBySifraPostupkaIsInShouldWork() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where sifraPostupka in DEFAULT_SIFRA_POSTUPKA or UPDATED_SIFRA_POSTUPKA
        defaultSpecifikacijeShouldBeFound("sifraPostupka.in=" + DEFAULT_SIFRA_POSTUPKA + "," + UPDATED_SIFRA_POSTUPKA);

        // Get all the specifikacijeList where sifraPostupka equals to UPDATED_SIFRA_POSTUPKA
        defaultSpecifikacijeShouldNotBeFound("sifraPostupka.in=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesBySifraPostupkaIsNullOrNotNull() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where sifraPostupka is not null
        defaultSpecifikacijeShouldBeFound("sifraPostupka.specified=true");

        // Get all the specifikacijeList where sifraPostupka is null
        defaultSpecifikacijeShouldNotBeFound("sifraPostupka.specified=false");
    }

    @Test
    @Transactional
    void getAllSpecifikacijesBySifraPostupkaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where sifraPostupka is greater than or equal to DEFAULT_SIFRA_POSTUPKA
        defaultSpecifikacijeShouldBeFound("sifraPostupka.greaterThanOrEqual=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the specifikacijeList where sifraPostupka is greater than or equal to UPDATED_SIFRA_POSTUPKA
        defaultSpecifikacijeShouldNotBeFound("sifraPostupka.greaterThanOrEqual=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesBySifraPostupkaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where sifraPostupka is less than or equal to DEFAULT_SIFRA_POSTUPKA
        defaultSpecifikacijeShouldBeFound("sifraPostupka.lessThanOrEqual=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the specifikacijeList where sifraPostupka is less than or equal to SMALLER_SIFRA_POSTUPKA
        defaultSpecifikacijeShouldNotBeFound("sifraPostupka.lessThanOrEqual=" + SMALLER_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesBySifraPostupkaIsLessThanSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where sifraPostupka is less than DEFAULT_SIFRA_POSTUPKA
        defaultSpecifikacijeShouldNotBeFound("sifraPostupka.lessThan=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the specifikacijeList where sifraPostupka is less than UPDATED_SIFRA_POSTUPKA
        defaultSpecifikacijeShouldBeFound("sifraPostupka.lessThan=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesBySifraPostupkaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where sifraPostupka is greater than DEFAULT_SIFRA_POSTUPKA
        defaultSpecifikacijeShouldNotBeFound("sifraPostupka.greaterThan=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the specifikacijeList where sifraPostupka is greater than SMALLER_SIFRA_POSTUPKA
        defaultSpecifikacijeShouldBeFound("sifraPostupka.greaterThan=" + SMALLER_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByBrojPartijeIsEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where brojPartije equals to DEFAULT_BROJ_PARTIJE
        defaultSpecifikacijeShouldBeFound("brojPartije.equals=" + DEFAULT_BROJ_PARTIJE);

        // Get all the specifikacijeList where brojPartije equals to UPDATED_BROJ_PARTIJE
        defaultSpecifikacijeShouldNotBeFound("brojPartije.equals=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByBrojPartijeIsInShouldWork() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where brojPartije in DEFAULT_BROJ_PARTIJE or UPDATED_BROJ_PARTIJE
        defaultSpecifikacijeShouldBeFound("brojPartije.in=" + DEFAULT_BROJ_PARTIJE + "," + UPDATED_BROJ_PARTIJE);

        // Get all the specifikacijeList where brojPartije equals to UPDATED_BROJ_PARTIJE
        defaultSpecifikacijeShouldNotBeFound("brojPartije.in=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByBrojPartijeIsNullOrNotNull() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where brojPartije is not null
        defaultSpecifikacijeShouldBeFound("brojPartije.specified=true");

        // Get all the specifikacijeList where brojPartije is null
        defaultSpecifikacijeShouldNotBeFound("brojPartije.specified=false");
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByBrojPartijeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where brojPartije is greater than or equal to DEFAULT_BROJ_PARTIJE
        defaultSpecifikacijeShouldBeFound("brojPartije.greaterThanOrEqual=" + DEFAULT_BROJ_PARTIJE);

        // Get all the specifikacijeList where brojPartije is greater than or equal to UPDATED_BROJ_PARTIJE
        defaultSpecifikacijeShouldNotBeFound("brojPartije.greaterThanOrEqual=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByBrojPartijeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where brojPartije is less than or equal to DEFAULT_BROJ_PARTIJE
        defaultSpecifikacijeShouldBeFound("brojPartije.lessThanOrEqual=" + DEFAULT_BROJ_PARTIJE);

        // Get all the specifikacijeList where brojPartije is less than or equal to SMALLER_BROJ_PARTIJE
        defaultSpecifikacijeShouldNotBeFound("brojPartije.lessThanOrEqual=" + SMALLER_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByBrojPartijeIsLessThanSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where brojPartije is less than DEFAULT_BROJ_PARTIJE
        defaultSpecifikacijeShouldNotBeFound("brojPartije.lessThan=" + DEFAULT_BROJ_PARTIJE);

        // Get all the specifikacijeList where brojPartije is less than UPDATED_BROJ_PARTIJE
        defaultSpecifikacijeShouldBeFound("brojPartije.lessThan=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByBrojPartijeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where brojPartije is greater than DEFAULT_BROJ_PARTIJE
        defaultSpecifikacijeShouldNotBeFound("brojPartije.greaterThan=" + DEFAULT_BROJ_PARTIJE);

        // Get all the specifikacijeList where brojPartije is greater than SMALLER_BROJ_PARTIJE
        defaultSpecifikacijeShouldBeFound("brojPartije.greaterThan=" + SMALLER_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByAtcIsEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where atc equals to DEFAULT_ATC
        defaultSpecifikacijeShouldBeFound("atc.equals=" + DEFAULT_ATC);

        // Get all the specifikacijeList where atc equals to UPDATED_ATC
        defaultSpecifikacijeShouldNotBeFound("atc.equals=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByAtcIsInShouldWork() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where atc in DEFAULT_ATC or UPDATED_ATC
        defaultSpecifikacijeShouldBeFound("atc.in=" + DEFAULT_ATC + "," + UPDATED_ATC);

        // Get all the specifikacijeList where atc equals to UPDATED_ATC
        defaultSpecifikacijeShouldNotBeFound("atc.in=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByAtcIsNullOrNotNull() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where atc is not null
        defaultSpecifikacijeShouldBeFound("atc.specified=true");

        // Get all the specifikacijeList where atc is null
        defaultSpecifikacijeShouldNotBeFound("atc.specified=false");
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByAtcContainsSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where atc contains DEFAULT_ATC
        defaultSpecifikacijeShouldBeFound("atc.contains=" + DEFAULT_ATC);

        // Get all the specifikacijeList where atc contains UPDATED_ATC
        defaultSpecifikacijeShouldNotBeFound("atc.contains=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByAtcNotContainsSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where atc does not contain DEFAULT_ATC
        defaultSpecifikacijeShouldNotBeFound("atc.doesNotContain=" + DEFAULT_ATC);

        // Get all the specifikacijeList where atc does not contain UPDATED_ATC
        defaultSpecifikacijeShouldBeFound("atc.doesNotContain=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByInnIsEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where inn equals to DEFAULT_INN
        defaultSpecifikacijeShouldBeFound("inn.equals=" + DEFAULT_INN);

        // Get all the specifikacijeList where inn equals to UPDATED_INN
        defaultSpecifikacijeShouldNotBeFound("inn.equals=" + UPDATED_INN);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByInnIsInShouldWork() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where inn in DEFAULT_INN or UPDATED_INN
        defaultSpecifikacijeShouldBeFound("inn.in=" + DEFAULT_INN + "," + UPDATED_INN);

        // Get all the specifikacijeList where inn equals to UPDATED_INN
        defaultSpecifikacijeShouldNotBeFound("inn.in=" + UPDATED_INN);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByInnIsNullOrNotNull() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where inn is not null
        defaultSpecifikacijeShouldBeFound("inn.specified=true");

        // Get all the specifikacijeList where inn is null
        defaultSpecifikacijeShouldNotBeFound("inn.specified=false");
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByInnContainsSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where inn contains DEFAULT_INN
        defaultSpecifikacijeShouldBeFound("inn.contains=" + DEFAULT_INN);

        // Get all the specifikacijeList where inn contains UPDATED_INN
        defaultSpecifikacijeShouldNotBeFound("inn.contains=" + UPDATED_INN);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByInnNotContainsSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where inn does not contain DEFAULT_INN
        defaultSpecifikacijeShouldNotBeFound("inn.doesNotContain=" + DEFAULT_INN);

        // Get all the specifikacijeList where inn does not contain UPDATED_INN
        defaultSpecifikacijeShouldBeFound("inn.doesNotContain=" + UPDATED_INN);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByFarmaceutskiOblikLijekaIsEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where farmaceutskiOblikLijeka equals to DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA
        defaultSpecifikacijeShouldBeFound("farmaceutskiOblikLijeka.equals=" + DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA);

        // Get all the specifikacijeList where farmaceutskiOblikLijeka equals to UPDATED_FARMACEUTSKI_OBLIK_LIJEKA
        defaultSpecifikacijeShouldNotBeFound("farmaceutskiOblikLijeka.equals=" + UPDATED_FARMACEUTSKI_OBLIK_LIJEKA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByFarmaceutskiOblikLijekaIsInShouldWork() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where farmaceutskiOblikLijeka in DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA or UPDATED_FARMACEUTSKI_OBLIK_LIJEKA
        defaultSpecifikacijeShouldBeFound(
            "farmaceutskiOblikLijeka.in=" + DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA + "," + UPDATED_FARMACEUTSKI_OBLIK_LIJEKA
        );

        // Get all the specifikacijeList where farmaceutskiOblikLijeka equals to UPDATED_FARMACEUTSKI_OBLIK_LIJEKA
        defaultSpecifikacijeShouldNotBeFound("farmaceutskiOblikLijeka.in=" + UPDATED_FARMACEUTSKI_OBLIK_LIJEKA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByFarmaceutskiOblikLijekaIsNullOrNotNull() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where farmaceutskiOblikLijeka is not null
        defaultSpecifikacijeShouldBeFound("farmaceutskiOblikLijeka.specified=true");

        // Get all the specifikacijeList where farmaceutskiOblikLijeka is null
        defaultSpecifikacijeShouldNotBeFound("farmaceutskiOblikLijeka.specified=false");
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByFarmaceutskiOblikLijekaContainsSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where farmaceutskiOblikLijeka contains DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA
        defaultSpecifikacijeShouldBeFound("farmaceutskiOblikLijeka.contains=" + DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA);

        // Get all the specifikacijeList where farmaceutskiOblikLijeka contains UPDATED_FARMACEUTSKI_OBLIK_LIJEKA
        defaultSpecifikacijeShouldNotBeFound("farmaceutskiOblikLijeka.contains=" + UPDATED_FARMACEUTSKI_OBLIK_LIJEKA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByFarmaceutskiOblikLijekaNotContainsSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where farmaceutskiOblikLijeka does not contain DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA
        defaultSpecifikacijeShouldNotBeFound("farmaceutskiOblikLijeka.doesNotContain=" + DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA);

        // Get all the specifikacijeList where farmaceutskiOblikLijeka does not contain UPDATED_FARMACEUTSKI_OBLIK_LIJEKA
        defaultSpecifikacijeShouldBeFound("farmaceutskiOblikLijeka.doesNotContain=" + UPDATED_FARMACEUTSKI_OBLIK_LIJEKA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByJacinaLijekaIsEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where jacinaLijeka equals to DEFAULT_JACINA_LIJEKA
        defaultSpecifikacijeShouldBeFound("jacinaLijeka.equals=" + DEFAULT_JACINA_LIJEKA);

        // Get all the specifikacijeList where jacinaLijeka equals to UPDATED_JACINA_LIJEKA
        defaultSpecifikacijeShouldNotBeFound("jacinaLijeka.equals=" + UPDATED_JACINA_LIJEKA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByJacinaLijekaIsInShouldWork() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where jacinaLijeka in DEFAULT_JACINA_LIJEKA or UPDATED_JACINA_LIJEKA
        defaultSpecifikacijeShouldBeFound("jacinaLijeka.in=" + DEFAULT_JACINA_LIJEKA + "," + UPDATED_JACINA_LIJEKA);

        // Get all the specifikacijeList where jacinaLijeka equals to UPDATED_JACINA_LIJEKA
        defaultSpecifikacijeShouldNotBeFound("jacinaLijeka.in=" + UPDATED_JACINA_LIJEKA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByJacinaLijekaIsNullOrNotNull() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where jacinaLijeka is not null
        defaultSpecifikacijeShouldBeFound("jacinaLijeka.specified=true");

        // Get all the specifikacijeList where jacinaLijeka is null
        defaultSpecifikacijeShouldNotBeFound("jacinaLijeka.specified=false");
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByJacinaLijekaContainsSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where jacinaLijeka contains DEFAULT_JACINA_LIJEKA
        defaultSpecifikacijeShouldBeFound("jacinaLijeka.contains=" + DEFAULT_JACINA_LIJEKA);

        // Get all the specifikacijeList where jacinaLijeka contains UPDATED_JACINA_LIJEKA
        defaultSpecifikacijeShouldNotBeFound("jacinaLijeka.contains=" + UPDATED_JACINA_LIJEKA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByJacinaLijekaNotContainsSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where jacinaLijeka does not contain DEFAULT_JACINA_LIJEKA
        defaultSpecifikacijeShouldNotBeFound("jacinaLijeka.doesNotContain=" + DEFAULT_JACINA_LIJEKA);

        // Get all the specifikacijeList where jacinaLijeka does not contain UPDATED_JACINA_LIJEKA
        defaultSpecifikacijeShouldBeFound("jacinaLijeka.doesNotContain=" + UPDATED_JACINA_LIJEKA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByTrazenaKolicinaIsEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where trazenaKolicina equals to DEFAULT_TRAZENA_KOLICINA
        defaultSpecifikacijeShouldBeFound("trazenaKolicina.equals=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the specifikacijeList where trazenaKolicina equals to UPDATED_TRAZENA_KOLICINA
        defaultSpecifikacijeShouldNotBeFound("trazenaKolicina.equals=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByTrazenaKolicinaIsInShouldWork() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where trazenaKolicina in DEFAULT_TRAZENA_KOLICINA or UPDATED_TRAZENA_KOLICINA
        defaultSpecifikacijeShouldBeFound("trazenaKolicina.in=" + DEFAULT_TRAZENA_KOLICINA + "," + UPDATED_TRAZENA_KOLICINA);

        // Get all the specifikacijeList where trazenaKolicina equals to UPDATED_TRAZENA_KOLICINA
        defaultSpecifikacijeShouldNotBeFound("trazenaKolicina.in=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByTrazenaKolicinaIsNullOrNotNull() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where trazenaKolicina is not null
        defaultSpecifikacijeShouldBeFound("trazenaKolicina.specified=true");

        // Get all the specifikacijeList where trazenaKolicina is null
        defaultSpecifikacijeShouldNotBeFound("trazenaKolicina.specified=false");
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByTrazenaKolicinaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where trazenaKolicina is greater than or equal to DEFAULT_TRAZENA_KOLICINA
        defaultSpecifikacijeShouldBeFound("trazenaKolicina.greaterThanOrEqual=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the specifikacijeList where trazenaKolicina is greater than or equal to UPDATED_TRAZENA_KOLICINA
        defaultSpecifikacijeShouldNotBeFound("trazenaKolicina.greaterThanOrEqual=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByTrazenaKolicinaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where trazenaKolicina is less than or equal to DEFAULT_TRAZENA_KOLICINA
        defaultSpecifikacijeShouldBeFound("trazenaKolicina.lessThanOrEqual=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the specifikacijeList where trazenaKolicina is less than or equal to SMALLER_TRAZENA_KOLICINA
        defaultSpecifikacijeShouldNotBeFound("trazenaKolicina.lessThanOrEqual=" + SMALLER_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByTrazenaKolicinaIsLessThanSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where trazenaKolicina is less than DEFAULT_TRAZENA_KOLICINA
        defaultSpecifikacijeShouldNotBeFound("trazenaKolicina.lessThan=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the specifikacijeList where trazenaKolicina is less than UPDATED_TRAZENA_KOLICINA
        defaultSpecifikacijeShouldBeFound("trazenaKolicina.lessThan=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByTrazenaKolicinaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where trazenaKolicina is greater than DEFAULT_TRAZENA_KOLICINA
        defaultSpecifikacijeShouldNotBeFound("trazenaKolicina.greaterThan=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the specifikacijeList where trazenaKolicina is greater than SMALLER_TRAZENA_KOLICINA
        defaultSpecifikacijeShouldBeFound("trazenaKolicina.greaterThan=" + SMALLER_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByPakovanjeIsEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where pakovanje equals to DEFAULT_PAKOVANJE
        defaultSpecifikacijeShouldBeFound("pakovanje.equals=" + DEFAULT_PAKOVANJE);

        // Get all the specifikacijeList where pakovanje equals to UPDATED_PAKOVANJE
        defaultSpecifikacijeShouldNotBeFound("pakovanje.equals=" + UPDATED_PAKOVANJE);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByPakovanjeIsInShouldWork() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where pakovanje in DEFAULT_PAKOVANJE or UPDATED_PAKOVANJE
        defaultSpecifikacijeShouldBeFound("pakovanje.in=" + DEFAULT_PAKOVANJE + "," + UPDATED_PAKOVANJE);

        // Get all the specifikacijeList where pakovanje equals to UPDATED_PAKOVANJE
        defaultSpecifikacijeShouldNotBeFound("pakovanje.in=" + UPDATED_PAKOVANJE);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByPakovanjeIsNullOrNotNull() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where pakovanje is not null
        defaultSpecifikacijeShouldBeFound("pakovanje.specified=true");

        // Get all the specifikacijeList where pakovanje is null
        defaultSpecifikacijeShouldNotBeFound("pakovanje.specified=false");
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByPakovanjeContainsSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where pakovanje contains DEFAULT_PAKOVANJE
        defaultSpecifikacijeShouldBeFound("pakovanje.contains=" + DEFAULT_PAKOVANJE);

        // Get all the specifikacijeList where pakovanje contains UPDATED_PAKOVANJE
        defaultSpecifikacijeShouldNotBeFound("pakovanje.contains=" + UPDATED_PAKOVANJE);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByPakovanjeNotContainsSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where pakovanje does not contain DEFAULT_PAKOVANJE
        defaultSpecifikacijeShouldNotBeFound("pakovanje.doesNotContain=" + DEFAULT_PAKOVANJE);

        // Get all the specifikacijeList where pakovanje does not contain UPDATED_PAKOVANJE
        defaultSpecifikacijeShouldBeFound("pakovanje.doesNotContain=" + UPDATED_PAKOVANJE);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByJedinicaMjereIsEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where jedinicaMjere equals to DEFAULT_JEDINICA_MJERE
        defaultSpecifikacijeShouldBeFound("jedinicaMjere.equals=" + DEFAULT_JEDINICA_MJERE);

        // Get all the specifikacijeList where jedinicaMjere equals to UPDATED_JEDINICA_MJERE
        defaultSpecifikacijeShouldNotBeFound("jedinicaMjere.equals=" + UPDATED_JEDINICA_MJERE);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByJedinicaMjereIsInShouldWork() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where jedinicaMjere in DEFAULT_JEDINICA_MJERE or UPDATED_JEDINICA_MJERE
        defaultSpecifikacijeShouldBeFound("jedinicaMjere.in=" + DEFAULT_JEDINICA_MJERE + "," + UPDATED_JEDINICA_MJERE);

        // Get all the specifikacijeList where jedinicaMjere equals to UPDATED_JEDINICA_MJERE
        defaultSpecifikacijeShouldNotBeFound("jedinicaMjere.in=" + UPDATED_JEDINICA_MJERE);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByJedinicaMjereIsNullOrNotNull() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where jedinicaMjere is not null
        defaultSpecifikacijeShouldBeFound("jedinicaMjere.specified=true");

        // Get all the specifikacijeList where jedinicaMjere is null
        defaultSpecifikacijeShouldNotBeFound("jedinicaMjere.specified=false");
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByJedinicaMjereContainsSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where jedinicaMjere contains DEFAULT_JEDINICA_MJERE
        defaultSpecifikacijeShouldBeFound("jedinicaMjere.contains=" + DEFAULT_JEDINICA_MJERE);

        // Get all the specifikacijeList where jedinicaMjere contains UPDATED_JEDINICA_MJERE
        defaultSpecifikacijeShouldNotBeFound("jedinicaMjere.contains=" + UPDATED_JEDINICA_MJERE);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByJedinicaMjereNotContainsSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where jedinicaMjere does not contain DEFAULT_JEDINICA_MJERE
        defaultSpecifikacijeShouldNotBeFound("jedinicaMjere.doesNotContain=" + DEFAULT_JEDINICA_MJERE);

        // Get all the specifikacijeList where jedinicaMjere does not contain UPDATED_JEDINICA_MJERE
        defaultSpecifikacijeShouldBeFound("jedinicaMjere.doesNotContain=" + UPDATED_JEDINICA_MJERE);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByProcijenjenaVrijednostIsEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where procijenjenaVrijednost equals to DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultSpecifikacijeShouldBeFound("procijenjenaVrijednost.equals=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the specifikacijeList where procijenjenaVrijednost equals to UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultSpecifikacijeShouldNotBeFound("procijenjenaVrijednost.equals=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByProcijenjenaVrijednostIsInShouldWork() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where procijenjenaVrijednost in DEFAULT_PROCIJENJENA_VRIJEDNOST or UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultSpecifikacijeShouldBeFound(
            "procijenjenaVrijednost.in=" + DEFAULT_PROCIJENJENA_VRIJEDNOST + "," + UPDATED_PROCIJENJENA_VRIJEDNOST
        );

        // Get all the specifikacijeList where procijenjenaVrijednost equals to UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultSpecifikacijeShouldNotBeFound("procijenjenaVrijednost.in=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByProcijenjenaVrijednostIsNullOrNotNull() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where procijenjenaVrijednost is not null
        defaultSpecifikacijeShouldBeFound("procijenjenaVrijednost.specified=true");

        // Get all the specifikacijeList where procijenjenaVrijednost is null
        defaultSpecifikacijeShouldNotBeFound("procijenjenaVrijednost.specified=false");
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByProcijenjenaVrijednostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where procijenjenaVrijednost is greater than or equal to DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultSpecifikacijeShouldBeFound("procijenjenaVrijednost.greaterThanOrEqual=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the specifikacijeList where procijenjenaVrijednost is greater than or equal to UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultSpecifikacijeShouldNotBeFound("procijenjenaVrijednost.greaterThanOrEqual=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByProcijenjenaVrijednostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where procijenjenaVrijednost is less than or equal to DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultSpecifikacijeShouldBeFound("procijenjenaVrijednost.lessThanOrEqual=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the specifikacijeList where procijenjenaVrijednost is less than or equal to SMALLER_PROCIJENJENA_VRIJEDNOST
        defaultSpecifikacijeShouldNotBeFound("procijenjenaVrijednost.lessThanOrEqual=" + SMALLER_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByProcijenjenaVrijednostIsLessThanSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where procijenjenaVrijednost is less than DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultSpecifikacijeShouldNotBeFound("procijenjenaVrijednost.lessThan=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the specifikacijeList where procijenjenaVrijednost is less than UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultSpecifikacijeShouldBeFound("procijenjenaVrijednost.lessThan=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByProcijenjenaVrijednostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where procijenjenaVrijednost is greater than DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultSpecifikacijeShouldNotBeFound("procijenjenaVrijednost.greaterThan=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the specifikacijeList where procijenjenaVrijednost is greater than SMALLER_PROCIJENJENA_VRIJEDNOST
        defaultSpecifikacijeShouldBeFound("procijenjenaVrijednost.greaterThan=" + SMALLER_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByJedinicnaCijenaIsEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where jedinicnaCijena equals to DEFAULT_JEDINICNA_CIJENA
        defaultSpecifikacijeShouldBeFound("jedinicnaCijena.equals=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the specifikacijeList where jedinicnaCijena equals to UPDATED_JEDINICNA_CIJENA
        defaultSpecifikacijeShouldNotBeFound("jedinicnaCijena.equals=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByJedinicnaCijenaIsInShouldWork() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where jedinicnaCijena in DEFAULT_JEDINICNA_CIJENA or UPDATED_JEDINICNA_CIJENA
        defaultSpecifikacijeShouldBeFound("jedinicnaCijena.in=" + DEFAULT_JEDINICNA_CIJENA + "," + UPDATED_JEDINICNA_CIJENA);

        // Get all the specifikacijeList where jedinicnaCijena equals to UPDATED_JEDINICNA_CIJENA
        defaultSpecifikacijeShouldNotBeFound("jedinicnaCijena.in=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByJedinicnaCijenaIsNullOrNotNull() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where jedinicnaCijena is not null
        defaultSpecifikacijeShouldBeFound("jedinicnaCijena.specified=true");

        // Get all the specifikacijeList where jedinicnaCijena is null
        defaultSpecifikacijeShouldNotBeFound("jedinicnaCijena.specified=false");
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByJedinicnaCijenaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where jedinicnaCijena is greater than or equal to DEFAULT_JEDINICNA_CIJENA
        defaultSpecifikacijeShouldBeFound("jedinicnaCijena.greaterThanOrEqual=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the specifikacijeList where jedinicnaCijena is greater than or equal to UPDATED_JEDINICNA_CIJENA
        defaultSpecifikacijeShouldNotBeFound("jedinicnaCijena.greaterThanOrEqual=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByJedinicnaCijenaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where jedinicnaCijena is less than or equal to DEFAULT_JEDINICNA_CIJENA
        defaultSpecifikacijeShouldBeFound("jedinicnaCijena.lessThanOrEqual=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the specifikacijeList where jedinicnaCijena is less than or equal to SMALLER_JEDINICNA_CIJENA
        defaultSpecifikacijeShouldNotBeFound("jedinicnaCijena.lessThanOrEqual=" + SMALLER_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByJedinicnaCijenaIsLessThanSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where jedinicnaCijena is less than DEFAULT_JEDINICNA_CIJENA
        defaultSpecifikacijeShouldNotBeFound("jedinicnaCijena.lessThan=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the specifikacijeList where jedinicnaCijena is less than UPDATED_JEDINICNA_CIJENA
        defaultSpecifikacijeShouldBeFound("jedinicnaCijena.lessThan=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllSpecifikacijesByJedinicnaCijenaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList where jedinicnaCijena is greater than DEFAULT_JEDINICNA_CIJENA
        defaultSpecifikacijeShouldNotBeFound("jedinicnaCijena.greaterThan=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the specifikacijeList where jedinicnaCijena is greater than SMALLER_JEDINICNA_CIJENA
        defaultSpecifikacijeShouldBeFound("jedinicnaCijena.greaterThan=" + SMALLER_JEDINICNA_CIJENA);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSpecifikacijeShouldBeFound(String filter) throws Exception {
        restSpecifikacijeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specifikacije.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].brojPartije").value(hasItem(DEFAULT_BROJ_PARTIJE)))
            .andExpect(jsonPath("$.[*].atc").value(hasItem(DEFAULT_ATC)))
            .andExpect(jsonPath("$.[*].inn").value(hasItem(DEFAULT_INN)))
            .andExpect(jsonPath("$.[*].farmaceutskiOblikLijeka").value(hasItem(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA)))
            .andExpect(jsonPath("$.[*].jacinaLijeka").value(hasItem(DEFAULT_JACINA_LIJEKA)))
            .andExpect(jsonPath("$.[*].trazenaKolicina").value(hasItem(DEFAULT_TRAZENA_KOLICINA)))
            .andExpect(jsonPath("$.[*].pakovanje").value(hasItem(DEFAULT_PAKOVANJE)))
            .andExpect(jsonPath("$.[*].jedinicaMjere").value(hasItem(DEFAULT_JEDINICA_MJERE)))
            .andExpect(jsonPath("$.[*].procijenjenaVrijednost").value(hasItem(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].jedinicnaCijena").value(hasItem(DEFAULT_JEDINICNA_CIJENA.doubleValue())));

        // Check, that the count call also returns 1
        restSpecifikacijeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSpecifikacijeShouldNotBeFound(String filter) throws Exception {
        restSpecifikacijeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSpecifikacijeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSpecifikacije() throws Exception {
        // Get the specifikacije
        restSpecifikacijeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSpecifikacije() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        int databaseSizeBeforeUpdate = specifikacijeRepository.findAll().size();

        // Update the specifikacije
        Specifikacije updatedSpecifikacije = specifikacijeRepository.findById(specifikacije.getId()).get();
        // Disconnect from session so that the updates on updatedSpecifikacije are not directly saved in db
        em.detach(updatedSpecifikacije);
        updatedSpecifikacije
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .atc(UPDATED_ATC)
            .inn(UPDATED_INN)
            .farmaceutskiOblikLijeka(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA)
            .jacinaLijeka(UPDATED_JACINA_LIJEKA)
            .trazenaKolicina(UPDATED_TRAZENA_KOLICINA)
            .pakovanje(UPDATED_PAKOVANJE)
            .jedinicaMjere(UPDATED_JEDINICA_MJERE)
            .procijenjenaVrijednost(UPDATED_PROCIJENJENA_VRIJEDNOST)
            .jedinicnaCijena(UPDATED_JEDINICNA_CIJENA);

        restSpecifikacijeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSpecifikacije.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSpecifikacije))
            )
            .andExpect(status().isOk());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeUpdate);
        Specifikacije testSpecifikacije = specifikacijeList.get(specifikacijeList.size() - 1);
        assertThat(testSpecifikacije.getSifraPostupka()).isEqualTo(UPDATED_SIFRA_POSTUPKA);
        assertThat(testSpecifikacije.getBrojPartije()).isEqualTo(UPDATED_BROJ_PARTIJE);
        assertThat(testSpecifikacije.getAtc()).isEqualTo(UPDATED_ATC);
        assertThat(testSpecifikacije.getInn()).isEqualTo(UPDATED_INN);
        assertThat(testSpecifikacije.getFarmaceutskiOblikLijeka()).isEqualTo(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA);
        assertThat(testSpecifikacije.getJacinaLijeka()).isEqualTo(UPDATED_JACINA_LIJEKA);
        assertThat(testSpecifikacije.getTrazenaKolicina()).isEqualTo(UPDATED_TRAZENA_KOLICINA);
        assertThat(testSpecifikacije.getPakovanje()).isEqualTo(UPDATED_PAKOVANJE);
        assertThat(testSpecifikacije.getJedinicaMjere()).isEqualTo(UPDATED_JEDINICA_MJERE);
        assertThat(testSpecifikacije.getProcijenjenaVrijednost()).isEqualTo(UPDATED_PROCIJENJENA_VRIJEDNOST);
        assertThat(testSpecifikacije.getJedinicnaCijena()).isEqualTo(UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void putNonExistingSpecifikacije() throws Exception {
        int databaseSizeBeforeUpdate = specifikacijeRepository.findAll().size();
        specifikacije.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecifikacijeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, specifikacije.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specifikacije))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSpecifikacije() throws Exception {
        int databaseSizeBeforeUpdate = specifikacijeRepository.findAll().size();
        specifikacije.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecifikacijeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specifikacije))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSpecifikacije() throws Exception {
        int databaseSizeBeforeUpdate = specifikacijeRepository.findAll().size();
        specifikacije.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecifikacijeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specifikacije)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSpecifikacijeWithPatch() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        int databaseSizeBeforeUpdate = specifikacijeRepository.findAll().size();

        // Update the specifikacije using partial update
        Specifikacije partialUpdatedSpecifikacije = new Specifikacije();
        partialUpdatedSpecifikacije.setId(specifikacije.getId());

        partialUpdatedSpecifikacije
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .inn(UPDATED_INN)
            .jacinaLijeka(UPDATED_JACINA_LIJEKA)
            .trazenaKolicina(UPDATED_TRAZENA_KOLICINA)
            .pakovanje(UPDATED_PAKOVANJE)
            .procijenjenaVrijednost(UPDATED_PROCIJENJENA_VRIJEDNOST);

        restSpecifikacijeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpecifikacije.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpecifikacije))
            )
            .andExpect(status().isOk());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeUpdate);
        Specifikacije testSpecifikacije = specifikacijeList.get(specifikacijeList.size() - 1);
        assertThat(testSpecifikacije.getSifraPostupka()).isEqualTo(DEFAULT_SIFRA_POSTUPKA);
        assertThat(testSpecifikacije.getBrojPartije()).isEqualTo(UPDATED_BROJ_PARTIJE);
        assertThat(testSpecifikacije.getAtc()).isEqualTo(DEFAULT_ATC);
        assertThat(testSpecifikacije.getInn()).isEqualTo(UPDATED_INN);
        assertThat(testSpecifikacije.getFarmaceutskiOblikLijeka()).isEqualTo(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA);
        assertThat(testSpecifikacije.getJacinaLijeka()).isEqualTo(UPDATED_JACINA_LIJEKA);
        assertThat(testSpecifikacije.getTrazenaKolicina()).isEqualTo(UPDATED_TRAZENA_KOLICINA);
        assertThat(testSpecifikacije.getPakovanje()).isEqualTo(UPDATED_PAKOVANJE);
        assertThat(testSpecifikacije.getJedinicaMjere()).isEqualTo(DEFAULT_JEDINICA_MJERE);
        assertThat(testSpecifikacije.getProcijenjenaVrijednost()).isEqualTo(UPDATED_PROCIJENJENA_VRIJEDNOST);
        assertThat(testSpecifikacije.getJedinicnaCijena()).isEqualTo(DEFAULT_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void fullUpdateSpecifikacijeWithPatch() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        int databaseSizeBeforeUpdate = specifikacijeRepository.findAll().size();

        // Update the specifikacije using partial update
        Specifikacije partialUpdatedSpecifikacije = new Specifikacije();
        partialUpdatedSpecifikacije.setId(specifikacije.getId());

        partialUpdatedSpecifikacije
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .atc(UPDATED_ATC)
            .inn(UPDATED_INN)
            .farmaceutskiOblikLijeka(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA)
            .jacinaLijeka(UPDATED_JACINA_LIJEKA)
            .trazenaKolicina(UPDATED_TRAZENA_KOLICINA)
            .pakovanje(UPDATED_PAKOVANJE)
            .jedinicaMjere(UPDATED_JEDINICA_MJERE)
            .procijenjenaVrijednost(UPDATED_PROCIJENJENA_VRIJEDNOST)
            .jedinicnaCijena(UPDATED_JEDINICNA_CIJENA);

        restSpecifikacijeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpecifikacije.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpecifikacije))
            )
            .andExpect(status().isOk());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeUpdate);
        Specifikacije testSpecifikacije = specifikacijeList.get(specifikacijeList.size() - 1);
        assertThat(testSpecifikacije.getSifraPostupka()).isEqualTo(UPDATED_SIFRA_POSTUPKA);
        assertThat(testSpecifikacije.getBrojPartije()).isEqualTo(UPDATED_BROJ_PARTIJE);
        assertThat(testSpecifikacije.getAtc()).isEqualTo(UPDATED_ATC);
        assertThat(testSpecifikacije.getInn()).isEqualTo(UPDATED_INN);
        assertThat(testSpecifikacije.getFarmaceutskiOblikLijeka()).isEqualTo(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA);
        assertThat(testSpecifikacije.getJacinaLijeka()).isEqualTo(UPDATED_JACINA_LIJEKA);
        assertThat(testSpecifikacije.getTrazenaKolicina()).isEqualTo(UPDATED_TRAZENA_KOLICINA);
        assertThat(testSpecifikacije.getPakovanje()).isEqualTo(UPDATED_PAKOVANJE);
        assertThat(testSpecifikacije.getJedinicaMjere()).isEqualTo(UPDATED_JEDINICA_MJERE);
        assertThat(testSpecifikacije.getProcijenjenaVrijednost()).isEqualTo(UPDATED_PROCIJENJENA_VRIJEDNOST);
        assertThat(testSpecifikacije.getJedinicnaCijena()).isEqualTo(UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void patchNonExistingSpecifikacije() throws Exception {
        int databaseSizeBeforeUpdate = specifikacijeRepository.findAll().size();
        specifikacije.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecifikacijeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, specifikacije.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(specifikacije))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSpecifikacije() throws Exception {
        int databaseSizeBeforeUpdate = specifikacijeRepository.findAll().size();
        specifikacije.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecifikacijeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(specifikacije))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSpecifikacije() throws Exception {
        int databaseSizeBeforeUpdate = specifikacijeRepository.findAll().size();
        specifikacije.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecifikacijeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(specifikacije))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSpecifikacije() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        int databaseSizeBeforeDelete = specifikacijeRepository.findAll().size();

        // Delete the specifikacije
        restSpecifikacijeMockMvc
            .perform(delete(ENTITY_API_URL_ID, specifikacije.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
