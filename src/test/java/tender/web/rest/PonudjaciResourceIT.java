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
import tender.domain.Ponudjaci;
import tender.repository.PonudjaciRepository;
import tender.service.criteria.PonudjaciCriteria;

/**
 * Integration tests for the {@link PonudjaciResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PonudjaciResourceIT {

    private static final String DEFAULT_NAZIV_PONUDJACA = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV_PONUDJACA = "BBBBBBBBBB";

    private static final String DEFAULT_ODGOVORNO_LICE = "AAAAAAAAAA";
    private static final String UPDATED_ODGOVORNO_LICE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESA_PONUDJACA = "AAAAAAAAAA";
    private static final String UPDATED_ADRESA_PONUDJACA = "BBBBBBBBBB";

    private static final String DEFAULT_BANKA_RACUN = "AAAAAAAAAA";
    private static final String UPDATED_BANKA_RACUN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ponudjacis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PonudjaciRepository ponudjaciRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPonudjaciMockMvc;

    private Ponudjaci ponudjaci;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ponudjaci createEntity(EntityManager em) {
        Ponudjaci ponudjaci = new Ponudjaci()
            .nazivPonudjaca(DEFAULT_NAZIV_PONUDJACA)
            .odgovornoLice(DEFAULT_ODGOVORNO_LICE)
            .adresaPonudjaca(DEFAULT_ADRESA_PONUDJACA)
            .bankaRacun(DEFAULT_BANKA_RACUN);
        return ponudjaci;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ponudjaci createUpdatedEntity(EntityManager em) {
        Ponudjaci ponudjaci = new Ponudjaci()
            .nazivPonudjaca(UPDATED_NAZIV_PONUDJACA)
            .odgovornoLice(UPDATED_ODGOVORNO_LICE)
            .adresaPonudjaca(UPDATED_ADRESA_PONUDJACA)
            .bankaRacun(UPDATED_BANKA_RACUN);
        return ponudjaci;
    }

    @BeforeEach
    public void initTest() {
        ponudjaci = createEntity(em);
    }

    @Test
    @Transactional
    void createPonudjaci() throws Exception {
        int databaseSizeBeforeCreate = ponudjaciRepository.findAll().size();
        // Create the Ponudjaci
        restPonudjaciMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ponudjaci)))
            .andExpect(status().isCreated());

        // Validate the Ponudjaci in the database
        List<Ponudjaci> ponudjaciList = ponudjaciRepository.findAll();
        assertThat(ponudjaciList).hasSize(databaseSizeBeforeCreate + 1);
        Ponudjaci testPonudjaci = ponudjaciList.get(ponudjaciList.size() - 1);
        assertThat(testPonudjaci.getNazivPonudjaca()).isEqualTo(DEFAULT_NAZIV_PONUDJACA);
        assertThat(testPonudjaci.getOdgovornoLice()).isEqualTo(DEFAULT_ODGOVORNO_LICE);
        assertThat(testPonudjaci.getAdresaPonudjaca()).isEqualTo(DEFAULT_ADRESA_PONUDJACA);
        assertThat(testPonudjaci.getBankaRacun()).isEqualTo(DEFAULT_BANKA_RACUN);
    }

    @Test
    @Transactional
    void createPonudjaciWithExistingId() throws Exception {
        // Create the Ponudjaci with an existing ID
        ponudjaci.setId(1L);

        int databaseSizeBeforeCreate = ponudjaciRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPonudjaciMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ponudjaci)))
            .andExpect(status().isBadRequest());

        // Validate the Ponudjaci in the database
        List<Ponudjaci> ponudjaciList = ponudjaciRepository.findAll();
        assertThat(ponudjaciList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPonudjacis() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList
        restPonudjaciMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ponudjaci.getId().intValue())))
            .andExpect(jsonPath("$.[*].nazivPonudjaca").value(hasItem(DEFAULT_NAZIV_PONUDJACA)))
            .andExpect(jsonPath("$.[*].odgovornoLice").value(hasItem(DEFAULT_ODGOVORNO_LICE)))
            .andExpect(jsonPath("$.[*].adresaPonudjaca").value(hasItem(DEFAULT_ADRESA_PONUDJACA)))
            .andExpect(jsonPath("$.[*].bankaRacun").value(hasItem(DEFAULT_BANKA_RACUN)));
    }

    @Test
    @Transactional
    void getPonudjaci() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get the ponudjaci
        restPonudjaciMockMvc
            .perform(get(ENTITY_API_URL_ID, ponudjaci.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ponudjaci.getId().intValue()))
            .andExpect(jsonPath("$.nazivPonudjaca").value(DEFAULT_NAZIV_PONUDJACA))
            .andExpect(jsonPath("$.odgovornoLice").value(DEFAULT_ODGOVORNO_LICE))
            .andExpect(jsonPath("$.adresaPonudjaca").value(DEFAULT_ADRESA_PONUDJACA))
            .andExpect(jsonPath("$.bankaRacun").value(DEFAULT_BANKA_RACUN));
    }

    @Test
    @Transactional
    void getPonudjacisByIdFiltering() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        Long id = ponudjaci.getId();

        defaultPonudjaciShouldBeFound("id.equals=" + id);
        defaultPonudjaciShouldNotBeFound("id.notEquals=" + id);

        defaultPonudjaciShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPonudjaciShouldNotBeFound("id.greaterThan=" + id);

        defaultPonudjaciShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPonudjaciShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPonudjacisByNazivPonudjacaIsEqualToSomething() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where nazivPonudjaca equals to DEFAULT_NAZIV_PONUDJACA
        defaultPonudjaciShouldBeFound("nazivPonudjaca.equals=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the ponudjaciList where nazivPonudjaca equals to UPDATED_NAZIV_PONUDJACA
        defaultPonudjaciShouldNotBeFound("nazivPonudjaca.equals=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllPonudjacisByNazivPonudjacaIsInShouldWork() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where nazivPonudjaca in DEFAULT_NAZIV_PONUDJACA or UPDATED_NAZIV_PONUDJACA
        defaultPonudjaciShouldBeFound("nazivPonudjaca.in=" + DEFAULT_NAZIV_PONUDJACA + "," + UPDATED_NAZIV_PONUDJACA);

        // Get all the ponudjaciList where nazivPonudjaca equals to UPDATED_NAZIV_PONUDJACA
        defaultPonudjaciShouldNotBeFound("nazivPonudjaca.in=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllPonudjacisByNazivPonudjacaIsNullOrNotNull() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where nazivPonudjaca is not null
        defaultPonudjaciShouldBeFound("nazivPonudjaca.specified=true");

        // Get all the ponudjaciList where nazivPonudjaca is null
        defaultPonudjaciShouldNotBeFound("nazivPonudjaca.specified=false");
    }

    @Test
    @Transactional
    void getAllPonudjacisByNazivPonudjacaContainsSomething() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where nazivPonudjaca contains DEFAULT_NAZIV_PONUDJACA
        defaultPonudjaciShouldBeFound("nazivPonudjaca.contains=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the ponudjaciList where nazivPonudjaca contains UPDATED_NAZIV_PONUDJACA
        defaultPonudjaciShouldNotBeFound("nazivPonudjaca.contains=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllPonudjacisByNazivPonudjacaNotContainsSomething() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where nazivPonudjaca does not contain DEFAULT_NAZIV_PONUDJACA
        defaultPonudjaciShouldNotBeFound("nazivPonudjaca.doesNotContain=" + DEFAULT_NAZIV_PONUDJACA);

        // Get all the ponudjaciList where nazivPonudjaca does not contain UPDATED_NAZIV_PONUDJACA
        defaultPonudjaciShouldBeFound("nazivPonudjaca.doesNotContain=" + UPDATED_NAZIV_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllPonudjacisByOdgovornoLiceIsEqualToSomething() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where odgovornoLice equals to DEFAULT_ODGOVORNO_LICE
        defaultPonudjaciShouldBeFound("odgovornoLice.equals=" + DEFAULT_ODGOVORNO_LICE);

        // Get all the ponudjaciList where odgovornoLice equals to UPDATED_ODGOVORNO_LICE
        defaultPonudjaciShouldNotBeFound("odgovornoLice.equals=" + UPDATED_ODGOVORNO_LICE);
    }

    @Test
    @Transactional
    void getAllPonudjacisByOdgovornoLiceIsInShouldWork() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where odgovornoLice in DEFAULT_ODGOVORNO_LICE or UPDATED_ODGOVORNO_LICE
        defaultPonudjaciShouldBeFound("odgovornoLice.in=" + DEFAULT_ODGOVORNO_LICE + "," + UPDATED_ODGOVORNO_LICE);

        // Get all the ponudjaciList where odgovornoLice equals to UPDATED_ODGOVORNO_LICE
        defaultPonudjaciShouldNotBeFound("odgovornoLice.in=" + UPDATED_ODGOVORNO_LICE);
    }

    @Test
    @Transactional
    void getAllPonudjacisByOdgovornoLiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where odgovornoLice is not null
        defaultPonudjaciShouldBeFound("odgovornoLice.specified=true");

        // Get all the ponudjaciList where odgovornoLice is null
        defaultPonudjaciShouldNotBeFound("odgovornoLice.specified=false");
    }

    @Test
    @Transactional
    void getAllPonudjacisByOdgovornoLiceContainsSomething() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where odgovornoLice contains DEFAULT_ODGOVORNO_LICE
        defaultPonudjaciShouldBeFound("odgovornoLice.contains=" + DEFAULT_ODGOVORNO_LICE);

        // Get all the ponudjaciList where odgovornoLice contains UPDATED_ODGOVORNO_LICE
        defaultPonudjaciShouldNotBeFound("odgovornoLice.contains=" + UPDATED_ODGOVORNO_LICE);
    }

    @Test
    @Transactional
    void getAllPonudjacisByOdgovornoLiceNotContainsSomething() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where odgovornoLice does not contain DEFAULT_ODGOVORNO_LICE
        defaultPonudjaciShouldNotBeFound("odgovornoLice.doesNotContain=" + DEFAULT_ODGOVORNO_LICE);

        // Get all the ponudjaciList where odgovornoLice does not contain UPDATED_ODGOVORNO_LICE
        defaultPonudjaciShouldBeFound("odgovornoLice.doesNotContain=" + UPDATED_ODGOVORNO_LICE);
    }

    @Test
    @Transactional
    void getAllPonudjacisByAdresaPonudjacaIsEqualToSomething() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where adresaPonudjaca equals to DEFAULT_ADRESA_PONUDJACA
        defaultPonudjaciShouldBeFound("adresaPonudjaca.equals=" + DEFAULT_ADRESA_PONUDJACA);

        // Get all the ponudjaciList where adresaPonudjaca equals to UPDATED_ADRESA_PONUDJACA
        defaultPonudjaciShouldNotBeFound("adresaPonudjaca.equals=" + UPDATED_ADRESA_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllPonudjacisByAdresaPonudjacaIsInShouldWork() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where adresaPonudjaca in DEFAULT_ADRESA_PONUDJACA or UPDATED_ADRESA_PONUDJACA
        defaultPonudjaciShouldBeFound("adresaPonudjaca.in=" + DEFAULT_ADRESA_PONUDJACA + "," + UPDATED_ADRESA_PONUDJACA);

        // Get all the ponudjaciList where adresaPonudjaca equals to UPDATED_ADRESA_PONUDJACA
        defaultPonudjaciShouldNotBeFound("adresaPonudjaca.in=" + UPDATED_ADRESA_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllPonudjacisByAdresaPonudjacaIsNullOrNotNull() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where adresaPonudjaca is not null
        defaultPonudjaciShouldBeFound("adresaPonudjaca.specified=true");

        // Get all the ponudjaciList where adresaPonudjaca is null
        defaultPonudjaciShouldNotBeFound("adresaPonudjaca.specified=false");
    }

    @Test
    @Transactional
    void getAllPonudjacisByAdresaPonudjacaContainsSomething() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where adresaPonudjaca contains DEFAULT_ADRESA_PONUDJACA
        defaultPonudjaciShouldBeFound("adresaPonudjaca.contains=" + DEFAULT_ADRESA_PONUDJACA);

        // Get all the ponudjaciList where adresaPonudjaca contains UPDATED_ADRESA_PONUDJACA
        defaultPonudjaciShouldNotBeFound("adresaPonudjaca.contains=" + UPDATED_ADRESA_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllPonudjacisByAdresaPonudjacaNotContainsSomething() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where adresaPonudjaca does not contain DEFAULT_ADRESA_PONUDJACA
        defaultPonudjaciShouldNotBeFound("adresaPonudjaca.doesNotContain=" + DEFAULT_ADRESA_PONUDJACA);

        // Get all the ponudjaciList where adresaPonudjaca does not contain UPDATED_ADRESA_PONUDJACA
        defaultPonudjaciShouldBeFound("adresaPonudjaca.doesNotContain=" + UPDATED_ADRESA_PONUDJACA);
    }

    @Test
    @Transactional
    void getAllPonudjacisByBankaRacunIsEqualToSomething() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where bankaRacun equals to DEFAULT_BANKA_RACUN
        defaultPonudjaciShouldBeFound("bankaRacun.equals=" + DEFAULT_BANKA_RACUN);

        // Get all the ponudjaciList where bankaRacun equals to UPDATED_BANKA_RACUN
        defaultPonudjaciShouldNotBeFound("bankaRacun.equals=" + UPDATED_BANKA_RACUN);
    }

    @Test
    @Transactional
    void getAllPonudjacisByBankaRacunIsInShouldWork() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where bankaRacun in DEFAULT_BANKA_RACUN or UPDATED_BANKA_RACUN
        defaultPonudjaciShouldBeFound("bankaRacun.in=" + DEFAULT_BANKA_RACUN + "," + UPDATED_BANKA_RACUN);

        // Get all the ponudjaciList where bankaRacun equals to UPDATED_BANKA_RACUN
        defaultPonudjaciShouldNotBeFound("bankaRacun.in=" + UPDATED_BANKA_RACUN);
    }

    @Test
    @Transactional
    void getAllPonudjacisByBankaRacunIsNullOrNotNull() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where bankaRacun is not null
        defaultPonudjaciShouldBeFound("bankaRacun.specified=true");

        // Get all the ponudjaciList where bankaRacun is null
        defaultPonudjaciShouldNotBeFound("bankaRacun.specified=false");
    }

    @Test
    @Transactional
    void getAllPonudjacisByBankaRacunContainsSomething() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where bankaRacun contains DEFAULT_BANKA_RACUN
        defaultPonudjaciShouldBeFound("bankaRacun.contains=" + DEFAULT_BANKA_RACUN);

        // Get all the ponudjaciList where bankaRacun contains UPDATED_BANKA_RACUN
        defaultPonudjaciShouldNotBeFound("bankaRacun.contains=" + UPDATED_BANKA_RACUN);
    }

    @Test
    @Transactional
    void getAllPonudjacisByBankaRacunNotContainsSomething() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        // Get all the ponudjaciList where bankaRacun does not contain DEFAULT_BANKA_RACUN
        defaultPonudjaciShouldNotBeFound("bankaRacun.doesNotContain=" + DEFAULT_BANKA_RACUN);

        // Get all the ponudjaciList where bankaRacun does not contain UPDATED_BANKA_RACUN
        defaultPonudjaciShouldBeFound("bankaRacun.doesNotContain=" + UPDATED_BANKA_RACUN);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPonudjaciShouldBeFound(String filter) throws Exception {
        restPonudjaciMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ponudjaci.getId().intValue())))
            .andExpect(jsonPath("$.[*].nazivPonudjaca").value(hasItem(DEFAULT_NAZIV_PONUDJACA)))
            .andExpect(jsonPath("$.[*].odgovornoLice").value(hasItem(DEFAULT_ODGOVORNO_LICE)))
            .andExpect(jsonPath("$.[*].adresaPonudjaca").value(hasItem(DEFAULT_ADRESA_PONUDJACA)))
            .andExpect(jsonPath("$.[*].bankaRacun").value(hasItem(DEFAULT_BANKA_RACUN)));

        // Check, that the count call also returns 1
        restPonudjaciMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPonudjaciShouldNotBeFound(String filter) throws Exception {
        restPonudjaciMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPonudjaciMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPonudjaci() throws Exception {
        // Get the ponudjaci
        restPonudjaciMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPonudjaci() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        int databaseSizeBeforeUpdate = ponudjaciRepository.findAll().size();

        // Update the ponudjaci
        Ponudjaci updatedPonudjaci = ponudjaciRepository.findById(ponudjaci.getId()).get();
        // Disconnect from session so that the updates on updatedPonudjaci are not directly saved in db
        em.detach(updatedPonudjaci);
        updatedPonudjaci
            .nazivPonudjaca(UPDATED_NAZIV_PONUDJACA)
            .odgovornoLice(UPDATED_ODGOVORNO_LICE)
            .adresaPonudjaca(UPDATED_ADRESA_PONUDJACA)
            .bankaRacun(UPDATED_BANKA_RACUN);

        restPonudjaciMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPonudjaci.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPonudjaci))
            )
            .andExpect(status().isOk());

        // Validate the Ponudjaci in the database
        List<Ponudjaci> ponudjaciList = ponudjaciRepository.findAll();
        assertThat(ponudjaciList).hasSize(databaseSizeBeforeUpdate);
        Ponudjaci testPonudjaci = ponudjaciList.get(ponudjaciList.size() - 1);
        assertThat(testPonudjaci.getNazivPonudjaca()).isEqualTo(UPDATED_NAZIV_PONUDJACA);
        assertThat(testPonudjaci.getOdgovornoLice()).isEqualTo(UPDATED_ODGOVORNO_LICE);
        assertThat(testPonudjaci.getAdresaPonudjaca()).isEqualTo(UPDATED_ADRESA_PONUDJACA);
        assertThat(testPonudjaci.getBankaRacun()).isEqualTo(UPDATED_BANKA_RACUN);
    }

    @Test
    @Transactional
    void putNonExistingPonudjaci() throws Exception {
        int databaseSizeBeforeUpdate = ponudjaciRepository.findAll().size();
        ponudjaci.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPonudjaciMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ponudjaci.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ponudjaci))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ponudjaci in the database
        List<Ponudjaci> ponudjaciList = ponudjaciRepository.findAll();
        assertThat(ponudjaciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPonudjaci() throws Exception {
        int databaseSizeBeforeUpdate = ponudjaciRepository.findAll().size();
        ponudjaci.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPonudjaciMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ponudjaci))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ponudjaci in the database
        List<Ponudjaci> ponudjaciList = ponudjaciRepository.findAll();
        assertThat(ponudjaciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPonudjaci() throws Exception {
        int databaseSizeBeforeUpdate = ponudjaciRepository.findAll().size();
        ponudjaci.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPonudjaciMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ponudjaci)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ponudjaci in the database
        List<Ponudjaci> ponudjaciList = ponudjaciRepository.findAll();
        assertThat(ponudjaciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePonudjaciWithPatch() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        int databaseSizeBeforeUpdate = ponudjaciRepository.findAll().size();

        // Update the ponudjaci using partial update
        Ponudjaci partialUpdatedPonudjaci = new Ponudjaci();
        partialUpdatedPonudjaci.setId(ponudjaci.getId());

        partialUpdatedPonudjaci
            .odgovornoLice(UPDATED_ODGOVORNO_LICE)
            .adresaPonudjaca(UPDATED_ADRESA_PONUDJACA)
            .bankaRacun(UPDATED_BANKA_RACUN);

        restPonudjaciMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPonudjaci.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPonudjaci))
            )
            .andExpect(status().isOk());

        // Validate the Ponudjaci in the database
        List<Ponudjaci> ponudjaciList = ponudjaciRepository.findAll();
        assertThat(ponudjaciList).hasSize(databaseSizeBeforeUpdate);
        Ponudjaci testPonudjaci = ponudjaciList.get(ponudjaciList.size() - 1);
        assertThat(testPonudjaci.getNazivPonudjaca()).isEqualTo(DEFAULT_NAZIV_PONUDJACA);
        assertThat(testPonudjaci.getOdgovornoLice()).isEqualTo(UPDATED_ODGOVORNO_LICE);
        assertThat(testPonudjaci.getAdresaPonudjaca()).isEqualTo(UPDATED_ADRESA_PONUDJACA);
        assertThat(testPonudjaci.getBankaRacun()).isEqualTo(UPDATED_BANKA_RACUN);
    }

    @Test
    @Transactional
    void fullUpdatePonudjaciWithPatch() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        int databaseSizeBeforeUpdate = ponudjaciRepository.findAll().size();

        // Update the ponudjaci using partial update
        Ponudjaci partialUpdatedPonudjaci = new Ponudjaci();
        partialUpdatedPonudjaci.setId(ponudjaci.getId());

        partialUpdatedPonudjaci
            .nazivPonudjaca(UPDATED_NAZIV_PONUDJACA)
            .odgovornoLice(UPDATED_ODGOVORNO_LICE)
            .adresaPonudjaca(UPDATED_ADRESA_PONUDJACA)
            .bankaRacun(UPDATED_BANKA_RACUN);

        restPonudjaciMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPonudjaci.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPonudjaci))
            )
            .andExpect(status().isOk());

        // Validate the Ponudjaci in the database
        List<Ponudjaci> ponudjaciList = ponudjaciRepository.findAll();
        assertThat(ponudjaciList).hasSize(databaseSizeBeforeUpdate);
        Ponudjaci testPonudjaci = ponudjaciList.get(ponudjaciList.size() - 1);
        assertThat(testPonudjaci.getNazivPonudjaca()).isEqualTo(UPDATED_NAZIV_PONUDJACA);
        assertThat(testPonudjaci.getOdgovornoLice()).isEqualTo(UPDATED_ODGOVORNO_LICE);
        assertThat(testPonudjaci.getAdresaPonudjaca()).isEqualTo(UPDATED_ADRESA_PONUDJACA);
        assertThat(testPonudjaci.getBankaRacun()).isEqualTo(UPDATED_BANKA_RACUN);
    }

    @Test
    @Transactional
    void patchNonExistingPonudjaci() throws Exception {
        int databaseSizeBeforeUpdate = ponudjaciRepository.findAll().size();
        ponudjaci.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPonudjaciMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ponudjaci.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ponudjaci))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ponudjaci in the database
        List<Ponudjaci> ponudjaciList = ponudjaciRepository.findAll();
        assertThat(ponudjaciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPonudjaci() throws Exception {
        int databaseSizeBeforeUpdate = ponudjaciRepository.findAll().size();
        ponudjaci.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPonudjaciMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ponudjaci))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ponudjaci in the database
        List<Ponudjaci> ponudjaciList = ponudjaciRepository.findAll();
        assertThat(ponudjaciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPonudjaci() throws Exception {
        int databaseSizeBeforeUpdate = ponudjaciRepository.findAll().size();
        ponudjaci.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPonudjaciMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ponudjaci))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ponudjaci in the database
        List<Ponudjaci> ponudjaciList = ponudjaciRepository.findAll();
        assertThat(ponudjaciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePonudjaci() throws Exception {
        // Initialize the database
        ponudjaciRepository.saveAndFlush(ponudjaci);

        int databaseSizeBeforeDelete = ponudjaciRepository.findAll().size();

        // Delete the ponudjaci
        restPonudjaciMockMvc
            .perform(delete(ENTITY_API_URL_ID, ponudjaci.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ponudjaci> ponudjaciList = ponudjaciRepository.findAll();
        assertThat(ponudjaciList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
