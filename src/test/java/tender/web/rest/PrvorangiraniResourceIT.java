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
import tender.domain.Prvorangirani;
import tender.repository.PrvorangiraniRepository;
import tender.service.criteria.PrvorangiraniCriteria;

/**
 * Integration tests for the {@link PrvorangiraniResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrvorangiraniResourceIT {

    private static final Integer DEFAULT_SIFRA_POSTUPKA = 1;
    private static final Integer UPDATED_SIFRA_POSTUPKA = 2;
    private static final Integer SMALLER_SIFRA_POSTUPKA = 1 - 1;

    private static final String DEFAULT_NAZIV_PONUDJACA = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV_PONUDJACA = "BBBBBBBBBB";

    private static final Integer DEFAULT_SIFRA_PONUDE = 1;
    private static final Integer UPDATED_SIFRA_PONUDE = 2;
    private static final Integer SMALLER_SIFRA_PONUDE = 1 - 1;

    private static final Integer DEFAULT_BROJ_PARTIJE = 1;
    private static final Integer UPDATED_BROJ_PARTIJE = 2;
    private static final Integer SMALLER_BROJ_PARTIJE = 1 - 1;

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

    private static final String ENTITY_API_URL = "/api/prvorangiranis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrvorangiraniRepository prvorangiraniRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrvorangiraniMockMvc;

    private Prvorangirani prvorangirani;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prvorangirani createEntity(EntityManager em) {
        Prvorangirani prvorangirani = new Prvorangirani()
            .sifraPostupka(DEFAULT_SIFRA_POSTUPKA)
            .nazivPonudjaca(DEFAULT_NAZIV_PONUDJACA)
            .sifraPonude(DEFAULT_SIFRA_PONUDE)
            .brojPartije(DEFAULT_BROJ_PARTIJE)
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
            .bodUkupno(DEFAULT_BOD_UKUPNO);
        return prvorangirani;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prvorangirani createUpdatedEntity(EntityManager em) {
        Prvorangirani prvorangirani = new Prvorangirani()
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .nazivPonudjaca(UPDATED_NAZIV_PONUDJACA)
            .sifraPonude(UPDATED_SIFRA_PONUDE)
            .brojPartije(UPDATED_BROJ_PARTIJE)
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
            .bodUkupno(UPDATED_BOD_UKUPNO);
        return prvorangirani;
    }

    @BeforeEach
    public void initTest() {
        prvorangirani = createEntity(em);
    }

    @Test
    @Transactional
    void getAllPrvorangiranis() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList
        restPrvorangiraniMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prvorangirani.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].nazivPonudjaca").value(hasItem(DEFAULT_NAZIV_PONUDJACA)))
            .andExpect(jsonPath("$.[*].sifraPonude").value(hasItem(DEFAULT_SIFRA_PONUDE)))
            .andExpect(jsonPath("$.[*].brojPartije").value(hasItem(DEFAULT_BROJ_PARTIJE)))
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
            .andExpect(jsonPath("$.[*].bodUkupno").value(hasItem(DEFAULT_BOD_UKUPNO.doubleValue())));
    }

    @Test
    @Transactional
    void getPrvorangirani() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get the prvorangirani
        restPrvorangiraniMockMvc
            .perform(get(ENTITY_API_URL_ID, prvorangirani.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prvorangirani.getId().intValue()))
            .andExpect(jsonPath("$.sifraPostupka").value(DEFAULT_SIFRA_POSTUPKA))
            .andExpect(jsonPath("$.nazivPonudjaca").value(DEFAULT_NAZIV_PONUDJACA))
            .andExpect(jsonPath("$.sifraPonude").value(DEFAULT_SIFRA_PONUDE))
            .andExpect(jsonPath("$.brojPartije").value(DEFAULT_BROJ_PARTIJE))
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
            .andExpect(jsonPath("$.bodUkupno").value(DEFAULT_BOD_UKUPNO.doubleValue()));
    }

    @Test
    @Transactional
    void getPrvorangiranisByIdFiltering() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        Long id = prvorangirani.getId();

        defaultPrvorangiraniShouldBeFound("id.equals=" + id);
        defaultPrvorangiraniShouldNotBeFound("id.notEquals=" + id);

        defaultPrvorangiraniShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPrvorangiraniShouldNotBeFound("id.greaterThan=" + id);

        defaultPrvorangiraniShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPrvorangiraniShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisBySifraPostupkaIsEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where sifraPostupka equals to DEFAULT_SIFRA_POSTUPKA
        defaultPrvorangiraniShouldBeFound("sifraPostupka.equals=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the prvorangiraniList where sifraPostupka equals to UPDATED_SIFRA_POSTUPKA
        defaultPrvorangiraniShouldNotBeFound("sifraPostupka.equals=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisBySifraPostupkaIsInShouldWork() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where sifraPostupka in DEFAULT_SIFRA_POSTUPKA or UPDATED_SIFRA_POSTUPKA
        defaultPrvorangiraniShouldBeFound("sifraPostupka.in=" + DEFAULT_SIFRA_POSTUPKA + "," + UPDATED_SIFRA_POSTUPKA);

        // Get all the prvorangiraniList where sifraPostupka equals to UPDATED_SIFRA_POSTUPKA
        defaultPrvorangiraniShouldNotBeFound("sifraPostupka.in=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisBySifraPostupkaIsNullOrNotNull() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where sifraPostupka is not null
        defaultPrvorangiraniShouldBeFound("sifraPostupka.specified=true");

        // Get all the prvorangiraniList where sifraPostupka is null
        defaultPrvorangiraniShouldNotBeFound("sifraPostupka.specified=false");
    }

    @Test
    @Transactional
    void getAllPrvorangiranisBySifraPostupkaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where sifraPostupka is greater than or equal to DEFAULT_SIFRA_POSTUPKA
        defaultPrvorangiraniShouldBeFound("sifraPostupka.greaterThanOrEqual=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the prvorangiraniList where sifraPostupka is greater than or equal to UPDATED_SIFRA_POSTUPKA
        defaultPrvorangiraniShouldNotBeFound("sifraPostupka.greaterThanOrEqual=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisBySifraPostupkaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where sifraPostupka is less than or equal to DEFAULT_SIFRA_POSTUPKA
        defaultPrvorangiraniShouldBeFound("sifraPostupka.lessThanOrEqual=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the prvorangiraniList where sifraPostupka is less than or equal to SMALLER_SIFRA_POSTUPKA
        defaultPrvorangiraniShouldNotBeFound("sifraPostupka.lessThanOrEqual=" + SMALLER_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisBySifraPostupkaIsLessThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where sifraPostupka is less than DEFAULT_SIFRA_POSTUPKA
        defaultPrvorangiraniShouldNotBeFound("sifraPostupka.lessThan=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the prvorangiraniList where sifraPostupka is less than UPDATED_SIFRA_POSTUPKA
        defaultPrvorangiraniShouldBeFound("sifraPostupka.lessThan=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisBySifraPostupkaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where sifraPostupka is greater than DEFAULT_SIFRA_POSTUPKA
        defaultPrvorangiraniShouldNotBeFound("sifraPostupka.greaterThan=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the prvorangiraniList where sifraPostupka is greater than SMALLER_SIFRA_POSTUPKA
        defaultPrvorangiraniShouldBeFound("sifraPostupka.greaterThan=" + SMALLER_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByNazivPonudjacaIsEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where nazivPonudjaca equals to DEFAULT_NAZIV_PONUDJACA
        defaultPrvorangiraniShouldBeFound("nazivPonudjaca.equals=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the prvorangiraniList where nazivPonudjaca equals to UPDATED_NAZIV_PONUDJACA
        defaultPrvorangiraniShouldNotBeFound("nazivPonudjaca.equals=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByNazivPonudjacaIsInShouldWork() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where nazivPonudjaca in DEFAULT_NAZIV_PONUDJACA or UPDATED_NAZIV_PONUDJACA
        defaultPrvorangiraniShouldBeFound("nazivPonudjaca.in=" + DEFAULT_NAZIV_PONUDJACA + "," + UPDATED_NAZIV_PONUDJACA);

        // Get all the prvorangiraniList where nazivPonudjaca equals to UPDATED_NAZIV_PONUDJACA
        defaultPrvorangiraniShouldNotBeFound("nazivPonudjaca.in=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByNazivPonudjacaIsNullOrNotNull() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where nazivPonudjaca is not null
        defaultPrvorangiraniShouldBeFound("nazivPonudjaca.specified=true");

        // Get all the prvorangiraniList where nazivPonudjaca is null
        defaultPrvorangiraniShouldNotBeFound("nazivPonudjaca.specified=false");
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByNazivPonudjacaContainsSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where nazivPonudjaca contains DEFAULT_NAZIV_PONUDJACA
        defaultPrvorangiraniShouldBeFound("nazivPonudjaca.contains=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the prvorangiraniList where nazivPonudjaca contains UPDATED_NAZIV_PONUDJACA
        defaultPrvorangiraniShouldNotBeFound("nazivPonudjaca.contains=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByNazivPonudjacaNotContainsSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where nazivPonudjaca does not contain DEFAULT_NAZIV_PONUDJACA
        defaultPrvorangiraniShouldNotBeFound("nazivPonudjaca.doesNotContain=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the prvorangiraniList where nazivPonudjaca does not contain UPDATED_NAZIV_PONUDJACA
        defaultPrvorangiraniShouldBeFound("nazivPonudjaca.doesNotContain=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisBySifraPonudeIsEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where sifraPonude equals to DEFAULT_SIFRA_PONUDE
        defaultPrvorangiraniShouldBeFound("sifraPonude.equals=" + DEFAULT_SIFRA_PONUDE);

        // Get all the prvorangiraniList where sifraPonude equals to UPDATED_SIFRA_PONUDE
        defaultPrvorangiraniShouldNotBeFound("sifraPonude.equals=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisBySifraPonudeIsInShouldWork() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where sifraPonude in DEFAULT_SIFRA_PONUDE or UPDATED_SIFRA_PONUDE
        defaultPrvorangiraniShouldBeFound("sifraPonude.in=" + DEFAULT_SIFRA_PONUDE + "," + UPDATED_SIFRA_PONUDE);

        // Get all the prvorangiraniList where sifraPonude equals to UPDATED_SIFRA_PONUDE
        defaultPrvorangiraniShouldNotBeFound("sifraPonude.in=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisBySifraPonudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where sifraPonude is not null
        defaultPrvorangiraniShouldBeFound("sifraPonude.specified=true");

        // Get all the prvorangiraniList where sifraPonude is null
        defaultPrvorangiraniShouldNotBeFound("sifraPonude.specified=false");
    }

    @Test
    @Transactional
    void getAllPrvorangiranisBySifraPonudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where sifraPonude is greater than or equal to DEFAULT_SIFRA_PONUDE
        defaultPrvorangiraniShouldBeFound("sifraPonude.greaterThanOrEqual=" + DEFAULT_SIFRA_PONUDE);

        // Get all the prvorangiraniList where sifraPonude is greater than or equal to UPDATED_SIFRA_PONUDE
        defaultPrvorangiraniShouldNotBeFound("sifraPonude.greaterThanOrEqual=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisBySifraPonudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where sifraPonude is less than or equal to DEFAULT_SIFRA_PONUDE
        defaultPrvorangiraniShouldBeFound("sifraPonude.lessThanOrEqual=" + DEFAULT_SIFRA_PONUDE);

        // Get all the prvorangiraniList where sifraPonude is less than or equal to SMALLER_SIFRA_PONUDE
        defaultPrvorangiraniShouldNotBeFound("sifraPonude.lessThanOrEqual=" + SMALLER_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisBySifraPonudeIsLessThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where sifraPonude is less than DEFAULT_SIFRA_PONUDE
        defaultPrvorangiraniShouldNotBeFound("sifraPonude.lessThan=" + DEFAULT_SIFRA_PONUDE);

        // Get all the prvorangiraniList where sifraPonude is less than UPDATED_SIFRA_PONUDE
        defaultPrvorangiraniShouldBeFound("sifraPonude.lessThan=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisBySifraPonudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where sifraPonude is greater than DEFAULT_SIFRA_PONUDE
        defaultPrvorangiraniShouldNotBeFound("sifraPonude.greaterThan=" + DEFAULT_SIFRA_PONUDE);

        // Get all the prvorangiraniList where sifraPonude is greater than SMALLER_SIFRA_PONUDE
        defaultPrvorangiraniShouldBeFound("sifraPonude.greaterThan=" + SMALLER_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBrojPartijeIsEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where brojPartije equals to DEFAULT_BROJ_PARTIJE
        defaultPrvorangiraniShouldBeFound("brojPartije.equals=" + DEFAULT_BROJ_PARTIJE);

        // Get all the prvorangiraniList where brojPartije equals to UPDATED_BROJ_PARTIJE
        defaultPrvorangiraniShouldNotBeFound("brojPartije.equals=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBrojPartijeIsInShouldWork() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where brojPartije in DEFAULT_BROJ_PARTIJE or UPDATED_BROJ_PARTIJE
        defaultPrvorangiraniShouldBeFound("brojPartije.in=" + DEFAULT_BROJ_PARTIJE + "," + UPDATED_BROJ_PARTIJE);

        // Get all the prvorangiraniList where brojPartije equals to UPDATED_BROJ_PARTIJE
        defaultPrvorangiraniShouldNotBeFound("brojPartije.in=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBrojPartijeIsNullOrNotNull() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where brojPartije is not null
        defaultPrvorangiraniShouldBeFound("brojPartije.specified=true");

        // Get all the prvorangiraniList where brojPartije is null
        defaultPrvorangiraniShouldNotBeFound("brojPartije.specified=false");
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBrojPartijeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where brojPartije is greater than or equal to DEFAULT_BROJ_PARTIJE
        defaultPrvorangiraniShouldBeFound("brojPartije.greaterThanOrEqual=" + DEFAULT_BROJ_PARTIJE);

        // Get all the prvorangiraniList where brojPartije is greater than or equal to UPDATED_BROJ_PARTIJE
        defaultPrvorangiraniShouldNotBeFound("brojPartije.greaterThanOrEqual=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBrojPartijeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where brojPartije is less than or equal to DEFAULT_BROJ_PARTIJE
        defaultPrvorangiraniShouldBeFound("brojPartije.lessThanOrEqual=" + DEFAULT_BROJ_PARTIJE);

        // Get all the prvorangiraniList where brojPartije is less than or equal to SMALLER_BROJ_PARTIJE
        defaultPrvorangiraniShouldNotBeFound("brojPartije.lessThanOrEqual=" + SMALLER_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBrojPartijeIsLessThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where brojPartije is less than DEFAULT_BROJ_PARTIJE
        defaultPrvorangiraniShouldNotBeFound("brojPartije.lessThan=" + DEFAULT_BROJ_PARTIJE);

        // Get all the prvorangiraniList where brojPartije is less than UPDATED_BROJ_PARTIJE
        defaultPrvorangiraniShouldBeFound("brojPartije.lessThan=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBrojPartijeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where brojPartije is greater than DEFAULT_BROJ_PARTIJE
        defaultPrvorangiraniShouldNotBeFound("brojPartije.greaterThan=" + DEFAULT_BROJ_PARTIJE);

        // Get all the prvorangiraniList where brojPartije is greater than SMALLER_BROJ_PARTIJE
        defaultPrvorangiraniShouldBeFound("brojPartije.greaterThan=" + SMALLER_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByAtcIsEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where atc equals to DEFAULT_ATC
        defaultPrvorangiraniShouldBeFound("atc.equals=" + DEFAULT_ATC);

        // Get all the prvorangiraniList where atc equals to UPDATED_ATC
        defaultPrvorangiraniShouldNotBeFound("atc.equals=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByAtcIsInShouldWork() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where atc in DEFAULT_ATC or UPDATED_ATC
        defaultPrvorangiraniShouldBeFound("atc.in=" + DEFAULT_ATC + "," + UPDATED_ATC);

        // Get all the prvorangiraniList where atc equals to UPDATED_ATC
        defaultPrvorangiraniShouldNotBeFound("atc.in=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByAtcIsNullOrNotNull() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where atc is not null
        defaultPrvorangiraniShouldBeFound("atc.specified=true");

        // Get all the prvorangiraniList where atc is null
        defaultPrvorangiraniShouldNotBeFound("atc.specified=false");
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByAtcContainsSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where atc contains DEFAULT_ATC
        defaultPrvorangiraniShouldBeFound("atc.contains=" + DEFAULT_ATC);

        // Get all the prvorangiraniList where atc contains UPDATED_ATC
        defaultPrvorangiraniShouldNotBeFound("atc.contains=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByAtcNotContainsSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where atc does not contain DEFAULT_ATC
        defaultPrvorangiraniShouldNotBeFound("atc.doesNotContain=" + DEFAULT_ATC);

        // Get all the prvorangiraniList where atc does not contain UPDATED_ATC
        defaultPrvorangiraniShouldBeFound("atc.doesNotContain=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByTrazenaKolicinaIsEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where trazenaKolicina equals to DEFAULT_TRAZENA_KOLICINA
        defaultPrvorangiraniShouldBeFound("trazenaKolicina.equals=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the prvorangiraniList where trazenaKolicina equals to UPDATED_TRAZENA_KOLICINA
        defaultPrvorangiraniShouldNotBeFound("trazenaKolicina.equals=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByTrazenaKolicinaIsInShouldWork() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where trazenaKolicina in DEFAULT_TRAZENA_KOLICINA or UPDATED_TRAZENA_KOLICINA
        defaultPrvorangiraniShouldBeFound("trazenaKolicina.in=" + DEFAULT_TRAZENA_KOLICINA + "," + UPDATED_TRAZENA_KOLICINA);

        // Get all the prvorangiraniList where trazenaKolicina equals to UPDATED_TRAZENA_KOLICINA
        defaultPrvorangiraniShouldNotBeFound("trazenaKolicina.in=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByTrazenaKolicinaIsNullOrNotNull() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where trazenaKolicina is not null
        defaultPrvorangiraniShouldBeFound("trazenaKolicina.specified=true");

        // Get all the prvorangiraniList where trazenaKolicina is null
        defaultPrvorangiraniShouldNotBeFound("trazenaKolicina.specified=false");
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByTrazenaKolicinaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where trazenaKolicina is greater than or equal to DEFAULT_TRAZENA_KOLICINA
        defaultPrvorangiraniShouldBeFound("trazenaKolicina.greaterThanOrEqual=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the prvorangiraniList where trazenaKolicina is greater than or equal to UPDATED_TRAZENA_KOLICINA
        defaultPrvorangiraniShouldNotBeFound("trazenaKolicina.greaterThanOrEqual=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByTrazenaKolicinaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where trazenaKolicina is less than or equal to DEFAULT_TRAZENA_KOLICINA
        defaultPrvorangiraniShouldBeFound("trazenaKolicina.lessThanOrEqual=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the prvorangiraniList where trazenaKolicina is less than or equal to SMALLER_TRAZENA_KOLICINA
        defaultPrvorangiraniShouldNotBeFound("trazenaKolicina.lessThanOrEqual=" + SMALLER_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByTrazenaKolicinaIsLessThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where trazenaKolicina is less than DEFAULT_TRAZENA_KOLICINA
        defaultPrvorangiraniShouldNotBeFound("trazenaKolicina.lessThan=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the prvorangiraniList where trazenaKolicina is less than UPDATED_TRAZENA_KOLICINA
        defaultPrvorangiraniShouldBeFound("trazenaKolicina.lessThan=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByTrazenaKolicinaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where trazenaKolicina is greater than DEFAULT_TRAZENA_KOLICINA
        defaultPrvorangiraniShouldNotBeFound("trazenaKolicina.greaterThan=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the prvorangiraniList where trazenaKolicina is greater than SMALLER_TRAZENA_KOLICINA
        defaultPrvorangiraniShouldBeFound("trazenaKolicina.greaterThan=" + SMALLER_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByProcijenjenaVrijednostIsEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where procijenjenaVrijednost equals to DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultPrvorangiraniShouldBeFound("procijenjenaVrijednost.equals=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the prvorangiraniList where procijenjenaVrijednost equals to UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultPrvorangiraniShouldNotBeFound("procijenjenaVrijednost.equals=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByProcijenjenaVrijednostIsInShouldWork() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where procijenjenaVrijednost in DEFAULT_PROCIJENJENA_VRIJEDNOST or UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultPrvorangiraniShouldBeFound(
            "procijenjenaVrijednost.in=" + DEFAULT_PROCIJENJENA_VRIJEDNOST + "," + UPDATED_PROCIJENJENA_VRIJEDNOST
        );

        // Get all the prvorangiraniList where procijenjenaVrijednost equals to UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultPrvorangiraniShouldNotBeFound("procijenjenaVrijednost.in=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByProcijenjenaVrijednostIsNullOrNotNull() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where procijenjenaVrijednost is not null
        defaultPrvorangiraniShouldBeFound("procijenjenaVrijednost.specified=true");

        // Get all the prvorangiraniList where procijenjenaVrijednost is null
        defaultPrvorangiraniShouldNotBeFound("procijenjenaVrijednost.specified=false");
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByProcijenjenaVrijednostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where procijenjenaVrijednost is greater than or equal to DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultPrvorangiraniShouldBeFound("procijenjenaVrijednost.greaterThanOrEqual=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the prvorangiraniList where procijenjenaVrijednost is greater than or equal to UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultPrvorangiraniShouldNotBeFound("procijenjenaVrijednost.greaterThanOrEqual=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByProcijenjenaVrijednostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where procijenjenaVrijednost is less than or equal to DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultPrvorangiraniShouldBeFound("procijenjenaVrijednost.lessThanOrEqual=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the prvorangiraniList where procijenjenaVrijednost is less than or equal to SMALLER_PROCIJENJENA_VRIJEDNOST
        defaultPrvorangiraniShouldNotBeFound("procijenjenaVrijednost.lessThanOrEqual=" + SMALLER_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByProcijenjenaVrijednostIsLessThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where procijenjenaVrijednost is less than DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultPrvorangiraniShouldNotBeFound("procijenjenaVrijednost.lessThan=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the prvorangiraniList where procijenjenaVrijednost is less than UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultPrvorangiraniShouldBeFound("procijenjenaVrijednost.lessThan=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByProcijenjenaVrijednostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where procijenjenaVrijednost is greater than DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultPrvorangiraniShouldNotBeFound("procijenjenaVrijednost.greaterThan=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the prvorangiraniList where procijenjenaVrijednost is greater than SMALLER_PROCIJENJENA_VRIJEDNOST
        defaultPrvorangiraniShouldBeFound("procijenjenaVrijednost.greaterThan=" + SMALLER_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByNazivProizvodjacaIsEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where nazivProizvodjaca equals to DEFAULT_NAZIV_PROIZVODJACA
        defaultPrvorangiraniShouldBeFound("nazivProizvodjaca.equals=" + DEFAULT_NAZIV_PROIZVODJACA);

        // Get all the prvorangiraniList where nazivProizvodjaca equals to UPDATED_NAZIV_PROIZVODJACA
        defaultPrvorangiraniShouldNotBeFound("nazivProizvodjaca.equals=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByNazivProizvodjacaIsInShouldWork() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where nazivProizvodjaca in DEFAULT_NAZIV_PROIZVODJACA or UPDATED_NAZIV_PROIZVODJACA
        defaultPrvorangiraniShouldBeFound("nazivProizvodjaca.in=" + DEFAULT_NAZIV_PROIZVODJACA + "," + UPDATED_NAZIV_PROIZVODJACA);

        // Get all the prvorangiraniList where nazivProizvodjaca equals to UPDATED_NAZIV_PROIZVODJACA
        defaultPrvorangiraniShouldNotBeFound("nazivProizvodjaca.in=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByNazivProizvodjacaIsNullOrNotNull() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where nazivProizvodjaca is not null
        defaultPrvorangiraniShouldBeFound("nazivProizvodjaca.specified=true");

        // Get all the prvorangiraniList where nazivProizvodjaca is null
        defaultPrvorangiraniShouldNotBeFound("nazivProizvodjaca.specified=false");
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByNazivProizvodjacaContainsSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where nazivProizvodjaca contains DEFAULT_NAZIV_PROIZVODJACA
        defaultPrvorangiraniShouldBeFound("nazivProizvodjaca.contains=" + DEFAULT_NAZIV_PROIZVODJACA);

        // Get all the prvorangiraniList where nazivProizvodjaca contains UPDATED_NAZIV_PROIZVODJACA
        defaultPrvorangiraniShouldNotBeFound("nazivProizvodjaca.contains=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByNazivProizvodjacaNotContainsSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where nazivProizvodjaca does not contain DEFAULT_NAZIV_PROIZVODJACA
        defaultPrvorangiraniShouldNotBeFound("nazivProizvodjaca.doesNotContain=" + DEFAULT_NAZIV_PROIZVODJACA);

        // Get all the prvorangiraniList where nazivProizvodjaca does not contain UPDATED_NAZIV_PROIZVODJACA
        defaultPrvorangiraniShouldBeFound("nazivProizvodjaca.doesNotContain=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByZasticeniNazivIsEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where zasticeniNaziv equals to DEFAULT_ZASTICENI_NAZIV
        defaultPrvorangiraniShouldBeFound("zasticeniNaziv.equals=" + DEFAULT_ZASTICENI_NAZIV);

        // Get all the prvorangiraniList where zasticeniNaziv equals to UPDATED_ZASTICENI_NAZIV
        defaultPrvorangiraniShouldNotBeFound("zasticeniNaziv.equals=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByZasticeniNazivIsInShouldWork() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where zasticeniNaziv in DEFAULT_ZASTICENI_NAZIV or UPDATED_ZASTICENI_NAZIV
        defaultPrvorangiraniShouldBeFound("zasticeniNaziv.in=" + DEFAULT_ZASTICENI_NAZIV + "," + UPDATED_ZASTICENI_NAZIV);

        // Get all the prvorangiraniList where zasticeniNaziv equals to UPDATED_ZASTICENI_NAZIV
        defaultPrvorangiraniShouldNotBeFound("zasticeniNaziv.in=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByZasticeniNazivIsNullOrNotNull() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where zasticeniNaziv is not null
        defaultPrvorangiraniShouldBeFound("zasticeniNaziv.specified=true");

        // Get all the prvorangiraniList where zasticeniNaziv is null
        defaultPrvorangiraniShouldNotBeFound("zasticeniNaziv.specified=false");
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByZasticeniNazivContainsSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where zasticeniNaziv contains DEFAULT_ZASTICENI_NAZIV
        defaultPrvorangiraniShouldBeFound("zasticeniNaziv.contains=" + DEFAULT_ZASTICENI_NAZIV);

        // Get all the prvorangiraniList where zasticeniNaziv contains UPDATED_ZASTICENI_NAZIV
        defaultPrvorangiraniShouldNotBeFound("zasticeniNaziv.contains=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByZasticeniNazivNotContainsSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where zasticeniNaziv does not contain DEFAULT_ZASTICENI_NAZIV
        defaultPrvorangiraniShouldNotBeFound("zasticeniNaziv.doesNotContain=" + DEFAULT_ZASTICENI_NAZIV);

        // Get all the prvorangiraniList where zasticeniNaziv does not contain UPDATED_ZASTICENI_NAZIV
        defaultPrvorangiraniShouldBeFound("zasticeniNaziv.doesNotContain=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByJedinicnaCijenaIsEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where jedinicnaCijena equals to DEFAULT_JEDINICNA_CIJENA
        defaultPrvorangiraniShouldBeFound("jedinicnaCijena.equals=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the prvorangiraniList where jedinicnaCijena equals to UPDATED_JEDINICNA_CIJENA
        defaultPrvorangiraniShouldNotBeFound("jedinicnaCijena.equals=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByJedinicnaCijenaIsInShouldWork() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where jedinicnaCijena in DEFAULT_JEDINICNA_CIJENA or UPDATED_JEDINICNA_CIJENA
        defaultPrvorangiraniShouldBeFound("jedinicnaCijena.in=" + DEFAULT_JEDINICNA_CIJENA + "," + UPDATED_JEDINICNA_CIJENA);

        // Get all the prvorangiraniList where jedinicnaCijena equals to UPDATED_JEDINICNA_CIJENA
        defaultPrvorangiraniShouldNotBeFound("jedinicnaCijena.in=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByJedinicnaCijenaIsNullOrNotNull() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where jedinicnaCijena is not null
        defaultPrvorangiraniShouldBeFound("jedinicnaCijena.specified=true");

        // Get all the prvorangiraniList where jedinicnaCijena is null
        defaultPrvorangiraniShouldNotBeFound("jedinicnaCijena.specified=false");
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByJedinicnaCijenaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where jedinicnaCijena is greater than or equal to DEFAULT_JEDINICNA_CIJENA
        defaultPrvorangiraniShouldBeFound("jedinicnaCijena.greaterThanOrEqual=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the prvorangiraniList where jedinicnaCijena is greater than or equal to UPDATED_JEDINICNA_CIJENA
        defaultPrvorangiraniShouldNotBeFound("jedinicnaCijena.greaterThanOrEqual=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByJedinicnaCijenaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where jedinicnaCijena is less than or equal to DEFAULT_JEDINICNA_CIJENA
        defaultPrvorangiraniShouldBeFound("jedinicnaCijena.lessThanOrEqual=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the prvorangiraniList where jedinicnaCijena is less than or equal to SMALLER_JEDINICNA_CIJENA
        defaultPrvorangiraniShouldNotBeFound("jedinicnaCijena.lessThanOrEqual=" + SMALLER_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByJedinicnaCijenaIsLessThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where jedinicnaCijena is less than DEFAULT_JEDINICNA_CIJENA
        defaultPrvorangiraniShouldNotBeFound("jedinicnaCijena.lessThan=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the prvorangiraniList where jedinicnaCijena is less than UPDATED_JEDINICNA_CIJENA
        defaultPrvorangiraniShouldBeFound("jedinicnaCijena.lessThan=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByJedinicnaCijenaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where jedinicnaCijena is greater than DEFAULT_JEDINICNA_CIJENA
        defaultPrvorangiraniShouldNotBeFound("jedinicnaCijena.greaterThan=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the prvorangiraniList where jedinicnaCijena is greater than SMALLER_JEDINICNA_CIJENA
        defaultPrvorangiraniShouldBeFound("jedinicnaCijena.greaterThan=" + SMALLER_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByPonudjenaVrijednostIsEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where ponudjenaVrijednost equals to DEFAULT_PONUDJENA_VRIJEDNOST
        defaultPrvorangiraniShouldBeFound("ponudjenaVrijednost.equals=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the prvorangiraniList where ponudjenaVrijednost equals to UPDATED_PONUDJENA_VRIJEDNOST
        defaultPrvorangiraniShouldNotBeFound("ponudjenaVrijednost.equals=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByPonudjenaVrijednostIsInShouldWork() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where ponudjenaVrijednost in DEFAULT_PONUDJENA_VRIJEDNOST or UPDATED_PONUDJENA_VRIJEDNOST
        defaultPrvorangiraniShouldBeFound("ponudjenaVrijednost.in=" + DEFAULT_PONUDJENA_VRIJEDNOST + "," + UPDATED_PONUDJENA_VRIJEDNOST);

        // Get all the prvorangiraniList where ponudjenaVrijednost equals to UPDATED_PONUDJENA_VRIJEDNOST
        defaultPrvorangiraniShouldNotBeFound("ponudjenaVrijednost.in=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByPonudjenaVrijednostIsNullOrNotNull() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where ponudjenaVrijednost is not null
        defaultPrvorangiraniShouldBeFound("ponudjenaVrijednost.specified=true");

        // Get all the prvorangiraniList where ponudjenaVrijednost is null
        defaultPrvorangiraniShouldNotBeFound("ponudjenaVrijednost.specified=false");
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByPonudjenaVrijednostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where ponudjenaVrijednost is greater than or equal to DEFAULT_PONUDJENA_VRIJEDNOST
        defaultPrvorangiraniShouldBeFound("ponudjenaVrijednost.greaterThanOrEqual=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the prvorangiraniList where ponudjenaVrijednost is greater than or equal to UPDATED_PONUDJENA_VRIJEDNOST
        defaultPrvorangiraniShouldNotBeFound("ponudjenaVrijednost.greaterThanOrEqual=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByPonudjenaVrijednostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where ponudjenaVrijednost is less than or equal to DEFAULT_PONUDJENA_VRIJEDNOST
        defaultPrvorangiraniShouldBeFound("ponudjenaVrijednost.lessThanOrEqual=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the prvorangiraniList where ponudjenaVrijednost is less than or equal to SMALLER_PONUDJENA_VRIJEDNOST
        defaultPrvorangiraniShouldNotBeFound("ponudjenaVrijednost.lessThanOrEqual=" + SMALLER_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByPonudjenaVrijednostIsLessThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where ponudjenaVrijednost is less than DEFAULT_PONUDJENA_VRIJEDNOST
        defaultPrvorangiraniShouldNotBeFound("ponudjenaVrijednost.lessThan=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the prvorangiraniList where ponudjenaVrijednost is less than UPDATED_PONUDJENA_VRIJEDNOST
        defaultPrvorangiraniShouldBeFound("ponudjenaVrijednost.lessThan=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByPonudjenaVrijednostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where ponudjenaVrijednost is greater than DEFAULT_PONUDJENA_VRIJEDNOST
        defaultPrvorangiraniShouldNotBeFound("ponudjenaVrijednost.greaterThan=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the prvorangiraniList where ponudjenaVrijednost is greater than SMALLER_PONUDJENA_VRIJEDNOST
        defaultPrvorangiraniShouldBeFound("ponudjenaVrijednost.greaterThan=" + SMALLER_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByRokIsporukeIsEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where rokIsporuke equals to DEFAULT_ROK_ISPORUKE
        defaultPrvorangiraniShouldBeFound("rokIsporuke.equals=" + DEFAULT_ROK_ISPORUKE);

        // Get all the prvorangiraniList where rokIsporuke equals to UPDATED_ROK_ISPORUKE
        defaultPrvorangiraniShouldNotBeFound("rokIsporuke.equals=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByRokIsporukeIsInShouldWork() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where rokIsporuke in DEFAULT_ROK_ISPORUKE or UPDATED_ROK_ISPORUKE
        defaultPrvorangiraniShouldBeFound("rokIsporuke.in=" + DEFAULT_ROK_ISPORUKE + "," + UPDATED_ROK_ISPORUKE);

        // Get all the prvorangiraniList where rokIsporuke equals to UPDATED_ROK_ISPORUKE
        defaultPrvorangiraniShouldNotBeFound("rokIsporuke.in=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByRokIsporukeIsNullOrNotNull() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where rokIsporuke is not null
        defaultPrvorangiraniShouldBeFound("rokIsporuke.specified=true");

        // Get all the prvorangiraniList where rokIsporuke is null
        defaultPrvorangiraniShouldNotBeFound("rokIsporuke.specified=false");
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByRokIsporukeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where rokIsporuke is greater than or equal to DEFAULT_ROK_ISPORUKE
        defaultPrvorangiraniShouldBeFound("rokIsporuke.greaterThanOrEqual=" + DEFAULT_ROK_ISPORUKE);

        // Get all the prvorangiraniList where rokIsporuke is greater than or equal to UPDATED_ROK_ISPORUKE
        defaultPrvorangiraniShouldNotBeFound("rokIsporuke.greaterThanOrEqual=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByRokIsporukeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where rokIsporuke is less than or equal to DEFAULT_ROK_ISPORUKE
        defaultPrvorangiraniShouldBeFound("rokIsporuke.lessThanOrEqual=" + DEFAULT_ROK_ISPORUKE);

        // Get all the prvorangiraniList where rokIsporuke is less than or equal to SMALLER_ROK_ISPORUKE
        defaultPrvorangiraniShouldNotBeFound("rokIsporuke.lessThanOrEqual=" + SMALLER_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByRokIsporukeIsLessThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where rokIsporuke is less than DEFAULT_ROK_ISPORUKE
        defaultPrvorangiraniShouldNotBeFound("rokIsporuke.lessThan=" + DEFAULT_ROK_ISPORUKE);

        // Get all the prvorangiraniList where rokIsporuke is less than UPDATED_ROK_ISPORUKE
        defaultPrvorangiraniShouldBeFound("rokIsporuke.lessThan=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByRokIsporukeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where rokIsporuke is greater than DEFAULT_ROK_ISPORUKE
        defaultPrvorangiraniShouldNotBeFound("rokIsporuke.greaterThan=" + DEFAULT_ROK_ISPORUKE);

        // Get all the prvorangiraniList where rokIsporuke is greater than SMALLER_ROK_ISPORUKE
        defaultPrvorangiraniShouldBeFound("rokIsporuke.greaterThan=" + SMALLER_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByVrstaPostupkaIsEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where vrstaPostupka equals to DEFAULT_VRSTA_POSTUPKA
        defaultPrvorangiraniShouldBeFound("vrstaPostupka.equals=" + DEFAULT_VRSTA_POSTUPKA);

        // Get all the prvorangiraniList where vrstaPostupka equals to UPDATED_VRSTA_POSTUPKA
        defaultPrvorangiraniShouldNotBeFound("vrstaPostupka.equals=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByVrstaPostupkaIsInShouldWork() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where vrstaPostupka in DEFAULT_VRSTA_POSTUPKA or UPDATED_VRSTA_POSTUPKA
        defaultPrvorangiraniShouldBeFound("vrstaPostupka.in=" + DEFAULT_VRSTA_POSTUPKA + "," + UPDATED_VRSTA_POSTUPKA);

        // Get all the prvorangiraniList where vrstaPostupka equals to UPDATED_VRSTA_POSTUPKA
        defaultPrvorangiraniShouldNotBeFound("vrstaPostupka.in=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByVrstaPostupkaIsNullOrNotNull() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where vrstaPostupka is not null
        defaultPrvorangiraniShouldBeFound("vrstaPostupka.specified=true");

        // Get all the prvorangiraniList where vrstaPostupka is null
        defaultPrvorangiraniShouldNotBeFound("vrstaPostupka.specified=false");
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByVrstaPostupkaContainsSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where vrstaPostupka contains DEFAULT_VRSTA_POSTUPKA
        defaultPrvorangiraniShouldBeFound("vrstaPostupka.contains=" + DEFAULT_VRSTA_POSTUPKA);

        // Get all the prvorangiraniList where vrstaPostupka contains UPDATED_VRSTA_POSTUPKA
        defaultPrvorangiraniShouldNotBeFound("vrstaPostupka.contains=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByVrstaPostupkaNotContainsSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where vrstaPostupka does not contain DEFAULT_VRSTA_POSTUPKA
        defaultPrvorangiraniShouldNotBeFound("vrstaPostupka.doesNotContain=" + DEFAULT_VRSTA_POSTUPKA);

        // Get all the prvorangiraniList where vrstaPostupka does not contain UPDATED_VRSTA_POSTUPKA
        defaultPrvorangiraniShouldBeFound("vrstaPostupka.doesNotContain=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodCijenaIsEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodCijena equals to DEFAULT_BOD_CIJENA
        defaultPrvorangiraniShouldBeFound("bodCijena.equals=" + DEFAULT_BOD_CIJENA);

        // Get all the prvorangiraniList where bodCijena equals to UPDATED_BOD_CIJENA
        defaultPrvorangiraniShouldNotBeFound("bodCijena.equals=" + UPDATED_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodCijenaIsInShouldWork() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodCijena in DEFAULT_BOD_CIJENA or UPDATED_BOD_CIJENA
        defaultPrvorangiraniShouldBeFound("bodCijena.in=" + DEFAULT_BOD_CIJENA + "," + UPDATED_BOD_CIJENA);

        // Get all the prvorangiraniList where bodCijena equals to UPDATED_BOD_CIJENA
        defaultPrvorangiraniShouldNotBeFound("bodCijena.in=" + UPDATED_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodCijenaIsNullOrNotNull() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodCijena is not null
        defaultPrvorangiraniShouldBeFound("bodCijena.specified=true");

        // Get all the prvorangiraniList where bodCijena is null
        defaultPrvorangiraniShouldNotBeFound("bodCijena.specified=false");
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodCijenaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodCijena is greater than or equal to DEFAULT_BOD_CIJENA
        defaultPrvorangiraniShouldBeFound("bodCijena.greaterThanOrEqual=" + DEFAULT_BOD_CIJENA);

        // Get all the prvorangiraniList where bodCijena is greater than or equal to UPDATED_BOD_CIJENA
        defaultPrvorangiraniShouldNotBeFound("bodCijena.greaterThanOrEqual=" + UPDATED_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodCijenaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodCijena is less than or equal to DEFAULT_BOD_CIJENA
        defaultPrvorangiraniShouldBeFound("bodCijena.lessThanOrEqual=" + DEFAULT_BOD_CIJENA);

        // Get all the prvorangiraniList where bodCijena is less than or equal to SMALLER_BOD_CIJENA
        defaultPrvorangiraniShouldNotBeFound("bodCijena.lessThanOrEqual=" + SMALLER_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodCijenaIsLessThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodCijena is less than DEFAULT_BOD_CIJENA
        defaultPrvorangiraniShouldNotBeFound("bodCijena.lessThan=" + DEFAULT_BOD_CIJENA);

        // Get all the prvorangiraniList where bodCijena is less than UPDATED_BOD_CIJENA
        defaultPrvorangiraniShouldBeFound("bodCijena.lessThan=" + UPDATED_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodCijenaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodCijena is greater than DEFAULT_BOD_CIJENA
        defaultPrvorangiraniShouldNotBeFound("bodCijena.greaterThan=" + DEFAULT_BOD_CIJENA);

        // Get all the prvorangiraniList where bodCijena is greater than SMALLER_BOD_CIJENA
        defaultPrvorangiraniShouldBeFound("bodCijena.greaterThan=" + SMALLER_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodRokIsEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodRok equals to DEFAULT_BOD_ROK
        defaultPrvorangiraniShouldBeFound("bodRok.equals=" + DEFAULT_BOD_ROK);

        // Get all the prvorangiraniList where bodRok equals to UPDATED_BOD_ROK
        defaultPrvorangiraniShouldNotBeFound("bodRok.equals=" + UPDATED_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodRokIsInShouldWork() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodRok in DEFAULT_BOD_ROK or UPDATED_BOD_ROK
        defaultPrvorangiraniShouldBeFound("bodRok.in=" + DEFAULT_BOD_ROK + "," + UPDATED_BOD_ROK);

        // Get all the prvorangiraniList where bodRok equals to UPDATED_BOD_ROK
        defaultPrvorangiraniShouldNotBeFound("bodRok.in=" + UPDATED_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodRokIsNullOrNotNull() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodRok is not null
        defaultPrvorangiraniShouldBeFound("bodRok.specified=true");

        // Get all the prvorangiraniList where bodRok is null
        defaultPrvorangiraniShouldNotBeFound("bodRok.specified=false");
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodRokIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodRok is greater than or equal to DEFAULT_BOD_ROK
        defaultPrvorangiraniShouldBeFound("bodRok.greaterThanOrEqual=" + DEFAULT_BOD_ROK);

        // Get all the prvorangiraniList where bodRok is greater than or equal to UPDATED_BOD_ROK
        defaultPrvorangiraniShouldNotBeFound("bodRok.greaterThanOrEqual=" + UPDATED_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodRokIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodRok is less than or equal to DEFAULT_BOD_ROK
        defaultPrvorangiraniShouldBeFound("bodRok.lessThanOrEqual=" + DEFAULT_BOD_ROK);

        // Get all the prvorangiraniList where bodRok is less than or equal to SMALLER_BOD_ROK
        defaultPrvorangiraniShouldNotBeFound("bodRok.lessThanOrEqual=" + SMALLER_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodRokIsLessThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodRok is less than DEFAULT_BOD_ROK
        defaultPrvorangiraniShouldNotBeFound("bodRok.lessThan=" + DEFAULT_BOD_ROK);

        // Get all the prvorangiraniList where bodRok is less than UPDATED_BOD_ROK
        defaultPrvorangiraniShouldBeFound("bodRok.lessThan=" + UPDATED_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodRokIsGreaterThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodRok is greater than DEFAULT_BOD_ROK
        defaultPrvorangiraniShouldNotBeFound("bodRok.greaterThan=" + DEFAULT_BOD_ROK);

        // Get all the prvorangiraniList where bodRok is greater than SMALLER_BOD_ROK
        defaultPrvorangiraniShouldBeFound("bodRok.greaterThan=" + SMALLER_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodUkupnoIsEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodUkupno equals to DEFAULT_BOD_UKUPNO
        defaultPrvorangiraniShouldBeFound("bodUkupno.equals=" + DEFAULT_BOD_UKUPNO);

        // Get all the prvorangiraniList where bodUkupno equals to UPDATED_BOD_UKUPNO
        defaultPrvorangiraniShouldNotBeFound("bodUkupno.equals=" + UPDATED_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodUkupnoIsInShouldWork() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodUkupno in DEFAULT_BOD_UKUPNO or UPDATED_BOD_UKUPNO
        defaultPrvorangiraniShouldBeFound("bodUkupno.in=" + DEFAULT_BOD_UKUPNO + "," + UPDATED_BOD_UKUPNO);

        // Get all the prvorangiraniList where bodUkupno equals to UPDATED_BOD_UKUPNO
        defaultPrvorangiraniShouldNotBeFound("bodUkupno.in=" + UPDATED_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodUkupnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodUkupno is not null
        defaultPrvorangiraniShouldBeFound("bodUkupno.specified=true");

        // Get all the prvorangiraniList where bodUkupno is null
        defaultPrvorangiraniShouldNotBeFound("bodUkupno.specified=false");
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodUkupnoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodUkupno is greater than or equal to DEFAULT_BOD_UKUPNO
        defaultPrvorangiraniShouldBeFound("bodUkupno.greaterThanOrEqual=" + DEFAULT_BOD_UKUPNO);

        // Get all the prvorangiraniList where bodUkupno is greater than or equal to UPDATED_BOD_UKUPNO
        defaultPrvorangiraniShouldNotBeFound("bodUkupno.greaterThanOrEqual=" + UPDATED_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodUkupnoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodUkupno is less than or equal to DEFAULT_BOD_UKUPNO
        defaultPrvorangiraniShouldBeFound("bodUkupno.lessThanOrEqual=" + DEFAULT_BOD_UKUPNO);

        // Get all the prvorangiraniList where bodUkupno is less than or equal to SMALLER_BOD_UKUPNO
        defaultPrvorangiraniShouldNotBeFound("bodUkupno.lessThanOrEqual=" + SMALLER_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodUkupnoIsLessThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodUkupno is less than DEFAULT_BOD_UKUPNO
        defaultPrvorangiraniShouldNotBeFound("bodUkupno.lessThan=" + DEFAULT_BOD_UKUPNO);

        // Get all the prvorangiraniList where bodUkupno is less than UPDATED_BOD_UKUPNO
        defaultPrvorangiraniShouldBeFound("bodUkupno.lessThan=" + UPDATED_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllPrvorangiranisByBodUkupnoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        prvorangiraniRepository.saveAndFlush(prvorangirani);

        // Get all the prvorangiraniList where bodUkupno is greater than DEFAULT_BOD_UKUPNO
        defaultPrvorangiraniShouldNotBeFound("bodUkupno.greaterThan=" + DEFAULT_BOD_UKUPNO);

        // Get all the prvorangiraniList where bodUkupno is greater than SMALLER_BOD_UKUPNO
        defaultPrvorangiraniShouldBeFound("bodUkupno.greaterThan=" + SMALLER_BOD_UKUPNO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPrvorangiraniShouldBeFound(String filter) throws Exception {
        restPrvorangiraniMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prvorangirani.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].nazivPonudjaca").value(hasItem(DEFAULT_NAZIV_PONUDJACA)))
            .andExpect(jsonPath("$.[*].sifraPonude").value(hasItem(DEFAULT_SIFRA_PONUDE)))
            .andExpect(jsonPath("$.[*].brojPartije").value(hasItem(DEFAULT_BROJ_PARTIJE)))
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
            .andExpect(jsonPath("$.[*].bodUkupno").value(hasItem(DEFAULT_BOD_UKUPNO.doubleValue())));

        // Check, that the count call also returns 1
        restPrvorangiraniMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPrvorangiraniShouldNotBeFound(String filter) throws Exception {
        restPrvorangiraniMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPrvorangiraniMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPrvorangirani() throws Exception {
        // Get the prvorangirani
        restPrvorangiraniMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
