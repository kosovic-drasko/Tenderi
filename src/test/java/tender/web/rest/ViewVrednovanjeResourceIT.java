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
import tender.domain.ViewVrednovanje;
import tender.repository.ViewVrednovanjeRepository;
import tender.service.criteria.ViewVrednovanjeCriteria;

/**
 * Integration tests for the {@link ViewVrednovanjeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ViewVrednovanjeResourceIT {

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

    private static final Integer DEFAULT_PONUDJENA_VRIJEDNOST = 1;
    private static final Integer UPDATED_PONUDJENA_VRIJEDNOST = 2;
    private static final Integer SMALLER_PONUDJENA_VRIJEDNOST = 1 - 1;

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

    private static final String DEFAULT_KATEKTERISTIKA_PONUDE = "AAAAAAAAAA";
    private static final String UPDATED_KATEKTERISTIKA_PONUDE = "BBBBBBBBBB";

    private static final String DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE = "AAAAAAAAAA";
    private static final String UPDATED_KARAKTERISTIKA_SPECIFIKACIJE = "BBBBBBBBBB";

    private static final Double DEFAULT_BOD_CIJENA = 1D;
    private static final Double UPDATED_BOD_CIJENA = 2D;
    private static final Double SMALLER_BOD_CIJENA = 1D - 1D;

    private static final Double DEFAULT_BOD_ROK = 1D;
    private static final Double UPDATED_BOD_ROK = 2D;
    private static final Double SMALLER_BOD_ROK = 1D - 1D;

    private static final Double DEFAULT_BOD_UKUPNO = 1D;
    private static final Double UPDATED_BOD_UKUPNO = 2D;
    private static final Double SMALLER_BOD_UKUPNO = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/view-vrednovanjes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ViewVrednovanjeRepository viewVrednovanjeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restViewVrednovanjeMockMvc;

    private ViewVrednovanje viewVrednovanje;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ViewVrednovanje createEntity(EntityManager em) {
        ViewVrednovanje viewVrednovanje = new ViewVrednovanje()
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
            .katekteristikaPonude(DEFAULT_KATEKTERISTIKA_PONUDE)
            .karakteristikaSpecifikacije(DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE)
            .bodCijena(DEFAULT_BOD_CIJENA)
            .bodRok(DEFAULT_BOD_ROK)
            .bodUkupno(DEFAULT_BOD_UKUPNO);
        return viewVrednovanje;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ViewVrednovanje createUpdatedEntity(EntityManager em) {
        ViewVrednovanje viewVrednovanje = new ViewVrednovanje()
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
            .katekteristikaPonude(UPDATED_KATEKTERISTIKA_PONUDE)
            .karakteristikaSpecifikacije(UPDATED_KARAKTERISTIKA_SPECIFIKACIJE)
            .bodCijena(UPDATED_BOD_CIJENA)
            .bodRok(UPDATED_BOD_ROK)
            .bodUkupno(UPDATED_BOD_UKUPNO);
        return viewVrednovanje;
    }

    @BeforeEach
    public void initTest() {
        viewVrednovanje = createEntity(em);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjes() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList
        restViewVrednovanjeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viewVrednovanje.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].sifraPonude").value(hasItem(DEFAULT_SIFRA_PONUDE)))
            .andExpect(jsonPath("$.[*].brojPartije").value(hasItem(DEFAULT_BROJ_PARTIJE)))
            .andExpect(jsonPath("$.[*].nazivProizvodjaca").value(hasItem(DEFAULT_NAZIV_PROIZVODJACA)))
            .andExpect(jsonPath("$.[*].zasticeniNaziv").value(hasItem(DEFAULT_ZASTICENI_NAZIV)))
            .andExpect(jsonPath("$.[*].ponudjenaVrijednost").value(hasItem(DEFAULT_PONUDJENA_VRIJEDNOST)))
            .andExpect(jsonPath("$.[*].rokIsporuke").value(hasItem(DEFAULT_ROK_ISPORUKE)))
            .andExpect(jsonPath("$.[*].jedinicnaCijena").value(hasItem(DEFAULT_JEDINICNA_CIJENA.doubleValue())))
            .andExpect(jsonPath("$.[*].nazivPonudjaca").value(hasItem(DEFAULT_NAZIV_PONUDJACA)))
            .andExpect(jsonPath("$.[*].atc").value(hasItem(DEFAULT_ATC)))
            .andExpect(jsonPath("$.[*].trazenaKolicina").value(hasItem(DEFAULT_TRAZENA_KOLICINA)))
            .andExpect(jsonPath("$.[*].procijenjenaVrijednost").value(hasItem(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].vrstaPostupka").value(hasItem(DEFAULT_VRSTA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].katekteristikaPonude").value(hasItem(DEFAULT_KATEKTERISTIKA_PONUDE)))
            .andExpect(jsonPath("$.[*].karakteristikaSpecifikacije").value(hasItem(DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE)))
            .andExpect(jsonPath("$.[*].bodCijena").value(hasItem(DEFAULT_BOD_CIJENA.doubleValue())))
            .andExpect(jsonPath("$.[*].bodRok").value(hasItem(DEFAULT_BOD_ROK.doubleValue())))
            .andExpect(jsonPath("$.[*].bodUkupno").value(hasItem(DEFAULT_BOD_UKUPNO.doubleValue())));
    }

    @Test
    @Transactional
    void getViewVrednovanje() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get the viewVrednovanje
        restViewVrednovanjeMockMvc
            .perform(get(ENTITY_API_URL_ID, viewVrednovanje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(viewVrednovanje.getId().intValue()))
            .andExpect(jsonPath("$.sifraPostupka").value(DEFAULT_SIFRA_POSTUPKA))
            .andExpect(jsonPath("$.sifraPonude").value(DEFAULT_SIFRA_PONUDE))
            .andExpect(jsonPath("$.brojPartije").value(DEFAULT_BROJ_PARTIJE))
            .andExpect(jsonPath("$.nazivProizvodjaca").value(DEFAULT_NAZIV_PROIZVODJACA))
            .andExpect(jsonPath("$.zasticeniNaziv").value(DEFAULT_ZASTICENI_NAZIV))
            .andExpect(jsonPath("$.ponudjenaVrijednost").value(DEFAULT_PONUDJENA_VRIJEDNOST))
            .andExpect(jsonPath("$.rokIsporuke").value(DEFAULT_ROK_ISPORUKE))
            .andExpect(jsonPath("$.jedinicnaCijena").value(DEFAULT_JEDINICNA_CIJENA.doubleValue()))
            .andExpect(jsonPath("$.nazivPonudjaca").value(DEFAULT_NAZIV_PONUDJACA))
            .andExpect(jsonPath("$.atc").value(DEFAULT_ATC))
            .andExpect(jsonPath("$.trazenaKolicina").value(DEFAULT_TRAZENA_KOLICINA))
            .andExpect(jsonPath("$.procijenjenaVrijednost").value(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue()))
            .andExpect(jsonPath("$.vrstaPostupka").value(DEFAULT_VRSTA_POSTUPKA))
            .andExpect(jsonPath("$.katekteristikaPonude").value(DEFAULT_KATEKTERISTIKA_PONUDE))
            .andExpect(jsonPath("$.karakteristikaSpecifikacije").value(DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE))
            .andExpect(jsonPath("$.bodCijena").value(DEFAULT_BOD_CIJENA.doubleValue()))
            .andExpect(jsonPath("$.bodRok").value(DEFAULT_BOD_ROK.doubleValue()))
            .andExpect(jsonPath("$.bodUkupno").value(DEFAULT_BOD_UKUPNO.doubleValue()));
    }

    @Test
    @Transactional
    void getViewVrednovanjesByIdFiltering() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        Long id = viewVrednovanje.getId();

        defaultViewVrednovanjeShouldBeFound("id.equals=" + id);
        defaultViewVrednovanjeShouldNotBeFound("id.notEquals=" + id);

        defaultViewVrednovanjeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultViewVrednovanjeShouldNotBeFound("id.greaterThan=" + id);

        defaultViewVrednovanjeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultViewVrednovanjeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesBySifraPostupkaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where sifraPostupka equals to DEFAULT_SIFRA_POSTUPKA
        defaultViewVrednovanjeShouldBeFound("sifraPostupka.equals=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the viewVrednovanjeList where sifraPostupka equals to UPDATED_SIFRA_POSTUPKA
        defaultViewVrednovanjeShouldNotBeFound("sifraPostupka.equals=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesBySifraPostupkaIsInShouldWork() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where sifraPostupka in DEFAULT_SIFRA_POSTUPKA or UPDATED_SIFRA_POSTUPKA
        defaultViewVrednovanjeShouldBeFound("sifraPostupka.in=" + DEFAULT_SIFRA_POSTUPKA + "," + UPDATED_SIFRA_POSTUPKA);

        // Get all the viewVrednovanjeList where sifraPostupka equals to UPDATED_SIFRA_POSTUPKA
        defaultViewVrednovanjeShouldNotBeFound("sifraPostupka.in=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesBySifraPostupkaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where sifraPostupka is not null
        defaultViewVrednovanjeShouldBeFound("sifraPostupka.specified=true");

        // Get all the viewVrednovanjeList where sifraPostupka is null
        defaultViewVrednovanjeShouldNotBeFound("sifraPostupka.specified=false");
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesBySifraPostupkaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where sifraPostupka is greater than or equal to DEFAULT_SIFRA_POSTUPKA
        defaultViewVrednovanjeShouldBeFound("sifraPostupka.greaterThanOrEqual=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the viewVrednovanjeList where sifraPostupka is greater than or equal to UPDATED_SIFRA_POSTUPKA
        defaultViewVrednovanjeShouldNotBeFound("sifraPostupka.greaterThanOrEqual=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesBySifraPostupkaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where sifraPostupka is less than or equal to DEFAULT_SIFRA_POSTUPKA
        defaultViewVrednovanjeShouldBeFound("sifraPostupka.lessThanOrEqual=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the viewVrednovanjeList where sifraPostupka is less than or equal to SMALLER_SIFRA_POSTUPKA
        defaultViewVrednovanjeShouldNotBeFound("sifraPostupka.lessThanOrEqual=" + SMALLER_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesBySifraPostupkaIsLessThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where sifraPostupka is less than DEFAULT_SIFRA_POSTUPKA
        defaultViewVrednovanjeShouldNotBeFound("sifraPostupka.lessThan=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the viewVrednovanjeList where sifraPostupka is less than UPDATED_SIFRA_POSTUPKA
        defaultViewVrednovanjeShouldBeFound("sifraPostupka.lessThan=" + UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesBySifraPostupkaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where sifraPostupka is greater than DEFAULT_SIFRA_POSTUPKA
        defaultViewVrednovanjeShouldNotBeFound("sifraPostupka.greaterThan=" + DEFAULT_SIFRA_POSTUPKA);

        // Get all the viewVrednovanjeList where sifraPostupka is greater than SMALLER_SIFRA_POSTUPKA
        defaultViewVrednovanjeShouldBeFound("sifraPostupka.greaterThan=" + SMALLER_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesBySifraPonudeIsEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where sifraPonude equals to DEFAULT_SIFRA_PONUDE
        defaultViewVrednovanjeShouldBeFound("sifraPonude.equals=" + DEFAULT_SIFRA_PONUDE);

        // Get all the viewVrednovanjeList where sifraPonude equals to UPDATED_SIFRA_PONUDE
        defaultViewVrednovanjeShouldNotBeFound("sifraPonude.equals=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesBySifraPonudeIsInShouldWork() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where sifraPonude in DEFAULT_SIFRA_PONUDE or UPDATED_SIFRA_PONUDE
        defaultViewVrednovanjeShouldBeFound("sifraPonude.in=" + DEFAULT_SIFRA_PONUDE + "," + UPDATED_SIFRA_PONUDE);

        // Get all the viewVrednovanjeList where sifraPonude equals to UPDATED_SIFRA_PONUDE
        defaultViewVrednovanjeShouldNotBeFound("sifraPonude.in=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesBySifraPonudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where sifraPonude is not null
        defaultViewVrednovanjeShouldBeFound("sifraPonude.specified=true");

        // Get all the viewVrednovanjeList where sifraPonude is null
        defaultViewVrednovanjeShouldNotBeFound("sifraPonude.specified=false");
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesBySifraPonudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where sifraPonude is greater than or equal to DEFAULT_SIFRA_PONUDE
        defaultViewVrednovanjeShouldBeFound("sifraPonude.greaterThanOrEqual=" + DEFAULT_SIFRA_PONUDE);

        // Get all the viewVrednovanjeList where sifraPonude is greater than or equal to UPDATED_SIFRA_PONUDE
        defaultViewVrednovanjeShouldNotBeFound("sifraPonude.greaterThanOrEqual=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesBySifraPonudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where sifraPonude is less than or equal to DEFAULT_SIFRA_PONUDE
        defaultViewVrednovanjeShouldBeFound("sifraPonude.lessThanOrEqual=" + DEFAULT_SIFRA_PONUDE);

        // Get all the viewVrednovanjeList where sifraPonude is less than or equal to SMALLER_SIFRA_PONUDE
        defaultViewVrednovanjeShouldNotBeFound("sifraPonude.lessThanOrEqual=" + SMALLER_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesBySifraPonudeIsLessThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where sifraPonude is less than DEFAULT_SIFRA_PONUDE
        defaultViewVrednovanjeShouldNotBeFound("sifraPonude.lessThan=" + DEFAULT_SIFRA_PONUDE);

        // Get all the viewVrednovanjeList where sifraPonude is less than UPDATED_SIFRA_PONUDE
        defaultViewVrednovanjeShouldBeFound("sifraPonude.lessThan=" + UPDATED_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesBySifraPonudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where sifraPonude is greater than DEFAULT_SIFRA_PONUDE
        defaultViewVrednovanjeShouldNotBeFound("sifraPonude.greaterThan=" + DEFAULT_SIFRA_PONUDE);

        // Get all the viewVrednovanjeList where sifraPonude is greater than SMALLER_SIFRA_PONUDE
        defaultViewVrednovanjeShouldBeFound("sifraPonude.greaterThan=" + SMALLER_SIFRA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBrojPartijeIsEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where brojPartije equals to DEFAULT_BROJ_PARTIJE
        defaultViewVrednovanjeShouldBeFound("brojPartije.equals=" + DEFAULT_BROJ_PARTIJE);

        // Get all the viewVrednovanjeList where brojPartije equals to UPDATED_BROJ_PARTIJE
        defaultViewVrednovanjeShouldNotBeFound("brojPartije.equals=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBrojPartijeIsInShouldWork() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where brojPartije in DEFAULT_BROJ_PARTIJE or UPDATED_BROJ_PARTIJE
        defaultViewVrednovanjeShouldBeFound("brojPartije.in=" + DEFAULT_BROJ_PARTIJE + "," + UPDATED_BROJ_PARTIJE);

        // Get all the viewVrednovanjeList where brojPartije equals to UPDATED_BROJ_PARTIJE
        defaultViewVrednovanjeShouldNotBeFound("brojPartije.in=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBrojPartijeIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where brojPartije is not null
        defaultViewVrednovanjeShouldBeFound("brojPartije.specified=true");

        // Get all the viewVrednovanjeList where brojPartije is null
        defaultViewVrednovanjeShouldNotBeFound("brojPartije.specified=false");
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBrojPartijeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where brojPartije is greater than or equal to DEFAULT_BROJ_PARTIJE
        defaultViewVrednovanjeShouldBeFound("brojPartije.greaterThanOrEqual=" + DEFAULT_BROJ_PARTIJE);

        // Get all the viewVrednovanjeList where brojPartije is greater than or equal to UPDATED_BROJ_PARTIJE
        defaultViewVrednovanjeShouldNotBeFound("brojPartije.greaterThanOrEqual=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBrojPartijeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where brojPartije is less than or equal to DEFAULT_BROJ_PARTIJE
        defaultViewVrednovanjeShouldBeFound("brojPartije.lessThanOrEqual=" + DEFAULT_BROJ_PARTIJE);

        // Get all the viewVrednovanjeList where brojPartije is less than or equal to SMALLER_BROJ_PARTIJE
        defaultViewVrednovanjeShouldNotBeFound("brojPartije.lessThanOrEqual=" + SMALLER_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBrojPartijeIsLessThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where brojPartije is less than DEFAULT_BROJ_PARTIJE
        defaultViewVrednovanjeShouldNotBeFound("brojPartije.lessThan=" + DEFAULT_BROJ_PARTIJE);

        // Get all the viewVrednovanjeList where brojPartije is less than UPDATED_BROJ_PARTIJE
        defaultViewVrednovanjeShouldBeFound("brojPartije.lessThan=" + UPDATED_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBrojPartijeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where brojPartije is greater than DEFAULT_BROJ_PARTIJE
        defaultViewVrednovanjeShouldNotBeFound("brojPartije.greaterThan=" + DEFAULT_BROJ_PARTIJE);

        // Get all the viewVrednovanjeList where brojPartije is greater than SMALLER_BROJ_PARTIJE
        defaultViewVrednovanjeShouldBeFound("brojPartije.greaterThan=" + SMALLER_BROJ_PARTIJE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByNazivProizvodjacaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where nazivProizvodjaca equals to DEFAULT_NAZIV_PROIZVODJACA
        defaultViewVrednovanjeShouldBeFound("nazivProizvodjaca.equals=" + DEFAULT_NAZIV_PROIZVODJACA);

        // Get all the viewVrednovanjeList where nazivProizvodjaca equals to UPDATED_NAZIV_PROIZVODJACA
        defaultViewVrednovanjeShouldNotBeFound("nazivProizvodjaca.equals=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByNazivProizvodjacaIsInShouldWork() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where nazivProizvodjaca in DEFAULT_NAZIV_PROIZVODJACA or UPDATED_NAZIV_PROIZVODJACA
        defaultViewVrednovanjeShouldBeFound("nazivProizvodjaca.in=" + DEFAULT_NAZIV_PROIZVODJACA + "," + UPDATED_NAZIV_PROIZVODJACA);

        // Get all the viewVrednovanjeList where nazivProizvodjaca equals to UPDATED_NAZIV_PROIZVODJACA
        defaultViewVrednovanjeShouldNotBeFound("nazivProizvodjaca.in=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByNazivProizvodjacaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where nazivProizvodjaca is not null
        defaultViewVrednovanjeShouldBeFound("nazivProizvodjaca.specified=true");

        // Get all the viewVrednovanjeList where nazivProizvodjaca is null
        defaultViewVrednovanjeShouldNotBeFound("nazivProizvodjaca.specified=false");
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByNazivProizvodjacaContainsSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where nazivProizvodjaca contains DEFAULT_NAZIV_PROIZVODJACA
        defaultViewVrednovanjeShouldBeFound("nazivProizvodjaca.contains=" + DEFAULT_NAZIV_PROIZVODJACA);

        // Get all the viewVrednovanjeList where nazivProizvodjaca contains UPDATED_NAZIV_PROIZVODJACA
        defaultViewVrednovanjeShouldNotBeFound("nazivProizvodjaca.contains=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByNazivProizvodjacaNotContainsSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where nazivProizvodjaca does not contain DEFAULT_NAZIV_PROIZVODJACA
        defaultViewVrednovanjeShouldNotBeFound("nazivProizvodjaca.doesNotContain=" + DEFAULT_NAZIV_PROIZVODJACA);

        // Get all the viewVrednovanjeList where nazivProizvodjaca does not contain UPDATED_NAZIV_PROIZVODJACA
        defaultViewVrednovanjeShouldBeFound("nazivProizvodjaca.doesNotContain=" + UPDATED_NAZIV_PROIZVODJACA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByZasticeniNazivIsEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where zasticeniNaziv equals to DEFAULT_ZASTICENI_NAZIV
        defaultViewVrednovanjeShouldBeFound("zasticeniNaziv.equals=" + DEFAULT_ZASTICENI_NAZIV);

        // Get all the viewVrednovanjeList where zasticeniNaziv equals to UPDATED_ZASTICENI_NAZIV
        defaultViewVrednovanjeShouldNotBeFound("zasticeniNaziv.equals=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByZasticeniNazivIsInShouldWork() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where zasticeniNaziv in DEFAULT_ZASTICENI_NAZIV or UPDATED_ZASTICENI_NAZIV
        defaultViewVrednovanjeShouldBeFound("zasticeniNaziv.in=" + DEFAULT_ZASTICENI_NAZIV + "," + UPDATED_ZASTICENI_NAZIV);

        // Get all the viewVrednovanjeList where zasticeniNaziv equals to UPDATED_ZASTICENI_NAZIV
        defaultViewVrednovanjeShouldNotBeFound("zasticeniNaziv.in=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByZasticeniNazivIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where zasticeniNaziv is not null
        defaultViewVrednovanjeShouldBeFound("zasticeniNaziv.specified=true");

        // Get all the viewVrednovanjeList where zasticeniNaziv is null
        defaultViewVrednovanjeShouldNotBeFound("zasticeniNaziv.specified=false");
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByZasticeniNazivContainsSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where zasticeniNaziv contains DEFAULT_ZASTICENI_NAZIV
        defaultViewVrednovanjeShouldBeFound("zasticeniNaziv.contains=" + DEFAULT_ZASTICENI_NAZIV);

        // Get all the viewVrednovanjeList where zasticeniNaziv contains UPDATED_ZASTICENI_NAZIV
        defaultViewVrednovanjeShouldNotBeFound("zasticeniNaziv.contains=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByZasticeniNazivNotContainsSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where zasticeniNaziv does not contain DEFAULT_ZASTICENI_NAZIV
        defaultViewVrednovanjeShouldNotBeFound("zasticeniNaziv.doesNotContain=" + DEFAULT_ZASTICENI_NAZIV);

        // Get all the viewVrednovanjeList where zasticeniNaziv does not contain UPDATED_ZASTICENI_NAZIV
        defaultViewVrednovanjeShouldBeFound("zasticeniNaziv.doesNotContain=" + UPDATED_ZASTICENI_NAZIV);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByPonudjenaVrijednostIsEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where ponudjenaVrijednost equals to DEFAULT_PONUDJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldBeFound("ponudjenaVrijednost.equals=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the viewVrednovanjeList where ponudjenaVrijednost equals to UPDATED_PONUDJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldNotBeFound("ponudjenaVrijednost.equals=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByPonudjenaVrijednostIsInShouldWork() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where ponudjenaVrijednost in DEFAULT_PONUDJENA_VRIJEDNOST or UPDATED_PONUDJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldBeFound("ponudjenaVrijednost.in=" + DEFAULT_PONUDJENA_VRIJEDNOST + "," + UPDATED_PONUDJENA_VRIJEDNOST);

        // Get all the viewVrednovanjeList where ponudjenaVrijednost equals to UPDATED_PONUDJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldNotBeFound("ponudjenaVrijednost.in=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByPonudjenaVrijednostIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where ponudjenaVrijednost is not null
        defaultViewVrednovanjeShouldBeFound("ponudjenaVrijednost.specified=true");

        // Get all the viewVrednovanjeList where ponudjenaVrijednost is null
        defaultViewVrednovanjeShouldNotBeFound("ponudjenaVrijednost.specified=false");
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByPonudjenaVrijednostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where ponudjenaVrijednost is greater than or equal to DEFAULT_PONUDJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldBeFound("ponudjenaVrijednost.greaterThanOrEqual=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the viewVrednovanjeList where ponudjenaVrijednost is greater than or equal to UPDATED_PONUDJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldNotBeFound("ponudjenaVrijednost.greaterThanOrEqual=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByPonudjenaVrijednostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where ponudjenaVrijednost is less than or equal to DEFAULT_PONUDJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldBeFound("ponudjenaVrijednost.lessThanOrEqual=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the viewVrednovanjeList where ponudjenaVrijednost is less than or equal to SMALLER_PONUDJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldNotBeFound("ponudjenaVrijednost.lessThanOrEqual=" + SMALLER_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByPonudjenaVrijednostIsLessThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where ponudjenaVrijednost is less than DEFAULT_PONUDJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldNotBeFound("ponudjenaVrijednost.lessThan=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the viewVrednovanjeList where ponudjenaVrijednost is less than UPDATED_PONUDJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldBeFound("ponudjenaVrijednost.lessThan=" + UPDATED_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByPonudjenaVrijednostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where ponudjenaVrijednost is greater than DEFAULT_PONUDJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldNotBeFound("ponudjenaVrijednost.greaterThan=" + DEFAULT_PONUDJENA_VRIJEDNOST);

        // Get all the viewVrednovanjeList where ponudjenaVrijednost is greater than SMALLER_PONUDJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldBeFound("ponudjenaVrijednost.greaterThan=" + SMALLER_PONUDJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByRokIsporukeIsEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where rokIsporuke equals to DEFAULT_ROK_ISPORUKE
        defaultViewVrednovanjeShouldBeFound("rokIsporuke.equals=" + DEFAULT_ROK_ISPORUKE);

        // Get all the viewVrednovanjeList where rokIsporuke equals to UPDATED_ROK_ISPORUKE
        defaultViewVrednovanjeShouldNotBeFound("rokIsporuke.equals=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByRokIsporukeIsInShouldWork() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where rokIsporuke in DEFAULT_ROK_ISPORUKE or UPDATED_ROK_ISPORUKE
        defaultViewVrednovanjeShouldBeFound("rokIsporuke.in=" + DEFAULT_ROK_ISPORUKE + "," + UPDATED_ROK_ISPORUKE);

        // Get all the viewVrednovanjeList where rokIsporuke equals to UPDATED_ROK_ISPORUKE
        defaultViewVrednovanjeShouldNotBeFound("rokIsporuke.in=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByRokIsporukeIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where rokIsporuke is not null
        defaultViewVrednovanjeShouldBeFound("rokIsporuke.specified=true");

        // Get all the viewVrednovanjeList where rokIsporuke is null
        defaultViewVrednovanjeShouldNotBeFound("rokIsporuke.specified=false");
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByRokIsporukeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where rokIsporuke is greater than or equal to DEFAULT_ROK_ISPORUKE
        defaultViewVrednovanjeShouldBeFound("rokIsporuke.greaterThanOrEqual=" + DEFAULT_ROK_ISPORUKE);

        // Get all the viewVrednovanjeList where rokIsporuke is greater than or equal to UPDATED_ROK_ISPORUKE
        defaultViewVrednovanjeShouldNotBeFound("rokIsporuke.greaterThanOrEqual=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByRokIsporukeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where rokIsporuke is less than or equal to DEFAULT_ROK_ISPORUKE
        defaultViewVrednovanjeShouldBeFound("rokIsporuke.lessThanOrEqual=" + DEFAULT_ROK_ISPORUKE);

        // Get all the viewVrednovanjeList where rokIsporuke is less than or equal to SMALLER_ROK_ISPORUKE
        defaultViewVrednovanjeShouldNotBeFound("rokIsporuke.lessThanOrEqual=" + SMALLER_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByRokIsporukeIsLessThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where rokIsporuke is less than DEFAULT_ROK_ISPORUKE
        defaultViewVrednovanjeShouldNotBeFound("rokIsporuke.lessThan=" + DEFAULT_ROK_ISPORUKE);

        // Get all the viewVrednovanjeList where rokIsporuke is less than UPDATED_ROK_ISPORUKE
        defaultViewVrednovanjeShouldBeFound("rokIsporuke.lessThan=" + UPDATED_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByRokIsporukeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where rokIsporuke is greater than DEFAULT_ROK_ISPORUKE
        defaultViewVrednovanjeShouldNotBeFound("rokIsporuke.greaterThan=" + DEFAULT_ROK_ISPORUKE);

        // Get all the viewVrednovanjeList where rokIsporuke is greater than SMALLER_ROK_ISPORUKE
        defaultViewVrednovanjeShouldBeFound("rokIsporuke.greaterThan=" + SMALLER_ROK_ISPORUKE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByJedinicnaCijenaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where jedinicnaCijena equals to DEFAULT_JEDINICNA_CIJENA
        defaultViewVrednovanjeShouldBeFound("jedinicnaCijena.equals=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the viewVrednovanjeList where jedinicnaCijena equals to UPDATED_JEDINICNA_CIJENA
        defaultViewVrednovanjeShouldNotBeFound("jedinicnaCijena.equals=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByJedinicnaCijenaIsInShouldWork() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where jedinicnaCijena in DEFAULT_JEDINICNA_CIJENA or UPDATED_JEDINICNA_CIJENA
        defaultViewVrednovanjeShouldBeFound("jedinicnaCijena.in=" + DEFAULT_JEDINICNA_CIJENA + "," + UPDATED_JEDINICNA_CIJENA);

        // Get all the viewVrednovanjeList where jedinicnaCijena equals to UPDATED_JEDINICNA_CIJENA
        defaultViewVrednovanjeShouldNotBeFound("jedinicnaCijena.in=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByJedinicnaCijenaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where jedinicnaCijena is not null
        defaultViewVrednovanjeShouldBeFound("jedinicnaCijena.specified=true");

        // Get all the viewVrednovanjeList where jedinicnaCijena is null
        defaultViewVrednovanjeShouldNotBeFound("jedinicnaCijena.specified=false");
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByJedinicnaCijenaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where jedinicnaCijena is greater than or equal to DEFAULT_JEDINICNA_CIJENA
        defaultViewVrednovanjeShouldBeFound("jedinicnaCijena.greaterThanOrEqual=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the viewVrednovanjeList where jedinicnaCijena is greater than or equal to UPDATED_JEDINICNA_CIJENA
        defaultViewVrednovanjeShouldNotBeFound("jedinicnaCijena.greaterThanOrEqual=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByJedinicnaCijenaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where jedinicnaCijena is less than or equal to DEFAULT_JEDINICNA_CIJENA
        defaultViewVrednovanjeShouldBeFound("jedinicnaCijena.lessThanOrEqual=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the viewVrednovanjeList where jedinicnaCijena is less than or equal to SMALLER_JEDINICNA_CIJENA
        defaultViewVrednovanjeShouldNotBeFound("jedinicnaCijena.lessThanOrEqual=" + SMALLER_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByJedinicnaCijenaIsLessThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where jedinicnaCijena is less than DEFAULT_JEDINICNA_CIJENA
        defaultViewVrednovanjeShouldNotBeFound("jedinicnaCijena.lessThan=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the viewVrednovanjeList where jedinicnaCijena is less than UPDATED_JEDINICNA_CIJENA
        defaultViewVrednovanjeShouldBeFound("jedinicnaCijena.lessThan=" + UPDATED_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByJedinicnaCijenaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where jedinicnaCijena is greater than DEFAULT_JEDINICNA_CIJENA
        defaultViewVrednovanjeShouldNotBeFound("jedinicnaCijena.greaterThan=" + DEFAULT_JEDINICNA_CIJENA);

        // Get all the viewVrednovanjeList where jedinicnaCijena is greater than SMALLER_JEDINICNA_CIJENA
        defaultViewVrednovanjeShouldBeFound("jedinicnaCijena.greaterThan=" + SMALLER_JEDINICNA_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByNazivPonudjacaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where nazivPonudjaca equals to DEFAULT_NAZIV_PONUDJACA
        defaultViewVrednovanjeShouldBeFound("nazivPonudjaca.equals=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the viewVrednovanjeList where nazivPonudjaca equals to UPDATED_NAZIV_PONUDJACA
        defaultViewVrednovanjeShouldNotBeFound("nazivPonudjaca.equals=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByNazivPonudjacaIsInShouldWork() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where nazivPonudjaca in DEFAULT_NAZIV_PONUDJACA or UPDATED_NAZIV_PONUDJACA
        defaultViewVrednovanjeShouldBeFound("nazivPonudjaca.in=" + DEFAULT_NAZIV_PONUDJACA + "," + UPDATED_NAZIV_PONUDJACA);

        // Get all the viewVrednovanjeList where nazivPonudjaca equals to UPDATED_NAZIV_PONUDJACA
        defaultViewVrednovanjeShouldNotBeFound("nazivPonudjaca.in=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByNazivPonudjacaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where nazivPonudjaca is not null
        defaultViewVrednovanjeShouldBeFound("nazivPonudjaca.specified=true");

        // Get all the viewVrednovanjeList where nazivPonudjaca is null
        defaultViewVrednovanjeShouldNotBeFound("nazivPonudjaca.specified=false");
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByNazivPonudjacaContainsSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where nazivPonudjaca contains DEFAULT_NAZIV_PONUDJACA
        defaultViewVrednovanjeShouldBeFound("nazivPonudjaca.contains=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the viewVrednovanjeList where nazivPonudjaca contains UPDATED_NAZIV_PONUDJACA
        defaultViewVrednovanjeShouldNotBeFound("nazivPonudjaca.contains=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByNazivPonudjacaNotContainsSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where nazivPonudjaca does not contain DEFAULT_NAZIV_PONUDJACA
        defaultViewVrednovanjeShouldNotBeFound("nazivPonudjaca.doesNotContain=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the viewVrednovanjeList where nazivPonudjaca does not contain UPDATED_NAZIV_PONUDJACA
        defaultViewVrednovanjeShouldBeFound("nazivPonudjaca.doesNotContain=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByAtcIsEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where atc equals to DEFAULT_ATC
        defaultViewVrednovanjeShouldBeFound("atc.equals=" + DEFAULT_ATC);

        // Get all the viewVrednovanjeList where atc equals to UPDATED_ATC
        defaultViewVrednovanjeShouldNotBeFound("atc.equals=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByAtcIsInShouldWork() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where atc in DEFAULT_ATC or UPDATED_ATC
        defaultViewVrednovanjeShouldBeFound("atc.in=" + DEFAULT_ATC + "," + UPDATED_ATC);

        // Get all the viewVrednovanjeList where atc equals to UPDATED_ATC
        defaultViewVrednovanjeShouldNotBeFound("atc.in=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByAtcIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where atc is not null
        defaultViewVrednovanjeShouldBeFound("atc.specified=true");

        // Get all the viewVrednovanjeList where atc is null
        defaultViewVrednovanjeShouldNotBeFound("atc.specified=false");
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByAtcContainsSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where atc contains DEFAULT_ATC
        defaultViewVrednovanjeShouldBeFound("atc.contains=" + DEFAULT_ATC);

        // Get all the viewVrednovanjeList where atc contains UPDATED_ATC
        defaultViewVrednovanjeShouldNotBeFound("atc.contains=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByAtcNotContainsSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where atc does not contain DEFAULT_ATC
        defaultViewVrednovanjeShouldNotBeFound("atc.doesNotContain=" + DEFAULT_ATC);

        // Get all the viewVrednovanjeList where atc does not contain UPDATED_ATC
        defaultViewVrednovanjeShouldBeFound("atc.doesNotContain=" + UPDATED_ATC);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByTrazenaKolicinaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where trazenaKolicina equals to DEFAULT_TRAZENA_KOLICINA
        defaultViewVrednovanjeShouldBeFound("trazenaKolicina.equals=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the viewVrednovanjeList where trazenaKolicina equals to UPDATED_TRAZENA_KOLICINA
        defaultViewVrednovanjeShouldNotBeFound("trazenaKolicina.equals=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByTrazenaKolicinaIsInShouldWork() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where trazenaKolicina in DEFAULT_TRAZENA_KOLICINA or UPDATED_TRAZENA_KOLICINA
        defaultViewVrednovanjeShouldBeFound("trazenaKolicina.in=" + DEFAULT_TRAZENA_KOLICINA + "," + UPDATED_TRAZENA_KOLICINA);

        // Get all the viewVrednovanjeList where trazenaKolicina equals to UPDATED_TRAZENA_KOLICINA
        defaultViewVrednovanjeShouldNotBeFound("trazenaKolicina.in=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByTrazenaKolicinaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where trazenaKolicina is not null
        defaultViewVrednovanjeShouldBeFound("trazenaKolicina.specified=true");

        // Get all the viewVrednovanjeList where trazenaKolicina is null
        defaultViewVrednovanjeShouldNotBeFound("trazenaKolicina.specified=false");
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByTrazenaKolicinaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where trazenaKolicina is greater than or equal to DEFAULT_TRAZENA_KOLICINA
        defaultViewVrednovanjeShouldBeFound("trazenaKolicina.greaterThanOrEqual=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the viewVrednovanjeList where trazenaKolicina is greater than or equal to UPDATED_TRAZENA_KOLICINA
        defaultViewVrednovanjeShouldNotBeFound("trazenaKolicina.greaterThanOrEqual=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByTrazenaKolicinaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where trazenaKolicina is less than or equal to DEFAULT_TRAZENA_KOLICINA
        defaultViewVrednovanjeShouldBeFound("trazenaKolicina.lessThanOrEqual=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the viewVrednovanjeList where trazenaKolicina is less than or equal to SMALLER_TRAZENA_KOLICINA
        defaultViewVrednovanjeShouldNotBeFound("trazenaKolicina.lessThanOrEqual=" + SMALLER_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByTrazenaKolicinaIsLessThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where trazenaKolicina is less than DEFAULT_TRAZENA_KOLICINA
        defaultViewVrednovanjeShouldNotBeFound("trazenaKolicina.lessThan=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the viewVrednovanjeList where trazenaKolicina is less than UPDATED_TRAZENA_KOLICINA
        defaultViewVrednovanjeShouldBeFound("trazenaKolicina.lessThan=" + UPDATED_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByTrazenaKolicinaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where trazenaKolicina is greater than DEFAULT_TRAZENA_KOLICINA
        defaultViewVrednovanjeShouldNotBeFound("trazenaKolicina.greaterThan=" + DEFAULT_TRAZENA_KOLICINA);

        // Get all the viewVrednovanjeList where trazenaKolicina is greater than SMALLER_TRAZENA_KOLICINA
        defaultViewVrednovanjeShouldBeFound("trazenaKolicina.greaterThan=" + SMALLER_TRAZENA_KOLICINA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByProcijenjenaVrijednostIsEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where procijenjenaVrijednost equals to DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldBeFound("procijenjenaVrijednost.equals=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the viewVrednovanjeList where procijenjenaVrijednost equals to UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldNotBeFound("procijenjenaVrijednost.equals=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByProcijenjenaVrijednostIsInShouldWork() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where procijenjenaVrijednost in DEFAULT_PROCIJENJENA_VRIJEDNOST or UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldBeFound(
            "procijenjenaVrijednost.in=" + DEFAULT_PROCIJENJENA_VRIJEDNOST + "," + UPDATED_PROCIJENJENA_VRIJEDNOST
        );

        // Get all the viewVrednovanjeList where procijenjenaVrijednost equals to UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldNotBeFound("procijenjenaVrijednost.in=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByProcijenjenaVrijednostIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where procijenjenaVrijednost is not null
        defaultViewVrednovanjeShouldBeFound("procijenjenaVrijednost.specified=true");

        // Get all the viewVrednovanjeList where procijenjenaVrijednost is null
        defaultViewVrednovanjeShouldNotBeFound("procijenjenaVrijednost.specified=false");
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByProcijenjenaVrijednostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where procijenjenaVrijednost is greater than or equal to DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldBeFound("procijenjenaVrijednost.greaterThanOrEqual=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the viewVrednovanjeList where procijenjenaVrijednost is greater than or equal to UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldNotBeFound("procijenjenaVrijednost.greaterThanOrEqual=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByProcijenjenaVrijednostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where procijenjenaVrijednost is less than or equal to DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldBeFound("procijenjenaVrijednost.lessThanOrEqual=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the viewVrednovanjeList where procijenjenaVrijednost is less than or equal to SMALLER_PROCIJENJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldNotBeFound("procijenjenaVrijednost.lessThanOrEqual=" + SMALLER_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByProcijenjenaVrijednostIsLessThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where procijenjenaVrijednost is less than DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldNotBeFound("procijenjenaVrijednost.lessThan=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the viewVrednovanjeList where procijenjenaVrijednost is less than UPDATED_PROCIJENJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldBeFound("procijenjenaVrijednost.lessThan=" + UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByProcijenjenaVrijednostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where procijenjenaVrijednost is greater than DEFAULT_PROCIJENJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldNotBeFound("procijenjenaVrijednost.greaterThan=" + DEFAULT_PROCIJENJENA_VRIJEDNOST);

        // Get all the viewVrednovanjeList where procijenjenaVrijednost is greater than SMALLER_PROCIJENJENA_VRIJEDNOST
        defaultViewVrednovanjeShouldBeFound("procijenjenaVrijednost.greaterThan=" + SMALLER_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByVrstaPostupkaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where vrstaPostupka equals to DEFAULT_VRSTA_POSTUPKA
        defaultViewVrednovanjeShouldBeFound("vrstaPostupka.equals=" + DEFAULT_VRSTA_POSTUPKA);

        // Get all the viewVrednovanjeList where vrstaPostupka equals to UPDATED_VRSTA_POSTUPKA
        defaultViewVrednovanjeShouldNotBeFound("vrstaPostupka.equals=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByVrstaPostupkaIsInShouldWork() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where vrstaPostupka in DEFAULT_VRSTA_POSTUPKA or UPDATED_VRSTA_POSTUPKA
        defaultViewVrednovanjeShouldBeFound("vrstaPostupka.in=" + DEFAULT_VRSTA_POSTUPKA + "," + UPDATED_VRSTA_POSTUPKA);

        // Get all the viewVrednovanjeList where vrstaPostupka equals to UPDATED_VRSTA_POSTUPKA
        defaultViewVrednovanjeShouldNotBeFound("vrstaPostupka.in=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByVrstaPostupkaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where vrstaPostupka is not null
        defaultViewVrednovanjeShouldBeFound("vrstaPostupka.specified=true");

        // Get all the viewVrednovanjeList where vrstaPostupka is null
        defaultViewVrednovanjeShouldNotBeFound("vrstaPostupka.specified=false");
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByVrstaPostupkaContainsSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where vrstaPostupka contains DEFAULT_VRSTA_POSTUPKA
        defaultViewVrednovanjeShouldBeFound("vrstaPostupka.contains=" + DEFAULT_VRSTA_POSTUPKA);

        // Get all the viewVrednovanjeList where vrstaPostupka contains UPDATED_VRSTA_POSTUPKA
        defaultViewVrednovanjeShouldNotBeFound("vrstaPostupka.contains=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByVrstaPostupkaNotContainsSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where vrstaPostupka does not contain DEFAULT_VRSTA_POSTUPKA
        defaultViewVrednovanjeShouldNotBeFound("vrstaPostupka.doesNotContain=" + DEFAULT_VRSTA_POSTUPKA);

        // Get all the viewVrednovanjeList where vrstaPostupka does not contain UPDATED_VRSTA_POSTUPKA
        defaultViewVrednovanjeShouldBeFound("vrstaPostupka.doesNotContain=" + UPDATED_VRSTA_POSTUPKA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByKatekteristikaPonudeIsEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where katekteristikaPonude equals to DEFAULT_KATEKTERISTIKA_PONUDE
        defaultViewVrednovanjeShouldBeFound("katekteristikaPonude.equals=" + DEFAULT_KATEKTERISTIKA_PONUDE);

        // Get all the viewVrednovanjeList where katekteristikaPonude equals to UPDATED_KATEKTERISTIKA_PONUDE
        defaultViewVrednovanjeShouldNotBeFound("katekteristikaPonude.equals=" + UPDATED_KATEKTERISTIKA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByKatekteristikaPonudeIsInShouldWork() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where katekteristikaPonude in DEFAULT_KATEKTERISTIKA_PONUDE or UPDATED_KATEKTERISTIKA_PONUDE
        defaultViewVrednovanjeShouldBeFound(
            "katekteristikaPonude.in=" + DEFAULT_KATEKTERISTIKA_PONUDE + "," + UPDATED_KATEKTERISTIKA_PONUDE
        );

        // Get all the viewVrednovanjeList where katekteristikaPonude equals to UPDATED_KATEKTERISTIKA_PONUDE
        defaultViewVrednovanjeShouldNotBeFound("katekteristikaPonude.in=" + UPDATED_KATEKTERISTIKA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByKatekteristikaPonudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where katekteristikaPonude is not null
        defaultViewVrednovanjeShouldBeFound("katekteristikaPonude.specified=true");

        // Get all the viewVrednovanjeList where katekteristikaPonude is null
        defaultViewVrednovanjeShouldNotBeFound("katekteristikaPonude.specified=false");
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByKatekteristikaPonudeContainsSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where katekteristikaPonude contains DEFAULT_KATEKTERISTIKA_PONUDE
        defaultViewVrednovanjeShouldBeFound("katekteristikaPonude.contains=" + DEFAULT_KATEKTERISTIKA_PONUDE);

        // Get all the viewVrednovanjeList where katekteristikaPonude contains UPDATED_KATEKTERISTIKA_PONUDE
        defaultViewVrednovanjeShouldNotBeFound("katekteristikaPonude.contains=" + UPDATED_KATEKTERISTIKA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByKatekteristikaPonudeNotContainsSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where katekteristikaPonude does not contain DEFAULT_KATEKTERISTIKA_PONUDE
        defaultViewVrednovanjeShouldNotBeFound("katekteristikaPonude.doesNotContain=" + DEFAULT_KATEKTERISTIKA_PONUDE);

        // Get all the viewVrednovanjeList where katekteristikaPonude does not contain UPDATED_KATEKTERISTIKA_PONUDE
        defaultViewVrednovanjeShouldBeFound("katekteristikaPonude.doesNotContain=" + UPDATED_KATEKTERISTIKA_PONUDE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByKarakteristikaSpecifikacijeIsEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where karakteristikaSpecifikacije equals to DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE
        defaultViewVrednovanjeShouldBeFound("karakteristikaSpecifikacije.equals=" + DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE);

        // Get all the viewVrednovanjeList where karakteristikaSpecifikacije equals to UPDATED_KARAKTERISTIKA_SPECIFIKACIJE
        defaultViewVrednovanjeShouldNotBeFound("karakteristikaSpecifikacije.equals=" + UPDATED_KARAKTERISTIKA_SPECIFIKACIJE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByKarakteristikaSpecifikacijeIsInShouldWork() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where karakteristikaSpecifikacije in DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE or UPDATED_KARAKTERISTIKA_SPECIFIKACIJE
        defaultViewVrednovanjeShouldBeFound(
            "karakteristikaSpecifikacije.in=" + DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE + "," + UPDATED_KARAKTERISTIKA_SPECIFIKACIJE
        );

        // Get all the viewVrednovanjeList where karakteristikaSpecifikacije equals to UPDATED_KARAKTERISTIKA_SPECIFIKACIJE
        defaultViewVrednovanjeShouldNotBeFound("karakteristikaSpecifikacije.in=" + UPDATED_KARAKTERISTIKA_SPECIFIKACIJE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByKarakteristikaSpecifikacijeIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where karakteristikaSpecifikacije is not null
        defaultViewVrednovanjeShouldBeFound("karakteristikaSpecifikacije.specified=true");

        // Get all the viewVrednovanjeList where karakteristikaSpecifikacije is null
        defaultViewVrednovanjeShouldNotBeFound("karakteristikaSpecifikacije.specified=false");
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByKarakteristikaSpecifikacijeContainsSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where karakteristikaSpecifikacije contains DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE
        defaultViewVrednovanjeShouldBeFound("karakteristikaSpecifikacije.contains=" + DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE);

        // Get all the viewVrednovanjeList where karakteristikaSpecifikacije contains UPDATED_KARAKTERISTIKA_SPECIFIKACIJE
        defaultViewVrednovanjeShouldNotBeFound("karakteristikaSpecifikacije.contains=" + UPDATED_KARAKTERISTIKA_SPECIFIKACIJE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByKarakteristikaSpecifikacijeNotContainsSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where karakteristikaSpecifikacije does not contain DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE
        defaultViewVrednovanjeShouldNotBeFound("karakteristikaSpecifikacije.doesNotContain=" + DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE);

        // Get all the viewVrednovanjeList where karakteristikaSpecifikacije does not contain UPDATED_KARAKTERISTIKA_SPECIFIKACIJE
        defaultViewVrednovanjeShouldBeFound("karakteristikaSpecifikacije.doesNotContain=" + UPDATED_KARAKTERISTIKA_SPECIFIKACIJE);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodCijenaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodCijena equals to DEFAULT_BOD_CIJENA
        defaultViewVrednovanjeShouldBeFound("bodCijena.equals=" + DEFAULT_BOD_CIJENA);

        // Get all the viewVrednovanjeList where bodCijena equals to UPDATED_BOD_CIJENA
        defaultViewVrednovanjeShouldNotBeFound("bodCijena.equals=" + UPDATED_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodCijenaIsInShouldWork() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodCijena in DEFAULT_BOD_CIJENA or UPDATED_BOD_CIJENA
        defaultViewVrednovanjeShouldBeFound("bodCijena.in=" + DEFAULT_BOD_CIJENA + "," + UPDATED_BOD_CIJENA);

        // Get all the viewVrednovanjeList where bodCijena equals to UPDATED_BOD_CIJENA
        defaultViewVrednovanjeShouldNotBeFound("bodCijena.in=" + UPDATED_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodCijenaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodCijena is not null
        defaultViewVrednovanjeShouldBeFound("bodCijena.specified=true");

        // Get all the viewVrednovanjeList where bodCijena is null
        defaultViewVrednovanjeShouldNotBeFound("bodCijena.specified=false");
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodCijenaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodCijena is greater than or equal to DEFAULT_BOD_CIJENA
        defaultViewVrednovanjeShouldBeFound("bodCijena.greaterThanOrEqual=" + DEFAULT_BOD_CIJENA);

        // Get all the viewVrednovanjeList where bodCijena is greater than or equal to UPDATED_BOD_CIJENA
        defaultViewVrednovanjeShouldNotBeFound("bodCijena.greaterThanOrEqual=" + UPDATED_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodCijenaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodCijena is less than or equal to DEFAULT_BOD_CIJENA
        defaultViewVrednovanjeShouldBeFound("bodCijena.lessThanOrEqual=" + DEFAULT_BOD_CIJENA);

        // Get all the viewVrednovanjeList where bodCijena is less than or equal to SMALLER_BOD_CIJENA
        defaultViewVrednovanjeShouldNotBeFound("bodCijena.lessThanOrEqual=" + SMALLER_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodCijenaIsLessThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodCijena is less than DEFAULT_BOD_CIJENA
        defaultViewVrednovanjeShouldNotBeFound("bodCijena.lessThan=" + DEFAULT_BOD_CIJENA);

        // Get all the viewVrednovanjeList where bodCijena is less than UPDATED_BOD_CIJENA
        defaultViewVrednovanjeShouldBeFound("bodCijena.lessThan=" + UPDATED_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodCijenaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodCijena is greater than DEFAULT_BOD_CIJENA
        defaultViewVrednovanjeShouldNotBeFound("bodCijena.greaterThan=" + DEFAULT_BOD_CIJENA);

        // Get all the viewVrednovanjeList where bodCijena is greater than SMALLER_BOD_CIJENA
        defaultViewVrednovanjeShouldBeFound("bodCijena.greaterThan=" + SMALLER_BOD_CIJENA);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodRokIsEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodRok equals to DEFAULT_BOD_ROK
        defaultViewVrednovanjeShouldBeFound("bodRok.equals=" + DEFAULT_BOD_ROK);

        // Get all the viewVrednovanjeList where bodRok equals to UPDATED_BOD_ROK
        defaultViewVrednovanjeShouldNotBeFound("bodRok.equals=" + UPDATED_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodRokIsInShouldWork() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodRok in DEFAULT_BOD_ROK or UPDATED_BOD_ROK
        defaultViewVrednovanjeShouldBeFound("bodRok.in=" + DEFAULT_BOD_ROK + "," + UPDATED_BOD_ROK);

        // Get all the viewVrednovanjeList where bodRok equals to UPDATED_BOD_ROK
        defaultViewVrednovanjeShouldNotBeFound("bodRok.in=" + UPDATED_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodRokIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodRok is not null
        defaultViewVrednovanjeShouldBeFound("bodRok.specified=true");

        // Get all the viewVrednovanjeList where bodRok is null
        defaultViewVrednovanjeShouldNotBeFound("bodRok.specified=false");
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodRokIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodRok is greater than or equal to DEFAULT_BOD_ROK
        defaultViewVrednovanjeShouldBeFound("bodRok.greaterThanOrEqual=" + DEFAULT_BOD_ROK);

        // Get all the viewVrednovanjeList where bodRok is greater than or equal to UPDATED_BOD_ROK
        defaultViewVrednovanjeShouldNotBeFound("bodRok.greaterThanOrEqual=" + UPDATED_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodRokIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodRok is less than or equal to DEFAULT_BOD_ROK
        defaultViewVrednovanjeShouldBeFound("bodRok.lessThanOrEqual=" + DEFAULT_BOD_ROK);

        // Get all the viewVrednovanjeList where bodRok is less than or equal to SMALLER_BOD_ROK
        defaultViewVrednovanjeShouldNotBeFound("bodRok.lessThanOrEqual=" + SMALLER_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodRokIsLessThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodRok is less than DEFAULT_BOD_ROK
        defaultViewVrednovanjeShouldNotBeFound("bodRok.lessThan=" + DEFAULT_BOD_ROK);

        // Get all the viewVrednovanjeList where bodRok is less than UPDATED_BOD_ROK
        defaultViewVrednovanjeShouldBeFound("bodRok.lessThan=" + UPDATED_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodRokIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodRok is greater than DEFAULT_BOD_ROK
        defaultViewVrednovanjeShouldNotBeFound("bodRok.greaterThan=" + DEFAULT_BOD_ROK);

        // Get all the viewVrednovanjeList where bodRok is greater than SMALLER_BOD_ROK
        defaultViewVrednovanjeShouldBeFound("bodRok.greaterThan=" + SMALLER_BOD_ROK);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodUkupnoIsEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodUkupno equals to DEFAULT_BOD_UKUPNO
        defaultViewVrednovanjeShouldBeFound("bodUkupno.equals=" + DEFAULT_BOD_UKUPNO);

        // Get all the viewVrednovanjeList where bodUkupno equals to UPDATED_BOD_UKUPNO
        defaultViewVrednovanjeShouldNotBeFound("bodUkupno.equals=" + UPDATED_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodUkupnoIsInShouldWork() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodUkupno in DEFAULT_BOD_UKUPNO or UPDATED_BOD_UKUPNO
        defaultViewVrednovanjeShouldBeFound("bodUkupno.in=" + DEFAULT_BOD_UKUPNO + "," + UPDATED_BOD_UKUPNO);

        // Get all the viewVrednovanjeList where bodUkupno equals to UPDATED_BOD_UKUPNO
        defaultViewVrednovanjeShouldNotBeFound("bodUkupno.in=" + UPDATED_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodUkupnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodUkupno is not null
        defaultViewVrednovanjeShouldBeFound("bodUkupno.specified=true");

        // Get all the viewVrednovanjeList where bodUkupno is null
        defaultViewVrednovanjeShouldNotBeFound("bodUkupno.specified=false");
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodUkupnoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodUkupno is greater than or equal to DEFAULT_BOD_UKUPNO
        defaultViewVrednovanjeShouldBeFound("bodUkupno.greaterThanOrEqual=" + DEFAULT_BOD_UKUPNO);

        // Get all the viewVrednovanjeList where bodUkupno is greater than or equal to UPDATED_BOD_UKUPNO
        defaultViewVrednovanjeShouldNotBeFound("bodUkupno.greaterThanOrEqual=" + UPDATED_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodUkupnoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodUkupno is less than or equal to DEFAULT_BOD_UKUPNO
        defaultViewVrednovanjeShouldBeFound("bodUkupno.lessThanOrEqual=" + DEFAULT_BOD_UKUPNO);

        // Get all the viewVrednovanjeList where bodUkupno is less than or equal to SMALLER_BOD_UKUPNO
        defaultViewVrednovanjeShouldNotBeFound("bodUkupno.lessThanOrEqual=" + SMALLER_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodUkupnoIsLessThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodUkupno is less than DEFAULT_BOD_UKUPNO
        defaultViewVrednovanjeShouldNotBeFound("bodUkupno.lessThan=" + DEFAULT_BOD_UKUPNO);

        // Get all the viewVrednovanjeList where bodUkupno is less than UPDATED_BOD_UKUPNO
        defaultViewVrednovanjeShouldBeFound("bodUkupno.lessThan=" + UPDATED_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjesByBodUkupnoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList where bodUkupno is greater than DEFAULT_BOD_UKUPNO
        defaultViewVrednovanjeShouldNotBeFound("bodUkupno.greaterThan=" + DEFAULT_BOD_UKUPNO);

        // Get all the viewVrednovanjeList where bodUkupno is greater than SMALLER_BOD_UKUPNO
        defaultViewVrednovanjeShouldBeFound("bodUkupno.greaterThan=" + SMALLER_BOD_UKUPNO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultViewVrednovanjeShouldBeFound(String filter) throws Exception {
        restViewVrednovanjeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viewVrednovanje.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].sifraPonude").value(hasItem(DEFAULT_SIFRA_PONUDE)))
            .andExpect(jsonPath("$.[*].brojPartije").value(hasItem(DEFAULT_BROJ_PARTIJE)))
            .andExpect(jsonPath("$.[*].nazivProizvodjaca").value(hasItem(DEFAULT_NAZIV_PROIZVODJACA)))
            .andExpect(jsonPath("$.[*].zasticeniNaziv").value(hasItem(DEFAULT_ZASTICENI_NAZIV)))
            .andExpect(jsonPath("$.[*].ponudjenaVrijednost").value(hasItem(DEFAULT_PONUDJENA_VRIJEDNOST)))
            .andExpect(jsonPath("$.[*].rokIsporuke").value(hasItem(DEFAULT_ROK_ISPORUKE)))
            .andExpect(jsonPath("$.[*].jedinicnaCijena").value(hasItem(DEFAULT_JEDINICNA_CIJENA.doubleValue())))
            .andExpect(jsonPath("$.[*].nazivPonudjaca").value(hasItem(DEFAULT_NAZIV_PONUDJACA)))
            .andExpect(jsonPath("$.[*].atc").value(hasItem(DEFAULT_ATC)))
            .andExpect(jsonPath("$.[*].trazenaKolicina").value(hasItem(DEFAULT_TRAZENA_KOLICINA)))
            .andExpect(jsonPath("$.[*].procijenjenaVrijednost").value(hasItem(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].vrstaPostupka").value(hasItem(DEFAULT_VRSTA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].katekteristikaPonude").value(hasItem(DEFAULT_KATEKTERISTIKA_PONUDE)))
            .andExpect(jsonPath("$.[*].karakteristikaSpecifikacije").value(hasItem(DEFAULT_KARAKTERISTIKA_SPECIFIKACIJE)))
            .andExpect(jsonPath("$.[*].bodCijena").value(hasItem(DEFAULT_BOD_CIJENA.doubleValue())))
            .andExpect(jsonPath("$.[*].bodRok").value(hasItem(DEFAULT_BOD_ROK.doubleValue())))
            .andExpect(jsonPath("$.[*].bodUkupno").value(hasItem(DEFAULT_BOD_UKUPNO.doubleValue())));

        // Check, that the count call also returns 1
        restViewVrednovanjeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultViewVrednovanjeShouldNotBeFound(String filter) throws Exception {
        restViewVrednovanjeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restViewVrednovanjeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingViewVrednovanje() throws Exception {
        // Get the viewVrednovanje
        restViewVrednovanjeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
