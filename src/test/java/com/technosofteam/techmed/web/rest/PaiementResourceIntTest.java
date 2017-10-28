package com.technosofteam.techmed.web.rest;

import com.technosofteam.techmed.TechmedApp;

import com.technosofteam.techmed.domain.Paiement;
import com.technosofteam.techmed.repository.PaiementRepository;
import com.technosofteam.techmed.service.PaiementService;
import com.technosofteam.techmed.service.dto.PaiementDTO;
import com.technosofteam.techmed.service.mapper.PaiementMapper;
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
 * Test class for the PaiementResource REST controller.
 *
 * @see PaiementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechmedApp.class)
public class PaiementResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE_TRANSATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_TRANSATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_MONTANT_TTC = 1D;
    private static final Double UPDATED_MONTANT_TTC = 2D;

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private PaiementMapper paiementMapper;

    @Autowired
    private PaiementService paiementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaiementMockMvc;

    private Paiement paiement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaiementResource paiementResource = new PaiementResource(paiementService);
        this.restPaiementMockMvc = MockMvcBuilders.standaloneSetup(paiementResource)
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
    public static Paiement createEntity(EntityManager em) {
        Paiement paiement = new Paiement()
            .dateTransation(DEFAULT_DATE_TRANSATION)
            .montantTTC(DEFAULT_MONTANT_TTC);
        return paiement;
    }

    @Before
    public void initTest() {
        paiement = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaiement() throws Exception {
        int databaseSizeBeforeCreate = paiementRepository.findAll().size();

        // Create the Paiement
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);
        restPaiementMockMvc.perform(post("/api/paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiementDTO)))
            .andExpect(status().isCreated());

        // Validate the Paiement in the database
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeCreate + 1);
        Paiement testPaiement = paiementList.get(paiementList.size() - 1);
        assertThat(testPaiement.getDateTransation()).isEqualTo(DEFAULT_DATE_TRANSATION);
        assertThat(testPaiement.getMontantTTC()).isEqualTo(DEFAULT_MONTANT_TTC);
    }

    @Test
    @Transactional
    public void createPaiementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paiementRepository.findAll().size();

        // Create the Paiement with an existing ID
        paiement.setId(1L);
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaiementMockMvc.perform(post("/api/paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Paiement in the database
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateTransationIsRequired() throws Exception {
        int databaseSizeBeforeTest = paiementRepository.findAll().size();
        // set the field null
        paiement.setDateTransation(null);

        // Create the Paiement, which fails.
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);

        restPaiementMockMvc.perform(post("/api/paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiementDTO)))
            .andExpect(status().isBadRequest());

        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontantTTCIsRequired() throws Exception {
        int databaseSizeBeforeTest = paiementRepository.findAll().size();
        // set the field null
        paiement.setMontantTTC(null);

        // Create the Paiement, which fails.
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);

        restPaiementMockMvc.perform(post("/api/paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiementDTO)))
            .andExpect(status().isBadRequest());

        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaiements() throws Exception {
        // Initialize the database
        paiementRepository.saveAndFlush(paiement);

        // Get all the paiementList
        restPaiementMockMvc.perform(get("/api/paiements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paiement.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTransation").value(hasItem(sameInstant(DEFAULT_DATE_TRANSATION))))
            .andExpect(jsonPath("$.[*].montantTTC").value(hasItem(DEFAULT_MONTANT_TTC.doubleValue())));
    }

    @Test
    @Transactional
    public void getPaiement() throws Exception {
        // Initialize the database
        paiementRepository.saveAndFlush(paiement);

        // Get the paiement
        restPaiementMockMvc.perform(get("/api/paiements/{id}", paiement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paiement.getId().intValue()))
            .andExpect(jsonPath("$.dateTransation").value(sameInstant(DEFAULT_DATE_TRANSATION)))
            .andExpect(jsonPath("$.montantTTC").value(DEFAULT_MONTANT_TTC.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPaiement() throws Exception {
        // Get the paiement
        restPaiementMockMvc.perform(get("/api/paiements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaiement() throws Exception {
        // Initialize the database
        paiementRepository.saveAndFlush(paiement);
        int databaseSizeBeforeUpdate = paiementRepository.findAll().size();

        // Update the paiement
        Paiement updatedPaiement = paiementRepository.findOne(paiement.getId());
        updatedPaiement
            .dateTransation(UPDATED_DATE_TRANSATION)
            .montantTTC(UPDATED_MONTANT_TTC);
        PaiementDTO paiementDTO = paiementMapper.toDto(updatedPaiement);

        restPaiementMockMvc.perform(put("/api/paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiementDTO)))
            .andExpect(status().isOk());

        // Validate the Paiement in the database
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeUpdate);
        Paiement testPaiement = paiementList.get(paiementList.size() - 1);
        assertThat(testPaiement.getDateTransation()).isEqualTo(UPDATED_DATE_TRANSATION);
        assertThat(testPaiement.getMontantTTC()).isEqualTo(UPDATED_MONTANT_TTC);
    }

    @Test
    @Transactional
    public void updateNonExistingPaiement() throws Exception {
        int databaseSizeBeforeUpdate = paiementRepository.findAll().size();

        // Create the Paiement
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaiementMockMvc.perform(put("/api/paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiementDTO)))
            .andExpect(status().isCreated());

        // Validate the Paiement in the database
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePaiement() throws Exception {
        // Initialize the database
        paiementRepository.saveAndFlush(paiement);
        int databaseSizeBeforeDelete = paiementRepository.findAll().size();

        // Get the paiement
        restPaiementMockMvc.perform(delete("/api/paiements/{id}", paiement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paiement.class);
        Paiement paiement1 = new Paiement();
        paiement1.setId(1L);
        Paiement paiement2 = new Paiement();
        paiement2.setId(paiement1.getId());
        assertThat(paiement1).isEqualTo(paiement2);
        paiement2.setId(2L);
        assertThat(paiement1).isNotEqualTo(paiement2);
        paiement1.setId(null);
        assertThat(paiement1).isNotEqualTo(paiement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaiementDTO.class);
        PaiementDTO paiementDTO1 = new PaiementDTO();
        paiementDTO1.setId(1L);
        PaiementDTO paiementDTO2 = new PaiementDTO();
        assertThat(paiementDTO1).isNotEqualTo(paiementDTO2);
        paiementDTO2.setId(paiementDTO1.getId());
        assertThat(paiementDTO1).isEqualTo(paiementDTO2);
        paiementDTO2.setId(2L);
        assertThat(paiementDTO1).isNotEqualTo(paiementDTO2);
        paiementDTO1.setId(null);
        assertThat(paiementDTO1).isNotEqualTo(paiementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paiementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paiementMapper.fromId(null)).isNull();
    }
}
