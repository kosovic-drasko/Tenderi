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
import tender.domain.Vrednovanje;
import tender.repository.VrednovanjeRepository;
import tender.service.criteria.VrednovanjeCriteria;

/**
 * Integration tests for the {@link VrednovanjeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VrednovanjeResourceIT {

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

    private static final String DEFAULT_NAZIV_PONUDJACA = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV_PONUDJACA = "BBBBBBBBBB";

    private static final String DEFAULT_ATC = "AAAAAAAAAA";
    private static final String UPDATED_ATC = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRAZENA_KOLICINA = 1;
    private static final Integer UPDATED_TRAZENA_KOLICINA = 2;
    private static final Integer SMALLER_TRAZENA_KOLICINA = 1 - 1;

    private static final Double DEFAULT_PROCIJENJENA_VRIJEDNOST = 1D;
    private static final Double UPDATED_PROCIJENJENA_VRIJEDNOST = 2D;
    private static final Double SMALLER_PROCIJENJENA_VRIJEDNOST = 1D - 1D;

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

    private static final String ENTITY_API_URL = "/api/vrednovanjes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VrednovanjeRepository vrednovanjeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVrednovanjeMockMvc;

    private Vrednovanje vrednovanje;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vrednovanje createEntity(EntityManager em) {
        Vrednovanje vrednovanje = new Vrednovanje()
            .sifraPostupka(DEFAULT_SIFRA_POSTUPKA)
            .sifraPonude(DEFAULT_SIFRA_PONUDE)
            .brojPartije(DEFAULT_BROJ_PARTIJE)
            .nazivProizvodjaca(DEFAULT_NAZIV_PROIZVODJACA)
            .zasticeniNaziv(DEFAULT_ZASTICENI_NAZIV)
            .ponudjenaVrijednost(DEFAULT_PONUDJENA_VRIJEDNOST)
            .rokIsporuke(DEFAULT_ROK_ISPORUKE)
            .jedinicnaCijena(DEFAULT_JEDINICNA_CIJENA)
            .nazivPonudjaca(DEFAULT_NAZIV_PONUDJACA)
            .atc(DEFAULT_ATC)
            .trazenaKolicina(DEFAULT_TRAZENA_KOLICINA)
            .procijenjenaVrijednost(DEFAULT_PROCIJENJENA_VRIJEDNOST)
            .vrstaPostupka(DEFAULT_VRSTA_POSTUPKA)
            .bodCijena(DEFAULT_BOD_CIJENA)
            .bodRok(DEFAULT_BOD_ROK)
            .bodUkupno(DEFAULT_BOD_UKUPNO);
        return vrednovanje;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vrednovanje createUpdatedEntity(EntityManager em) {
        Vrednovanje vrednovanje = new Vrednovanje()
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .sifraPonude(UPDATED_SIFRA_PONUDE)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .nazivProizvodjaca(UPDATED_NAZIV_PROIZVODJACA)
            .zasticeniNaziv(UPDATED_ZASTICENI_NAZIV)
            .ponudjenaVrijednost(UPDATED_PONUDJENA_VRIJEDNOST)
            .rokIsporuke(UPDATED_ROK_ISPORUKE)
            .jedinicnaCijena(UPDATED_JEDINICNA_CIJENA)
            .nazivPonudjaca(UPDATED_NAZIV_PONUDJACA)
            .atc(UPDATED_ATC)
            .trazenaKolicina(UPDATED_TRAZENA_KOLICINA)
            .procijenjenaVrijednost(UPDATED_PROCIJENJENA_VRIJEDNOST)
            .vrstaPostupka(UPDATED_VRSTA_POSTUPKA)
            .bodCijena(UPDATED_BOD_CIJENA)
            .bodRok(UPDATED_BOD_ROK)
            .bodUkupno(UPDATED_BOD_UKUPNO);
        return vrednovanje;
    }

    @BeforeEach
    public void initTest() {
        vrednovanje = createEntity(em);
    }

    @Test
    @Transactional
    void getAllVrednovanjes() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList
        restVrednovanjeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vrednovanje.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].sifraPonude").value(hasItem(DEFAULT_SIFRA_PONUDE)))
            .andExpect(jsonPath("$.[*].brojPartije").value(hasItem(DEFAULT_BROJ_PARTIJE)))
            .andExpect(jsonPath("$.[*].nazivProizvodjaca").value(hasItem(DEFAULT_NAZIV_PROIZVODJACA)))
            .andExpect(jsonPath("$.[*].zasticeniNaziv").value(hasItem(DEFAULT_ZASTICENI_NAZIV)))
            .andExpect(jsonPath("$.[*].ponudjenaVrijednost").value(hasItem(DEFAULT_PONUDJENA_VRIJEDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].rokIsporuke").value(hasItem(DEFAULT_ROK_ISPORUKE)))
            .andExpect(jsonPath("$.[*].jedinicnaCijena").value(hasItem(DEFAULT_JEDINICNA_CIJENA.doubleValue())))
            .andExpect(jsonPath("$.[*].nazivPonudjaca").value(hasItem(DEFAULT_NAZIV_PONUDJACA)))
            .andExpect(jsonPath("$.[*].atc").value(hasItem(DEFAULT_ATC)))
            .andExpect(jsonPath("$.[*].trazenaKolicina").value(hasItem(DEFAULT_TRAZENA_KOLICINA)))
            .andExpect(jsonPath("$.[*].procijenjenaVrijednost").value(hasItem(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].vrstaPostupka").value(hasItem(DEFAULT_VRSTA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].bodCijena").value(hasItem(DEFAULT_BOD_CIJENA.doubleValue())))
            .andExpect(jsonPath("$.[*].bodRok").value(hasItem(DEFAULT_BOD_ROK.doubleValue())))
            .andExpect(jsonPath("$.[*].bodUkupno").value(hasItem(DEFAULT_BOD_UKUPNO.doubleValue())));
    }

    @Test
    @Transactional
    void getVrednovanje() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get the vrednovanje
        restVrednovanjeMockMvc
            .perform(get(ENTITY_API_URL_ID, vrednovanje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vrednovanje.getId().intValue()))
            .andExpect(jsonPath("$.sifraPostupka").value(DEFAULT_SIFRA_POSTUPKA))
            .andExpect(jsonPath("$.sifraPonude").value(DEFAULT_SIFRA_PONUDE))
            .andExpect(jsonPath("$.brojPartije").value(DEFAULT_BROJ_PARTIJE))
            .andExpect(jsonPath("$.nazivProizvodjaca").value(DEFAULT_NAZIV_PROIZVODJACA))
            .andExpect(jsonPath("$.zasticeniNaziv").value(DEFAULT_ZASTICENI_NAZIV))
            .andExpect(jsonPath("$.ponudjenaVrijednost").value(DEFAULT_PONUDJENA_VRIJEDNOST.doubleValue()))
            .andExpect(jsonPath("$.rokIsporuke").value(DEFAULT_ROK_ISPORUKE))
            .andExpect(jsonPath("$.jedinicnaCijena").value(DEFAULT_JEDINICNA_CIJENA.doubleValue()))
            .andExpect(jsonPath("$.nazivPonudjaca").value(DEFAULT_NAZIV_PONUDJACA))
            .andExpect(jsonPath("$.atc").value(DEFAULT_ATC))
            .andExpect(jsonPath("$.trazenaKolicina").value(DEFAULT_TRAZENA_KOLICINA))
            .andExpect(jsonPath("$.procijenjenaVrijednost").value(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue()))
            .andExpect(jsonPath("$.vrstaPostupka").value(DEFAULT_VRSTA_POSTUPKA))
            .andExpect(jsonPath("$.bodCijena").value(DEFAULT_BOD_CIJENA.doubleValue()))
            .andExpect(jsonPath("$.bodRok").value(DEFAULT_BOD_ROK.doubleValue()))
            .andExpect(jsonPath("$.bodUkupno").value(DEFAULT_BOD_UKUPNO.doubleValue()));
    }

    @Test
    @Transactional
    void getVrednovanjesByIdFiltering() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        Long id = vrednovanje.getId();

        defaultVrednovanjeShouldBeFound("id.equals=" + id);
        defaultVrednovanjeShouldNotBeFound("id.notEquals=" + id);

        defaultVrednovanjeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVrednovanjeShouldNotBeFound("id.greaterThan=" + id);

        defaultVrednovanjeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVrednovanjeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVrednovanjesBySifraPostupkaIsEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where sifraPostupka equals to DEFAULT_SIFRA_POSTUPKA
        defaultVrednovanjeShouldBeFound("sifraPostupka.equals=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the vrednovanjeList where sifraPostupka equals to UPDATED_SIFRA_POSTUPKA
        defaultVrednovanjeShouldNotBeFound("sifraPostupka.equals=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesBySifraPostupkaIsInShouldWork() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where sifraPostupka in DEFAULT_SIFRA_POSTUPKA or UPDATED_SIFRA_POSTUPKA
        defaultVrednovanjeShouldBeFound("sifraPostupka.in=" + DEFAULT_SIFRA_POSTUPKA + "," + UPDATED_SIFRA_POSTUPKA);

        // Get all the vrednovanjeList where sifraPostupka equals to UPDATED_SIFRA_POSTUPKA
        defaultVrednovanjeShouldNotBeFound("sifraPostupka.in=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesBySifraPostupkaIsNullOrNotNull() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where sifraPostupka is not null
        defaultVrednovanjeShouldBeFound("sifraPostupka.specified=true");

        // Get all the vrednovanjeList where sifraPostupka is null
        defaultVrednovanjeShouldNotBeFound("sifraPostupka.specified=false");
    }

    @Test
    @Transactional
    void getAllVrednovanjesBySifraPostupkaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where sifraPostupka is greater than or equal to DEFAULT_SIFRA_POSTUPKA
        defaultVrednovanjeShouldBeFound("sifraPostupka.greaterThanOrEqual=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the vrednovanjeList where sifraPostupka is greater than or equal to UPDATED_SIFRA_POSTUPKA
        defaultVrednovanjeShouldNotBeFound("sifraPostupka.greaterThanOrEqual=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesBySifraPostupkaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where sifraPostupka is less than or equal to DEFAULT_SIFRA_POSTUPKA
        defaultVrednovanjeShouldBeFound("sifraPostupka.lessThanOrEqual=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the vrednovanjeList where sifraPostupka is less than or equal to SMALLER_SIFRA_POSTUPKA
        defaultVrednovanjeShouldNotBeFound("sifraPostupka.lessThanOrEqual=" + SMALLER_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesBySifraPostupkaIsLessThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where sifraPostupka is less than DEFAULT_SIFRA_POSTUPKA
        defaultVrednovanjeShouldNotBeFound("sifraPostupka.lessThan=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the vrednovanjeList where sifraPostupka is less than UPDATED_SIFRA_POSTUPKA
        defaultVrednovanjeShouldBeFound("sifraPostupka.lessThan=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesBySifraPostupkaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where sifraPostupka is greater than DEFAULT_SIFRA_POSTUPKA
        defaultVrednovanjeShouldNotBeFound("sifraPostupka.greaterThan=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the vrednovanjeList where sifraPostupka is greater than SMALLER_SIFRA_POSTUPKA
        defaultVrednovanjeShouldBeFound("sifraPostupka.greaterThan=" + SMALLER_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesBySifraPonudeIsEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where sifraPonude equals to DEFAULT_SIFRA_PONUDE
        defaultVrednovanjeShouldBeFound("sifraPonude.equals=" + DEFAULT_SIFRA_PONUDE);

        // Get all the vrednovanjeList where sifraPonude equals to UPDATED_SIFRA_PONUDE
        defaultVrednovanjeShouldNotBeFound("sifraPonude.equals=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllVrednovanjesBySifraPonudeIsInShouldWork() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where sifraPonude in DEFAULT_SIFRA_PONUDE or UPDATED_SIFRA_PONUDE
        defaultVrednovanjeShouldBeFound("sifraPonude.in=" + DEFAULT_SIFRA_PONUDE + "," + UPDATED_SIFRA_PONUDE);

        // Get all the vrednovanjeList where sifraPonude equals to UPDATED_SIFRA_PONUDE
        defaultVrednovanjeShouldNotBeFound("sifraPonude.in=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllVrednovanjesBySifraPonudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where sifraPonude is not null
        defaultVrednovanjeShouldBeFound("sifraPonude.specified=true");

        // Get all the vrednovanjeList where sifraPonude is null
        defaultVrednovanjeShouldNotBeFound("sifraPonude.specified=false");
    }

    @Test
    @Transactional
    void getAllVrednovanjesBySifraPonudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where sifraPonude is greater than or equal to DEFAULT_SIFRA_PONUDE
        defaultVrednovanjeShouldBeFound("sifraPonude.greaterThanOrEqual=" + DEFAULT_SIFRA_PONUDE);

        // Get all the vrednovanjeList where sifraPonude is greater than or equal to UPDATED_SIFRA_PONUDE
        defaultVrednovanjeShouldNotBeFound("sifraPonude.greaterThanOrEqual=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllVrednovanjesBySifraPonudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where sifraPonude is less than or equal to DEFAULT_SIFRA_PONUDE
        defaultVrednovanjeShouldBeFound("sifraPonude.lessThanOrEqual=" + DEFAULT_SIFRA_PONUDE);

        // Get all the vrednovanjeList where sifraPonude is less than or equal to SMALLER_SIFRA_PONUDE
        defaultVrednovanjeShouldNotBeFound("sifraPonude.lessThanOrEqual=" + SMALLER_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllVrednovanjesBySifraPonudeIsLessThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where sifraPonude is less than DEFAULT_SIFRA_PONUDE
        defaultVrednovanjeShouldNotBeFound("sifraPonude.lessThan=" + DEFAULT_SIFRA_PONUDE);

        // Get all the vrednovanjeList where sifraPonude is less than UPDATED_SIFRA_PONUDE
        defaultVrednovanjeShouldBeFound("sifraPonude.lessThan=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllVrednovanjesBySifraPonudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where sifraPonude is greater than DEFAULT_SIFRA_PONUDE
        defaultVrednovanjeShouldNotBeFound("sifraPonude.greaterThan=" + DEFAULT_SIFRA_PONUDE);

        // Get all the vrednovanjeList where sifraPonude is greater than SMALLER_SIFRA_PONUDE
        defaultVrednovanjeShouldBeFound("sifraPonude.greaterThan=" + SMALLER_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBrojPartijeIsEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where brojPartije equals to DEFAULT_BROJ_PARTIJE
        defaultVrednovanjeShouldBeFound("brojPartije.equals=" + DEFAULT_BROJ_PARTIJE);

        // Get all the vrednovanjeList where brojPartije equals to UPDATED_BROJ_PARTIJE
        defaultVrednovanjeShouldNotBeFound("brojPartije.equals=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBrojPartijeIsInShouldWork() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where brojPartije in DEFAULT_BROJ_PARTIJE or UPDATED_BROJ_PARTIJE
        defaultVrednovanjeShouldBeFound("brojPartije.in=" + DEFAULT_BROJ_PARTIJE + "," + UPDATED_BROJ_PARTIJE);

        // Get all the vrednovanjeList where brojPartije equals to UPDATED_BROJ_PARTIJE
        defaultVrednovanjeShouldNotBeFound("brojPartije.in=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBrojPartijeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where brojPartije is not null
        defaultVrednovanjeShouldBeFound("brojPartije.specified=true");

        // Get all the vrednovanjeList where brojPartije is null
        defaultVrednovanjeShouldNotBeFound("brojPartije.specified=false");
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBrojPartijeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where brojPartije is greater than or equal to DEFAULT_BROJ_PARTIJE
        defaultVrednovanjeShouldBeFound("brojPartije.greaterThanOrEqual=" + DEFAULT_BROJ_PARTIJE);

        // Get all the vrednovanjeList where brojPartije is greater than or equal to UPDATED_BROJ_PARTIJE
        defaultVrednovanjeShouldNotBeFound("brojPartije.greaterThanOrEqual=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBrojPartijeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where brojPartije is less than or equal to DEFAULT_BROJ_PARTIJE
        defaultVrednovanjeShouldBeFound("brojPartije.lessThanOrEqual=" + DEFAULT_BROJ_PARTIJE);

        // Get all the vrednovanjeList where brojPartije is less than or equal to SMALLER_BROJ_PARTIJE
        defaultVrednovanjeShouldNotBeFound("brojPartije.lessThanOrEqual=" + SMALLER_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBrojPartijeIsLessThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where brojPartije is less than DEFAULT_BROJ_PARTIJE
        defaultVrednovanjeShouldNotBeFound("brojPartije.lessThan=" + DEFAULT_BROJ_PARTIJE);

        // Get all the vrednovanjeList where brojPartije is less than UPDATED_BROJ_PARTIJE
        defaultVrednovanjeShouldBeFound("brojPartije.lessThan=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBrojPartijeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where brojPartije is greater than DEFAULT_BROJ_PARTIJE
        defaultVrednovanjeShouldNotBeFound("brojPartije.greaterThan=" + DEFAULT_BROJ_PARTIJE);

        // Get all the vrednovanjeList where brojPartije is greater than SMALLER_BROJ_PARTIJE
        defaultVrednovanjeShouldBeFound("brojPartije.greaterThan=" + SMALLER_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByNazivProizvodjacaIsEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where nazivProizvodjaca equals to DEFAULT_NAZIV_PROIZVODJACA
        defaultVrednovanjeShouldBeFound("nazivProizvodjaca.equals=" + DEFAULT_NAZIV_PROIZVODJACA);

        // Get all the vrednovanjeList where nazivProizvodjaca equals to UPDATED_NAZIV_PROIZVODJACA
        defaultVrednovanjeShouldNotBeFound("nazivProizvodjaca.equals=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByNazivProizvodjacaIsInShouldWork() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where nazivProizvodjaca in DEFAULT_NAZIV_PROIZVODJACA or UPDATED_NAZIV_PROIZVODJACA
        defaultVrednovanjeShouldBeFound("nazivProizvodjaca.in=" + DEFAULT_NAZIV_PROIZVODJACA + "," + UPDATED_NAZIV_PROIZVODJACA);

        // Get all the vrednovanjeList where nazivProizvodjaca equals to UPDATED_NAZIV_PROIZVODJACA
        defaultVrednovanjeShouldNotBeFound("nazivProizvodjaca.in=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByNazivProizvodjacaIsNullOrNotNull() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where nazivProizvodjaca is not null
        defaultVrednovanjeShouldBeFound("nazivProizvodjaca.specified=true");

        // Get all the vrednovanjeList where nazivProizvodjaca is null
        defaultVrednovanjeShouldNotBeFound("nazivProizvodjaca.specified=false");
    }

    @Test
    @Transactional
    void getAllVrednovanjesByNazivProizvodjacaContainsSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where nazivProizvodjaca contains DEFAULT_NAZIV_PROIZVODJACA
        defaultVrednovanjeShouldBeFound("nazivProizvodjaca.contains=" + DEFAULT_NAZIV_PROIZVODJACA);

        // Get all the vrednovanjeList where nazivProizvodjaca contains UPDATED_NAZIV_PROIZVODJACA
        defaultVrednovanjeShouldNotBeFound("nazivProizvodjaca.contains=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByNazivProizvodjacaNotContainsSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where nazivProizvodjaca does not contain DEFAULT_NAZIV_PROIZVODJACA
        defaultVrednovanjeShouldNotBeFound("nazivProizvodjaca.doesNotContain=" + DEFAULT_NAZIV_PROIZVODJACA);

        // Get all the vrednovanjeList where nazivProizvodjaca does not contain UPDATED_NAZIV_PROIZVODJACA
        defaultVrednovanjeShouldBeFound("nazivProizvodjaca.doesNotContain=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByZasticeniNazivIsEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where zasticeniNaziv equals to DEFAULT_ZASTICENI_NAZIV
        defaultVrednovanjeShouldBeFound("zasticeniNaziv.equals=" + DEFAULT_ZASTICENI_NAZIV);

        // Get all the vrednovanjeList where zasticeniNaziv equals to UPDATED_ZASTICENI_NAZIV
        defaultVrednovanjeShouldNotBeFound("zasticeniNaziv.equals=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByZasticeniNazivIsInShouldWork() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where zasticeniNaziv in DEFAULT_ZASTICENI_NAZIV or UPDATED_ZASTICENI_NAZIV
        defaultVrednovanjeShouldBeFound("zasticeniNaziv.in=" + DEFAULT_ZASTICENI_NAZIV + "," + UPDATED_ZASTICENI_NAZIV);

        // Get all the vrednovanjeList where zasticeniNaziv equals to UPDATED_ZASTICENI_NAZIV
        defaultVrednovanjeShouldNotBeFound("zasticeniNaziv.in=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByZasticeniNazivIsNullOrNotNull() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where zasticeniNaziv is not null
        defaultVrednovanjeShouldBeFound("zasticeniNaziv.specified=true");

        // Get all the vrednovanjeList where zasticeniNaziv is null
        defaultVrednovanjeShouldNotBeFound("zasticeniNaziv.specified=false");
    }

    @Test
    @Transactional
    void getAllVrednovanjesByZasticeniNazivContainsSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where zasticeniNaziv contains DEFAULT_ZASTICENI_NAZIV
        defaultVrednovanjeShouldBeFound("zasticeniNaziv.contains=" + DEFAULT_ZASTICENI_NAZIV);

        // Get all the vrednovanjeList where zasticeniNaziv contains UPDATED_ZASTICENI_NAZIV
        defaultVrednovanjeShouldNotBeFound("zasticeniNaziv.contains=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByZasticeniNazivNotContainsSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where zasticeniNaziv does not contain DEFAULT_ZASTICENI_NAZIV
        defaultVrednovanjeShouldNotBeFound("zasticeniNaziv.doesNotContain=" + DEFAULT_ZASTICENI_NAZIV);

        // Get all the vrednovanjeList where zasticeniNaziv does not contain UPDATED_ZASTICENI_NAZIV
        defaultVrednovanjeShouldBeFound("zasticeniNaziv.doesNotContain=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByPonudjenaVrijednostIsEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where ponudjenaVrijednost equals to DEFAULT_PONUDJENA_VRIJEDNOST
        defaultVrednovanjeShouldBeFound("ponudjenaVrijednost.equals=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the vrednovanjeList where ponudjenaVrijednost equals to UPDATED_PONUDJENA_VRIJEDNOST
        defaultVrednovanjeShouldNotBeFound("ponudjenaVrijednost.equals=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByPonudjenaVrijednostIsInShouldWork() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where ponudjenaVrijednost in DEFAULT_PONUDJENA_VRIJEDNOST or UPDATED_PONUDJENA_VRIJEDNOST
        defaultVrednovanjeShouldBeFound("ponudjenaVrijednost.in=" + DEFAULT_PONUDJENA_VRIJEDNOST + "," + UPDATED_PONUDJENA_VRIJEDNOST);

        // Get all the vrednovanjeList where ponudjenaVrijednost equals to UPDATED_PONUDJENA_VRIJEDNOST
        defaultVrednovanjeShouldNotBeFound("ponudjenaVrijednost.in=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByPonudjenaVrijednostIsNullOrNotNull() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where ponudjenaVrijednost is not null
        defaultVrednovanjeShouldBeFound("ponudjenaVrijednost.specified=true");

        // Get all the vrednovanjeList where ponudjenaVrijednost is null
        defaultVrednovanjeShouldNotBeFound("ponudjenaVrijednost.specified=false");
    }

    @Test
    @Transactional
    void getAllVrednovanjesByPonudjenaVrijednostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where ponudjenaVrijednost is greater than or equal to DEFAULT_PONUDJENA_VRIJEDNOST
        defaultVrednovanjeShouldBeFound("ponudjenaVrijednost.greaterThanOrEqual=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the vrednovanjeList where ponudjenaVrijednost is greater than or equal to UPDATED_PONUDJENA_VRIJEDNOST
        defaultVrednovanjeShouldNotBeFound("ponudjenaVrijednost.greaterThanOrEqual=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByPonudjenaVrijednostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where ponudjenaVrijednost is less than or equal to DEFAULT_PONUDJENA_VRIJEDNOST
        defaultVrednovanjeShouldBeFound("ponudjenaVrijednost.lessThanOrEqual=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the vrednovanjeList where ponudjenaVrijednost is less than or equal to SMALLER_PONUDJENA_VRIJEDNOST
        defaultVrednovanjeShouldNotBeFound("ponudjenaVrijednost.lessThanOrEqual=" + SMALLER_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByPonudjenaVrijednostIsLessThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where ponudjenaVrijednost is less than DEFAULT_PONUDJENA_VRIJEDNOST
        defaultVrednovanjeShouldNotBeFound("ponudjenaVrijednost.lessThan=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the vrednovanjeList where ponudjenaVrijednost is less than UPDATED_PONUDJENA_VRIJEDNOST
        defaultVrednovanjeShouldBeFound("ponudjenaVrijednost.lessThan=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByPonudjenaVrijednostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where ponudjenaVrijednost is greater than DEFAULT_PONUDJENA_VRIJEDNOST
        defaultVrednovanjeShouldNotBeFound("ponudjenaVrijednost.greaterThan=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the vrednovanjeList where ponudjenaVrijednost is greater than SMALLER_PONUDJENA_VRIJEDNOST
        defaultVrednovanjeShouldBeFound("ponudjenaVrijednost.greaterThan=" + SMALLER_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByRokIsporukeIsEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where rokIsporuke equals to DEFAULT_ROK_ISPORUKE
        defaultVrednovanjeShouldBeFound("rokIsporuke.equals=" + DEFAULT_ROK_ISPORUKE);

        // Get all the vrednovanjeList where rokIsporuke equals to UPDATED_ROK_ISPORUKE
        defaultVrednovanjeShouldNotBeFound("rokIsporuke.equals=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByRokIsporukeIsInShouldWork() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where rokIsporuke in DEFAULT_ROK_ISPORUKE or UPDATED_ROK_ISPORUKE
        defaultVrednovanjeShouldBeFound("rokIsporuke.in=" + DEFAULT_ROK_ISPORUKE + "," + UPDATED_ROK_ISPORUKE);

        // Get all the vrednovanjeList where rokIsporuke equals to UPDATED_ROK_ISPORUKE
        defaultVrednovanjeShouldNotBeFound("rokIsporuke.in=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByRokIsporukeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where rokIsporuke is not null
        defaultVrednovanjeShouldBeFound("rokIsporuke.specified=true");

        // Get all the vrednovanjeList where rokIsporuke is null
        defaultVrednovanjeShouldNotBeFound("rokIsporuke.specified=false");
    }

    @Test
    @Transactional
    void getAllVrednovanjesByRokIsporukeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where rokIsporuke is greater than or equal to DEFAULT_ROK_ISPORUKE
        defaultVrednovanjeShouldBeFound("rokIsporuke.greaterThanOrEqual=" + DEFAULT_ROK_ISPORUKE);

        // Get all the vrednovanjeList where rokIsporuke is greater than or equal to UPDATED_ROK_ISPORUKE
        defaultVrednovanjeShouldNotBeFound("rokIsporuke.greaterThanOrEqual=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByRokIsporukeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where rokIsporuke is less than or equal to DEFAULT_ROK_ISPORUKE
        defaultVrednovanjeShouldBeFound("rokIsporuke.lessThanOrEqual=" + DEFAULT_ROK_ISPORUKE);

        // Get all the vrednovanjeList where rokIsporuke is less than or equal to SMALLER_ROK_ISPORUKE
        defaultVrednovanjeShouldNotBeFound("rokIsporuke.lessThanOrEqual=" + SMALLER_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByRokIsporukeIsLessThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where rokIsporuke is less than DEFAULT_ROK_ISPORUKE
        defaultVrednovanjeShouldNotBeFound("rokIsporuke.lessThan=" + DEFAULT_ROK_ISPORUKE);

        // Get all the vrednovanjeList where rokIsporuke is less than UPDATED_ROK_ISPORUKE
        defaultVrednovanjeShouldBeFound("rokIsporuke.lessThan=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByRokIsporukeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where rokIsporuke is greater than DEFAULT_ROK_ISPORUKE
        defaultVrednovanjeShouldNotBeFound("rokIsporuke.greaterThan=" + DEFAULT_ROK_ISPORUKE);

        // Get all the vrednovanjeList where rokIsporuke is greater than SMALLER_ROK_ISPORUKE
        defaultVrednovanjeShouldBeFound("rokIsporuke.greaterThan=" + SMALLER_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByJedinicnaCijenaIsEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where jedinicnaCijena equals to DEFAULT_JEDINICNA_CIJENA
        defaultVrednovanjeShouldBeFound("jedinicnaCijena.equals=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the vrednovanjeList where jedinicnaCijena equals to UPDATED_JEDINICNA_CIJENA
        defaultVrednovanjeShouldNotBeFound("jedinicnaCijena.equals=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByJedinicnaCijenaIsInShouldWork() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where jedinicnaCijena in DEFAULT_JEDINICNA_CIJENA or UPDATED_JEDINICNA_CIJENA
        defaultVrednovanjeShouldBeFound("jedinicnaCijena.in=" + DEFAULT_JEDINICNA_CIJENA + "," + UPDATED_JEDINICNA_CIJENA);

        // Get all the vrednovanjeList where jedinicnaCijena equals to UPDATED_JEDINICNA_CIJENA
        defaultVrednovanjeShouldNotBeFound("jedinicnaCijena.in=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByJedinicnaCijenaIsNullOrNotNull() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where jedinicnaCijena is not null
        defaultVrednovanjeShouldBeFound("jedinicnaCijena.specified=true");

        // Get all the vrednovanjeList where jedinicnaCijena is null
        defaultVrednovanjeShouldNotBeFound("jedinicnaCijena.specified=false");
    }

    @Test
    @Transactional
    void getAllVrednovanjesByJedinicnaCijenaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where jedinicnaCijena is greater than or equal to DEFAULT_JEDINICNA_CIJENA
        defaultVrednovanjeShouldBeFound("jedinicnaCijena.greaterThanOrEqual=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the vrednovanjeList where jedinicnaCijena is greater than or equal to UPDATED_JEDINICNA_CIJENA
        defaultVrednovanjeShouldNotBeFound("jedinicnaCijena.greaterThanOrEqual=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByJedinicnaCijenaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where jedinicnaCijena is less than or equal to DEFAULT_JEDINICNA_CIJENA
        defaultVrednovanjeShouldBeFound("jedinicnaCijena.lessThanOrEqual=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the vrednovanjeList where jedinicnaCijena is less than or equal to SMALLER_JEDINICNA_CIJENA
        defaultVrednovanjeShouldNotBeFound("jedinicnaCijena.lessThanOrEqual=" + SMALLER_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByJedinicnaCijenaIsLessThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where jedinicnaCijena is less than DEFAULT_JEDINICNA_CIJENA
        defaultVrednovanjeShouldNotBeFound("jedinicnaCijena.lessThan=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the vrednovanjeList where jedinicnaCijena is less than UPDATED_JEDINICNA_CIJENA
        defaultVrednovanjeShouldBeFound("jedinicnaCijena.lessThan=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByJedinicnaCijenaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where jedinicnaCijena is greater than DEFAULT_JEDINICNA_CIJENA
        defaultVrednovanjeShouldNotBeFound("jedinicnaCijena.greaterThan=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the vrednovanjeList where jedinicnaCijena is greater than SMALLER_JEDINICNA_CIJENA
        defaultVrednovanjeShouldBeFound("jedinicnaCijena.greaterThan=" + SMALLER_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByNazivPonudjacaIsEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where nazivPonudjaca equals to DEFAULT_NAZIV_PONUDJACA
        defaultVrednovanjeShouldBeFound("nazivPonudjaca.equals=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the vrednovanjeList where nazivPonudjaca equals to UPDATED_NAZIV_PONUDJACA
        defaultVrednovanjeShouldNotBeFound("nazivPonudjaca.equals=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByNazivPonudjacaIsInShouldWork() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where nazivPonudjaca in DEFAULT_NAZIV_PONUDJACA or UPDATED_NAZIV_PONUDJACA
        defaultVrednovanjeShouldBeFound("nazivPonudjaca.in=" + DEFAULT_NAZIV_PONUDJACA + "," + UPDATED_NAZIV_PONUDJACA);

        // Get all the vrednovanjeList where nazivPonudjaca equals to UPDATED_NAZIV_PONUDJACA
        defaultVrednovanjeShouldNotBeFound("nazivPonudjaca.in=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByNazivPonudjacaIsNullOrNotNull() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where nazivPonudjaca is not null
        defaultVrednovanjeShouldBeFound("nazivPonudjaca.specified=true");

        // Get all the vrednovanjeList where nazivPonudjaca is null
        defaultVrednovanjeShouldNotBeFound("nazivPonudjaca.specified=false");
    }

    @Test
    @Transactional
    void getAllVrednovanjesByNazivPonudjacaContainsSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where nazivPonudjaca contains DEFAULT_NAZIV_PONUDJACA
        defaultVrednovanjeShouldBeFound("nazivPonudjaca.contains=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the vrednovanjeList where nazivPonudjaca contains UPDATED_NAZIV_PONUDJACA
        defaultVrednovanjeShouldNotBeFound("nazivPonudjaca.contains=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByNazivPonudjacaNotContainsSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where nazivPonudjaca does not contain DEFAULT_NAZIV_PONUDJACA
        defaultVrednovanjeShouldNotBeFound("nazivPonudjaca.doesNotContain=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the vrednovanjeList where nazivPonudjaca does not contain UPDATED_NAZIV_PONUDJACA
        defaultVrednovanjeShouldBeFound("nazivPonudjaca.doesNotContain=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByAtcIsEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where atc equals to DEFAULT_ATC
        defaultVrednovanjeShouldBeFound("atc.equals=" + DEFAULT_ATC);

        // Get all the vrednovanjeList where atc equals to UPDATED_ATC
        defaultVrednovanjeShouldNotBeFound("atc.equals=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByAtcIsInShouldWork() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where atc in DEFAULT_ATC or UPDATED_ATC
        defaultVrednovanjeShouldBeFound("atc.in=" + DEFAULT_ATC + "," + UPDATED_ATC);

        // Get all the vrednovanjeList where atc equals to UPDATED_ATC
        defaultVrednovanjeShouldNotBeFound("atc.in=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByAtcIsNullOrNotNull() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where atc is not null
        defaultVrednovanjeShouldBeFound("atc.specified=true");

        // Get all the vrednovanjeList where atc is null
        defaultVrednovanjeShouldNotBeFound("atc.specified=false");
    }

    @Test
    @Transactional
    void getAllVrednovanjesByAtcContainsSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where atc contains DEFAULT_ATC
        defaultVrednovanjeShouldBeFound("atc.contains=" + DEFAULT_ATC);

        // Get all the vrednovanjeList where atc contains UPDATED_ATC
        defaultVrednovanjeShouldNotBeFound("atc.contains=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByAtcNotContainsSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where atc does not contain DEFAULT_ATC
        defaultVrednovanjeShouldNotBeFound("atc.doesNotContain=" + DEFAULT_ATC);

        // Get all the vrednovanjeList where atc does not contain UPDATED_ATC
        defaultVrednovanjeShouldBeFound("atc.doesNotContain=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByTrazenaKolicinaIsEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where trazenaKolicina equals to DEFAULT_TRAZENA_KOLICINA
        defaultVrednovanjeShouldBeFound("trazenaKolicina.equals=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the vrednovanjeList where trazenaKolicina equals to UPDATED_TRAZENA_KOLICINA
        defaultVrednovanjeShouldNotBeFound("trazenaKolicina.equals=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByTrazenaKolicinaIsInShouldWork() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where trazenaKolicina in DEFAULT_TRAZENA_KOLICINA or UPDATED_TRAZENA_KOLICINA
        defaultVrednovanjeShouldBeFound("trazenaKolicina.in=" + DEFAULT_TRAZENA_KOLICINA + "," + UPDATED_TRAZENA_KOLICINA);

        // Get all the vrednovanjeList where trazenaKolicina equals to UPDATED_TRAZENA_KOLICINA
        defaultVrednovanjeShouldNotBeFound("trazenaKolicina.in=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByTrazenaKolicinaIsNullOrNotNull() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where trazenaKolicina is not null
        defaultVrednovanjeShouldBeFound("trazenaKolicina.specified=true");

        // Get all the vrednovanjeList where trazenaKolicina is null
        defaultVrednovanjeShouldNotBeFound("trazenaKolicina.specified=false");
    }

    @Test
    @Transactional
    void getAllVrednovanjesByTrazenaKolicinaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where trazenaKolicina is greater than or equal to DEFAULT_TRAZENA_KOLICINA
        defaultVrednovanjeShouldBeFound("trazenaKolicina.greaterThanOrEqual=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the vrednovanjeList where trazenaKolicina is greater than or equal to UPDATED_TRAZENA_KOLICINA
        defaultVrednovanjeShouldNotBeFound("trazenaKolicina.greaterThanOrEqual=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByTrazenaKolicinaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where trazenaKolicina is less than or equal to DEFAULT_TRAZENA_KOLICINA
        defaultVrednovanjeShouldBeFound("trazenaKolicina.lessThanOrEqual=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the vrednovanjeList where trazenaKolicina is less than or equal to SMALLER_TRAZENA_KOLICINA
        defaultVrednovanjeShouldNotBeFound("trazenaKolicina.lessThanOrEqual=" + SMALLER_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByTrazenaKolicinaIsLessThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where trazenaKolicina is less than DEFAULT_TRAZENA_KOLICINA
        defaultVrednovanjeShouldNotBeFound("trazenaKolicina.lessThan=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the vrednovanjeList where trazenaKolicina is less than UPDATED_TRAZENA_KOLICINA
        defaultVrednovanjeShouldBeFound("trazenaKolicina.lessThan=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByTrazenaKolicinaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where trazenaKolicina is greater than DEFAULT_TRAZENA_KOLICINA
        defaultVrednovanjeShouldNotBeFound("trazenaKolicina.greaterThan=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the vrednovanjeList where trazenaKolicina is greater than SMALLER_TRAZENA_KOLICINA
        defaultVrednovanjeShouldBeFound("trazenaKolicina.greaterThan=" + SMALLER_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByProcijenjenaVrijednostIsEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where procijenjenaVrijednost equals to DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultVrednovanjeShouldBeFound("procijenjenaVrijednost.equals=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the vrednovanjeList where procijenjenaVrijednost equals to UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultVrednovanjeShouldNotBeFound("procijenjenaVrijednost.equals=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByProcijenjenaVrijednostIsInShouldWork() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where procijenjenaVrijednost in DEFAULT_PROCIJENJENA_VRIJEDNOST or UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultVrednovanjeShouldBeFound(
            "procijenjenaVrijednost.in=" + DEFAULT_PROCIJENJENA_VRIJEDNOST + "," + UPDATED_PROCIJENJENA_VRIJEDNOST
        );

        // Get all the vrednovanjeList where procijenjenaVrijednost equals to UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultVrednovanjeShouldNotBeFound("procijenjenaVrijednost.in=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByProcijenjenaVrijednostIsNullOrNotNull() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where procijenjenaVrijednost is not null
        defaultVrednovanjeShouldBeFound("procijenjenaVrijednost.specified=true");

        // Get all the vrednovanjeList where procijenjenaVrijednost is null
        defaultVrednovanjeShouldNotBeFound("procijenjenaVrijednost.specified=false");
    }

    @Test
    @Transactional
    void getAllVrednovanjesByProcijenjenaVrijednostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where procijenjenaVrijednost is greater than or equal to DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultVrednovanjeShouldBeFound("procijenjenaVrijednost.greaterThanOrEqual=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the vrednovanjeList where procijenjenaVrijednost is greater than or equal to UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultVrednovanjeShouldNotBeFound("procijenjenaVrijednost.greaterThanOrEqual=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByProcijenjenaVrijednostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where procijenjenaVrijednost is less than or equal to DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultVrednovanjeShouldBeFound("procijenjenaVrijednost.lessThanOrEqual=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the vrednovanjeList where procijenjenaVrijednost is less than or equal to SMALLER_PROCIJENJENA_VRIJEDNOST
        defaultVrednovanjeShouldNotBeFound("procijenjenaVrijednost.lessThanOrEqual=" + SMALLER_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByProcijenjenaVrijednostIsLessThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where procijenjenaVrijednost is less than DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultVrednovanjeShouldNotBeFound("procijenjenaVrijednost.lessThan=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the vrednovanjeList where procijenjenaVrijednost is less than UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultVrednovanjeShouldBeFound("procijenjenaVrijednost.lessThan=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByProcijenjenaVrijednostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where procijenjenaVrijednost is greater than DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultVrednovanjeShouldNotBeFound("procijenjenaVrijednost.greaterThan=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the vrednovanjeList where procijenjenaVrijednost is greater than SMALLER_PROCIJENJENA_VRIJEDNOST
        defaultVrednovanjeShouldBeFound("procijenjenaVrijednost.greaterThan=" + SMALLER_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByVrstaPostupkaIsEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where vrstaPostupka equals to DEFAULT_VRSTA_POSTUPKA
        defaultVrednovanjeShouldBeFound("vrstaPostupka.equals=" + DEFAULT_VRSTA_POSTUPKA);

        // Get all the vrednovanjeList where vrstaPostupka equals to UPDATED_VRSTA_POSTUPKA
        defaultVrednovanjeShouldNotBeFound("vrstaPostupka.equals=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByVrstaPostupkaIsInShouldWork() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where vrstaPostupka in DEFAULT_VRSTA_POSTUPKA or UPDATED_VRSTA_POSTUPKA
        defaultVrednovanjeShouldBeFound("vrstaPostupka.in=" + DEFAULT_VRSTA_POSTUPKA + "," + UPDATED_VRSTA_POSTUPKA);

        // Get all the vrednovanjeList where vrstaPostupka equals to UPDATED_VRSTA_POSTUPKA
        defaultVrednovanjeShouldNotBeFound("vrstaPostupka.in=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByVrstaPostupkaIsNullOrNotNull() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where vrstaPostupka is not null
        defaultVrednovanjeShouldBeFound("vrstaPostupka.specified=true");

        // Get all the vrednovanjeList where vrstaPostupka is null
        defaultVrednovanjeShouldNotBeFound("vrstaPostupka.specified=false");
    }

    @Test
    @Transactional
    void getAllVrednovanjesByVrstaPostupkaContainsSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where vrstaPostupka contains DEFAULT_VRSTA_POSTUPKA
        defaultVrednovanjeShouldBeFound("vrstaPostupka.contains=" + DEFAULT_VRSTA_POSTUPKA);

        // Get all the vrednovanjeList where vrstaPostupka contains UPDATED_VRSTA_POSTUPKA
        defaultVrednovanjeShouldNotBeFound("vrstaPostupka.contains=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByVrstaPostupkaNotContainsSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where vrstaPostupka does not contain DEFAULT_VRSTA_POSTUPKA
        defaultVrednovanjeShouldNotBeFound("vrstaPostupka.doesNotContain=" + DEFAULT_VRSTA_POSTUPKA);

        // Get all the vrednovanjeList where vrstaPostupka does not contain UPDATED_VRSTA_POSTUPKA
        defaultVrednovanjeShouldBeFound("vrstaPostupka.doesNotContain=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodCijenaIsEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodCijena equals to DEFAULT_BOD_CIJENA
        defaultVrednovanjeShouldBeFound("bodCijena.equals=" + DEFAULT_BOD_CIJENA);

        // Get all the vrednovanjeList where bodCijena equals to UPDATED_BOD_CIJENA
        defaultVrednovanjeShouldNotBeFound("bodCijena.equals=" + UPDATED_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodCijenaIsInShouldWork() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodCijena in DEFAULT_BOD_CIJENA or UPDATED_BOD_CIJENA
        defaultVrednovanjeShouldBeFound("bodCijena.in=" + DEFAULT_BOD_CIJENA + "," + UPDATED_BOD_CIJENA);

        // Get all the vrednovanjeList where bodCijena equals to UPDATED_BOD_CIJENA
        defaultVrednovanjeShouldNotBeFound("bodCijena.in=" + UPDATED_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodCijenaIsNullOrNotNull() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodCijena is not null
        defaultVrednovanjeShouldBeFound("bodCijena.specified=true");

        // Get all the vrednovanjeList where bodCijena is null
        defaultVrednovanjeShouldNotBeFound("bodCijena.specified=false");
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodCijenaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodCijena is greater than or equal to DEFAULT_BOD_CIJENA
        defaultVrednovanjeShouldBeFound("bodCijena.greaterThanOrEqual=" + DEFAULT_BOD_CIJENA);

        // Get all the vrednovanjeList where bodCijena is greater than or equal to UPDATED_BOD_CIJENA
        defaultVrednovanjeShouldNotBeFound("bodCijena.greaterThanOrEqual=" + UPDATED_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodCijenaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodCijena is less than or equal to DEFAULT_BOD_CIJENA
        defaultVrednovanjeShouldBeFound("bodCijena.lessThanOrEqual=" + DEFAULT_BOD_CIJENA);

        // Get all the vrednovanjeList where bodCijena is less than or equal to SMALLER_BOD_CIJENA
        defaultVrednovanjeShouldNotBeFound("bodCijena.lessThanOrEqual=" + SMALLER_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodCijenaIsLessThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodCijena is less than DEFAULT_BOD_CIJENA
        defaultVrednovanjeShouldNotBeFound("bodCijena.lessThan=" + DEFAULT_BOD_CIJENA);

        // Get all the vrednovanjeList where bodCijena is less than UPDATED_BOD_CIJENA
        defaultVrednovanjeShouldBeFound("bodCijena.lessThan=" + UPDATED_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodCijenaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodCijena is greater than DEFAULT_BOD_CIJENA
        defaultVrednovanjeShouldNotBeFound("bodCijena.greaterThan=" + DEFAULT_BOD_CIJENA);

        // Get all the vrednovanjeList where bodCijena is greater than SMALLER_BOD_CIJENA
        defaultVrednovanjeShouldBeFound("bodCijena.greaterThan=" + SMALLER_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodRokIsEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodRok equals to DEFAULT_BOD_ROK
        defaultVrednovanjeShouldBeFound("bodRok.equals=" + DEFAULT_BOD_ROK);

        // Get all the vrednovanjeList where bodRok equals to UPDATED_BOD_ROK
        defaultVrednovanjeShouldNotBeFound("bodRok.equals=" + UPDATED_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodRokIsInShouldWork() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodRok in DEFAULT_BOD_ROK or UPDATED_BOD_ROK
        defaultVrednovanjeShouldBeFound("bodRok.in=" + DEFAULT_BOD_ROK + "," + UPDATED_BOD_ROK);

        // Get all the vrednovanjeList where bodRok equals to UPDATED_BOD_ROK
        defaultVrednovanjeShouldNotBeFound("bodRok.in=" + UPDATED_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodRokIsNullOrNotNull() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodRok is not null
        defaultVrednovanjeShouldBeFound("bodRok.specified=true");

        // Get all the vrednovanjeList where bodRok is null
        defaultVrednovanjeShouldNotBeFound("bodRok.specified=false");
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodRokIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodRok is greater than or equal to DEFAULT_BOD_ROK
        defaultVrednovanjeShouldBeFound("bodRok.greaterThanOrEqual=" + DEFAULT_BOD_ROK);

        // Get all the vrednovanjeList where bodRok is greater than or equal to UPDATED_BOD_ROK
        defaultVrednovanjeShouldNotBeFound("bodRok.greaterThanOrEqual=" + UPDATED_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodRokIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodRok is less than or equal to DEFAULT_BOD_ROK
        defaultVrednovanjeShouldBeFound("bodRok.lessThanOrEqual=" + DEFAULT_BOD_ROK);

        // Get all the vrednovanjeList where bodRok is less than or equal to SMALLER_BOD_ROK
        defaultVrednovanjeShouldNotBeFound("bodRok.lessThanOrEqual=" + SMALLER_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodRokIsLessThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodRok is less than DEFAULT_BOD_ROK
        defaultVrednovanjeShouldNotBeFound("bodRok.lessThan=" + DEFAULT_BOD_ROK);

        // Get all the vrednovanjeList where bodRok is less than UPDATED_BOD_ROK
        defaultVrednovanjeShouldBeFound("bodRok.lessThan=" + UPDATED_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodRokIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodRok is greater than DEFAULT_BOD_ROK
        defaultVrednovanjeShouldNotBeFound("bodRok.greaterThan=" + DEFAULT_BOD_ROK);

        // Get all the vrednovanjeList where bodRok is greater than SMALLER_BOD_ROK
        defaultVrednovanjeShouldBeFound("bodRok.greaterThan=" + SMALLER_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodUkupnoIsEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodUkupno equals to DEFAULT_BOD_UKUPNO
        defaultVrednovanjeShouldBeFound("bodUkupno.equals=" + DEFAULT_BOD_UKUPNO);

        // Get all the vrednovanjeList where bodUkupno equals to UPDATED_BOD_UKUPNO
        defaultVrednovanjeShouldNotBeFound("bodUkupno.equals=" + UPDATED_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodUkupnoIsInShouldWork() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodUkupno in DEFAULT_BOD_UKUPNO or UPDATED_BOD_UKUPNO
        defaultVrednovanjeShouldBeFound("bodUkupno.in=" + DEFAULT_BOD_UKUPNO + "," + UPDATED_BOD_UKUPNO);

        // Get all the vrednovanjeList where bodUkupno equals to UPDATED_BOD_UKUPNO
        defaultVrednovanjeShouldNotBeFound("bodUkupno.in=" + UPDATED_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodUkupnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodUkupno is not null
        defaultVrednovanjeShouldBeFound("bodUkupno.specified=true");

        // Get all the vrednovanjeList where bodUkupno is null
        defaultVrednovanjeShouldNotBeFound("bodUkupno.specified=false");
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodUkupnoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodUkupno is greater than or equal to DEFAULT_BOD_UKUPNO
        defaultVrednovanjeShouldBeFound("bodUkupno.greaterThanOrEqual=" + DEFAULT_BOD_UKUPNO);

        // Get all the vrednovanjeList where bodUkupno is greater than or equal to UPDATED_BOD_UKUPNO
        defaultVrednovanjeShouldNotBeFound("bodUkupno.greaterThanOrEqual=" + UPDATED_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodUkupnoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodUkupno is less than or equal to DEFAULT_BOD_UKUPNO
        defaultVrednovanjeShouldBeFound("bodUkupno.lessThanOrEqual=" + DEFAULT_BOD_UKUPNO);

        // Get all the vrednovanjeList where bodUkupno is less than or equal to SMALLER_BOD_UKUPNO
        defaultVrednovanjeShouldNotBeFound("bodUkupno.lessThanOrEqual=" + SMALLER_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodUkupnoIsLessThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodUkupno is less than DEFAULT_BOD_UKUPNO
        defaultVrednovanjeShouldNotBeFound("bodUkupno.lessThan=" + DEFAULT_BOD_UKUPNO);

        // Get all the vrednovanjeList where bodUkupno is less than UPDATED_BOD_UKUPNO
        defaultVrednovanjeShouldBeFound("bodUkupno.lessThan=" + UPDATED_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllVrednovanjesByBodUkupnoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vrednovanjeRepository.saveAndFlush(vrednovanje);

        // Get all the vrednovanjeList where bodUkupno is greater than DEFAULT_BOD_UKUPNO
        defaultVrednovanjeShouldNotBeFound("bodUkupno.greaterThan=" + DEFAULT_BOD_UKUPNO);

        // Get all the vrednovanjeList where bodUkupno is greater than SMALLER_BOD_UKUPNO
        defaultVrednovanjeShouldBeFound("bodUkupno.greaterThan=" + SMALLER_BOD_UKUPNO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVrednovanjeShouldBeFound(String filter) throws Exception {
        restVrednovanjeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vrednovanje.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].sifraPonude").value(hasItem(DEFAULT_SIFRA_PONUDE)))
            .andExpect(jsonPath("$.[*].brojPartije").value(hasItem(DEFAULT_BROJ_PARTIJE)))
            .andExpect(jsonPath("$.[*].nazivProizvodjaca").value(hasItem(DEFAULT_NAZIV_PROIZVODJACA)))
            .andExpect(jsonPath("$.[*].zasticeniNaziv").value(hasItem(DEFAULT_ZASTICENI_NAZIV)))
            .andExpect(jsonPath("$.[*].ponudjenaVrijednost").value(hasItem(DEFAULT_PONUDJENA_VRIJEDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].rokIsporuke").value(hasItem(DEFAULT_ROK_ISPORUKE)))
            .andExpect(jsonPath("$.[*].jedinicnaCijena").value(hasItem(DEFAULT_JEDINICNA_CIJENA.doubleValue())))
            .andExpect(jsonPath("$.[*].nazivPonudjaca").value(hasItem(DEFAULT_NAZIV_PONUDJACA)))
            .andExpect(jsonPath("$.[*].atc").value(hasItem(DEFAULT_ATC)))
            .andExpect(jsonPath("$.[*].trazenaKolicina").value(hasItem(DEFAULT_TRAZENA_KOLICINA)))
            .andExpect(jsonPath("$.[*].procijenjenaVrijednost").value(hasItem(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].vrstaPostupka").value(hasItem(DEFAULT_VRSTA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].bodCijena").value(hasItem(DEFAULT_BOD_CIJENA.doubleValue())))
            .andExpect(jsonPath("$.[*].bodRok").value(hasItem(DEFAULT_BOD_ROK.doubleValue())))
            .andExpect(jsonPath("$.[*].bodUkupno").value(hasItem(DEFAULT_BOD_UKUPNO.doubleValue())));

        // Check, that the count call also returns 1
        restVrednovanjeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVrednovanjeShouldNotBeFound(String filter) throws Exception {
        restVrednovanjeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVrednovanjeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVrednovanje() throws Exception {
        // Get the vrednovanje
        restVrednovanjeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
