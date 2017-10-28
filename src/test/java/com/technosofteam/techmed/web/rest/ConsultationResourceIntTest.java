package com.technosofteam.techmed.web.rest;

import com.technosofteam.techmed.TechmedApp;

import com.technosofteam.techmed.domain.Consultation;
import com.technosofteam.techmed.repository.ConsultationRepository;
import com.technosofteam.techmed.service.ConsultationService;
import com.technosofteam.techmed.service.dto.ConsultationDTO;
import com.technosofteam.techmed.service.mapper.ConsultationMapper;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the ConsultationResource REST controller.
 *
 * @see ConsultationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechmedApp.class)
public class ConsultationResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE_ACTE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_ACTE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_MONTANT_TTC = 1D;
    private static final Double UPDATED_MONTANT_TTC = 2D;

    private static final String DEFAULT_LOCK = "AAAAAAAAAA";
    private static final String UPDATED_LOCK = "BBBBBBBBBB";

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private ConsultationMapper consultationMapper;

    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConsultationMockMvc;

    private Consultation consultation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsultationResource consultationResource = new ConsultationResource(consultationService);
        this.restConsultationMockMvc = MockMvcBuilders.standaloneSetup(consultationResource)
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
    public static Consultation createEntity(EntityManager em) {
        Consultation consultation = new Consultation()
            .dateActe(DEFAULT_DATE_ACTE)
            .montantTTC(DEFAULT_MONTANT_TTC)
            .lock(DEFAULT_LOCK);
        return consultation;
    }

    @Before
    public void initTest() {
        consultation = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsultation() throws Exception {
        int databaseSizeBeforeCreate = consultationRepository.findAll().size();

        // Create the Consultation
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);
        restConsultationMockMvc.perform(post("/api/consultations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultationDTO)))
            .andExpect(status().isCreated());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeCreate + 1);
        Consultation testConsultation = consultationList.get(consultationList.size() - 1);
        assertThat(testConsultation.getDateActe()).isEqualTo(DEFAULT_DATE_ACTE);
        assertThat(testConsultation.getMontantTTC()).isEqualTo(DEFAULT_MONTANT_TTC);
        assertThat(testConsultation.getLock()).isEqualTo(DEFAULT_LOCK);
    }

    @Test
    @Transactional
    public void createConsultationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultationRepository.findAll().size();

        // Create the Consultation with an existing ID
        consultation.setId(1L);
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultationMockMvc.perform(post("/api/consultations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateActeIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultationRepository.findAll().size();
        // set the field null
        consultation.setDateActe(null);

        // Create the Consultation, which fails.
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);

        restConsultationMockMvc.perform(post("/api/consultations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultationDTO)))
            .andExpect(status().isBadRequest());

        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontantTTCIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultationRepository.findAll().size();
        // set the field null
        consultation.setMontantTTC(null);

        // Create the Consultation, which fails.
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);

        restConsultationMockMvc.perform(post("/api/consultations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultationDTO)))
            .andExpect(status().isBadRequest());

        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConsultations() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

        // Get all the consultationList
        restConsultationMockMvc.perform(get("/api/consultations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultation.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateActe").value(hasItem(sameInstant(DEFAULT_DATE_ACTE))))
            .andExpect(jsonPath("$.[*].montantTTC").value(hasItem(DEFAULT_MONTANT_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].lock").value(hasItem(DEFAULT_LOCK.toString())));
    }

    @Test
    @Transactional
    public void getConsultation() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);

        // Get the consultation
        restConsultationMockMvc.perform(get("/api/consultations/{id}", consultation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consultation.getId().intValue()))
            .andExpect(jsonPath("$.dateActe").value(sameInstant(DEFAULT_DATE_ACTE)))
            .andExpect(jsonPath("$.montantTTC").value(DEFAULT_MONTANT_TTC.doubleValue()))
            .andExpect(jsonPath("$.lock").value(DEFAULT_LOCK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConsultation() throws Exception {
        // Get the consultation
        restConsultationMockMvc.perform(get("/api/consultations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsultation() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);
        int databaseSizeBeforeUpdate = consultationRepository.findAll().size();

        // Update the consultation
        Consultation updatedConsultation = consultationRepository.findOne(consultation.getId());
        updatedConsultation
            .dateActe(UPDATED_DATE_ACTE)
            .montantTTC(UPDATED_MONTANT_TTC)
            .lock(UPDATED_LOCK);
        ConsultationDTO consultationDTO = consultationMapper.toDto(updatedConsultation);

        restConsultationMockMvc.perform(put("/api/consultations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultationDTO)))
            .andExpect(status().isOk());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeUpdate);
        Consultation testConsultation = consultationList.get(consultationList.size() - 1);
        assertThat(testConsultation.getDateActe()).isEqualTo(UPDATED_DATE_ACTE);
        assertThat(testConsultation.getMontantTTC()).isEqualTo(UPDATED_MONTANT_TTC);
        assertThat(testConsultation.getLock()).isEqualTo(UPDATED_LOCK);
    }

    @Test
    @Transactional
    public void updateNonExistingConsultation() throws Exception {
        int databaseSizeBeforeUpdate = consultationRepository.findAll().size();

        // Create the Consultation
        ConsultationDTO consultationDTO = consultationMapper.toDto(consultation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConsultationMockMvc.perform(put("/api/consultations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultationDTO)))
            .andExpect(status().isCreated());

        // Validate the Consultation in the database
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConsultation() throws Exception {
        // Initialize the database
        consultationRepository.saveAndFlush(consultation);
        int databaseSizeBeforeDelete = consultationRepository.findAll().size();

        // Get the consultation
        restConsultationMockMvc.perform(delete("/api/consultations/{id}", consultation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Consultation> consultationList = consultationRepository.findAll();
        assertThat(consultationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Consultation.class);
        Consultation consultation1 = new Consultation();
        consultation1.setId(1L);
        Consultation consultation2 = new Consultation();
        consultation2.setId(consultation1.getId());
        assertThat(consultation1).isEqualTo(consultation2);
        consultation2.setId(2L);
        assertThat(consultation1).isNotEqualTo(consultation2);
        consultation1.setId(null);
        assertThat(consultation1).isNotEqualTo(consultation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultationDTO.class);
        ConsultationDTO consultationDTO1 = new ConsultationDTO();
        consultationDTO1.setId(1L);
        ConsultationDTO consultationDTO2 = new ConsultationDTO();
        assertThat(consultationDTO1).isNotEqualTo(consultationDTO2);
        consultationDTO2.setId(consultationDTO1.getId());
        assertThat(consultationDTO1).isEqualTo(consultationDTO2);
        consultationDTO2.setId(2L);
        assertThat(consultationDTO1).isNotEqualTo(consultationDTO2);
        consultationDTO1.setId(null);
        assertThat(consultationDTO1).isNotEqualTo(consultationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(consultationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(consultationMapper.fromId(null)).isNull();
    }
}
