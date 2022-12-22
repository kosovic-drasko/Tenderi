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
import tender.domain.ViewPonude;
import tender.repository.ViewPonudeRepository;
import tender.service.criteria.ViewPonudeCriteria;

/**
 * Integration tests for the {@link ViewPonudeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ViewPonudeResourceIT {

    private static final Integer DEFAULT_SIFRA_POSTUPKA = 1;
    private static final Integer UPDATED_SIFRA_POSTUPKA = 2;
    private static final Integer SMALLER_SIFRA_POSTUPKA = 1 - 1;

    private static final Integer DEFAULT_SIFRA_PONUDE = 1;
    private static final Integer UPDATED_SIFRA_PONUDE = 2;
    private static final Integer SMALLER_SIFRA_PONUDE = 1 - 1;

    private static final Integer DEFAULT_BROJ_PARTIJE = 1;
    private static final Integer UPDATED_BROJ_PARTIJE = 2;
    private static final Integer SMALLER_BROJ_PARTIJE = 1 - 1;

    private static final String DEFAULT_NAZIV_PROIZVODJACA = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV_PROIZVODJACA = "BBBBBBBBBB";

    private static final String DEFAULT_ZASTICENI_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_ZASTICENI_NAZIV = "BBBBBBBBBB";

    private static final Double DEFAULT_PONUDJENA_VRIJEDNOST = 1D;
    private static final Double UPDATED_PONUDJENA_VRIJEDNOST = 2D;
    private static final Double SMALLER_PONUDJENA_VRIJEDNOST = 1D - 1D;

    private static final Integer DEFAULT_ROK_ISPORUKE = 1;
    private static final Integer UPDATED_ROK_ISPORUKE = 2;
    private static final Integer SMALLER_ROK_ISPORUKE = 1 - 1;

    private static final Double DEFAULT_JEDINICNA_CIJENA = 1D;
    private static final Double UPDATED_JEDINICNA_CIJENA = 2D;
    private static final Double SMALLER_JEDINICNA_CIJENA = 1D - 1D;

    private static final Boolean DEFAULT_SELECTED = false;
    private static final Boolean UPDATED_SELECTED = true;

    private static final String DEFAULT_NAZIV_PONUDJACA = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV_PONUDJACA = "BBBBBBBBBB";

    private static final String DEFAULT_KARAKTERISTIKA = "AAAAAAAAAA";
    private static final String UPDATED_KARAKTERISTIKA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/view-ponudes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ViewPonudeRepository viewPonudeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restViewPonudeMockMvc;

    private ViewPonude viewPonude;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ViewPonude createEntity(EntityManager em) {
        ViewPonude viewPonude = new ViewPonude()
            .sifraPostupka(DEFAULT_SIFRA_POSTUPKA)
            .sifraPonude(DEFAULT_SIFRA_PONUDE)
            .brojPartije(DEFAULT_BROJ_PARTIJE)
            .nazivProizvodjaca(DEFAULT_NAZIV_PROIZVODJACA)
            .zasticeniNaziv(DEFAULT_ZASTICENI_NAZIV)
            .ponudjenaVrijednost(DEFAULT_PONUDJENA_VRIJEDNOST)
            .rokIsporuke(DEFAULT_ROK_ISPORUKE)
            .jedinicnaCijena(DEFAULT_JEDINICNA_CIJENA)
            .selected(DEFAULT_SELECTED)
            .nazivPonudjaca(DEFAULT_NAZIV_PONUDJACA)
            .karakteristika(DEFAULT_KARAKTERISTIKA);
        return viewPonude;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ViewPonude createUpdatedEntity(EntityManager em) {
        ViewPonude viewPonude = new ViewPonude()
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .sifraPonude(UPDATED_SIFRA_PONUDE)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .nazivProizvodjaca(UPDATED_NAZIV_PROIZVODJACA)
            .zasticeniNaziv(UPDATED_ZASTICENI_NAZIV)
            .ponudjenaVrijednost(UPDATED_PONUDJENA_VRIJEDNOST)
            .rokIsporuke(UPDATED_ROK_ISPORUKE)
            .jedinicnaCijena(UPDATED_JEDINICNA_CIJENA)
            .selected(UPDATED_SELECTED)
            .nazivPonudjaca(UPDATED_NAZIV_PONUDJACA)
            .karakteristika(UPDATED_KARAKTERISTIKA);
        return viewPonude;
    }

    @BeforeEach
    public void initTest() {
        viewPonude = createEntity(em);
    }

    @Test
    @Transactional
    void createViewPonude() throws Exception {
        int databaseSizeBeforeCreate = viewPonudeRepository.findAll().size();
        // Create the ViewPonude
        restViewPonudeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(viewPonude)))
            .andExpect(status().isCreated());

        // Validate the ViewPonude in the database
        List<ViewPonude> viewPonudeList = viewPonudeRepository.findAll();
        assertThat(viewPonudeList).hasSize(databaseSizeBeforeCreate + 1);
        ViewPonude testViewPonude = viewPonudeList.get(viewPonudeList.size() - 1);
        assertThat(testViewPonude.getSifraPostupka()).isEqualTo(DEFAULT_SIFRA_POSTUPKA);
        assertThat(testViewPonude.getSifraPonude()).isEqualTo(DEFAULT_SIFRA_PONUDE);
        assertThat(testViewPonude.getBrojPartije()).isEqualTo(DEFAULT_BROJ_PARTIJE);
        assertThat(testViewPonude.getNazivProizvodjaca()).isEqualTo(DEFAULT_NAZIV_PROIZVODJACA);
        assertThat(testViewPonude.getZasticeniNaziv()).isEqualTo(DEFAULT_ZASTICENI_NAZIV);
        assertThat(testViewPonude.getPonudjenaVrijednost()).isEqualTo(DEFAULT_PONUDJENA_VRIJEDNOST);
        assertThat(testViewPonude.getRokIsporuke()).isEqualTo(DEFAULT_ROK_ISPORUKE);
        assertThat(testViewPonude.getJedinicnaCijena()).isEqualTo(DEFAULT_JEDINICNA_CIJENA);
        assertThat(testViewPonude.getSelected()).isEqualTo(DEFAULT_SELECTED);
        assertThat(testViewPonude.getNazivPonudjaca()).isEqualTo(DEFAULT_NAZIV_PONUDJACA);
        assertThat(testViewPonude.getKarakteristika()).isEqualTo(DEFAULT_KARAKTERISTIKA);
    }

    @Test
    @Transactional
    void createViewPonudeWithExistingId() throws Exception {
        // Create the ViewPonude with an existing ID
        viewPonude.setId(1L);

        int databaseSizeBeforeCreate = viewPonudeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restViewPonudeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(viewPonude)))
            .andExpect(status().isBadRequest());

        // Validate the ViewPonude in the database
        List<ViewPonude> viewPonudeList = viewPonudeRepository.findAll();
        assertThat(viewPonudeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSifraPostupkaIsRequired() throws Exception {
        int databaseSizeBeforeTest = viewPonudeRepository.findAll().size();
        // set the field null
        viewPonude.setSifraPostupka(null);

        // Create the ViewPonude, which fails.

        restViewPonudeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(viewPonude)))
            .andExpect(status().isBadRequest());

        List<ViewPonude> viewPonudeList = viewPonudeRepository.findAll();
        assertThat(viewPonudeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSifraPonudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = viewPonudeRepository.findAll().size();
        // set the field null
        viewPonude.setSifraPonude(null);

        // Create the ViewPonude, which fails.

        restViewPonudeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(viewPonude)))
            .andExpect(status().isBadRequest());

        List<ViewPonude> viewPonudeList = viewPonudeRepository.findAll();
        assertThat(viewPonudeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBrojPartijeIsRequired() throws Exception {
        int databaseSizeBeforeTest = viewPonudeRepository.findAll().size();
        // set the field null
        viewPonude.setBrojPartije(null);

        // Create the ViewPonude, which fails.

        restViewPonudeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(viewPonude)))
            .andExpect(status().isBadRequest());

        List<ViewPonude> viewPonudeList = viewPonudeRepository.findAll();
        assertThat(viewPonudeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPonudjenaVrijednostIsRequired() throws Exception {
        int databaseSizeBeforeTest = viewPonudeRepository.findAll().size();
        // set the field null
        viewPonude.setPonudjenaVrijednost(null);

        // Create the ViewPonude, which fails.

        restViewPonudeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(viewPonude)))
            .andExpect(status().isBadRequest());

        List<ViewPonude> viewPonudeList = viewPonudeRepository.findAll();
        assertThat(viewPonudeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllViewPonudes() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList
        restViewPonudeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viewPonude.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].sifraPonude").value(hasItem(DEFAULT_SIFRA_PONUDE)))
            .andExpect(jsonPath("$.[*].brojPartije").value(hasItem(DEFAULT_BROJ_PARTIJE)))
            .andExpect(jsonPath("$.[*].nazivProizvodjaca").value(hasItem(DEFAULT_NAZIV_PROIZVODJACA)))
            .andExpect(jsonPath("$.[*].zasticeniNaziv").value(hasItem(DEFAULT_ZASTICENI_NAZIV)))
            .andExpect(jsonPath("$.[*].ponudjenaVrijednost").value(hasItem(DEFAULT_PONUDJENA_VRIJEDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].rokIsporuke").value(hasItem(DEFAULT_ROK_ISPORUKE)))
            .andExpect(jsonPath("$.[*].jedinicnaCijena").value(hasItem(DEFAULT_JEDINICNA_CIJENA.doubleValue())))
            .andExpect(jsonPath("$.[*].selected").value(hasItem(DEFAULT_SELECTED.booleanValue())))
            .andExpect(jsonPath("$.[*].nazivPonudjaca").value(hasItem(DEFAULT_NAZIV_PONUDJACA)))
            .andExpect(jsonPath("$.[*].karakteristika").value(hasItem(DEFAULT_KARAKTERISTIKA)));
    }

    @Test
    @Transactional
    void getViewPonude() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get the viewPonude
        restViewPonudeMockMvc
            .perform(get(ENTITY_API_URL_ID, viewPonude.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(viewPonude.getId().intValue()))
            .andExpect(jsonPath("$.sifraPostupka").value(DEFAULT_SIFRA_POSTUPKA))
            .andExpect(jsonPath("$.sifraPonude").value(DEFAULT_SIFRA_PONUDE))
            .andExpect(jsonPath("$.brojPartije").value(DEFAULT_BROJ_PARTIJE))
            .andExpect(jsonPath("$.nazivProizvodjaca").value(DEFAULT_NAZIV_PROIZVODJACA))
            .andExpect(jsonPath("$.zasticeniNaziv").value(DEFAULT_ZASTICENI_NAZIV))
            .andExpect(jsonPath("$.ponudjenaVrijednost").value(DEFAULT_PONUDJENA_VRIJEDNOST.doubleValue()))
            .andExpect(jsonPath("$.rokIsporuke").value(DEFAULT_ROK_ISPORUKE))
            .andExpect(jsonPath("$.jedinicnaCijena").value(DEFAULT_JEDINICNA_CIJENA.doubleValue()))
            .andExpect(jsonPath("$.selected").value(DEFAULT_SELECTED.booleanValue()))
            .andExpect(jsonPath("$.nazivPonudjaca").value(DEFAULT_NAZIV_PONUDJACA))
            .andExpect(jsonPath("$.karakteristika").value(DEFAULT_KARAKTERISTIKA));
    }

    @Test
    @Transactional
    void getViewPonudesByIdFiltering() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        Long id = viewPonude.getId();

        defaultViewPonudeShouldBeFound("id.equals=" + id);
        defaultViewPonudeShouldNotBeFound("id.notEquals=" + id);

        defaultViewPonudeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultViewPonudeShouldNotBeFound("id.greaterThan=" + id);

        defaultViewPonudeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultViewPonudeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllViewPonudesBySifraPostupkaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where sifraPostupka equals to DEFAULT_SIFRA_POSTUPKA
        defaultViewPonudeShouldBeFound("sifraPostupka.equals=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the viewPonudeList where sifraPostupka equals to UPDATED_SIFRA_POSTUPKA
        defaultViewPonudeShouldNotBeFound("sifraPostupka.equals=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewPonudesBySifraPostupkaIsInShouldWork() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where sifraPostupka in DEFAULT_SIFRA_POSTUPKA or UPDATED_SIFRA_POSTUPKA
        defaultViewPonudeShouldBeFound("sifraPostupka.in=" + DEFAULT_SIFRA_POSTUPKA + "," + UPDATED_SIFRA_POSTUPKA);

        // Get all the viewPonudeList where sifraPostupka equals to UPDATED_SIFRA_POSTUPKA
        defaultViewPonudeShouldNotBeFound("sifraPostupka.in=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewPonudesBySifraPostupkaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where sifraPostupka is not null
        defaultViewPonudeShouldBeFound("sifraPostupka.specified=true");

        // Get all the viewPonudeList where sifraPostupka is null
        defaultViewPonudeShouldNotBeFound("sifraPostupka.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPonudesBySifraPostupkaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where sifraPostupka is greater than or equal to DEFAULT_SIFRA_POSTUPKA
        defaultViewPonudeShouldBeFound("sifraPostupka.greaterThanOrEqual=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the viewPonudeList where sifraPostupka is greater than or equal to UPDATED_SIFRA_POSTUPKA
        defaultViewPonudeShouldNotBeFound("sifraPostupka.greaterThanOrEqual=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewPonudesBySifraPostupkaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where sifraPostupka is less than or equal to DEFAULT_SIFRA_POSTUPKA
        defaultViewPonudeShouldBeFound("sifraPostupka.lessThanOrEqual=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the viewPonudeList where sifraPostupka is less than or equal to SMALLER_SIFRA_POSTUPKA
        defaultViewPonudeShouldNotBeFound("sifraPostupka.lessThanOrEqual=" + SMALLER_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewPonudesBySifraPostupkaIsLessThanSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where sifraPostupka is less than DEFAULT_SIFRA_POSTUPKA
        defaultViewPonudeShouldNotBeFound("sifraPostupka.lessThan=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the viewPonudeList where sifraPostupka is less than UPDATED_SIFRA_POSTUPKA
        defaultViewPonudeShouldBeFound("sifraPostupka.lessThan=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewPonudesBySifraPostupkaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where sifraPostupka is greater than DEFAULT_SIFRA_POSTUPKA
        defaultViewPonudeShouldNotBeFound("sifraPostupka.greaterThan=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the viewPonudeList where sifraPostupka is greater than SMALLER_SIFRA_POSTUPKA
        defaultViewPonudeShouldBeFound("sifraPostupka.greaterThan=" + SMALLER_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewPonudesBySifraPonudeIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where sifraPonude equals to DEFAULT_SIFRA_PONUDE
        defaultViewPonudeShouldBeFound("sifraPonude.equals=" + DEFAULT_SIFRA_PONUDE);

        // Get all the viewPonudeList where sifraPonude equals to UPDATED_SIFRA_PONUDE
        defaultViewPonudeShouldNotBeFound("sifraPonude.equals=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewPonudesBySifraPonudeIsInShouldWork() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where sifraPonude in DEFAULT_SIFRA_PONUDE or UPDATED_SIFRA_PONUDE
        defaultViewPonudeShouldBeFound("sifraPonude.in=" + DEFAULT_SIFRA_PONUDE + "," + UPDATED_SIFRA_PONUDE);

        // Get all the viewPonudeList where sifraPonude equals to UPDATED_SIFRA_PONUDE
        defaultViewPonudeShouldNotBeFound("sifraPonude.in=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewPonudesBySifraPonudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where sifraPonude is not null
        defaultViewPonudeShouldBeFound("sifraPonude.specified=true");

        // Get all the viewPonudeList where sifraPonude is null
        defaultViewPonudeShouldNotBeFound("sifraPonude.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPonudesBySifraPonudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where sifraPonude is greater than or equal to DEFAULT_SIFRA_PONUDE
        defaultViewPonudeShouldBeFound("sifraPonude.greaterThanOrEqual=" + DEFAULT_SIFRA_PONUDE);

        // Get all the viewPonudeList where sifraPonude is greater than or equal to UPDATED_SIFRA_PONUDE
        defaultViewPonudeShouldNotBeFound("sifraPonude.greaterThanOrEqual=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewPonudesBySifraPonudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where sifraPonude is less than or equal to DEFAULT_SIFRA_PONUDE
        defaultViewPonudeShouldBeFound("sifraPonude.lessThanOrEqual=" + DEFAULT_SIFRA_PONUDE);

        // Get all the viewPonudeList where sifraPonude is less than or equal to SMALLER_SIFRA_PONUDE
        defaultViewPonudeShouldNotBeFound("sifraPonude.lessThanOrEqual=" + SMALLER_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewPonudesBySifraPonudeIsLessThanSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where sifraPonude is less than DEFAULT_SIFRA_PONUDE
        defaultViewPonudeShouldNotBeFound("sifraPonude.lessThan=" + DEFAULT_SIFRA_PONUDE);

        // Get all the viewPonudeList where sifraPonude is less than UPDATED_SIFRA_PONUDE
        defaultViewPonudeShouldBeFound("sifraPonude.lessThan=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewPonudesBySifraPonudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where sifraPonude is greater than DEFAULT_SIFRA_PONUDE
        defaultViewPonudeShouldNotBeFound("sifraPonude.greaterThan=" + DEFAULT_SIFRA_PONUDE);

        // Get all the viewPonudeList where sifraPonude is greater than SMALLER_SIFRA_PONUDE
        defaultViewPonudeShouldBeFound("sifraPonude.greaterThan=" + SMALLER_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewPonudesByBrojPartijeIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where brojPartije equals to DEFAULT_BROJ_PARTIJE
        defaultViewPonudeShouldBeFound("brojPartije.equals=" + DEFAULT_BROJ_PARTIJE);

        // Get all the viewPonudeList where brojPartije equals to UPDATED_BROJ_PARTIJE
        defaultViewPonudeShouldNotBeFound("brojPartije.equals=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllViewPonudesByBrojPartijeIsInShouldWork() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where brojPartije in DEFAULT_BROJ_PARTIJE or UPDATED_BROJ_PARTIJE
        defaultViewPonudeShouldBeFound("brojPartije.in=" + DEFAULT_BROJ_PARTIJE + "," + UPDATED_BROJ_PARTIJE);

        // Get all the viewPonudeList where brojPartije equals to UPDATED_BROJ_PARTIJE
        defaultViewPonudeShouldNotBeFound("brojPartije.in=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllViewPonudesByBrojPartijeIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where brojPartije is not null
        defaultViewPonudeShouldBeFound("brojPartije.specified=true");

        // Get all the viewPonudeList where brojPartije is null
        defaultViewPonudeShouldNotBeFound("brojPartije.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPonudesByBrojPartijeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where brojPartije is greater than or equal to DEFAULT_BROJ_PARTIJE
        defaultViewPonudeShouldBeFound("brojPartije.greaterThanOrEqual=" + DEFAULT_BROJ_PARTIJE);

        // Get all the viewPonudeList where brojPartije is greater than or equal to UPDATED_BROJ_PARTIJE
        defaultViewPonudeShouldNotBeFound("brojPartije.greaterThanOrEqual=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllViewPonudesByBrojPartijeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where brojPartije is less than or equal to DEFAULT_BROJ_PARTIJE
        defaultViewPonudeShouldBeFound("brojPartije.lessThanOrEqual=" + DEFAULT_BROJ_PARTIJE);

        // Get all the viewPonudeList where brojPartije is less than or equal to SMALLER_BROJ_PARTIJE
        defaultViewPonudeShouldNotBeFound("brojPartije.lessThanOrEqual=" + SMALLER_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllViewPonudesByBrojPartijeIsLessThanSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where brojPartije is less than DEFAULT_BROJ_PARTIJE
        defaultViewPonudeShouldNotBeFound("brojPartije.lessThan=" + DEFAULT_BROJ_PARTIJE);

        // Get all the viewPonudeList where brojPartije is less than UPDATED_BROJ_PARTIJE
        defaultViewPonudeShouldBeFound("brojPartije.lessThan=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllViewPonudesByBrojPartijeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where brojPartije is greater than DEFAULT_BROJ_PARTIJE
        defaultViewPonudeShouldNotBeFound("brojPartije.greaterThan=" + DEFAULT_BROJ_PARTIJE);

        // Get all the viewPonudeList where brojPartije is greater than SMALLER_BROJ_PARTIJE
        defaultViewPonudeShouldBeFound("brojPartije.greaterThan=" + SMALLER_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllViewPonudesByNazivProizvodjacaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where nazivProizvodjaca equals to DEFAULT_NAZIV_PROIZVODJACA
        defaultViewPonudeShouldBeFound("nazivProizvodjaca.equals=" + DEFAULT_NAZIV_PROIZVODJACA);

        // Get all the viewPonudeList where nazivProizvodjaca equals to UPDATED_NAZIV_PROIZVODJACA
        defaultViewPonudeShouldNotBeFound("nazivProizvodjaca.equals=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllViewPonudesByNazivProizvodjacaIsInShouldWork() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where nazivProizvodjaca in DEFAULT_NAZIV_PROIZVODJACA or UPDATED_NAZIV_PROIZVODJACA
        defaultViewPonudeShouldBeFound("nazivProizvodjaca.in=" + DEFAULT_NAZIV_PROIZVODJACA + "," + UPDATED_NAZIV_PROIZVODJACA);

        // Get all the viewPonudeList where nazivProizvodjaca equals to UPDATED_NAZIV_PROIZVODJACA
        defaultViewPonudeShouldNotBeFound("nazivProizvodjaca.in=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllViewPonudesByNazivProizvodjacaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where nazivProizvodjaca is not null
        defaultViewPonudeShouldBeFound("nazivProizvodjaca.specified=true");

        // Get all the viewPonudeList where nazivProizvodjaca is null
        defaultViewPonudeShouldNotBeFound("nazivProizvodjaca.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPonudesByNazivProizvodjacaContainsSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where nazivProizvodjaca contains DEFAULT_NAZIV_PROIZVODJACA
        defaultViewPonudeShouldBeFound("nazivProizvodjaca.contains=" + DEFAULT_NAZIV_PROIZVODJACA);

        // Get all the viewPonudeList where nazivProizvodjaca contains UPDATED_NAZIV_PROIZVODJACA
        defaultViewPonudeShouldNotBeFound("nazivProizvodjaca.contains=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllViewPonudesByNazivProizvodjacaNotContainsSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where nazivProizvodjaca does not contain DEFAULT_NAZIV_PROIZVODJACA
        defaultViewPonudeShouldNotBeFound("nazivProizvodjaca.doesNotContain=" + DEFAULT_NAZIV_PROIZVODJACA);

        // Get all the viewPonudeList where nazivProizvodjaca does not contain UPDATED_NAZIV_PROIZVODJACA
        defaultViewPonudeShouldBeFound("nazivProizvodjaca.doesNotContain=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllViewPonudesByZasticeniNazivIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where zasticeniNaziv equals to DEFAULT_ZASTICENI_NAZIV
        defaultViewPonudeShouldBeFound("zasticeniNaziv.equals=" + DEFAULT_ZASTICENI_NAZIV);

        // Get all the viewPonudeList where zasticeniNaziv equals to UPDATED_ZASTICENI_NAZIV
        defaultViewPonudeShouldNotBeFound("zasticeniNaziv.equals=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllViewPonudesByZasticeniNazivIsInShouldWork() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where zasticeniNaziv in DEFAULT_ZASTICENI_NAZIV or UPDATED_ZASTICENI_NAZIV
        defaultViewPonudeShouldBeFound("zasticeniNaziv.in=" + DEFAULT_ZASTICENI_NAZIV + "," + UPDATED_ZASTICENI_NAZIV);

        // Get all the viewPonudeList where zasticeniNaziv equals to UPDATED_ZASTICENI_NAZIV
        defaultViewPonudeShouldNotBeFound("zasticeniNaziv.in=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllViewPonudesByZasticeniNazivIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where zasticeniNaziv is not null
        defaultViewPonudeShouldBeFound("zasticeniNaziv.specified=true");

        // Get all the viewPonudeList where zasticeniNaziv is null
        defaultViewPonudeShouldNotBeFound("zasticeniNaziv.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPonudesByZasticeniNazivContainsSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where zasticeniNaziv contains DEFAULT_ZASTICENI_NAZIV
        defaultViewPonudeShouldBeFound("zasticeniNaziv.contains=" + DEFAULT_ZASTICENI_NAZIV);

        // Get all the viewPonudeList where zasticeniNaziv contains UPDATED_ZASTICENI_NAZIV
        defaultViewPonudeShouldNotBeFound("zasticeniNaziv.contains=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllViewPonudesByZasticeniNazivNotContainsSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where zasticeniNaziv does not contain DEFAULT_ZASTICENI_NAZIV
        defaultViewPonudeShouldNotBeFound("zasticeniNaziv.doesNotContain=" + DEFAULT_ZASTICENI_NAZIV);

        // Get all the viewPonudeList where zasticeniNaziv does not contain UPDATED_ZASTICENI_NAZIV
        defaultViewPonudeShouldBeFound("zasticeniNaziv.doesNotContain=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllViewPonudesByPonudjenaVrijednostIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where ponudjenaVrijednost equals to DEFAULT_PONUDJENA_VRIJEDNOST
        defaultViewPonudeShouldBeFound("ponudjenaVrijednost.equals=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the viewPonudeList where ponudjenaVrijednost equals to UPDATED_PONUDJENA_VRIJEDNOST
        defaultViewPonudeShouldNotBeFound("ponudjenaVrijednost.equals=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewPonudesByPonudjenaVrijednostIsInShouldWork() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where ponudjenaVrijednost in DEFAULT_PONUDJENA_VRIJEDNOST or UPDATED_PONUDJENA_VRIJEDNOST
        defaultViewPonudeShouldBeFound("ponudjenaVrijednost.in=" + DEFAULT_PONUDJENA_VRIJEDNOST + "," + UPDATED_PONUDJENA_VRIJEDNOST);

        // Get all the viewPonudeList where ponudjenaVrijednost equals to UPDATED_PONUDJENA_VRIJEDNOST
        defaultViewPonudeShouldNotBeFound("ponudjenaVrijednost.in=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewPonudesByPonudjenaVrijednostIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where ponudjenaVrijednost is not null
        defaultViewPonudeShouldBeFound("ponudjenaVrijednost.specified=true");

        // Get all the viewPonudeList where ponudjenaVrijednost is null
        defaultViewPonudeShouldNotBeFound("ponudjenaVrijednost.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPonudesByPonudjenaVrijednostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where ponudjenaVrijednost is greater than or equal to DEFAULT_PONUDJENA_VRIJEDNOST
        defaultViewPonudeShouldBeFound("ponudjenaVrijednost.greaterThanOrEqual=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the viewPonudeList where ponudjenaVrijednost is greater than or equal to UPDATED_PONUDJENA_VRIJEDNOST
        defaultViewPonudeShouldNotBeFound("ponudjenaVrijednost.greaterThanOrEqual=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewPonudesByPonudjenaVrijednostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where ponudjenaVrijednost is less than or equal to DEFAULT_PONUDJENA_VRIJEDNOST
        defaultViewPonudeShouldBeFound("ponudjenaVrijednost.lessThanOrEqual=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the viewPonudeList where ponudjenaVrijednost is less than or equal to SMALLER_PONUDJENA_VRIJEDNOST
        defaultViewPonudeShouldNotBeFound("ponudjenaVrijednost.lessThanOrEqual=" + SMALLER_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewPonudesByPonudjenaVrijednostIsLessThanSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where ponudjenaVrijednost is less than DEFAULT_PONUDJENA_VRIJEDNOST
        defaultViewPonudeShouldNotBeFound("ponudjenaVrijednost.lessThan=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the viewPonudeList where ponudjenaVrijednost is less than UPDATED_PONUDJENA_VRIJEDNOST
        defaultViewPonudeShouldBeFound("ponudjenaVrijednost.lessThan=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewPonudesByPonudjenaVrijednostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where ponudjenaVrijednost is greater than DEFAULT_PONUDJENA_VRIJEDNOST
        defaultViewPonudeShouldNotBeFound("ponudjenaVrijednost.greaterThan=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the viewPonudeList where ponudjenaVrijednost is greater than SMALLER_PONUDJENA_VRIJEDNOST
        defaultViewPonudeShouldBeFound("ponudjenaVrijednost.greaterThan=" + SMALLER_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewPonudesByRokIsporukeIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where rokIsporuke equals to DEFAULT_ROK_ISPORUKE
        defaultViewPonudeShouldBeFound("rokIsporuke.equals=" + DEFAULT_ROK_ISPORUKE);

        // Get all the viewPonudeList where rokIsporuke equals to UPDATED_ROK_ISPORUKE
        defaultViewPonudeShouldNotBeFound("rokIsporuke.equals=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllViewPonudesByRokIsporukeIsInShouldWork() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where rokIsporuke in DEFAULT_ROK_ISPORUKE or UPDATED_ROK_ISPORUKE
        defaultViewPonudeShouldBeFound("rokIsporuke.in=" + DEFAULT_ROK_ISPORUKE + "," + UPDATED_ROK_ISPORUKE);

        // Get all the viewPonudeList where rokIsporuke equals to UPDATED_ROK_ISPORUKE
        defaultViewPonudeShouldNotBeFound("rokIsporuke.in=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllViewPonudesByRokIsporukeIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where rokIsporuke is not null
        defaultViewPonudeShouldBeFound("rokIsporuke.specified=true");

        // Get all the viewPonudeList where rokIsporuke is null
        defaultViewPonudeShouldNotBeFound("rokIsporuke.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPonudesByRokIsporukeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where rokIsporuke is greater than or equal to DEFAULT_ROK_ISPORUKE
        defaultViewPonudeShouldBeFound("rokIsporuke.greaterThanOrEqual=" + DEFAULT_ROK_ISPORUKE);

        // Get all the viewPonudeList where rokIsporuke is greater than or equal to UPDATED_ROK_ISPORUKE
        defaultViewPonudeShouldNotBeFound("rokIsporuke.greaterThanOrEqual=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllViewPonudesByRokIsporukeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where rokIsporuke is less than or equal to DEFAULT_ROK_ISPORUKE
        defaultViewPonudeShouldBeFound("rokIsporuke.lessThanOrEqual=" + DEFAULT_ROK_ISPORUKE);

        // Get all the viewPonudeList where rokIsporuke is less than or equal to SMALLER_ROK_ISPORUKE
        defaultViewPonudeShouldNotBeFound("rokIsporuke.lessThanOrEqual=" + SMALLER_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllViewPonudesByRokIsporukeIsLessThanSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where rokIsporuke is less than DEFAULT_ROK_ISPORUKE
        defaultViewPonudeShouldNotBeFound("rokIsporuke.lessThan=" + DEFAULT_ROK_ISPORUKE);

        // Get all the viewPonudeList where rokIsporuke is less than UPDATED_ROK_ISPORUKE
        defaultViewPonudeShouldBeFound("rokIsporuke.lessThan=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllViewPonudesByRokIsporukeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where rokIsporuke is greater than DEFAULT_ROK_ISPORUKE
        defaultViewPonudeShouldNotBeFound("rokIsporuke.greaterThan=" + DEFAULT_ROK_ISPORUKE);

        // Get all the viewPonudeList where rokIsporuke is greater than SMALLER_ROK_ISPORUKE
        defaultViewPonudeShouldBeFound("rokIsporuke.greaterThan=" + SMALLER_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllViewPonudesByJedinicnaCijenaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where jedinicnaCijena equals to DEFAULT_JEDINICNA_CIJENA
        defaultViewPonudeShouldBeFound("jedinicnaCijena.equals=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the viewPonudeList where jedinicnaCijena equals to UPDATED_JEDINICNA_CIJENA
        defaultViewPonudeShouldNotBeFound("jedinicnaCijena.equals=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewPonudesByJedinicnaCijenaIsInShouldWork() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where jedinicnaCijena in DEFAULT_JEDINICNA_CIJENA or UPDATED_JEDINICNA_CIJENA
        defaultViewPonudeShouldBeFound("jedinicnaCijena.in=" + DEFAULT_JEDINICNA_CIJENA + "," + UPDATED_JEDINICNA_CIJENA);

        // Get all the viewPonudeList where jedinicnaCijena equals to UPDATED_JEDINICNA_CIJENA
        defaultViewPonudeShouldNotBeFound("jedinicnaCijena.in=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewPonudesByJedinicnaCijenaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where jedinicnaCijena is not null
        defaultViewPonudeShouldBeFound("jedinicnaCijena.specified=true");

        // Get all the viewPonudeList where jedinicnaCijena is null
        defaultViewPonudeShouldNotBeFound("jedinicnaCijena.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPonudesByJedinicnaCijenaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where jedinicnaCijena is greater than or equal to DEFAULT_JEDINICNA_CIJENA
        defaultViewPonudeShouldBeFound("jedinicnaCijena.greaterThanOrEqual=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the viewPonudeList where jedinicnaCijena is greater than or equal to UPDATED_JEDINICNA_CIJENA
        defaultViewPonudeShouldNotBeFound("jedinicnaCijena.greaterThanOrEqual=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewPonudesByJedinicnaCijenaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where jedinicnaCijena is less than or equal to DEFAULT_JEDINICNA_CIJENA
        defaultViewPonudeShouldBeFound("jedinicnaCijena.lessThanOrEqual=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the viewPonudeList where jedinicnaCijena is less than or equal to SMALLER_JEDINICNA_CIJENA
        defaultViewPonudeShouldNotBeFound("jedinicnaCijena.lessThanOrEqual=" + SMALLER_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewPonudesByJedinicnaCijenaIsLessThanSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where jedinicnaCijena is less than DEFAULT_JEDINICNA_CIJENA
        defaultViewPonudeShouldNotBeFound("jedinicnaCijena.lessThan=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the viewPonudeList where jedinicnaCijena is less than UPDATED_JEDINICNA_CIJENA
        defaultViewPonudeShouldBeFound("jedinicnaCijena.lessThan=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewPonudesByJedinicnaCijenaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where jedinicnaCijena is greater than DEFAULT_JEDINICNA_CIJENA
        defaultViewPonudeShouldNotBeFound("jedinicnaCijena.greaterThan=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the viewPonudeList where jedinicnaCijena is greater than SMALLER_JEDINICNA_CIJENA
        defaultViewPonudeShouldBeFound("jedinicnaCijena.greaterThan=" + SMALLER_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewPonudesBySelectedIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where selected equals to DEFAULT_SELECTED
        defaultViewPonudeShouldBeFound("selected.equals=" + DEFAULT_SELECTED);

        // Get all the viewPonudeList where selected equals to UPDATED_SELECTED
        defaultViewPonudeShouldNotBeFound("selected.equals=" + UPDATED_SELECTED);
    }

    @Test
    @Transactional
    void getAllViewPonudesBySelectedIsInShouldWork() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where selected in DEFAULT_SELECTED or UPDATED_SELECTED
        defaultViewPonudeShouldBeFound("selected.in=" + DEFAULT_SELECTED + "," + UPDATED_SELECTED);

        // Get all the viewPonudeList where selected equals to UPDATED_SELECTED
        defaultViewPonudeShouldNotBeFound("selected.in=" + UPDATED_SELECTED);
    }

    @Test
    @Transactional
    void getAllViewPonudesBySelectedIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where selected is not null
        defaultViewPonudeShouldBeFound("selected.specified=true");

        // Get all the viewPonudeList where selected is null
        defaultViewPonudeShouldNotBeFound("selected.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPonudesByNazivPonudjacaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where nazivPonudjaca equals to DEFAULT_NAZIV_PONUDJACA
        defaultViewPonudeShouldBeFound("nazivPonudjaca.equals=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the viewPonudeList where nazivPonudjaca equals to UPDATED_NAZIV_PONUDJACA
        defaultViewPonudeShouldNotBeFound("nazivPonudjaca.equals=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllViewPonudesByNazivPonudjacaIsInShouldWork() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where nazivPonudjaca in DEFAULT_NAZIV_PONUDJACA or UPDATED_NAZIV_PONUDJACA
        defaultViewPonudeShouldBeFound("nazivPonudjaca.in=" + DEFAULT_NAZIV_PONUDJACA + "," + UPDATED_NAZIV_PONUDJACA);

        // Get all the viewPonudeList where nazivPonudjaca equals to UPDATED_NAZIV_PONUDJACA
        defaultViewPonudeShouldNotBeFound("nazivPonudjaca.in=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllViewPonudesByNazivPonudjacaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where nazivPonudjaca is not null
        defaultViewPonudeShouldBeFound("nazivPonudjaca.specified=true");

        // Get all the viewPonudeList where nazivPonudjaca is null
        defaultViewPonudeShouldNotBeFound("nazivPonudjaca.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPonudesByNazivPonudjacaContainsSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where nazivPonudjaca contains DEFAULT_NAZIV_PONUDJACA
        defaultViewPonudeShouldBeFound("nazivPonudjaca.contains=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the viewPonudeList where nazivPonudjaca contains UPDATED_NAZIV_PONUDJACA
        defaultViewPonudeShouldNotBeFound("nazivPonudjaca.contains=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllViewPonudesByNazivPonudjacaNotContainsSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where nazivPonudjaca does not contain DEFAULT_NAZIV_PONUDJACA
        defaultViewPonudeShouldNotBeFound("nazivPonudjaca.doesNotContain=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the viewPonudeList where nazivPonudjaca does not contain UPDATED_NAZIV_PONUDJACA
        defaultViewPonudeShouldBeFound("nazivPonudjaca.doesNotContain=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllViewPonudesByKarakteristikaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where karakteristika equals to DEFAULT_KARAKTERISTIKA
        defaultViewPonudeShouldBeFound("karakteristika.equals=" + DEFAULT_KARAKTERISTIKA);

        // Get all the viewPonudeList where karakteristika equals to UPDATED_KARAKTERISTIKA
        defaultViewPonudeShouldNotBeFound("karakteristika.equals=" + UPDATED_KARAKTERISTIKA);
    }

    @Test
    @Transactional
    void getAllViewPonudesByKarakteristikaIsInShouldWork() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where karakteristika in DEFAULT_KARAKTERISTIKA or UPDATED_KARAKTERISTIKA
        defaultViewPonudeShouldBeFound("karakteristika.in=" + DEFAULT_KARAKTERISTIKA + "," + UPDATED_KARAKTERISTIKA);

        // Get all the viewPonudeList where karakteristika equals to UPDATED_KARAKTERISTIKA
        defaultViewPonudeShouldNotBeFound("karakteristika.in=" + UPDATED_KARAKTERISTIKA);
    }

    @Test
    @Transactional
    void getAllViewPonudesByKarakteristikaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where karakteristika is not null
        defaultViewPonudeShouldBeFound("karakteristika.specified=true");

        // Get all the viewPonudeList where karakteristika is null
        defaultViewPonudeShouldNotBeFound("karakteristika.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPonudesByKarakteristikaContainsSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where karakteristika contains DEFAULT_KARAKTERISTIKA
        defaultViewPonudeShouldBeFound("karakteristika.contains=" + DEFAULT_KARAKTERISTIKA);

        // Get all the viewPonudeList where karakteristika contains UPDATED_KARAKTERISTIKA
        defaultViewPonudeShouldNotBeFound("karakteristika.contains=" + UPDATED_KARAKTERISTIKA);
    }

    @Test
    @Transactional
    void getAllViewPonudesByKarakteristikaNotContainsSomething() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        // Get all the viewPonudeList where karakteristika does not contain DEFAULT_KARAKTERISTIKA
        defaultViewPonudeShouldNotBeFound("karakteristika.doesNotContain=" + DEFAULT_KARAKTERISTIKA);

        // Get all the viewPonudeList where karakteristika does not contain UPDATED_KARAKTERISTIKA
        defaultViewPonudeShouldBeFound("karakteristika.doesNotContain=" + UPDATED_KARAKTERISTIKA);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultViewPonudeShouldBeFound(String filter) throws Exception {
        restViewPonudeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viewPonude.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].sifraPonude").value(hasItem(DEFAULT_SIFRA_PONUDE)))
            .andExpect(jsonPath("$.[*].brojPartije").value(hasItem(DEFAULT_BROJ_PARTIJE)))
            .andExpect(jsonPath("$.[*].nazivProizvodjaca").value(hasItem(DEFAULT_NAZIV_PROIZVODJACA)))
            .andExpect(jsonPath("$.[*].zasticeniNaziv").value(hasItem(DEFAULT_ZASTICENI_NAZIV)))
            .andExpect(jsonPath("$.[*].ponudjenaVrijednost").value(hasItem(DEFAULT_PONUDJENA_VRIJEDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].rokIsporuke").value(hasItem(DEFAULT_ROK_ISPORUKE)))
            .andExpect(jsonPath("$.[*].jedinicnaCijena").value(hasItem(DEFAULT_JEDINICNA_CIJENA.doubleValue())))
            .andExpect(jsonPath("$.[*].selected").value(hasItem(DEFAULT_SELECTED.booleanValue())))
            .andExpect(jsonPath("$.[*].nazivPonudjaca").value(hasItem(DEFAULT_NAZIV_PONUDJACA)))
            .andExpect(jsonPath("$.[*].karakteristika").value(hasItem(DEFAULT_KARAKTERISTIKA)));

        // Check, that the count call also returns 1
        restViewPonudeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultViewPonudeShouldNotBeFound(String filter) throws Exception {
        restViewPonudeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restViewPonudeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingViewPonude() throws Exception {
        // Get the viewPonude
        restViewPonudeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingViewPonude() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        int databaseSizeBeforeUpdate = viewPonudeRepository.findAll().size();

        // Update the viewPonude
        ViewPonude updatedViewPonude = viewPonudeRepository.findById(viewPonude.getId()).get();
        // Disconnect from session so that the updates on updatedViewPonude are not directly saved in db
        em.detach(updatedViewPonude);
        updatedViewPonude
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .sifraPonude(UPDATED_SIFRA_PONUDE)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .nazivProizvodjaca(UPDATED_NAZIV_PROIZVODJACA)
            .zasticeniNaziv(UPDATED_ZASTICENI_NAZIV)
            .ponudjenaVrijednost(UPDATED_PONUDJENA_VRIJEDNOST)
            .rokIsporuke(UPDATED_ROK_ISPORUKE)
            .jedinicnaCijena(UPDATED_JEDINICNA_CIJENA)
            .selected(UPDATED_SELECTED)
            .nazivPonudjaca(UPDATED_NAZIV_PONUDJACA)
            .karakteristika(UPDATED_KARAKTERISTIKA);

        restViewPonudeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedViewPonude.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedViewPonude))
            )
            .andExpect(status().isOk());

        // Validate the ViewPonude in the database
        List<ViewPonude> viewPonudeList = viewPonudeRepository.findAll();
        assertThat(viewPonudeList).hasSize(databaseSizeBeforeUpdate);
        ViewPonude testViewPonude = viewPonudeList.get(viewPonudeList.size() - 1);
        assertThat(testViewPonude.getSifraPostupka()).isEqualTo(UPDATED_SIFRA_POSTUPKA);
        assertThat(testViewPonude.getSifraPonude()).isEqualTo(UPDATED_SIFRA_PONUDE);
        assertThat(testViewPonude.getBrojPartije()).isEqualTo(UPDATED_BROJ_PARTIJE);
        assertThat(testViewPonude.getNazivProizvodjaca()).isEqualTo(UPDATED_NAZIV_PROIZVODJACA);
        assertThat(testViewPonude.getZasticeniNaziv()).isEqualTo(UPDATED_ZASTICENI_NAZIV);
        assertThat(testViewPonude.getPonudjenaVrijednost()).isEqualTo(UPDATED_PONUDJENA_VRIJEDNOST);
        assertThat(testViewPonude.getRokIsporuke()).isEqualTo(UPDATED_ROK_ISPORUKE);
        assertThat(testViewPonude.getJedinicnaCijena()).isEqualTo(UPDATED_JEDINICNA_CIJENA);
        assertThat(testViewPonude.getSelected()).isEqualTo(UPDATED_SELECTED);
        assertThat(testViewPonude.getNazivPonudjaca()).isEqualTo(UPDATED_NAZIV_PONUDJACA);
        assertThat(testViewPonude.getKarakteristika()).isEqualTo(UPDATED_KARAKTERISTIKA);
    }

    @Test
    @Transactional
    void putNonExistingViewPonude() throws Exception {
        int databaseSizeBeforeUpdate = viewPonudeRepository.findAll().size();
        viewPonude.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restViewPonudeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, viewPonude.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(viewPonude))
            )
            .andExpect(status().isBadRequest());

        // Validate the ViewPonude in the database
        List<ViewPonude> viewPonudeList = viewPonudeRepository.findAll();
        assertThat(viewPonudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchViewPonude() throws Exception {
        int databaseSizeBeforeUpdate = viewPonudeRepository.findAll().size();
        viewPonude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViewPonudeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(viewPonude))
            )
            .andExpect(status().isBadRequest());

        // Validate the ViewPonude in the database
        List<ViewPonude> viewPonudeList = viewPonudeRepository.findAll();
        assertThat(viewPonudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamViewPonude() throws Exception {
        int databaseSizeBeforeUpdate = viewPonudeRepository.findAll().size();
        viewPonude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViewPonudeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(viewPonude)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ViewPonude in the database
        List<ViewPonude> viewPonudeList = viewPonudeRepository.findAll();
        assertThat(viewPonudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateViewPonudeWithPatch() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        int databaseSizeBeforeUpdate = viewPonudeRepository.findAll().size();

        // Update the viewPonude using partial update
        ViewPonude partialUpdatedViewPonude = new ViewPonude();
        partialUpdatedViewPonude.setId(viewPonude.getId());

        partialUpdatedViewPonude
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .sifraPonude(UPDATED_SIFRA_PONUDE)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .zasticeniNaziv(UPDATED_ZASTICENI_NAZIV)
            .ponudjenaVrijednost(UPDATED_PONUDJENA_VRIJEDNOST)
            .rokIsporuke(UPDATED_ROK_ISPORUKE)
            .jedinicnaCijena(UPDATED_JEDINICNA_CIJENA)
            .nazivPonudjaca(UPDATED_NAZIV_PONUDJACA);

        restViewPonudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedViewPonude.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedViewPonude))
            )
            .andExpect(status().isOk());

        // Validate the ViewPonude in the database
        List<ViewPonude> viewPonudeList = viewPonudeRepository.findAll();
        assertThat(viewPonudeList).hasSize(databaseSizeBeforeUpdate);
        ViewPonude testViewPonude = viewPonudeList.get(viewPonudeList.size() - 1);
        assertThat(testViewPonude.getSifraPostupka()).isEqualTo(UPDATED_SIFRA_POSTUPKA);
        assertThat(testViewPonude.getSifraPonude()).isEqualTo(UPDATED_SIFRA_PONUDE);
        assertThat(testViewPonude.getBrojPartije()).isEqualTo(UPDATED_BROJ_PARTIJE);
        assertThat(testViewPonude.getNazivProizvodjaca()).isEqualTo(DEFAULT_NAZIV_PROIZVODJACA);
        assertThat(testViewPonude.getZasticeniNaziv()).isEqualTo(UPDATED_ZASTICENI_NAZIV);
        assertThat(testViewPonude.getPonudjenaVrijednost()).isEqualTo(UPDATED_PONUDJENA_VRIJEDNOST);
        assertThat(testViewPonude.getRokIsporuke()).isEqualTo(UPDATED_ROK_ISPORUKE);
        assertThat(testViewPonude.getJedinicnaCijena()).isEqualTo(UPDATED_JEDINICNA_CIJENA);
        assertThat(testViewPonude.getSelected()).isEqualTo(DEFAULT_SELECTED);
        assertThat(testViewPonude.getNazivPonudjaca()).isEqualTo(UPDATED_NAZIV_PONUDJACA);
        assertThat(testViewPonude.getKarakteristika()).isEqualTo(DEFAULT_KARAKTERISTIKA);
    }

    @Test
    @Transactional
    void fullUpdateViewPonudeWithPatch() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        int databaseSizeBeforeUpdate = viewPonudeRepository.findAll().size();

        // Update the viewPonude using partial update
        ViewPonude partialUpdatedViewPonude = new ViewPonude();
        partialUpdatedViewPonude.setId(viewPonude.getId());

        partialUpdatedViewPonude
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .sifraPonude(UPDATED_SIFRA_PONUDE)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .nazivProizvodjaca(UPDATED_NAZIV_PROIZVODJACA)
            .zasticeniNaziv(UPDATED_ZASTICENI_NAZIV)
            .ponudjenaVrijednost(UPDATED_PONUDJENA_VRIJEDNOST)
            .rokIsporuke(UPDATED_ROK_ISPORUKE)
            .jedinicnaCijena(UPDATED_JEDINICNA_CIJENA)
            .selected(UPDATED_SELECTED)
            .nazivPonudjaca(UPDATED_NAZIV_PONUDJACA)
            .karakteristika(UPDATED_KARAKTERISTIKA);

        restViewPonudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedViewPonude.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedViewPonude))
            )
            .andExpect(status().isOk());

        // Validate the ViewPonude in the database
        List<ViewPonude> viewPonudeList = viewPonudeRepository.findAll();
        assertThat(viewPonudeList).hasSize(databaseSizeBeforeUpdate);
        ViewPonude testViewPonude = viewPonudeList.get(viewPonudeList.size() - 1);
        assertThat(testViewPonude.getSifraPostupka()).isEqualTo(UPDATED_SIFRA_POSTUPKA);
        assertThat(testViewPonude.getSifraPonude()).isEqualTo(UPDATED_SIFRA_PONUDE);
        assertThat(testViewPonude.getBrojPartije()).isEqualTo(UPDATED_BROJ_PARTIJE);
        assertThat(testViewPonude.getNazivProizvodjaca()).isEqualTo(UPDATED_NAZIV_PROIZVODJACA);
        assertThat(testViewPonude.getZasticeniNaziv()).isEqualTo(UPDATED_ZASTICENI_NAZIV);
        assertThat(testViewPonude.getPonudjenaVrijednost()).isEqualTo(UPDATED_PONUDJENA_VRIJEDNOST);
        assertThat(testViewPonude.getRokIsporuke()).isEqualTo(UPDATED_ROK_ISPORUKE);
        assertThat(testViewPonude.getJedinicnaCijena()).isEqualTo(UPDATED_JEDINICNA_CIJENA);
        assertThat(testViewPonude.getSelected()).isEqualTo(UPDATED_SELECTED);
        assertThat(testViewPonude.getNazivPonudjaca()).isEqualTo(UPDATED_NAZIV_PONUDJACA);
        assertThat(testViewPonude.getKarakteristika()).isEqualTo(UPDATED_KARAKTERISTIKA);
    }

    @Test
    @Transactional
    void patchNonExistingViewPonude() throws Exception {
        int databaseSizeBeforeUpdate = viewPonudeRepository.findAll().size();
        viewPonude.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restViewPonudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, viewPonude.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(viewPonude))
            )
            .andExpect(status().isBadRequest());

        // Validate the ViewPonude in the database
        List<ViewPonude> viewPonudeList = viewPonudeRepository.findAll();
        assertThat(viewPonudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchViewPonude() throws Exception {
        int databaseSizeBeforeUpdate = viewPonudeRepository.findAll().size();
        viewPonude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViewPonudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(viewPonude))
            )
            .andExpect(status().isBadRequest());

        // Validate the ViewPonude in the database
        List<ViewPonude> viewPonudeList = viewPonudeRepository.findAll();
        assertThat(viewPonudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamViewPonude() throws Exception {
        int databaseSizeBeforeUpdate = viewPonudeRepository.findAll().size();
        viewPonude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViewPonudeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(viewPonude))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ViewPonude in the database
        List<ViewPonude> viewPonudeList = viewPonudeRepository.findAll();
        assertThat(viewPonudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteViewPonude() throws Exception {
        // Initialize the database
        viewPonudeRepository.saveAndFlush(viewPonude);

        int databaseSizeBeforeDelete = viewPonudeRepository.findAll().size();

        // Delete the viewPonude
        restViewPonudeMockMvc
            .perform(delete(ENTITY_API_URL_ID, viewPonude.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ViewPonude> viewPonudeList = viewPonudeRepository.findAll();
        assertThat(viewPonudeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
