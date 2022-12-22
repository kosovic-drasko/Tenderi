package tender.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
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
import tender.domain.Postupci;
import tender.repository.PostupciRepository;
import tender.service.criteria.PostupciCriteria;

/**
 * Integration tests for the {@link PostupciResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PostupciResourceIT {

    private static final Integer DEFAULT_SIFRA_POSTUPKA = 1;
    private static final Integer UPDATED_SIFRA_POSTUPKA = 2;
    private static final Integer SMALLER_SIFRA_POSTUPKA = 1 - 1;

    private static final String DEFAULT_BROJ_TENDERA = "AAAAAAAAAA";
    private static final String UPDATED_BROJ_TENDERA = "BBBBBBBBBB";

    private static final String DEFAULT_OPIS_POSTUPKA = "AAAAAAAAAA";
    private static final String UPDATED_OPIS_POSTUPKA = "BBBBBBBBBB";

    private static final String DEFAULT_VRSTA_POSTUPKA = "AAAAAAAAAA";
    private static final String UPDATED_VRSTA_POSTUPKA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUM_OBJAVE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM_OBJAVE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATUM_OBJAVE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATUM_OTVARANJA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM_OTVARANJA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATUM_OTVARANJA = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_KRITERIJUM_CIJENA = 1;
    private static final Integer UPDATED_KRITERIJUM_CIJENA = 2;
    private static final Integer SMALLER_KRITERIJUM_CIJENA = 1 - 1;

    private static final Integer DEFAULT_DRUGI_KRITERIJUM = 1;
    private static final Integer UPDATED_DRUGI_KRITERIJUM = 2;
    private static final Integer SMALLER_DRUGI_KRITERIJUM = 1 - 1;

    private static final String ENTITY_API_URL = "/api/postupcis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PostupciRepository postupciRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostupciMockMvc;

    private Postupci postupci;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Postupci createEntity(EntityManager em) {
        Postupci postupci = new Postupci()
            .sifraPostupka(DEFAULT_SIFRA_POSTUPKA)
            .brojTendera(DEFAULT_BROJ_TENDERA)
            .opisPostupka(DEFAULT_OPIS_POSTUPKA)
            .vrstaPostupka(DEFAULT_VRSTA_POSTUPKA)
            .datumObjave(DEFAULT_DATUM_OBJAVE)
            .datumOtvaranja(DEFAULT_DATUM_OTVARANJA)
            .kriterijumCijena(DEFAULT_KRITERIJUM_CIJENA)
            .drugiKriterijum(DEFAULT_DRUGI_KRITERIJUM);
        return postupci;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Postupci createUpdatedEntity(EntityManager em) {
        Postupci postupci = new Postupci()
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .brojTendera(UPDATED_BROJ_TENDERA)
            .opisPostupka(UPDATED_OPIS_POSTUPKA)
            .vrstaPostupka(UPDATED_VRSTA_POSTUPKA)
            .datumObjave(UPDATED_DATUM_OBJAVE)
            .datumOtvaranja(UPDATED_DATUM_OTVARANJA)
            .kriterijumCijena(UPDATED_KRITERIJUM_CIJENA)
            .drugiKriterijum(UPDATED_DRUGI_KRITERIJUM);
        return postupci;
    }

    @BeforeEach
    public void initTest() {
        postupci = createEntity(em);
    }

    @Test
    @Transactional
    void createPostupci() throws Exception {
        int databaseSizeBeforeCreate = postupciRepository.findAll().size();
        // Create the Postupci
        restPostupciMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postupci)))
            .andExpect(status().isCreated());

        // Validate the Postupci in the database
        List<Postupci> postupciList = postupciRepository.findAll();
        assertThat(postupciList).hasSize(databaseSizeBeforeCreate + 1);
        Postupci testPostupci = postupciList.get(postupciList.size() - 1);
        assertThat(testPostupci.getSifraPostupka()).isEqualTo(DEFAULT_SIFRA_POSTUPKA);
        assertThat(testPostupci.getBrojTendera()).isEqualTo(DEFAULT_BROJ_TENDERA);
        assertThat(testPostupci.getOpisPostupka()).isEqualTo(DEFAULT_OPIS_POSTUPKA);
        assertThat(testPostupci.getVrstaPostupka()).isEqualTo(DEFAULT_VRSTA_POSTUPKA);
        assertThat(testPostupci.getDatumObjave()).isEqualTo(DEFAULT_DATUM_OBJAVE);
        assertThat(testPostupci.getDatumOtvaranja()).isEqualTo(DEFAULT_DATUM_OTVARANJA);
        assertThat(testPostupci.getKriterijumCijena()).isEqualTo(DEFAULT_KRITERIJUM_CIJENA);
        assertThat(testPostupci.getDrugiKriterijum()).isEqualTo(DEFAULT_DRUGI_KRITERIJUM);
    }

    @Test
    @Transactional
    void createPostupciWithExistingId() throws Exception {
        // Create the Postupci with an existing ID
        postupci.setId(1L);

        int databaseSizeBeforeCreate = postupciRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostupciMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postupci)))
            .andExpect(status().isBadRequest());

        // Validate the Postupci in the database
        List<Postupci> postupciList = postupciRepository.findAll();
        assertThat(postupciList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSifraPostupkaIsRequired() throws Exception {
        int databaseSizeBeforeTest = postupciRepository.findAll().size();
        // set the field null
        postupci.setSifraPostupka(null);

        // Create the Postupci, which fails.

        restPostupciMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postupci)))
            .andExpect(status().isBadRequest());

        List<Postupci> postupciList = postupciRepository.findAll();
        assertThat(postupciList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOpisPostupkaIsRequired() throws Exception {
        int databaseSizeBeforeTest = postupciRepository.findAll().size();
        // set the field null
        postupci.setOpisPostupka(null);

        // Create the Postupci, which fails.

        restPostupciMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postupci)))
            .andExpect(status().isBadRequest());

        List<Postupci> postupciList = postupciRepository.findAll();
        assertThat(postupciList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVrstaPostupkaIsRequired() throws Exception {
        int databaseSizeBeforeTest = postupciRepository.findAll().size();
        // set the field null
        postupci.setVrstaPostupka(null);

        // Create the Postupci, which fails.

        restPostupciMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postupci)))
            .andExpect(status().isBadRequest());

        List<Postupci> postupciList = postupciRepository.findAll();
        assertThat(postupciList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkKriterijumCijenaIsRequired() throws Exception {
        int databaseSizeBeforeTest = postupciRepository.findAll().size();
        // set the field null
        postupci.setKriterijumCijena(null);

        // Create the Postupci, which fails.

        restPostupciMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postupci)))
            .andExpect(status().isBadRequest());

        List<Postupci> postupciList = postupciRepository.findAll();
        assertThat(postupciList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDrugiKriterijumIsRequired() throws Exception {
        int databaseSizeBeforeTest = postupciRepository.findAll().size();
        // set the field null
        postupci.setDrugiKriterijum(null);

        // Create the Postupci, which fails.

        restPostupciMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postupci)))
            .andExpect(status().isBadRequest());

        List<Postupci> postupciList = postupciRepository.findAll();
        assertThat(postupciList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPostupcis() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList
        restPostupciMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postupci.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].brojTendera").value(hasItem(DEFAULT_BROJ_TENDERA)))
            .andExpect(jsonPath("$.[*].opisPostupka").value(hasItem(DEFAULT_OPIS_POSTUPKA)))
            .andExpect(jsonPath("$.[*].vrstaPostupka").value(hasItem(DEFAULT_VRSTA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].datumObjave").value(hasItem(DEFAULT_DATUM_OBJAVE.toString())))
            .andExpect(jsonPath("$.[*].datumOtvaranja").value(hasItem(DEFAULT_DATUM_OTVARANJA.toString())))
            .andExpect(jsonPath("$.[*].kriterijumCijena").value(hasItem(DEFAULT_KRITERIJUM_CIJENA)))
            .andExpect(jsonPath("$.[*].drugiKriterijum").value(hasItem(DEFAULT_DRUGI_KRITERIJUM)));
    }

    @Test
    @Transactional
    void getPostupci() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get the postupci
        restPostupciMockMvc
            .perform(get(ENTITY_API_URL_ID, postupci.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postupci.getId().intValue()))
            .andExpect(jsonPath("$.sifraPostupka").value(DEFAULT_SIFRA_POSTUPKA))
            .andExpect(jsonPath("$.brojTendera").value(DEFAULT_BROJ_TENDERA))
            .andExpect(jsonPath("$.opisPostupka").value(DEFAULT_OPIS_POSTUPKA))
            .andExpect(jsonPath("$.vrstaPostupka").value(DEFAULT_VRSTA_POSTUPKA))
            .andExpect(jsonPath("$.datumObjave").value(DEFAULT_DATUM_OBJAVE.toString()))
            .andExpect(jsonPath("$.datumOtvaranja").value(DEFAULT_DATUM_OTVARANJA.toString()))
            .andExpect(jsonPath("$.kriterijumCijena").value(DEFAULT_KRITERIJUM_CIJENA))
            .andExpect(jsonPath("$.drugiKriterijum").value(DEFAULT_DRUGI_KRITERIJUM));
    }

    @Test
    @Transactional
    void getPostupcisByIdFiltering() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        Long id = postupci.getId();

        defaultPostupciShouldBeFound("id.equals=" + id);
        defaultPostupciShouldNotBeFound("id.notEquals=" + id);

        defaultPostupciShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPostupciShouldNotBeFound("id.greaterThan=" + id);

        defaultPostupciShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPostupciShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPostupcisBySifraPostupkaIsEqualToSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where sifraPostupka equals to DEFAULT_SIFRA_POSTUPKA
        defaultPostupciShouldBeFound("sifraPostupka.equals=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the postupciList where sifraPostupka equals to UPDATED_SIFRA_POSTUPKA
        defaultPostupciShouldNotBeFound("sifraPostupka.equals=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPostupcisBySifraPostupkaIsInShouldWork() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where sifraPostupka in DEFAULT_SIFRA_POSTUPKA or UPDATED_SIFRA_POSTUPKA
        defaultPostupciShouldBeFound("sifraPostupka.in=" + DEFAULT_SIFRA_POSTUPKA + "," + UPDATED_SIFRA_POSTUPKA);

        // Get all the postupciList where sifraPostupka equals to UPDATED_SIFRA_POSTUPKA
        defaultPostupciShouldNotBeFound("sifraPostupka.in=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPostupcisBySifraPostupkaIsNullOrNotNull() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where sifraPostupka is not null
        defaultPostupciShouldBeFound("sifraPostupka.specified=true");

        // Get all the postupciList where sifraPostupka is null
        defaultPostupciShouldNotBeFound("sifraPostupka.specified=false");
    }

    @Test
    @Transactional
    void getAllPostupcisBySifraPostupkaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where sifraPostupka is greater than or equal to DEFAULT_SIFRA_POSTUPKA
        defaultPostupciShouldBeFound("sifraPostupka.greaterThanOrEqual=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the postupciList where sifraPostupka is greater than or equal to UPDATED_SIFRA_POSTUPKA
        defaultPostupciShouldNotBeFound("sifraPostupka.greaterThanOrEqual=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPostupcisBySifraPostupkaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where sifraPostupka is less than or equal to DEFAULT_SIFRA_POSTUPKA
        defaultPostupciShouldBeFound("sifraPostupka.lessThanOrEqual=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the postupciList where sifraPostupka is less than or equal to SMALLER_SIFRA_POSTUPKA
        defaultPostupciShouldNotBeFound("sifraPostupka.lessThanOrEqual=" + SMALLER_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPostupcisBySifraPostupkaIsLessThanSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where sifraPostupka is less than DEFAULT_SIFRA_POSTUPKA
        defaultPostupciShouldNotBeFound("sifraPostupka.lessThan=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the postupciList where sifraPostupka is less than UPDATED_SIFRA_POSTUPKA
        defaultPostupciShouldBeFound("sifraPostupka.lessThan=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPostupcisBySifraPostupkaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where sifraPostupka is greater than DEFAULT_SIFRA_POSTUPKA
        defaultPostupciShouldNotBeFound("sifraPostupka.greaterThan=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the postupciList where sifraPostupka is greater than SMALLER_SIFRA_POSTUPKA
        defaultPostupciShouldBeFound("sifraPostupka.greaterThan=" + SMALLER_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPostupcisByBrojTenderaIsEqualToSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where brojTendera equals to DEFAULT_BROJ_TENDERA
        defaultPostupciShouldBeFound("brojTendera.equals=" + DEFAULT_BROJ_TENDERA);

        // Get all the postupciList where brojTendera equals to UPDATED_BROJ_TENDERA
        defaultPostupciShouldNotBeFound("brojTendera.equals=" + UPDATED_BROJ_TENDERA);
    }

    @Test
    @Transactional
    void getAllPostupcisByBrojTenderaIsInShouldWork() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where brojTendera in DEFAULT_BROJ_TENDERA or UPDATED_BROJ_TENDERA
        defaultPostupciShouldBeFound("brojTendera.in=" + DEFAULT_BROJ_TENDERA + "," + UPDATED_BROJ_TENDERA);

        // Get all the postupciList where brojTendera equals to UPDATED_BROJ_TENDERA
        defaultPostupciShouldNotBeFound("brojTendera.in=" + UPDATED_BROJ_TENDERA);
    }

    @Test
    @Transactional
    void getAllPostupcisByBrojTenderaIsNullOrNotNull() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where brojTendera is not null
        defaultPostupciShouldBeFound("brojTendera.specified=true");

        // Get all the postupciList where brojTendera is null
        defaultPostupciShouldNotBeFound("brojTendera.specified=false");
    }

    @Test
    @Transactional
    void getAllPostupcisByBrojTenderaContainsSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where brojTendera contains DEFAULT_BROJ_TENDERA
        defaultPostupciShouldBeFound("brojTendera.contains=" + DEFAULT_BROJ_TENDERA);

        // Get all the postupciList where brojTendera contains UPDATED_BROJ_TENDERA
        defaultPostupciShouldNotBeFound("brojTendera.contains=" + UPDATED_BROJ_TENDERA);
    }

    @Test
    @Transactional
    void getAllPostupcisByBrojTenderaNotContainsSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where brojTendera does not contain DEFAULT_BROJ_TENDERA
        defaultPostupciShouldNotBeFound("brojTendera.doesNotContain=" + DEFAULT_BROJ_TENDERA);

        // Get all the postupciList where brojTendera does not contain UPDATED_BROJ_TENDERA
        defaultPostupciShouldBeFound("brojTendera.doesNotContain=" + UPDATED_BROJ_TENDERA);
    }

    @Test
    @Transactional
    void getAllPostupcisByOpisPostupkaIsEqualToSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where opisPostupka equals to DEFAULT_OPIS_POSTUPKA
        defaultPostupciShouldBeFound("opisPostupka.equals=" + DEFAULT_OPIS_POSTUPKA);

        // Get all the postupciList where opisPostupka equals to UPDATED_OPIS_POSTUPKA
        defaultPostupciShouldNotBeFound("opisPostupka.equals=" + UPDATED_OPIS_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPostupcisByOpisPostupkaIsInShouldWork() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where opisPostupka in DEFAULT_OPIS_POSTUPKA or UPDATED_OPIS_POSTUPKA
        defaultPostupciShouldBeFound("opisPostupka.in=" + DEFAULT_OPIS_POSTUPKA + "," + UPDATED_OPIS_POSTUPKA);

        // Get all the postupciList where opisPostupka equals to UPDATED_OPIS_POSTUPKA
        defaultPostupciShouldNotBeFound("opisPostupka.in=" + UPDATED_OPIS_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPostupcisByOpisPostupkaIsNullOrNotNull() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where opisPostupka is not null
        defaultPostupciShouldBeFound("opisPostupka.specified=true");

        // Get all the postupciList where opisPostupka is null
        defaultPostupciShouldNotBeFound("opisPostupka.specified=false");
    }

    @Test
    @Transactional
    void getAllPostupcisByOpisPostupkaContainsSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where opisPostupka contains DEFAULT_OPIS_POSTUPKA
        defaultPostupciShouldBeFound("opisPostupka.contains=" + DEFAULT_OPIS_POSTUPKA);

        // Get all the postupciList where opisPostupka contains UPDATED_OPIS_POSTUPKA
        defaultPostupciShouldNotBeFound("opisPostupka.contains=" + UPDATED_OPIS_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPostupcisByOpisPostupkaNotContainsSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where opisPostupka does not contain DEFAULT_OPIS_POSTUPKA
        defaultPostupciShouldNotBeFound("opisPostupka.doesNotContain=" + DEFAULT_OPIS_POSTUPKA);

        // Get all the postupciList where opisPostupka does not contain UPDATED_OPIS_POSTUPKA
        defaultPostupciShouldBeFound("opisPostupka.doesNotContain=" + UPDATED_OPIS_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPostupcisByVrstaPostupkaIsEqualToSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where vrstaPostupka equals to DEFAULT_VRSTA_POSTUPKA
        defaultPostupciShouldBeFound("vrstaPostupka.equals=" + DEFAULT_VRSTA_POSTUPKA);

        // Get all the postupciList where vrstaPostupka equals to UPDATED_VRSTA_POSTUPKA
        defaultPostupciShouldNotBeFound("vrstaPostupka.equals=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPostupcisByVrstaPostupkaIsInShouldWork() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where vrstaPostupka in DEFAULT_VRSTA_POSTUPKA or UPDATED_VRSTA_POSTUPKA
        defaultPostupciShouldBeFound("vrstaPostupka.in=" + DEFAULT_VRSTA_POSTUPKA + "," + UPDATED_VRSTA_POSTUPKA);

        // Get all the postupciList where vrstaPostupka equals to UPDATED_VRSTA_POSTUPKA
        defaultPostupciShouldNotBeFound("vrstaPostupka.in=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPostupcisByVrstaPostupkaIsNullOrNotNull() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where vrstaPostupka is not null
        defaultPostupciShouldBeFound("vrstaPostupka.specified=true");

        // Get all the postupciList where vrstaPostupka is null
        defaultPostupciShouldNotBeFound("vrstaPostupka.specified=false");
    }

    @Test
    @Transactional
    void getAllPostupcisByVrstaPostupkaContainsSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where vrstaPostupka contains DEFAULT_VRSTA_POSTUPKA
        defaultPostupciShouldBeFound("vrstaPostupka.contains=" + DEFAULT_VRSTA_POSTUPKA);

        // Get all the postupciList where vrstaPostupka contains UPDATED_VRSTA_POSTUPKA
        defaultPostupciShouldNotBeFound("vrstaPostupka.contains=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPostupcisByVrstaPostupkaNotContainsSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where vrstaPostupka does not contain DEFAULT_VRSTA_POSTUPKA
        defaultPostupciShouldNotBeFound("vrstaPostupka.doesNotContain=" + DEFAULT_VRSTA_POSTUPKA);

        // Get all the postupciList where vrstaPostupka does not contain UPDATED_VRSTA_POSTUPKA
        defaultPostupciShouldBeFound("vrstaPostupka.doesNotContain=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPostupcisByDatumObjaveIsEqualToSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where datumObjave equals to DEFAULT_DATUM_OBJAVE
        defaultPostupciShouldBeFound("datumObjave.equals=" + DEFAULT_DATUM_OBJAVE);

        // Get all the postupciList where datumObjave equals to UPDATED_DATUM_OBJAVE
        defaultPostupciShouldNotBeFound("datumObjave.equals=" + UPDATED_DATUM_OBJAVE);
    }

    @Test
    @Transactional
    void getAllPostupcisByDatumObjaveIsInShouldWork() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where datumObjave in DEFAULT_DATUM_OBJAVE or UPDATED_DATUM_OBJAVE
        defaultPostupciShouldBeFound("datumObjave.in=" + DEFAULT_DATUM_OBJAVE + "," + UPDATED_DATUM_OBJAVE);

        // Get all the postupciList where datumObjave equals to UPDATED_DATUM_OBJAVE
        defaultPostupciShouldNotBeFound("datumObjave.in=" + UPDATED_DATUM_OBJAVE);
    }

    @Test
    @Transactional
    void getAllPostupcisByDatumObjaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where datumObjave is not null
        defaultPostupciShouldBeFound("datumObjave.specified=true");

        // Get all the postupciList where datumObjave is null
        defaultPostupciShouldNotBeFound("datumObjave.specified=false");
    }

    @Test
    @Transactional
    void getAllPostupcisByDatumObjaveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where datumObjave is greater than or equal to DEFAULT_DATUM_OBJAVE
        defaultPostupciShouldBeFound("datumObjave.greaterThanOrEqual=" + DEFAULT_DATUM_OBJAVE);

        // Get all the postupciList where datumObjave is greater than or equal to UPDATED_DATUM_OBJAVE
        defaultPostupciShouldNotBeFound("datumObjave.greaterThanOrEqual=" + UPDATED_DATUM_OBJAVE);
    }

    @Test
    @Transactional
    void getAllPostupcisByDatumObjaveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where datumObjave is less than or equal to DEFAULT_DATUM_OBJAVE
        defaultPostupciShouldBeFound("datumObjave.lessThanOrEqual=" + DEFAULT_DATUM_OBJAVE);

        // Get all the postupciList where datumObjave is less than or equal to SMALLER_DATUM_OBJAVE
        defaultPostupciShouldNotBeFound("datumObjave.lessThanOrEqual=" + SMALLER_DATUM_OBJAVE);
    }

    @Test
    @Transactional
    void getAllPostupcisByDatumObjaveIsLessThanSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where datumObjave is less than DEFAULT_DATUM_OBJAVE
        defaultPostupciShouldNotBeFound("datumObjave.lessThan=" + DEFAULT_DATUM_OBJAVE);

        // Get all the postupciList where datumObjave is less than UPDATED_DATUM_OBJAVE
        defaultPostupciShouldBeFound("datumObjave.lessThan=" + UPDATED_DATUM_OBJAVE);
    }

    @Test
    @Transactional
    void getAllPostupcisByDatumObjaveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where datumObjave is greater than DEFAULT_DATUM_OBJAVE
        defaultPostupciShouldNotBeFound("datumObjave.greaterThan=" + DEFAULT_DATUM_OBJAVE);

        // Get all the postupciList where datumObjave is greater than SMALLER_DATUM_OBJAVE
        defaultPostupciShouldBeFound("datumObjave.greaterThan=" + SMALLER_DATUM_OBJAVE);
    }

    @Test
    @Transactional
    void getAllPostupcisByDatumOtvaranjaIsEqualToSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where datumOtvaranja equals to DEFAULT_DATUM_OTVARANJA
        defaultPostupciShouldBeFound("datumOtvaranja.equals=" + DEFAULT_DATUM_OTVARANJA);

        // Get all the postupciList where datumOtvaranja equals to UPDATED_DATUM_OTVARANJA
        defaultPostupciShouldNotBeFound("datumOtvaranja.equals=" + UPDATED_DATUM_OTVARANJA);
    }

    @Test
    @Transactional
    void getAllPostupcisByDatumOtvaranjaIsInShouldWork() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where datumOtvaranja in DEFAULT_DATUM_OTVARANJA or UPDATED_DATUM_OTVARANJA
        defaultPostupciShouldBeFound("datumOtvaranja.in=" + DEFAULT_DATUM_OTVARANJA + "," + UPDATED_DATUM_OTVARANJA);

        // Get all the postupciList where datumOtvaranja equals to UPDATED_DATUM_OTVARANJA
        defaultPostupciShouldNotBeFound("datumOtvaranja.in=" + UPDATED_DATUM_OTVARANJA);
    }

    @Test
    @Transactional
    void getAllPostupcisByDatumOtvaranjaIsNullOrNotNull() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where datumOtvaranja is not null
        defaultPostupciShouldBeFound("datumOtvaranja.specified=true");

        // Get all the postupciList where datumOtvaranja is null
        defaultPostupciShouldNotBeFound("datumOtvaranja.specified=false");
    }

    @Test
    @Transactional
    void getAllPostupcisByDatumOtvaranjaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where datumOtvaranja is greater than or equal to DEFAULT_DATUM_OTVARANJA
        defaultPostupciShouldBeFound("datumOtvaranja.greaterThanOrEqual=" + DEFAULT_DATUM_OTVARANJA);

        // Get all the postupciList where datumOtvaranja is greater than or equal to UPDATED_DATUM_OTVARANJA
        defaultPostupciShouldNotBeFound("datumOtvaranja.greaterThanOrEqual=" + UPDATED_DATUM_OTVARANJA);
    }

    @Test
    @Transactional
    void getAllPostupcisByDatumOtvaranjaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where datumOtvaranja is less than or equal to DEFAULT_DATUM_OTVARANJA
        defaultPostupciShouldBeFound("datumOtvaranja.lessThanOrEqual=" + DEFAULT_DATUM_OTVARANJA);

        // Get all the postupciList where datumOtvaranja is less than or equal to SMALLER_DATUM_OTVARANJA
        defaultPostupciShouldNotBeFound("datumOtvaranja.lessThanOrEqual=" + SMALLER_DATUM_OTVARANJA);
    }

    @Test
    @Transactional
    void getAllPostupcisByDatumOtvaranjaIsLessThanSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where datumOtvaranja is less than DEFAULT_DATUM_OTVARANJA
        defaultPostupciShouldNotBeFound("datumOtvaranja.lessThan=" + DEFAULT_DATUM_OTVARANJA);

        // Get all the postupciList where datumOtvaranja is less than UPDATED_DATUM_OTVARANJA
        defaultPostupciShouldBeFound("datumOtvaranja.lessThan=" + UPDATED_DATUM_OTVARANJA);
    }

    @Test
    @Transactional
    void getAllPostupcisByDatumOtvaranjaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where datumOtvaranja is greater than DEFAULT_DATUM_OTVARANJA
        defaultPostupciShouldNotBeFound("datumOtvaranja.greaterThan=" + DEFAULT_DATUM_OTVARANJA);

        // Get all the postupciList where datumOtvaranja is greater than SMALLER_DATUM_OTVARANJA
        defaultPostupciShouldBeFound("datumOtvaranja.greaterThan=" + SMALLER_DATUM_OTVARANJA);
    }

    @Test
    @Transactional
    void getAllPostupcisByKriterijumCijenaIsEqualToSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where kriterijumCijena equals to DEFAULT_KRITERIJUM_CIJENA
        defaultPostupciShouldBeFound("kriterijumCijena.equals=" + DEFAULT_KRITERIJUM_CIJENA);

        // Get all the postupciList where kriterijumCijena equals to UPDATED_KRITERIJUM_CIJENA
        defaultPostupciShouldNotBeFound("kriterijumCijena.equals=" + UPDATED_KRITERIJUM_CIJENA);
    }

    @Test
    @Transactional
    void getAllPostupcisByKriterijumCijenaIsInShouldWork() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where kriterijumCijena in DEFAULT_KRITERIJUM_CIJENA or UPDATED_KRITERIJUM_CIJENA
        defaultPostupciShouldBeFound("kriterijumCijena.in=" + DEFAULT_KRITERIJUM_CIJENA + "," + UPDATED_KRITERIJUM_CIJENA);

        // Get all the postupciList where kriterijumCijena equals to UPDATED_KRITERIJUM_CIJENA
        defaultPostupciShouldNotBeFound("kriterijumCijena.in=" + UPDATED_KRITERIJUM_CIJENA);
    }

    @Test
    @Transactional
    void getAllPostupcisByKriterijumCijenaIsNullOrNotNull() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where kriterijumCijena is not null
        defaultPostupciShouldBeFound("kriterijumCijena.specified=true");

        // Get all the postupciList where kriterijumCijena is null
        defaultPostupciShouldNotBeFound("kriterijumCijena.specified=false");
    }

    @Test
    @Transactional
    void getAllPostupcisByKriterijumCijenaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where kriterijumCijena is greater than or equal to DEFAULT_KRITERIJUM_CIJENA
        defaultPostupciShouldBeFound("kriterijumCijena.greaterThanOrEqual=" + DEFAULT_KRITERIJUM_CIJENA);

        // Get all the postupciList where kriterijumCijena is greater than or equal to UPDATED_KRITERIJUM_CIJENA
        defaultPostupciShouldNotBeFound("kriterijumCijena.greaterThanOrEqual=" + UPDATED_KRITERIJUM_CIJENA);
    }

    @Test
    @Transactional
    void getAllPostupcisByKriterijumCijenaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where kriterijumCijena is less than or equal to DEFAULT_KRITERIJUM_CIJENA
        defaultPostupciShouldBeFound("kriterijumCijena.lessThanOrEqual=" + DEFAULT_KRITERIJUM_CIJENA);

        // Get all the postupciList where kriterijumCijena is less than or equal to SMALLER_KRITERIJUM_CIJENA
        defaultPostupciShouldNotBeFound("kriterijumCijena.lessThanOrEqual=" + SMALLER_KRITERIJUM_CIJENA);
    }

    @Test
    @Transactional
    void getAllPostupcisByKriterijumCijenaIsLessThanSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where kriterijumCijena is less than DEFAULT_KRITERIJUM_CIJENA
        defaultPostupciShouldNotBeFound("kriterijumCijena.lessThan=" + DEFAULT_KRITERIJUM_CIJENA);

        // Get all the postupciList where kriterijumCijena is less than UPDATED_KRITERIJUM_CIJENA
        defaultPostupciShouldBeFound("kriterijumCijena.lessThan=" + UPDATED_KRITERIJUM_CIJENA);
    }

    @Test
    @Transactional
    void getAllPostupcisByKriterijumCijenaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where kriterijumCijena is greater than DEFAULT_KRITERIJUM_CIJENA
        defaultPostupciShouldNotBeFound("kriterijumCijena.greaterThan=" + DEFAULT_KRITERIJUM_CIJENA);

        // Get all the postupciList where kriterijumCijena is greater than SMALLER_KRITERIJUM_CIJENA
        defaultPostupciShouldBeFound("kriterijumCijena.greaterThan=" + SMALLER_KRITERIJUM_CIJENA);
    }

    @Test
    @Transactional
    void getAllPostupcisByDrugiKriterijumIsEqualToSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where drugiKriterijum equals to DEFAULT_DRUGI_KRITERIJUM
        defaultPostupciShouldBeFound("drugiKriterijum.equals=" + DEFAULT_DRUGI_KRITERIJUM);

        // Get all the postupciList where drugiKriterijum equals to UPDATED_DRUGI_KRITERIJUM
        defaultPostupciShouldNotBeFound("drugiKriterijum.equals=" + UPDATED_DRUGI_KRITERIJUM);
    }

    @Test
    @Transactional
    void getAllPostupcisByDrugiKriterijumIsInShouldWork() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where drugiKriterijum in DEFAULT_DRUGI_KRITERIJUM or UPDATED_DRUGI_KRITERIJUM
        defaultPostupciShouldBeFound("drugiKriterijum.in=" + DEFAULT_DRUGI_KRITERIJUM + "," + UPDATED_DRUGI_KRITERIJUM);

        // Get all the postupciList where drugiKriterijum equals to UPDATED_DRUGI_KRITERIJUM
        defaultPostupciShouldNotBeFound("drugiKriterijum.in=" + UPDATED_DRUGI_KRITERIJUM);
    }

    @Test
    @Transactional
    void getAllPostupcisByDrugiKriterijumIsNullOrNotNull() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where drugiKriterijum is not null
        defaultPostupciShouldBeFound("drugiKriterijum.specified=true");

        // Get all the postupciList where drugiKriterijum is null
        defaultPostupciShouldNotBeFound("drugiKriterijum.specified=false");
    }

    @Test
    @Transactional
    void getAllPostupcisByDrugiKriterijumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where drugiKriterijum is greater than or equal to DEFAULT_DRUGI_KRITERIJUM
        defaultPostupciShouldBeFound("drugiKriterijum.greaterThanOrEqual=" + DEFAULT_DRUGI_KRITERIJUM);

        // Get all the postupciList where drugiKriterijum is greater than or equal to UPDATED_DRUGI_KRITERIJUM
        defaultPostupciShouldNotBeFound("drugiKriterijum.greaterThanOrEqual=" + UPDATED_DRUGI_KRITERIJUM);
    }

    @Test
    @Transactional
    void getAllPostupcisByDrugiKriterijumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where drugiKriterijum is less than or equal to DEFAULT_DRUGI_KRITERIJUM
        defaultPostupciShouldBeFound("drugiKriterijum.lessThanOrEqual=" + DEFAULT_DRUGI_KRITERIJUM);

        // Get all the postupciList where drugiKriterijum is less than or equal to SMALLER_DRUGI_KRITERIJUM
        defaultPostupciShouldNotBeFound("drugiKriterijum.lessThanOrEqual=" + SMALLER_DRUGI_KRITERIJUM);
    }

    @Test
    @Transactional
    void getAllPostupcisByDrugiKriterijumIsLessThanSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where drugiKriterijum is less than DEFAULT_DRUGI_KRITERIJUM
        defaultPostupciShouldNotBeFound("drugiKriterijum.lessThan=" + DEFAULT_DRUGI_KRITERIJUM);

        // Get all the postupciList where drugiKriterijum is less than UPDATED_DRUGI_KRITERIJUM
        defaultPostupciShouldBeFound("drugiKriterijum.lessThan=" + UPDATED_DRUGI_KRITERIJUM);
    }

    @Test
    @Transactional
    void getAllPostupcisByDrugiKriterijumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        // Get all the postupciList where drugiKriterijum is greater than DEFAULT_DRUGI_KRITERIJUM
        defaultPostupciShouldNotBeFound("drugiKriterijum.greaterThan=" + DEFAULT_DRUGI_KRITERIJUM);

        // Get all the postupciList where drugiKriterijum is greater than SMALLER_DRUGI_KRITERIJUM
        defaultPostupciShouldBeFound("drugiKriterijum.greaterThan=" + SMALLER_DRUGI_KRITERIJUM);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPostupciShouldBeFound(String filter) throws Exception {
        restPostupciMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postupci.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].brojTendera").value(hasItem(DEFAULT_BROJ_TENDERA)))
            .andExpect(jsonPath("$.[*].opisPostupka").value(hasItem(DEFAULT_OPIS_POSTUPKA)))
            .andExpect(jsonPath("$.[*].vrstaPostupka").value(hasItem(DEFAULT_VRSTA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].datumObjave").value(hasItem(DEFAULT_DATUM_OBJAVE.toString())))
            .andExpect(jsonPath("$.[*].datumOtvaranja").value(hasItem(DEFAULT_DATUM_OTVARANJA.toString())))
            .andExpect(jsonPath("$.[*].kriterijumCijena").value(hasItem(DEFAULT_KRITERIJUM_CIJENA)))
            .andExpect(jsonPath("$.[*].drugiKriterijum").value(hasItem(DEFAULT_DRUGI_KRITERIJUM)));

        // Check, that the count call also returns 1
        restPostupciMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPostupciShouldNotBeFound(String filter) throws Exception {
        restPostupciMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPostupciMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPostupci() throws Exception {
        // Get the postupci
        restPostupciMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPostupci() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        int databaseSizeBeforeUpdate = postupciRepository.findAll().size();

        // Update the postupci
        Postupci updatedPostupci = postupciRepository.findById(postupci.getId()).get();
        // Disconnect from session so that the updates on updatedPostupci are not directly saved in db
        em.detach(updatedPostupci);
        updatedPostupci
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .brojTendera(UPDATED_BROJ_TENDERA)
            .opisPostupka(UPDATED_OPIS_POSTUPKA)
            .vrstaPostupka(UPDATED_VRSTA_POSTUPKA)
            .datumObjave(UPDATED_DATUM_OBJAVE)
            .datumOtvaranja(UPDATED_DATUM_OTVARANJA)
            .kriterijumCijena(UPDATED_KRITERIJUM_CIJENA)
            .drugiKriterijum(UPDATED_DRUGI_KRITERIJUM);

        restPostupciMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPostupci.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPostupci))
            )
            .andExpect(status().isOk());

        // Validate the Postupci in the database
        List<Postupci> postupciList = postupciRepository.findAll();
        assertThat(postupciList).hasSize(databaseSizeBeforeUpdate);
        Postupci testPostupci = postupciList.get(postupciList.size() - 1);
        assertThat(testPostupci.getSifraPostupka()).isEqualTo(UPDATED_SIFRA_POSTUPKA);
        assertThat(testPostupci.getBrojTendera()).isEqualTo(UPDATED_BROJ_TENDERA);
        assertThat(testPostupci.getOpisPostupka()).isEqualTo(UPDATED_OPIS_POSTUPKA);
        assertThat(testPostupci.getVrstaPostupka()).isEqualTo(UPDATED_VRSTA_POSTUPKA);
        assertThat(testPostupci.getDatumObjave()).isEqualTo(UPDATED_DATUM_OBJAVE);
        assertThat(testPostupci.getDatumOtvaranja()).isEqualTo(UPDATED_DATUM_OTVARANJA);
        assertThat(testPostupci.getKriterijumCijena()).isEqualTo(UPDATED_KRITERIJUM_CIJENA);
        assertThat(testPostupci.getDrugiKriterijum()).isEqualTo(UPDATED_DRUGI_KRITERIJUM);
    }

    @Test
    @Transactional
    void putNonExistingPostupci() throws Exception {
        int databaseSizeBeforeUpdate = postupciRepository.findAll().size();
        postupci.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostupciMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postupci.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postupci))
            )
            .andExpect(status().isBadRequest());

        // Validate the Postupci in the database
        List<Postupci> postupciList = postupciRepository.findAll();
        assertThat(postupciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPostupci() throws Exception {
        int databaseSizeBeforeUpdate = postupciRepository.findAll().size();
        postupci.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostupciMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postupci))
            )
            .andExpect(status().isBadRequest());

        // Validate the Postupci in the database
        List<Postupci> postupciList = postupciRepository.findAll();
        assertThat(postupciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPostupci() throws Exception {
        int databaseSizeBeforeUpdate = postupciRepository.findAll().size();
        postupci.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostupciMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postupci)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Postupci in the database
        List<Postupci> postupciList = postupciRepository.findAll();
        assertThat(postupciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePostupciWithPatch() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        int databaseSizeBeforeUpdate = postupciRepository.findAll().size();

        // Update the postupci using partial update
        Postupci partialUpdatedPostupci = new Postupci();
        partialUpdatedPostupci.setId(postupci.getId());

        partialUpdatedPostupci
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .brojTendera(UPDATED_BROJ_TENDERA)
            .opisPostupka(UPDATED_OPIS_POSTUPKA)
            .vrstaPostupka(UPDATED_VRSTA_POSTUPKA)
            .kriterijumCijena(UPDATED_KRITERIJUM_CIJENA)
            .drugiKriterijum(UPDATED_DRUGI_KRITERIJUM);

        restPostupciMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPostupci.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPostupci))
            )
            .andExpect(status().isOk());

        // Validate the Postupci in the database
        List<Postupci> postupciList = postupciRepository.findAll();
        assertThat(postupciList).hasSize(databaseSizeBeforeUpdate);
        Postupci testPostupci = postupciList.get(postupciList.size() - 1);
        assertThat(testPostupci.getSifraPostupka()).isEqualTo(UPDATED_SIFRA_POSTUPKA);
        assertThat(testPostupci.getBrojTendera()).isEqualTo(UPDATED_BROJ_TENDERA);
        assertThat(testPostupci.getOpisPostupka()).isEqualTo(UPDATED_OPIS_POSTUPKA);
        assertThat(testPostupci.getVrstaPostupka()).isEqualTo(UPDATED_VRSTA_POSTUPKA);
        assertThat(testPostupci.getDatumObjave()).isEqualTo(DEFAULT_DATUM_OBJAVE);
        assertThat(testPostupci.getDatumOtvaranja()).isEqualTo(DEFAULT_DATUM_OTVARANJA);
        assertThat(testPostupci.getKriterijumCijena()).isEqualTo(UPDATED_KRITERIJUM_CIJENA);
        assertThat(testPostupci.getDrugiKriterijum()).isEqualTo(UPDATED_DRUGI_KRITERIJUM);
    }

    @Test
    @Transactional
    void fullUpdatePostupciWithPatch() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        int databaseSizeBeforeUpdate = postupciRepository.findAll().size();

        // Update the postupci using partial update
        Postupci partialUpdatedPostupci = new Postupci();
        partialUpdatedPostupci.setId(postupci.getId());

        partialUpdatedPostupci
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .brojTendera(UPDATED_BROJ_TENDERA)
            .opisPostupka(UPDATED_OPIS_POSTUPKA)
            .vrstaPostupka(UPDATED_VRSTA_POSTUPKA)
            .datumObjave(UPDATED_DATUM_OBJAVE)
            .datumOtvaranja(UPDATED_DATUM_OTVARANJA)
            .kriterijumCijena(UPDATED_KRITERIJUM_CIJENA)
            .drugiKriterijum(UPDATED_DRUGI_KRITERIJUM);

        restPostupciMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPostupci.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPostupci))
            )
            .andExpect(status().isOk());

        // Validate the Postupci in the database
        List<Postupci> postupciList = postupciRepository.findAll();
        assertThat(postupciList).hasSize(databaseSizeBeforeUpdate);
        Postupci testPostupci = postupciList.get(postupciList.size() - 1);
        assertThat(testPostupci.getSifraPostupka()).isEqualTo(UPDATED_SIFRA_POSTUPKA);
        assertThat(testPostupci.getBrojTendera()).isEqualTo(UPDATED_BROJ_TENDERA);
        assertThat(testPostupci.getOpisPostupka()).isEqualTo(UPDATED_OPIS_POSTUPKA);
        assertThat(testPostupci.getVrstaPostupka()).isEqualTo(UPDATED_VRSTA_POSTUPKA);
        assertThat(testPostupci.getDatumObjave()).isEqualTo(UPDATED_DATUM_OBJAVE);
        assertThat(testPostupci.getDatumOtvaranja()).isEqualTo(UPDATED_DATUM_OTVARANJA);
        assertThat(testPostupci.getKriterijumCijena()).isEqualTo(UPDATED_KRITERIJUM_CIJENA);
        assertThat(testPostupci.getDrugiKriterijum()).isEqualTo(UPDATED_DRUGI_KRITERIJUM);
    }

    @Test
    @Transactional
    void patchNonExistingPostupci() throws Exception {
        int databaseSizeBeforeUpdate = postupciRepository.findAll().size();
        postupci.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostupciMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, postupci.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postupci))
            )
            .andExpect(status().isBadRequest());

        // Validate the Postupci in the database
        List<Postupci> postupciList = postupciRepository.findAll();
        assertThat(postupciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPostupci() throws Exception {
        int databaseSizeBeforeUpdate = postupciRepository.findAll().size();
        postupci.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostupciMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postupci))
            )
            .andExpect(status().isBadRequest());

        // Validate the Postupci in the database
        List<Postupci> postupciList = postupciRepository.findAll();
        assertThat(postupciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPostupci() throws Exception {
        int databaseSizeBeforeUpdate = postupciRepository.findAll().size();
        postupci.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostupciMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(postupci)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Postupci in the database
        List<Postupci> postupciList = postupciRepository.findAll();
        assertThat(postupciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePostupci() throws Exception {
        // Initialize the database
        postupciRepository.saveAndFlush(postupci);

        int databaseSizeBeforeDelete = postupciRepository.findAll().size();

        // Delete the postupci
        restPostupciMockMvc
            .perform(delete(ENTITY_API_URL_ID, postupci.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Postupci> postupciList = postupciRepository.findAll();
        assertThat(postupciList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
