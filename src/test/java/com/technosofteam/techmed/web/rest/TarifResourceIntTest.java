package com.technosofteam.techmed.web.rest;

import com.technosofteam.techmed.TechmedApp;

import com.technosofteam.techmed.domain.Tarif;
import com.technosofteam.techmed.repository.TarifRepository;
import com.technosofteam.techmed.service.TarifService;
import com.technosofteam.techmed.service.dto.TarifDTO;
import com.technosofteam.techmed.service.mapper.TarifMapper;
import com.technosofteam.techmed.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.technosofteam.techmed.web.rest.TestUtil.sameInstant;
import static com.technosofteam.techmed.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TarifResource REST controller.
 *
 * @see TarifResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechmedApp.class)
public class TarifResourceIntTest {

    private static final Double DEFAULT_PRIX_HT = 1D;
    private static final Double UPDATED_PRIX_HT = 2D;

    private static final Double DEFAULT_TVA = 1D;
    private static final Double UPDATED_TVA = 2D;

    private static final Double DEFAULT_PRIX_TTC = 1D;
    private static final Double UPDATED_PRIX_TTC = 2D;

    private static final Boolean DEFAULT_ACTIF = false;
    private static final Boolean UPDATED_ACTIF = true;

    private static final ZonedDateTime DEFAULT_DATE_DEBUT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_DEBUT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_FIN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_FIN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TarifRepository tarifRepository;

    @Autowired
    private TarifMapper tarifMapper;

    @Autowired
    private TarifService tarifService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTarifMockMvc;

    private Tarif tarif;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TarifResource tarifResource = new TarifResource(tarifService);
        this.restTarifMockMvc = MockMvcBuilders.standaloneSetup(tarifResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tarif createEntity(EntityManager em) {
        Tarif tarif = new Tarif()
            .prixHT(DEFAULT_PRIX_HT)
            .tva(DEFAULT_TVA)
            .prixTTC(DEFAULT_PRIX_TTC)
            .actif(DEFAULT_ACTIF)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN);
        return tarif;
    }

    @Before
    public void initTest() {
        tarif = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarif() throws Exception {
        int databaseSizeBeforeCreate = tarifRepository.findAll().size();

        // Create the Tarif
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);
        restTarifMockMvc.perform(post("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isCreated());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeCreate + 1);
        Tarif testTarif = tarifList.get(tarifList.size() - 1);
        assertThat(testTarif.getPrixHT()).isEqualTo(DEFAULT_PRIX_HT);
        assertThat(testTarif.getTva()).isEqualTo(DEFAULT_TVA);
        assertThat(testTarif.getPrixTTC()).isEqualTo(DEFAULT_PRIX_TTC);
        assertThat(testTarif.isActif()).isEqualTo(DEFAULT_ACTIF);
        assertThat(testTarif.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testTarif.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    @Transactional
    public void createTarifWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarifRepository.findAll().size();

        // Create the Tarif with an existing ID
        tarif.setId(1L);
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarifMockMvc.perform(post("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrixHTIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarifRepository.findAll().size();
        // set the field null
        tarif.setPrixHT(null);

        // Create the Tarif, which fails.
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);

        restTarifMockMvc.perform(post("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isBadRequest());

        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTvaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarifRepository.findAll().size();
        // set the field null
        tarif.setTva(null);

        // Create the Tarif, which fails.
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);

        restTarifMockMvc.perform(post("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isBadRequest());

        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrixTTCIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarifRepository.findAll().size();
        // set the field null
        tarif.setPrixTTC(null);

        // Create the Tarif, which fails.
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);

        restTarifMockMvc.perform(post("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isBadRequest());

        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarifRepository.findAll().size();
        // set the field null
        tarif.setDateDebut(null);

        // Create the Tarif, which fails.
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);

        restTarifMockMvc.perform(post("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isBadRequest());

        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTarifs() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList
        restTarifMockMvc.perform(get("/api/tarifs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarif.getId().intValue())))
            .andExpect(jsonPath("$.[*].prixHT").value(hasItem(DEFAULT_PRIX_HT.doubleValue())))
            .andExpect(jsonPath("$.[*].tva").value(hasItem(DEFAULT_TVA.doubleValue())))
            .andExpect(jsonPath("$.[*].prixTTC").value(hasItem(DEFAULT_PRIX_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].actif").value(hasItem(DEFAULT_ACTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(sameInstant(DEFAULT_DATE_DEBUT))))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(sameInstant(DEFAULT_DATE_FIN))));
    }

    @Test
    @Transactional
    public void getTarif() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get the tarif
        restTarifMockMvc.perform(get("/api/tarifs/{id}", tarif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tarif.getId().intValue()))
            .andExpect(jsonPath("$.prixHT").value(DEFAULT_PRIX_HT.doubleValue()))
            .andExpect(jsonPath("$.tva").value(DEFAULT_TVA.doubleValue()))
            .andExpect(jsonPath("$.prixTTC").value(DEFAULT_PRIX_TTC.doubleValue()))
            .andExpect(jsonPath("$.actif").value(DEFAULT_ACTIF.booleanValue()))
            .andExpect(jsonPath("$.dateDebut").value(sameInstant(DEFAULT_DATE_DEBUT)))
            .andExpect(jsonPath("$.dateFin").value(sameInstant(DEFAULT_DATE_FIN)));
    }

    @Test
    @Transactional
    public void getNonExistingTarif() throws Exception {
        // Get the tarif
        restTarifMockMvc.perform(get("/api/tarifs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarif() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);
        int databaseSizeBeforeUpdate = tarifRepository.findAll().size();

        // Update the tarif
        Tarif updatedTarif = tarifRepository.findOne(tarif.getId());
        updatedTarif
            .prixHT(UPDATED_PRIX_HT)
            .tva(UPDATED_TVA)
            .prixTTC(UPDATED_PRIX_TTC)
            .actif(UPDATED_ACTIF)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN);
        TarifDTO tarifDTO = tarifMapper.toDto(updatedTarif);

        restTarifMockMvc.perform(put("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isOk());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeUpdate);
        Tarif testTarif = tarifList.get(tarifList.size() - 1);
        assertThat(testTarif.getPrixHT()).isEqualTo(UPDATED_PRIX_HT);
        assertThat(testTarif.getTva()).isEqualTo(UPDATED_TVA);
        assertThat(testTarif.getPrixTTC()).isEqualTo(UPDATED_PRIX_TTC);
        assertThat(testTarif.isActif()).isEqualTo(UPDATED_ACTIF);
        assertThat(testTarif.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testTarif.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void updateNonExistingTarif() throws Exception {
        int databaseSizeBeforeUpdate = tarifRepository.findAll().size();

        // Create the Tarif
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTarifMockMvc.perform(put("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isCreated());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTarif() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);
        int databaseSizeBeforeDelete = tarifRepository.findAll().size();

        // Get the tarif
        restTarifMockMvc.perform(delete("/api/tarifs/{id}", tarif.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tarif.class);
        Tarif tarif1 = new Tarif();
        tarif1.setId(1L);
        Tarif tarif2 = new Tarif();
        tarif2.setId(tarif1.getId());
        assertThat(tarif1).isEqualTo(tarif2);
        tarif2.setId(2L);
        assertThat(tarif1).isNotEqualTo(tarif2);
        tarif1.setId(null);
        assertThat(tarif1).isNotEqualTo(tarif2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarifDTO.class);
        TarifDTO tarifDTO1 = new TarifDTO();
        tarifDTO1.setId(1L);
        TarifDTO tarifDTO2 = new TarifDTO();
        assertThat(tarifDTO1).isNotEqualTo(tarifDTO2);
        tarifDTO2.setId(tarifDTO1.getId());
        assertThat(tarifDTO1).isEqualTo(tarifDTO2);
        tarifDTO2.setId(2L);
        assertThat(tarifDTO1).isNotEqualTo(tarifDTO2);
        tarifDTO1.setId(null);
        assertThat(tarifDTO1).isNotEqualTo(tarifDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tarifMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tarifMapper.fromId(null)).isNull();
    }
}
