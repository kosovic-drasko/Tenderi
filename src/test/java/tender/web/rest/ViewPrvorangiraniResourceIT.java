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
import tender.domain.ViewPrvorangirani;
import tender.repository.ViewPrvorangiraniRepository;
import tender.service.criteria.ViewPrvorangiraniCriteria;
import tender.service.dto.ViewPrvorangiraniDTO;
import tender.service.mapper.ViewPrvorangiraniMapper;

/**
 * Integration tests for the {@link ViewPrvorangiraniResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ViewPrvorangiraniResourceIT {

    private static final Integer DEFAULT_SIFRA_POSTUPKA = 1;
    private static final Integer UPDATED_SIFRA_POSTUPKA = 2;
    private static final Integer SMALLER_SIFRA_POSTUPKA = 1 - 1;

    private static final String DEFAULT_NAZIV_PONUDJACA = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV_PONUDJACA = "BBBBBBBBBB";

    private static final Integer DEFAULT_SIFRA_PONUDE = 1;
    private static final Integer UPDATED_SIFRA_PONUDE = 2;
    private static final Integer SMALLER_SIFRA_PONUDE = 1 - 1;

    private static final String DEFAULT_ATC = "AAAAAAAAAA";
    private static final String UPDATED_ATC = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRAZENA_KOLICINA = 1;
    private static final Integer UPDATED_TRAZENA_KOLICINA = 2;
    private static final Integer SMALLER_TRAZENA_KOLICINA = 1 - 1;

    private static final Double DEFAULT_PROCIJENJENA_VRIJEDNOST = 1D;
    private static final Double UPDATED_PROCIJENJENA_VRIJEDNOST = 2D;
    private static final Double SMALLER_PROCIJENJENA_VRIJEDNOST = 1D - 1D;

    private static final String DEFAULT_NAZIV_PROIZVODJACA = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV_PROIZVODJACA = "BBBBBBBBBB";

    private static final String DEFAULT_ZASTICENI_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_ZASTICENI_NAZIV = "BBBBBBBBBB";

    private static final Double DEFAULT_JEDINICNA_CIJENA = 1D;
    private static final Double UPDATED_JEDINICNA_CIJENA = 2D;
    private static final Double SMALLER_JEDINICNA_CIJENA = 1D - 1D;

    private static final Double DEFAULT_PONUDJENA_VRIJEDNOST = 1D;
    private static final Double UPDATED_PONUDJENA_VRIJEDNOST = 2D;
    private static final Double SMALLER_PONUDJENA_VRIJEDNOST = 1D - 1D;

    private static final Integer DEFAULT_ROK_ISPORUKE = 1;
    private static final Integer UPDATED_ROK_ISPORUKE = 2;
    private static final Integer SMALLER_ROK_ISPORUKE = 1 - 1;

    private static final String DEFAULT_VRSTA_POSTUPKA = "AAAAAAAAAA";
    private static final String UPDATED_VRSTA_POSTUPKA = "BBBBBBBBBB";

    private static final Double DEFAULT_BOD_CIJENA = 1D;
    private static final Double UPDATED_BOD_CIJENA = 2D;
    private static final Double SMALLER_BOD_CIJENA = 1D - 1D;

    private static final Double DEFAULT_BOD_ROK = 1D;
    private static final Double UPDATED_BOD_ROK = 2D;
    private static final Double SMALLER_BOD_ROK = 1D - 1D;

    private static final Double DEFAULT_BOD_UKUPNO = 1D;
    private static final Double UPDATED_BOD_UKUPNO = 2D;
    private static final Double SMALLER_BOD_UKUPNO = 1D - 1D;

    private static final String DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE = "AAAAAAAAAA";
    private static final String UPDATED_KARAKTERISTIKA_SPECIFIKACIJE = "BBBBBBBBBB";

    private static final String DEFAULT_KARAKTERISTIKA_PONUDE = "AAAAAAAAAA";
    private static final String UPDATED_KARAKTERISTIKA_PONUDE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/view-prvorangiranis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ViewPrvorangiraniRepository viewPrvorangiraniRepository;

    @Autowired
    private ViewPrvorangiraniMapper viewPrvorangiraniMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restViewPrvorangiraniMockMvc;

    private ViewPrvorangirani viewPrvorangirani;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ViewPrvorangirani createEntity(EntityManager em) {
        ViewPrvorangirani viewPrvorangirani = new ViewPrvorangirani()
            .sifraPostupka(DEFAULT_SIFRA_POSTUPKA)
            .nazivPonudjaca(DEFAULT_NAZIV_PONUDJACA)
            .sifraPonude(DEFAULT_SIFRA_PONUDE)
            .atc(DEFAULT_ATC)
            .trazenaKolicina(DEFAULT_TRAZENA_KOLICINA)
            .procijenjenaVrijednost(DEFAULT_PROCIJENJENA_VRIJEDNOST)
            .nazivProizvodjaca(DEFAULT_NAZIV_PROIZVODJACA)
            .zasticeniNaziv(DEFAULT_ZASTICENI_NAZIV)
            .jedinicnaCijena(DEFAULT_JEDINICNA_CIJENA)
            .ponudjenaVrijednost(DEFAULT_PONUDJENA_VRIJEDNOST)
            .rokIsporuke(DEFAULT_ROK_ISPORUKE)
            .vrstaPostupka(DEFAULT_VRSTA_POSTUPKA)
            .bodCijena(DEFAULT_BOD_CIJENA)
            .bodRok(DEFAULT_BOD_ROK)
            .bodUkupno(DEFAULT_BOD_UKUPNO)
            .karakteristikaSpecifikacije(DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE)
            .karakteristikaPonude(DEFAULT_KARAKTERISTIKA_PONUDE);
        return viewPrvorangirani;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ViewPrvorangirani createUpdatedEntity(EntityManager em) {
        ViewPrvorangirani viewPrvorangirani = new ViewPrvorangirani()
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .nazivPonudjaca(UPDATED_NAZIV_PONUDJACA)
            .sifraPonude(UPDATED_SIFRA_PONUDE)
            .atc(UPDATED_ATC)
            .trazenaKolicina(UPDATED_TRAZENA_KOLICINA)
            .procijenjenaVrijednost(UPDATED_PROCIJENJENA_VRIJEDNOST)
            .nazivProizvodjaca(UPDATED_NAZIV_PROIZVODJACA)
            .zasticeniNaziv(UPDATED_ZASTICENI_NAZIV)
            .jedinicnaCijena(UPDATED_JEDINICNA_CIJENA)
            .ponudjenaVrijednost(UPDATED_PONUDJENA_VRIJEDNOST)
            .rokIsporuke(UPDATED_ROK_ISPORUKE)
            .vrstaPostupka(UPDATED_VRSTA_POSTUPKA)
            .bodCijena(UPDATED_BOD_CIJENA)
            .bodRok(UPDATED_BOD_ROK)
            .bodUkupno(UPDATED_BOD_UKUPNO)
            .karakteristikaSpecifikacije(UPDATED_KARAKTERISTIKA_SPECIFIKACIJE)
            .karakteristikaPonude(UPDATED_KARAKTERISTIKA_PONUDE);
        return viewPrvorangirani;
    }

    @BeforeEach
    public void initTest() {
        viewPrvorangirani = createEntity(em);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranis() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList
        restViewPrvorangiraniMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viewPrvorangirani.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].nazivPonudjaca").value(hasItem(DEFAULT_NAZIV_PONUDJACA)))
            .andExpect(jsonPath("$.[*].sifraPonude").value(hasItem(DEFAULT_SIFRA_PONUDE)))
            .andExpect(jsonPath("$.[*].atc").value(hasItem(DEFAULT_ATC)))
            .andExpect(jsonPath("$.[*].trazenaKolicina").value(hasItem(DEFAULT_TRAZENA_KOLICINA)))
            .andExpect(jsonPath("$.[*].procijenjenaVrijednost").value(hasItem(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].nazivProizvodjaca").value(hasItem(DEFAULT_NAZIV_PROIZVODJACA)))
            .andExpect(jsonPath("$.[*].zasticeniNaziv").value(hasItem(DEFAULT_ZASTICENI_NAZIV)))
            .andExpect(jsonPath("$.[*].jedinicnaCijena").value(hasItem(DEFAULT_JEDINICNA_CIJENA.doubleValue())))
            .andExpect(jsonPath("$.[*].ponudjenaVrijednost").value(hasItem(DEFAULT_PONUDJENA_VRIJEDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].rokIsporuke").value(hasItem(DEFAULT_ROK_ISPORUKE)))
            .andExpect(jsonPath("$.[*].vrstaPostupka").value(hasItem(DEFAULT_VRSTA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].bodCijena").value(hasItem(DEFAULT_BOD_CIJENA.doubleValue())))
            .andExpect(jsonPath("$.[*].bodRok").value(hasItem(DEFAULT_BOD_ROK.doubleValue())))
            .andExpect(jsonPath("$.[*].bodUkupno").value(hasItem(DEFAULT_BOD_UKUPNO.doubleValue())))
            .andExpect(jsonPath("$.[*].karakteristikaSpecifikacije").value(hasItem(DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE)))
            .andExpect(jsonPath("$.[*].karakteristikaPonude").value(hasItem(DEFAULT_KARAKTERISTIKA_PONUDE)));
    }

    @Test
    @Transactional
    void getViewPrvorangirani() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get the viewPrvorangirani
        restViewPrvorangiraniMockMvc
            .perform(get(ENTITY_API_URL_ID, viewPrvorangirani.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(viewPrvorangirani.getId().intValue()))
            .andExpect(jsonPath("$.sifraPostupka").value(DEFAULT_SIFRA_POSTUPKA))
            .andExpect(jsonPath("$.nazivPonudjaca").value(DEFAULT_NAZIV_PONUDJACA))
            .andExpect(jsonPath("$.sifraPonude").value(DEFAULT_SIFRA_PONUDE))
            .andExpect(jsonPath("$.atc").value(DEFAULT_ATC))
            .andExpect(jsonPath("$.trazenaKolicina").value(DEFAULT_TRAZENA_KOLICINA))
            .andExpect(jsonPath("$.procijenjenaVrijednost").value(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue()))
            .andExpect(jsonPath("$.nazivProizvodjaca").value(DEFAULT_NAZIV_PROIZVODJACA))
            .andExpect(jsonPath("$.zasticeniNaziv").value(DEFAULT_ZASTICENI_NAZIV))
            .andExpect(jsonPath("$.jedinicnaCijena").value(DEFAULT_JEDINICNA_CIJENA.doubleValue()))
            .andExpect(jsonPath("$.ponudjenaVrijednost").value(DEFAULT_PONUDJENA_VRIJEDNOST.doubleValue()))
            .andExpect(jsonPath("$.rokIsporuke").value(DEFAULT_ROK_ISPORUKE))
            .andExpect(jsonPath("$.vrstaPostupka").value(DEFAULT_VRSTA_POSTUPKA))
            .andExpect(jsonPath("$.bodCijena").value(DEFAULT_BOD_CIJENA.doubleValue()))
            .andExpect(jsonPath("$.bodRok").value(DEFAULT_BOD_ROK.doubleValue()))
            .andExpect(jsonPath("$.bodUkupno").value(DEFAULT_BOD_UKUPNO.doubleValue()))
            .andExpect(jsonPath("$.karakteristikaSpecifikacije").value(DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE))
            .andExpect(jsonPath("$.karakteristikaPonude").value(DEFAULT_KARAKTERISTIKA_PONUDE));
    }

    @Test
    @Transactional
    void getViewPrvorangiranisByIdFiltering() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        Long id = viewPrvorangirani.getId();

        defaultViewPrvorangiraniShouldBeFound("id.equals=" + id);
        defaultViewPrvorangiraniShouldNotBeFound("id.notEquals=" + id);

        defaultViewPrvorangiraniShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultViewPrvorangiraniShouldNotBeFound("id.greaterThan=" + id);

        defaultViewPrvorangiraniShouldBeFound("id.lessThanOrEqual=" + id);
        defaultViewPrvorangiraniShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisBySifraPostupkaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where sifraPostupka equals to DEFAULT_SIFRA_POSTUPKA
        defaultViewPrvorangiraniShouldBeFound("sifraPostupka.equals=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the viewPrvorangiraniList where sifraPostupka equals to UPDATED_SIFRA_POSTUPKA
        defaultViewPrvorangiraniShouldNotBeFound("sifraPostupka.equals=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisBySifraPostupkaIsInShouldWork() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where sifraPostupka in DEFAULT_SIFRA_POSTUPKA or UPDATED_SIFRA_POSTUPKA
        defaultViewPrvorangiraniShouldBeFound("sifraPostupka.in=" + DEFAULT_SIFRA_POSTUPKA + "," + UPDATED_SIFRA_POSTUPKA);

        // Get all the viewPrvorangiraniList where sifraPostupka equals to UPDATED_SIFRA_POSTUPKA
        defaultViewPrvorangiraniShouldNotBeFound("sifraPostupka.in=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisBySifraPostupkaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where sifraPostupka is not null
        defaultViewPrvorangiraniShouldBeFound("sifraPostupka.specified=true");

        // Get all the viewPrvorangiraniList where sifraPostupka is null
        defaultViewPrvorangiraniShouldNotBeFound("sifraPostupka.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisBySifraPostupkaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where sifraPostupka is greater than or equal to DEFAULT_SIFRA_POSTUPKA
        defaultViewPrvorangiraniShouldBeFound("sifraPostupka.greaterThanOrEqual=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the viewPrvorangiraniList where sifraPostupka is greater than or equal to UPDATED_SIFRA_POSTUPKA
        defaultViewPrvorangiraniShouldNotBeFound("sifraPostupka.greaterThanOrEqual=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisBySifraPostupkaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where sifraPostupka is less than or equal to DEFAULT_SIFRA_POSTUPKA
        defaultViewPrvorangiraniShouldBeFound("sifraPostupka.lessThanOrEqual=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the viewPrvorangiraniList where sifraPostupka is less than or equal to SMALLER_SIFRA_POSTUPKA
        defaultViewPrvorangiraniShouldNotBeFound("sifraPostupka.lessThanOrEqual=" + SMALLER_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisBySifraPostupkaIsLessThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where sifraPostupka is less than DEFAULT_SIFRA_POSTUPKA
        defaultViewPrvorangiraniShouldNotBeFound("sifraPostupka.lessThan=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the viewPrvorangiraniList where sifraPostupka is less than UPDATED_SIFRA_POSTUPKA
        defaultViewPrvorangiraniShouldBeFound("sifraPostupka.lessThan=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisBySifraPostupkaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where sifraPostupka is greater than DEFAULT_SIFRA_POSTUPKA
        defaultViewPrvorangiraniShouldNotBeFound("sifraPostupka.greaterThan=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the viewPrvorangiraniList where sifraPostupka is greater than SMALLER_SIFRA_POSTUPKA
        defaultViewPrvorangiraniShouldBeFound("sifraPostupka.greaterThan=" + SMALLER_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByNazivPonudjacaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where nazivPonudjaca equals to DEFAULT_NAZIV_PONUDJACA
        defaultViewPrvorangiraniShouldBeFound("nazivPonudjaca.equals=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the viewPrvorangiraniList where nazivPonudjaca equals to UPDATED_NAZIV_PONUDJACA
        defaultViewPrvorangiraniShouldNotBeFound("nazivPonudjaca.equals=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByNazivPonudjacaIsInShouldWork() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where nazivPonudjaca in DEFAULT_NAZIV_PONUDJACA or UPDATED_NAZIV_PONUDJACA
        defaultViewPrvorangiraniShouldBeFound("nazivPonudjaca.in=" + DEFAULT_NAZIV_PONUDJACA + "," + UPDATED_NAZIV_PONUDJACA);

        // Get all the viewPrvorangiraniList where nazivPonudjaca equals to UPDATED_NAZIV_PONUDJACA
        defaultViewPrvorangiraniShouldNotBeFound("nazivPonudjaca.in=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByNazivPonudjacaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where nazivPonudjaca is not null
        defaultViewPrvorangiraniShouldBeFound("nazivPonudjaca.specified=true");

        // Get all the viewPrvorangiraniList where nazivPonudjaca is null
        defaultViewPrvorangiraniShouldNotBeFound("nazivPonudjaca.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByNazivPonudjacaContainsSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where nazivPonudjaca contains DEFAULT_NAZIV_PONUDJACA
        defaultViewPrvorangiraniShouldBeFound("nazivPonudjaca.contains=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the viewPrvorangiraniList where nazivPonudjaca contains UPDATED_NAZIV_PONUDJACA
        defaultViewPrvorangiraniShouldNotBeFound("nazivPonudjaca.contains=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByNazivPonudjacaNotContainsSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where nazivPonudjaca does not contain DEFAULT_NAZIV_PONUDJACA
        defaultViewPrvorangiraniShouldNotBeFound("nazivPonudjaca.doesNotContain=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the viewPrvorangiraniList where nazivPonudjaca does not contain UPDATED_NAZIV_PONUDJACA
        defaultViewPrvorangiraniShouldBeFound("nazivPonudjaca.doesNotContain=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisBySifraPonudeIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where sifraPonude equals to DEFAULT_SIFRA_PONUDE
        defaultViewPrvorangiraniShouldBeFound("sifraPonude.equals=" + DEFAULT_SIFRA_PONUDE);

        // Get all the viewPrvorangiraniList where sifraPonude equals to UPDATED_SIFRA_PONUDE
        defaultViewPrvorangiraniShouldNotBeFound("sifraPonude.equals=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisBySifraPonudeIsInShouldWork() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where sifraPonude in DEFAULT_SIFRA_PONUDE or UPDATED_SIFRA_PONUDE
        defaultViewPrvorangiraniShouldBeFound("sifraPonude.in=" + DEFAULT_SIFRA_PONUDE + "," + UPDATED_SIFRA_PONUDE);

        // Get all the viewPrvorangiraniList where sifraPonude equals to UPDATED_SIFRA_PONUDE
        defaultViewPrvorangiraniShouldNotBeFound("sifraPonude.in=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisBySifraPonudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where sifraPonude is not null
        defaultViewPrvorangiraniShouldBeFound("sifraPonude.specified=true");

        // Get all the viewPrvorangiraniList where sifraPonude is null
        defaultViewPrvorangiraniShouldNotBeFound("sifraPonude.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisBySifraPonudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where sifraPonude is greater than or equal to DEFAULT_SIFRA_PONUDE
        defaultViewPrvorangiraniShouldBeFound("sifraPonude.greaterThanOrEqual=" + DEFAULT_SIFRA_PONUDE);

        // Get all the viewPrvorangiraniList where sifraPonude is greater than or equal to UPDATED_SIFRA_PONUDE
        defaultViewPrvorangiraniShouldNotBeFound("sifraPonude.greaterThanOrEqual=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisBySifraPonudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where sifraPonude is less than or equal to DEFAULT_SIFRA_PONUDE
        defaultViewPrvorangiraniShouldBeFound("sifraPonude.lessThanOrEqual=" + DEFAULT_SIFRA_PONUDE);

        // Get all the viewPrvorangiraniList where sifraPonude is less than or equal to SMALLER_SIFRA_PONUDE
        defaultViewPrvorangiraniShouldNotBeFound("sifraPonude.lessThanOrEqual=" + SMALLER_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisBySifraPonudeIsLessThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where sifraPonude is less than DEFAULT_SIFRA_PONUDE
        defaultViewPrvorangiraniShouldNotBeFound("sifraPonude.lessThan=" + DEFAULT_SIFRA_PONUDE);

        // Get all the viewPrvorangiraniList where sifraPonude is less than UPDATED_SIFRA_PONUDE
        defaultViewPrvorangiraniShouldBeFound("sifraPonude.lessThan=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisBySifraPonudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where sifraPonude is greater than DEFAULT_SIFRA_PONUDE
        defaultViewPrvorangiraniShouldNotBeFound("sifraPonude.greaterThan=" + DEFAULT_SIFRA_PONUDE);

        // Get all the viewPrvorangiraniList where sifraPonude is greater than SMALLER_SIFRA_PONUDE
        defaultViewPrvorangiraniShouldBeFound("sifraPonude.greaterThan=" + SMALLER_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByAtcIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where atc equals to DEFAULT_ATC
        defaultViewPrvorangiraniShouldBeFound("atc.equals=" + DEFAULT_ATC);

        // Get all the viewPrvorangiraniList where atc equals to UPDATED_ATC
        defaultViewPrvorangiraniShouldNotBeFound("atc.equals=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByAtcIsInShouldWork() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where atc in DEFAULT_ATC or UPDATED_ATC
        defaultViewPrvorangiraniShouldBeFound("atc.in=" + DEFAULT_ATC + "," + UPDATED_ATC);

        // Get all the viewPrvorangiraniList where atc equals to UPDATED_ATC
        defaultViewPrvorangiraniShouldNotBeFound("atc.in=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByAtcIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where atc is not null
        defaultViewPrvorangiraniShouldBeFound("atc.specified=true");

        // Get all the viewPrvorangiraniList where atc is null
        defaultViewPrvorangiraniShouldNotBeFound("atc.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByAtcContainsSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where atc contains DEFAULT_ATC
        defaultViewPrvorangiraniShouldBeFound("atc.contains=" + DEFAULT_ATC);

        // Get all the viewPrvorangiraniList where atc contains UPDATED_ATC
        defaultViewPrvorangiraniShouldNotBeFound("atc.contains=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByAtcNotContainsSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where atc does not contain DEFAULT_ATC
        defaultViewPrvorangiraniShouldNotBeFound("atc.doesNotContain=" + DEFAULT_ATC);

        // Get all the viewPrvorangiraniList where atc does not contain UPDATED_ATC
        defaultViewPrvorangiraniShouldBeFound("atc.doesNotContain=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByTrazenaKolicinaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where trazenaKolicina equals to DEFAULT_TRAZENA_KOLICINA
        defaultViewPrvorangiraniShouldBeFound("trazenaKolicina.equals=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the viewPrvorangiraniList where trazenaKolicina equals to UPDATED_TRAZENA_KOLICINA
        defaultViewPrvorangiraniShouldNotBeFound("trazenaKolicina.equals=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByTrazenaKolicinaIsInShouldWork() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where trazenaKolicina in DEFAULT_TRAZENA_KOLICINA or UPDATED_TRAZENA_KOLICINA
        defaultViewPrvorangiraniShouldBeFound("trazenaKolicina.in=" + DEFAULT_TRAZENA_KOLICINA + "," + UPDATED_TRAZENA_KOLICINA);

        // Get all the viewPrvorangiraniList where trazenaKolicina equals to UPDATED_TRAZENA_KOLICINA
        defaultViewPrvorangiraniShouldNotBeFound("trazenaKolicina.in=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByTrazenaKolicinaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where trazenaKolicina is not null
        defaultViewPrvorangiraniShouldBeFound("trazenaKolicina.specified=true");

        // Get all the viewPrvorangiraniList where trazenaKolicina is null
        defaultViewPrvorangiraniShouldNotBeFound("trazenaKolicina.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByTrazenaKolicinaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where trazenaKolicina is greater than or equal to DEFAULT_TRAZENA_KOLICINA
        defaultViewPrvorangiraniShouldBeFound("trazenaKolicina.greaterThanOrEqual=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the viewPrvorangiraniList where trazenaKolicina is greater than or equal to UPDATED_TRAZENA_KOLICINA
        defaultViewPrvorangiraniShouldNotBeFound("trazenaKolicina.greaterThanOrEqual=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByTrazenaKolicinaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where trazenaKolicina is less than or equal to DEFAULT_TRAZENA_KOLICINA
        defaultViewPrvorangiraniShouldBeFound("trazenaKolicina.lessThanOrEqual=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the viewPrvorangiraniList where trazenaKolicina is less than or equal to SMALLER_TRAZENA_KOLICINA
        defaultViewPrvorangiraniShouldNotBeFound("trazenaKolicina.lessThanOrEqual=" + SMALLER_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByTrazenaKolicinaIsLessThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where trazenaKolicina is less than DEFAULT_TRAZENA_KOLICINA
        defaultViewPrvorangiraniShouldNotBeFound("trazenaKolicina.lessThan=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the viewPrvorangiraniList where trazenaKolicina is less than UPDATED_TRAZENA_KOLICINA
        defaultViewPrvorangiraniShouldBeFound("trazenaKolicina.lessThan=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByTrazenaKolicinaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where trazenaKolicina is greater than DEFAULT_TRAZENA_KOLICINA
        defaultViewPrvorangiraniShouldNotBeFound("trazenaKolicina.greaterThan=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the viewPrvorangiraniList where trazenaKolicina is greater than SMALLER_TRAZENA_KOLICINA
        defaultViewPrvorangiraniShouldBeFound("trazenaKolicina.greaterThan=" + SMALLER_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByProcijenjenaVrijednostIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where procijenjenaVrijednost equals to DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldBeFound("procijenjenaVrijednost.equals=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the viewPrvorangiraniList where procijenjenaVrijednost equals to UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldNotBeFound("procijenjenaVrijednost.equals=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByProcijenjenaVrijednostIsInShouldWork() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where procijenjenaVrijednost in DEFAULT_PROCIJENJENA_VRIJEDNOST or UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldBeFound(
            "procijenjenaVrijednost.in=" + DEFAULT_PROCIJENJENA_VRIJEDNOST + "," + UPDATED_PROCIJENJENA_VRIJEDNOST
        );

        // Get all the viewPrvorangiraniList where procijenjenaVrijednost equals to UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldNotBeFound("procijenjenaVrijednost.in=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByProcijenjenaVrijednostIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where procijenjenaVrijednost is not null
        defaultViewPrvorangiraniShouldBeFound("procijenjenaVrijednost.specified=true");

        // Get all the viewPrvorangiraniList where procijenjenaVrijednost is null
        defaultViewPrvorangiraniShouldNotBeFound("procijenjenaVrijednost.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByProcijenjenaVrijednostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where procijenjenaVrijednost is greater than or equal to DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldBeFound("procijenjenaVrijednost.greaterThanOrEqual=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the viewPrvorangiraniList where procijenjenaVrijednost is greater than or equal to UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldNotBeFound("procijenjenaVrijednost.greaterThanOrEqual=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByProcijenjenaVrijednostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where procijenjenaVrijednost is less than or equal to DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldBeFound("procijenjenaVrijednost.lessThanOrEqual=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the viewPrvorangiraniList where procijenjenaVrijednost is less than or equal to SMALLER_PROCIJENJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldNotBeFound("procijenjenaVrijednost.lessThanOrEqual=" + SMALLER_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByProcijenjenaVrijednostIsLessThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where procijenjenaVrijednost is less than DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldNotBeFound("procijenjenaVrijednost.lessThan=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the viewPrvorangiraniList where procijenjenaVrijednost is less than UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldBeFound("procijenjenaVrijednost.lessThan=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByProcijenjenaVrijednostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where procijenjenaVrijednost is greater than DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldNotBeFound("procijenjenaVrijednost.greaterThan=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the viewPrvorangiraniList where procijenjenaVrijednost is greater than SMALLER_PROCIJENJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldBeFound("procijenjenaVrijednost.greaterThan=" + SMALLER_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByNazivProizvodjacaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where nazivProizvodjaca equals to DEFAULT_NAZIV_PROIZVODJACA
        defaultViewPrvorangiraniShouldBeFound("nazivProizvodjaca.equals=" + DEFAULT_NAZIV_PROIZVODJACA);

        // Get all the viewPrvorangiraniList where nazivProizvodjaca equals to UPDATED_NAZIV_PROIZVODJACA
        defaultViewPrvorangiraniShouldNotBeFound("nazivProizvodjaca.equals=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByNazivProizvodjacaIsInShouldWork() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where nazivProizvodjaca in DEFAULT_NAZIV_PROIZVODJACA or UPDATED_NAZIV_PROIZVODJACA
        defaultViewPrvorangiraniShouldBeFound("nazivProizvodjaca.in=" + DEFAULT_NAZIV_PROIZVODJACA + "," + UPDATED_NAZIV_PROIZVODJACA);

        // Get all the viewPrvorangiraniList where nazivProizvodjaca equals to UPDATED_NAZIV_PROIZVODJACA
        defaultViewPrvorangiraniShouldNotBeFound("nazivProizvodjaca.in=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByNazivProizvodjacaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where nazivProizvodjaca is not null
        defaultViewPrvorangiraniShouldBeFound("nazivProizvodjaca.specified=true");

        // Get all the viewPrvorangiraniList where nazivProizvodjaca is null
        defaultViewPrvorangiraniShouldNotBeFound("nazivProizvodjaca.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByNazivProizvodjacaContainsSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where nazivProizvodjaca contains DEFAULT_NAZIV_PROIZVODJACA
        defaultViewPrvorangiraniShouldBeFound("nazivProizvodjaca.contains=" + DEFAULT_NAZIV_PROIZVODJACA);

        // Get all the viewPrvorangiraniList where nazivProizvodjaca contains UPDATED_NAZIV_PROIZVODJACA
        defaultViewPrvorangiraniShouldNotBeFound("nazivProizvodjaca.contains=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByNazivProizvodjacaNotContainsSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where nazivProizvodjaca does not contain DEFAULT_NAZIV_PROIZVODJACA
        defaultViewPrvorangiraniShouldNotBeFound("nazivProizvodjaca.doesNotContain=" + DEFAULT_NAZIV_PROIZVODJACA);

        // Get all the viewPrvorangiraniList where nazivProizvodjaca does not contain UPDATED_NAZIV_PROIZVODJACA
        defaultViewPrvorangiraniShouldBeFound("nazivProizvodjaca.doesNotContain=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByZasticeniNazivIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where zasticeniNaziv equals to DEFAULT_ZASTICENI_NAZIV
        defaultViewPrvorangiraniShouldBeFound("zasticeniNaziv.equals=" + DEFAULT_ZASTICENI_NAZIV);

        // Get all the viewPrvorangiraniList where zasticeniNaziv equals to UPDATED_ZASTICENI_NAZIV
        defaultViewPrvorangiraniShouldNotBeFound("zasticeniNaziv.equals=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByZasticeniNazivIsInShouldWork() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where zasticeniNaziv in DEFAULT_ZASTICENI_NAZIV or UPDATED_ZASTICENI_NAZIV
        defaultViewPrvorangiraniShouldBeFound("zasticeniNaziv.in=" + DEFAULT_ZASTICENI_NAZIV + "," + UPDATED_ZASTICENI_NAZIV);

        // Get all the viewPrvorangiraniList where zasticeniNaziv equals to UPDATED_ZASTICENI_NAZIV
        defaultViewPrvorangiraniShouldNotBeFound("zasticeniNaziv.in=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByZasticeniNazivIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where zasticeniNaziv is not null
        defaultViewPrvorangiraniShouldBeFound("zasticeniNaziv.specified=true");

        // Get all the viewPrvorangiraniList where zasticeniNaziv is null
        defaultViewPrvorangiraniShouldNotBeFound("zasticeniNaziv.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByZasticeniNazivContainsSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where zasticeniNaziv contains DEFAULT_ZASTICENI_NAZIV
        defaultViewPrvorangiraniShouldBeFound("zasticeniNaziv.contains=" + DEFAULT_ZASTICENI_NAZIV);

        // Get all the viewPrvorangiraniList where zasticeniNaziv contains UPDATED_ZASTICENI_NAZIV
        defaultViewPrvorangiraniShouldNotBeFound("zasticeniNaziv.contains=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByZasticeniNazivNotContainsSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where zasticeniNaziv does not contain DEFAULT_ZASTICENI_NAZIV
        defaultViewPrvorangiraniShouldNotBeFound("zasticeniNaziv.doesNotContain=" + DEFAULT_ZASTICENI_NAZIV);

        // Get all the viewPrvorangiraniList where zasticeniNaziv does not contain UPDATED_ZASTICENI_NAZIV
        defaultViewPrvorangiraniShouldBeFound("zasticeniNaziv.doesNotContain=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByJedinicnaCijenaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where jedinicnaCijena equals to DEFAULT_JEDINICNA_CIJENA
        defaultViewPrvorangiraniShouldBeFound("jedinicnaCijena.equals=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the viewPrvorangiraniList where jedinicnaCijena equals to UPDATED_JEDINICNA_CIJENA
        defaultViewPrvorangiraniShouldNotBeFound("jedinicnaCijena.equals=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByJedinicnaCijenaIsInShouldWork() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where jedinicnaCijena in DEFAULT_JEDINICNA_CIJENA or UPDATED_JEDINICNA_CIJENA
        defaultViewPrvorangiraniShouldBeFound("jedinicnaCijena.in=" + DEFAULT_JEDINICNA_CIJENA + "," + UPDATED_JEDINICNA_CIJENA);

        // Get all the viewPrvorangiraniList where jedinicnaCijena equals to UPDATED_JEDINICNA_CIJENA
        defaultViewPrvorangiraniShouldNotBeFound("jedinicnaCijena.in=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByJedinicnaCijenaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where jedinicnaCijena is not null
        defaultViewPrvorangiraniShouldBeFound("jedinicnaCijena.specified=true");

        // Get all the viewPrvorangiraniList where jedinicnaCijena is null
        defaultViewPrvorangiraniShouldNotBeFound("jedinicnaCijena.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByJedinicnaCijenaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where jedinicnaCijena is greater than or equal to DEFAULT_JEDINICNA_CIJENA
        defaultViewPrvorangiraniShouldBeFound("jedinicnaCijena.greaterThanOrEqual=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the viewPrvorangiraniList where jedinicnaCijena is greater than or equal to UPDATED_JEDINICNA_CIJENA
        defaultViewPrvorangiraniShouldNotBeFound("jedinicnaCijena.greaterThanOrEqual=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByJedinicnaCijenaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where jedinicnaCijena is less than or equal to DEFAULT_JEDINICNA_CIJENA
        defaultViewPrvorangiraniShouldBeFound("jedinicnaCijena.lessThanOrEqual=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the viewPrvorangiraniList where jedinicnaCijena is less than or equal to SMALLER_JEDINICNA_CIJENA
        defaultViewPrvorangiraniShouldNotBeFound("jedinicnaCijena.lessThanOrEqual=" + SMALLER_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByJedinicnaCijenaIsLessThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where jedinicnaCijena is less than DEFAULT_JEDINICNA_CIJENA
        defaultViewPrvorangiraniShouldNotBeFound("jedinicnaCijena.lessThan=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the viewPrvorangiraniList where jedinicnaCijena is less than UPDATED_JEDINICNA_CIJENA
        defaultViewPrvorangiraniShouldBeFound("jedinicnaCijena.lessThan=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByJedinicnaCijenaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where jedinicnaCijena is greater than DEFAULT_JEDINICNA_CIJENA
        defaultViewPrvorangiraniShouldNotBeFound("jedinicnaCijena.greaterThan=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the viewPrvorangiraniList where jedinicnaCijena is greater than SMALLER_JEDINICNA_CIJENA
        defaultViewPrvorangiraniShouldBeFound("jedinicnaCijena.greaterThan=" + SMALLER_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByPonudjenaVrijednostIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where ponudjenaVrijednost equals to DEFAULT_PONUDJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldBeFound("ponudjenaVrijednost.equals=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the viewPrvorangiraniList where ponudjenaVrijednost equals to UPDATED_PONUDJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldNotBeFound("ponudjenaVrijednost.equals=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByPonudjenaVrijednostIsInShouldWork() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where ponudjenaVrijednost in DEFAULT_PONUDJENA_VRIJEDNOST or UPDATED_PONUDJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldBeFound(
            "ponudjenaVrijednost.in=" + DEFAULT_PONUDJENA_VRIJEDNOST + "," + UPDATED_PONUDJENA_VRIJEDNOST
        );

        // Get all the viewPrvorangiraniList where ponudjenaVrijednost equals to UPDATED_PONUDJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldNotBeFound("ponudjenaVrijednost.in=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByPonudjenaVrijednostIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where ponudjenaVrijednost is not null
        defaultViewPrvorangiraniShouldBeFound("ponudjenaVrijednost.specified=true");

        // Get all the viewPrvorangiraniList where ponudjenaVrijednost is null
        defaultViewPrvorangiraniShouldNotBeFound("ponudjenaVrijednost.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByPonudjenaVrijednostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where ponudjenaVrijednost is greater than or equal to DEFAULT_PONUDJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldBeFound("ponudjenaVrijednost.greaterThanOrEqual=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the viewPrvorangiraniList where ponudjenaVrijednost is greater than or equal to UPDATED_PONUDJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldNotBeFound("ponudjenaVrijednost.greaterThanOrEqual=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByPonudjenaVrijednostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where ponudjenaVrijednost is less than or equal to DEFAULT_PONUDJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldBeFound("ponudjenaVrijednost.lessThanOrEqual=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the viewPrvorangiraniList where ponudjenaVrijednost is less than or equal to SMALLER_PONUDJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldNotBeFound("ponudjenaVrijednost.lessThanOrEqual=" + SMALLER_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByPonudjenaVrijednostIsLessThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where ponudjenaVrijednost is less than DEFAULT_PONUDJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldNotBeFound("ponudjenaVrijednost.lessThan=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the viewPrvorangiraniList where ponudjenaVrijednost is less than UPDATED_PONUDJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldBeFound("ponudjenaVrijednost.lessThan=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByPonudjenaVrijednostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where ponudjenaVrijednost is greater than DEFAULT_PONUDJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldNotBeFound("ponudjenaVrijednost.greaterThan=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the viewPrvorangiraniList where ponudjenaVrijednost is greater than SMALLER_PONUDJENA_VRIJEDNOST
        defaultViewPrvorangiraniShouldBeFound("ponudjenaVrijednost.greaterThan=" + SMALLER_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByRokIsporukeIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where rokIsporuke equals to DEFAULT_ROK_ISPORUKE
        defaultViewPrvorangiraniShouldBeFound("rokIsporuke.equals=" + DEFAULT_ROK_ISPORUKE);

        // Get all the viewPrvorangiraniList where rokIsporuke equals to UPDATED_ROK_ISPORUKE
        defaultViewPrvorangiraniShouldNotBeFound("rokIsporuke.equals=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByRokIsporukeIsInShouldWork() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where rokIsporuke in DEFAULT_ROK_ISPORUKE or UPDATED_ROK_ISPORUKE
        defaultViewPrvorangiraniShouldBeFound("rokIsporuke.in=" + DEFAULT_ROK_ISPORUKE + "," + UPDATED_ROK_ISPORUKE);

        // Get all the viewPrvorangiraniList where rokIsporuke equals to UPDATED_ROK_ISPORUKE
        defaultViewPrvorangiraniShouldNotBeFound("rokIsporuke.in=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByRokIsporukeIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where rokIsporuke is not null
        defaultViewPrvorangiraniShouldBeFound("rokIsporuke.specified=true");

        // Get all the viewPrvorangiraniList where rokIsporuke is null
        defaultViewPrvorangiraniShouldNotBeFound("rokIsporuke.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByRokIsporukeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where rokIsporuke is greater than or equal to DEFAULT_ROK_ISPORUKE
        defaultViewPrvorangiraniShouldBeFound("rokIsporuke.greaterThanOrEqual=" + DEFAULT_ROK_ISPORUKE);

        // Get all the viewPrvorangiraniList where rokIsporuke is greater than or equal to UPDATED_ROK_ISPORUKE
        defaultViewPrvorangiraniShouldNotBeFound("rokIsporuke.greaterThanOrEqual=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByRokIsporukeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where rokIsporuke is less than or equal to DEFAULT_ROK_ISPORUKE
        defaultViewPrvorangiraniShouldBeFound("rokIsporuke.lessThanOrEqual=" + DEFAULT_ROK_ISPORUKE);

        // Get all the viewPrvorangiraniList where rokIsporuke is less than or equal to SMALLER_ROK_ISPORUKE
        defaultViewPrvorangiraniShouldNotBeFound("rokIsporuke.lessThanOrEqual=" + SMALLER_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByRokIsporukeIsLessThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where rokIsporuke is less than DEFAULT_ROK_ISPORUKE
        defaultViewPrvorangiraniShouldNotBeFound("rokIsporuke.lessThan=" + DEFAULT_ROK_ISPORUKE);

        // Get all the viewPrvorangiraniList where rokIsporuke is less than UPDATED_ROK_ISPORUKE
        defaultViewPrvorangiraniShouldBeFound("rokIsporuke.lessThan=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByRokIsporukeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where rokIsporuke is greater than DEFAULT_ROK_ISPORUKE
        defaultViewPrvorangiraniShouldNotBeFound("rokIsporuke.greaterThan=" + DEFAULT_ROK_ISPORUKE);

        // Get all the viewPrvorangiraniList where rokIsporuke is greater than SMALLER_ROK_ISPORUKE
        defaultViewPrvorangiraniShouldBeFound("rokIsporuke.greaterThan=" + SMALLER_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByVrstaPostupkaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where vrstaPostupka equals to DEFAULT_VRSTA_POSTUPKA
        defaultViewPrvorangiraniShouldBeFound("vrstaPostupka.equals=" + DEFAULT_VRSTA_POSTUPKA);

        // Get all the viewPrvorangiraniList where vrstaPostupka equals to UPDATED_VRSTA_POSTUPKA
        defaultViewPrvorangiraniShouldNotBeFound("vrstaPostupka.equals=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByVrstaPostupkaIsInShouldWork() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where vrstaPostupka in DEFAULT_VRSTA_POSTUPKA or UPDATED_VRSTA_POSTUPKA
        defaultViewPrvorangiraniShouldBeFound("vrstaPostupka.in=" + DEFAULT_VRSTA_POSTUPKA + "," + UPDATED_VRSTA_POSTUPKA);

        // Get all the viewPrvorangiraniList where vrstaPostupka equals to UPDATED_VRSTA_POSTUPKA
        defaultViewPrvorangiraniShouldNotBeFound("vrstaPostupka.in=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByVrstaPostupkaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where vrstaPostupka is not null
        defaultViewPrvorangiraniShouldBeFound("vrstaPostupka.specified=true");

        // Get all the viewPrvorangiraniList where vrstaPostupka is null
        defaultViewPrvorangiraniShouldNotBeFound("vrstaPostupka.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByVrstaPostupkaContainsSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where vrstaPostupka contains DEFAULT_VRSTA_POSTUPKA
        defaultViewPrvorangiraniShouldBeFound("vrstaPostupka.contains=" + DEFAULT_VRSTA_POSTUPKA);

        // Get all the viewPrvorangiraniList where vrstaPostupka contains UPDATED_VRSTA_POSTUPKA
        defaultViewPrvorangiraniShouldNotBeFound("vrstaPostupka.contains=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByVrstaPostupkaNotContainsSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where vrstaPostupka does not contain DEFAULT_VRSTA_POSTUPKA
        defaultViewPrvorangiraniShouldNotBeFound("vrstaPostupka.doesNotContain=" + DEFAULT_VRSTA_POSTUPKA);

        // Get all the viewPrvorangiraniList where vrstaPostupka does not contain UPDATED_VRSTA_POSTUPKA
        defaultViewPrvorangiraniShouldBeFound("vrstaPostupka.doesNotContain=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodCijenaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodCijena equals to DEFAULT_BOD_CIJENA
        defaultViewPrvorangiraniShouldBeFound("bodCijena.equals=" + DEFAULT_BOD_CIJENA);

        // Get all the viewPrvorangiraniList where bodCijena equals to UPDATED_BOD_CIJENA
        defaultViewPrvorangiraniShouldNotBeFound("bodCijena.equals=" + UPDATED_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodCijenaIsInShouldWork() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodCijena in DEFAULT_BOD_CIJENA or UPDATED_BOD_CIJENA
        defaultViewPrvorangiraniShouldBeFound("bodCijena.in=" + DEFAULT_BOD_CIJENA + "," + UPDATED_BOD_CIJENA);

        // Get all the viewPrvorangiraniList where bodCijena equals to UPDATED_BOD_CIJENA
        defaultViewPrvorangiraniShouldNotBeFound("bodCijena.in=" + UPDATED_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodCijenaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodCijena is not null
        defaultViewPrvorangiraniShouldBeFound("bodCijena.specified=true");

        // Get all the viewPrvorangiraniList where bodCijena is null
        defaultViewPrvorangiraniShouldNotBeFound("bodCijena.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodCijenaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodCijena is greater than or equal to DEFAULT_BOD_CIJENA
        defaultViewPrvorangiraniShouldBeFound("bodCijena.greaterThanOrEqual=" + DEFAULT_BOD_CIJENA);

        // Get all the viewPrvorangiraniList where bodCijena is greater than or equal to UPDATED_BOD_CIJENA
        defaultViewPrvorangiraniShouldNotBeFound("bodCijena.greaterThanOrEqual=" + UPDATED_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodCijenaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodCijena is less than or equal to DEFAULT_BOD_CIJENA
        defaultViewPrvorangiraniShouldBeFound("bodCijena.lessThanOrEqual=" + DEFAULT_BOD_CIJENA);

        // Get all the viewPrvorangiraniList where bodCijena is less than or equal to SMALLER_BOD_CIJENA
        defaultViewPrvorangiraniShouldNotBeFound("bodCijena.lessThanOrEqual=" + SMALLER_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodCijenaIsLessThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodCijena is less than DEFAULT_BOD_CIJENA
        defaultViewPrvorangiraniShouldNotBeFound("bodCijena.lessThan=" + DEFAULT_BOD_CIJENA);

        // Get all the viewPrvorangiraniList where bodCijena is less than UPDATED_BOD_CIJENA
        defaultViewPrvorangiraniShouldBeFound("bodCijena.lessThan=" + UPDATED_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodCijenaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodCijena is greater than DEFAULT_BOD_CIJENA
        defaultViewPrvorangiraniShouldNotBeFound("bodCijena.greaterThan=" + DEFAULT_BOD_CIJENA);

        // Get all the viewPrvorangiraniList where bodCijena is greater than SMALLER_BOD_CIJENA
        defaultViewPrvorangiraniShouldBeFound("bodCijena.greaterThan=" + SMALLER_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodRokIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodRok equals to DEFAULT_BOD_ROK
        defaultViewPrvorangiraniShouldBeFound("bodRok.equals=" + DEFAULT_BOD_ROK);

        // Get all the viewPrvorangiraniList where bodRok equals to UPDATED_BOD_ROK
        defaultViewPrvorangiraniShouldNotBeFound("bodRok.equals=" + UPDATED_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodRokIsInShouldWork() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodRok in DEFAULT_BOD_ROK or UPDATED_BOD_ROK
        defaultViewPrvorangiraniShouldBeFound("bodRok.in=" + DEFAULT_BOD_ROK + "," + UPDATED_BOD_ROK);

        // Get all the viewPrvorangiraniList where bodRok equals to UPDATED_BOD_ROK
        defaultViewPrvorangiraniShouldNotBeFound("bodRok.in=" + UPDATED_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodRokIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodRok is not null
        defaultViewPrvorangiraniShouldBeFound("bodRok.specified=true");

        // Get all the viewPrvorangiraniList where bodRok is null
        defaultViewPrvorangiraniShouldNotBeFound("bodRok.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodRokIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodRok is greater than or equal to DEFAULT_BOD_ROK
        defaultViewPrvorangiraniShouldBeFound("bodRok.greaterThanOrEqual=" + DEFAULT_BOD_ROK);

        // Get all the viewPrvorangiraniList where bodRok is greater than or equal to UPDATED_BOD_ROK
        defaultViewPrvorangiraniShouldNotBeFound("bodRok.greaterThanOrEqual=" + UPDATED_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodRokIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodRok is less than or equal to DEFAULT_BOD_ROK
        defaultViewPrvorangiraniShouldBeFound("bodRok.lessThanOrEqual=" + DEFAULT_BOD_ROK);

        // Get all the viewPrvorangiraniList where bodRok is less than or equal to SMALLER_BOD_ROK
        defaultViewPrvorangiraniShouldNotBeFound("bodRok.lessThanOrEqual=" + SMALLER_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodRokIsLessThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodRok is less than DEFAULT_BOD_ROK
        defaultViewPrvorangiraniShouldNotBeFound("bodRok.lessThan=" + DEFAULT_BOD_ROK);

        // Get all the viewPrvorangiraniList where bodRok is less than UPDATED_BOD_ROK
        defaultViewPrvorangiraniShouldBeFound("bodRok.lessThan=" + UPDATED_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodRokIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodRok is greater than DEFAULT_BOD_ROK
        defaultViewPrvorangiraniShouldNotBeFound("bodRok.greaterThan=" + DEFAULT_BOD_ROK);

        // Get all the viewPrvorangiraniList where bodRok is greater than SMALLER_BOD_ROK
        defaultViewPrvorangiraniShouldBeFound("bodRok.greaterThan=" + SMALLER_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodUkupnoIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodUkupno equals to DEFAULT_BOD_UKUPNO
        defaultViewPrvorangiraniShouldBeFound("bodUkupno.equals=" + DEFAULT_BOD_UKUPNO);

        // Get all the viewPrvorangiraniList where bodUkupno equals to UPDATED_BOD_UKUPNO
        defaultViewPrvorangiraniShouldNotBeFound("bodUkupno.equals=" + UPDATED_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodUkupnoIsInShouldWork() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodUkupno in DEFAULT_BOD_UKUPNO or UPDATED_BOD_UKUPNO
        defaultViewPrvorangiraniShouldBeFound("bodUkupno.in=" + DEFAULT_BOD_UKUPNO + "," + UPDATED_BOD_UKUPNO);

        // Get all the viewPrvorangiraniList where bodUkupno equals to UPDATED_BOD_UKUPNO
        defaultViewPrvorangiraniShouldNotBeFound("bodUkupno.in=" + UPDATED_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodUkupnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodUkupno is not null
        defaultViewPrvorangiraniShouldBeFound("bodUkupno.specified=true");

        // Get all the viewPrvorangiraniList where bodUkupno is null
        defaultViewPrvorangiraniShouldNotBeFound("bodUkupno.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodUkupnoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodUkupno is greater than or equal to DEFAULT_BOD_UKUPNO
        defaultViewPrvorangiraniShouldBeFound("bodUkupno.greaterThanOrEqual=" + DEFAULT_BOD_UKUPNO);

        // Get all the viewPrvorangiraniList where bodUkupno is greater than or equal to UPDATED_BOD_UKUPNO
        defaultViewPrvorangiraniShouldNotBeFound("bodUkupno.greaterThanOrEqual=" + UPDATED_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodUkupnoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodUkupno is less than or equal to DEFAULT_BOD_UKUPNO
        defaultViewPrvorangiraniShouldBeFound("bodUkupno.lessThanOrEqual=" + DEFAULT_BOD_UKUPNO);

        // Get all the viewPrvorangiraniList where bodUkupno is less than or equal to SMALLER_BOD_UKUPNO
        defaultViewPrvorangiraniShouldNotBeFound("bodUkupno.lessThanOrEqual=" + SMALLER_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodUkupnoIsLessThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodUkupno is less than DEFAULT_BOD_UKUPNO
        defaultViewPrvorangiraniShouldNotBeFound("bodUkupno.lessThan=" + DEFAULT_BOD_UKUPNO);

        // Get all the viewPrvorangiraniList where bodUkupno is less than UPDATED_BOD_UKUPNO
        defaultViewPrvorangiraniShouldBeFound("bodUkupno.lessThan=" + UPDATED_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByBodUkupnoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where bodUkupno is greater than DEFAULT_BOD_UKUPNO
        defaultViewPrvorangiraniShouldNotBeFound("bodUkupno.greaterThan=" + DEFAULT_BOD_UKUPNO);

        // Get all the viewPrvorangiraniList where bodUkupno is greater than SMALLER_BOD_UKUPNO
        defaultViewPrvorangiraniShouldBeFound("bodUkupno.greaterThan=" + SMALLER_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByKarakteristikaSpecifikacijeIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where karakteristikaSpecifikacije equals to DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE
        defaultViewPrvorangiraniShouldBeFound("karakteristikaSpecifikacije.equals=" + DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE);

        // Get all the viewPrvorangiraniList where karakteristikaSpecifikacije equals to UPDATED_KARAKTERISTIKA_SPECIFIKACIJE
        defaultViewPrvorangiraniShouldNotBeFound("karakteristikaSpecifikacije.equals=" + UPDATED_KARAKTERISTIKA_SPECIFIKACIJE);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByKarakteristikaSpecifikacijeIsInShouldWork() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where karakteristikaSpecifikacije in DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE or UPDATED_KARAKTERISTIKA_SPECIFIKACIJE
        defaultViewPrvorangiraniShouldBeFound(
            "karakteristikaSpecifikacije.in=" + DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE + "," + UPDATED_KARAKTERISTIKA_SPECIFIKACIJE
        );

        // Get all the viewPrvorangiraniList where karakteristikaSpecifikacije equals to UPDATED_KARAKTERISTIKA_SPECIFIKACIJE
        defaultViewPrvorangiraniShouldNotBeFound("karakteristikaSpecifikacije.in=" + UPDATED_KARAKTERISTIKA_SPECIFIKACIJE);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByKarakteristikaSpecifikacijeIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where karakteristikaSpecifikacije is not null
        defaultViewPrvorangiraniShouldBeFound("karakteristikaSpecifikacije.specified=true");

        // Get all the viewPrvorangiraniList where karakteristikaSpecifikacije is null
        defaultViewPrvorangiraniShouldNotBeFound("karakteristikaSpecifikacije.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByKarakteristikaSpecifikacijeContainsSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where karakteristikaSpecifikacije contains DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE
        defaultViewPrvorangiraniShouldBeFound("karakteristikaSpecifikacije.contains=" + DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE);

        // Get all the viewPrvorangiraniList where karakteristikaSpecifikacije contains UPDATED_KARAKTERISTIKA_SPECIFIKACIJE
        defaultViewPrvorangiraniShouldNotBeFound("karakteristikaSpecifikacije.contains=" + UPDATED_KARAKTERISTIKA_SPECIFIKACIJE);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByKarakteristikaSpecifikacijeNotContainsSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where karakteristikaSpecifikacije does not contain DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE
        defaultViewPrvorangiraniShouldNotBeFound("karakteristikaSpecifikacije.doesNotContain=" + DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE);

        // Get all the viewPrvorangiraniList where karakteristikaSpecifikacije does not contain UPDATED_KARAKTERISTIKA_SPECIFIKACIJE
        defaultViewPrvorangiraniShouldBeFound("karakteristikaSpecifikacije.doesNotContain=" + UPDATED_KARAKTERISTIKA_SPECIFIKACIJE);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByKarakteristikaPonudeIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where karakteristikaPonude equals to DEFAULT_KARAKTERISTIKA_PONUDE
        defaultViewPrvorangiraniShouldBeFound("karakteristikaPonude.equals=" + DEFAULT_KARAKTERISTIKA_PONUDE);

        // Get all the viewPrvorangiraniList where karakteristikaPonude equals to UPDATED_KARAKTERISTIKA_PONUDE
        defaultViewPrvorangiraniShouldNotBeFound("karakteristikaPonude.equals=" + UPDATED_KARAKTERISTIKA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByKarakteristikaPonudeIsInShouldWork() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where karakteristikaPonude in DEFAULT_KARAKTERISTIKA_PONUDE or UPDATED_KARAKTERISTIKA_PONUDE
        defaultViewPrvorangiraniShouldBeFound(
            "karakteristikaPonude.in=" + DEFAULT_KARAKTERISTIKA_PONUDE + "," + UPDATED_KARAKTERISTIKA_PONUDE
        );

        // Get all the viewPrvorangiraniList where karakteristikaPonude equals to UPDATED_KARAKTERISTIKA_PONUDE
        defaultViewPrvorangiraniShouldNotBeFound("karakteristikaPonude.in=" + UPDATED_KARAKTERISTIKA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByKarakteristikaPonudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where karakteristikaPonude is not null
        defaultViewPrvorangiraniShouldBeFound("karakteristikaPonude.specified=true");

        // Get all the viewPrvorangiraniList where karakteristikaPonude is null
        defaultViewPrvorangiraniShouldNotBeFound("karakteristikaPonude.specified=false");
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByKarakteristikaPonudeContainsSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where karakteristikaPonude contains DEFAULT_KARAKTERISTIKA_PONUDE
        defaultViewPrvorangiraniShouldBeFound("karakteristikaPonude.contains=" + DEFAULT_KARAKTERISTIKA_PONUDE);

        // Get all the viewPrvorangiraniList where karakteristikaPonude contains UPDATED_KARAKTERISTIKA_PONUDE
        defaultViewPrvorangiraniShouldNotBeFound("karakteristikaPonude.contains=" + UPDATED_KARAKTERISTIKA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewPrvorangiranisByKarakteristikaPonudeNotContainsSomething() throws Exception {
        // Initialize the database
        viewPrvorangiraniRepository.saveAndFlush(viewPrvorangirani);

        // Get all the viewPrvorangiraniList where karakteristikaPonude does not contain DEFAULT_KARAKTERISTIKA_PONUDE
        defaultViewPrvorangiraniShouldNotBeFound("karakteristikaPonude.doesNotContain=" + DEFAULT_KARAKTERISTIKA_PONUDE);

        // Get all the viewPrvorangiraniList where karakteristikaPonude does not contain UPDATED_KARAKTERISTIKA_PONUDE
        defaultViewPrvorangiraniShouldBeFound("karakteristikaPonude.doesNotContain=" + UPDATED_KARAKTERISTIKA_PONUDE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultViewPrvorangiraniShouldBeFound(String filter) throws Exception {
        restViewPrvorangiraniMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viewPrvorangirani.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].nazivPonudjaca").value(hasItem(DEFAULT_NAZIV_PONUDJACA)))
            .andExpect(jsonPath("$.[*].sifraPonude").value(hasItem(DEFAULT_SIFRA_PONUDE)))
            .andExpect(jsonPath("$.[*].atc").value(hasItem(DEFAULT_ATC)))
            .andExpect(jsonPath("$.[*].trazenaKolicina").value(hasItem(DEFAULT_TRAZENA_KOLICINA)))
            .andExpect(jsonPath("$.[*].procijenjenaVrijednost").value(hasItem(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].nazivProizvodjaca").value(hasItem(DEFAULT_NAZIV_PROIZVODJACA)))
            .andExpect(jsonPath("$.[*].zasticeniNaziv").value(hasItem(DEFAULT_ZASTICENI_NAZIV)))
            .andExpect(jsonPath("$.[*].jedinicnaCijena").value(hasItem(DEFAULT_JEDINICNA_CIJENA.doubleValue())))
            .andExpect(jsonPath("$.[*].ponudjenaVrijednost").value(hasItem(DEFAULT_PONUDJENA_VRIJEDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].rokIsporuke").value(hasItem(DEFAULT_ROK_ISPORUKE)))
            .andExpect(jsonPath("$.[*].vrstaPostupka").value(hasItem(DEFAULT_VRSTA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].bodCijena").value(hasItem(DEFAULT_BOD_CIJENA.doubleValue())))
            .andExpect(jsonPath("$.[*].bodRok").value(hasItem(DEFAULT_BOD_ROK.doubleValue())))
            .andExpect(jsonPath("$.[*].bodUkupno").value(hasItem(DEFAULT_BOD_UKUPNO.doubleValue())))
            .andExpect(jsonPath("$.[*].karakteristikaSpecifikacije").value(hasItem(DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE)))
            .andExpect(jsonPath("$.[*].karakteristikaPonude").value(hasItem(DEFAULT_KARAKTERISTIKA_PONUDE)));

        // Check, that the count call also returns 1
        restViewPrvorangiraniMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultViewPrvorangiraniShouldNotBeFound(String filter) throws Exception {
        restViewPrvorangiraniMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restViewPrvorangiraniMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingViewPrvorangirani() throws Exception {
        // Get the viewPrvorangirani
        restViewPrvorangiraniMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
