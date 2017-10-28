package com.technosofteam.techmed.web.rest;

import com.technosofteam.techmed.TechmedApp;

import com.technosofteam.techmed.domain.MoyenPaiement;
import com.technosofteam.techmed.repository.MoyenPaiementRepository;
import com.technosofteam.techmed.service.MoyenPaiementService;
import com.technosofteam.techmed.service.dto.MoyenPaiementDTO;
import com.technosofteam.techmed.service.mapper.MoyenPaiementMapper;
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
import java.util.List;

import static com.technosofteam.techmed.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MoyenPaiementResource REST controller.
 *
 * @see MoyenPaiementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechmedApp.class)
public class MoyenPaiementResourceIntTest {

    private static final String DEFAULT_MODE = "AAAAAAAAAA";
    private static final String UPDATED_MODE = "BBBBBBBBBB";

    @Autowired
    private MoyenPaiementRepository moyenPaiementRepository;

    @Autowired
    private MoyenPaiementMapper moyenPaiementMapper;

    @Autowired
    private MoyenPaiementService moyenPaiementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMoyenPaiementMockMvc;

    private MoyenPaiement moyenPaiement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MoyenPaiementResource moyenPaiementResource = new MoyenPaiementResource(moyenPaiementService);
        this.restMoyenPaiementMockMvc = MockMvcBuilders.standaloneSetup(moyenPaiementResource)
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
    public static MoyenPaiement createEntity(EntityManager em) {
        MoyenPaiement moyenPaiement = new MoyenPaiement()
            .mode(DEFAULT_MODE);
        return moyenPaiement;
    }

    @Before
    public void initTest() {
        moyenPaiement = createEntity(em);
    }

    @Test
    @Transactional
    public void createMoyenPaiement() throws Exception {
        int databaseSizeBeforeCreate = moyenPaiementRepository.findAll().size();

        // Create the MoyenPaiement
        MoyenPaiementDTO moyenPaiementDTO = moyenPaiementMapper.toDto(moyenPaiement);
        restMoyenPaiementMockMvc.perform(post("/api/moyen-paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moyenPaiementDTO)))
            .andExpect(status().isCreated());

        // Validate the MoyenPaiement in the database
        List<MoyenPaiement> moyenPaiementList = moyenPaiementRepository.findAll();
        assertThat(moyenPaiementList).hasSize(databaseSizeBeforeCreate + 1);
        MoyenPaiement testMoyenPaiement = moyenPaiementList.get(moyenPaiementList.size() - 1);
        assertThat(testMoyenPaiement.getMode()).isEqualTo(DEFAULT_MODE);
    }

    @Test
    @Transactional
    public void createMoyenPaiementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moyenPaiementRepository.findAll().size();

        // Create the MoyenPaiement with an existing ID
        moyenPaiement.setId(1L);
        MoyenPaiementDTO moyenPaiementDTO = moyenPaiementMapper.toDto(moyenPaiement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoyenPaiementMockMvc.perform(post("/api/moyen-paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moyenPaiementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MoyenPaiement in the database
        List<MoyenPaiement> moyenPaiementList = moyenPaiementRepository.findAll();
        assertThat(moyenPaiementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkModeIsRequired() throws Exception {
        int databaseSizeBeforeTest = moyenPaiementRepository.findAll().size();
        // set the field null
        moyenPaiement.setMode(null);

        // Create the MoyenPaiement, which fails.
        MoyenPaiementDTO moyenPaiementDTO = moyenPaiementMapper.toDto(moyenPaiement);

        restMoyenPaiementMockMvc.perform(post("/api/moyen-paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moyenPaiementDTO)))
            .andExpect(status().isBadRequest());

        List<MoyenPaiement> moyenPaiementList = moyenPaiementRepository.findAll();
        assertThat(moyenPaiementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMoyenPaiements() throws Exception {
        // Initialize the database
        moyenPaiementRepository.saveAndFlush(moyenPaiement);

        // Get all the moyenPaiementList
        restMoyenPaiementMockMvc.perform(get("/api/moyen-paiements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moyenPaiement.getId().intValue())))
            .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE.toString())));
    }

    @Test
    @Transactional
    public void getMoyenPaiement() throws Exception {
        // Initialize the database
        moyenPaiementRepository.saveAndFlush(moyenPaiement);

        // Get the moyenPaiement
        restMoyenPaiementMockMvc.perform(get("/api/moyen-paiements/{id}", moyenPaiement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(moyenPaiement.getId().intValue()))
            .andExpect(jsonPath("$.mode").value(DEFAULT_MODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMoyenPaiement() throws Exception {
        // Get the moyenPaiement
        restMoyenPaiementMockMvc.perform(get("/api/moyen-paiements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMoyenPaiement() throws Exception {
        // Initialize the database
        moyenPaiementRepository.saveAndFlush(moyenPaiement);
        int databaseSizeBeforeUpdate = moyenPaiementRepository.findAll().size();

        // Update the moyenPaiement
        MoyenPaiement updatedMoyenPaiement = moyenPaiementRepository.findOne(moyenPaiement.getId());
        updatedMoyenPaiement
            .mode(UPDATED_MODE);
        MoyenPaiementDTO moyenPaiementDTO = moyenPaiementMapper.toDto(updatedMoyenPaiement);

        restMoyenPaiementMockMvc.perform(put("/api/moyen-paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moyenPaiementDTO)))
            .andExpect(status().isOk());

        // Validate the MoyenPaiement in the database
        List<MoyenPaiement> moyenPaiementList = moyenPaiementRepository.findAll();
        assertThat(moyenPaiementList).hasSize(databaseSizeBeforeUpdate);
        MoyenPaiement testMoyenPaiement = moyenPaiementList.get(moyenPaiementList.size() - 1);
        assertThat(testMoyenPaiement.getMode()).isEqualTo(UPDATED_MODE);
    }

    @Test
    @Transactional
    public void updateNonExistingMoyenPaiement() throws Exception {
        int databaseSizeBeforeUpdate = moyenPaiementRepository.findAll().size();

        // Create the MoyenPaiement
        MoyenPaiementDTO moyenPaiementDTO = moyenPaiementMapper.toDto(moyenPaiement);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMoyenPaiementMockMvc.perform(put("/api/moyen-paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moyenPaiementDTO)))
            .andExpect(status().isCreated());

        // Validate the MoyenPaiement in the database
        List<MoyenPaiement> moyenPaiementList = moyenPaiementRepository.findAll();
        assertThat(moyenPaiementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMoyenPaiement() throws Exception {
        // Initialize the database
        moyenPaiementRepository.saveAndFlush(moyenPaiement);
        int databaseSizeBeforeDelete = moyenPaiementRepository.findAll().size();

        // Get the moyenPaiement
        restMoyenPaiementMockMvc.perform(delete("/api/moyen-paiements/{id}", moyenPaiement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MoyenPaiement> moyenPaiementList = moyenPaiementRepository.findAll();
        assertThat(moyenPaiementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoyenPaiement.class);
        MoyenPaiement moyenPaiement1 = new MoyenPaiement();
        moyenPaiement1.setId(1L);
        MoyenPaiement moyenPaiement2 = new MoyenPaiement();
        moyenPaiement2.setId(moyenPaiement1.getId());
        assertThat(moyenPaiement1).isEqualTo(moyenPaiement2);
        moyenPaiement2.setId(2L);
        assertThat(moyenPaiement1).isNotEqualTo(moyenPaiement2);
        moyenPaiement1.setId(null);
        assertThat(moyenPaiement1).isNotEqualTo(moyenPaiement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoyenPaiementDTO.class);
        MoyenPaiementDTO moyenPaiementDTO1 = new MoyenPaiementDTO();
        moyenPaiementDTO1.setId(1L);
        MoyenPaiementDTO moyenPaiementDTO2 = new MoyenPaiementDTO();
        assertThat(moyenPaiementDTO1).isNotEqualTo(moyenPaiementDTO2);
        moyenPaiementDTO2.setId(moyenPaiementDTO1.getId());
        assertThat(moyenPaiementDTO1).isEqualTo(moyenPaiementDTO2);
        moyenPaiementDTO2.setId(2L);
        assertThat(moyenPaiementDTO1).isNotEqualTo(moyenPaiementDTO2);
        moyenPaiementDTO1.setId(null);
        assertThat(moyenPaiementDTO1).isNotEqualTo(moyenPaiementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(moyenPaiementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(moyenPaiementMapper.fromId(null)).isNull();
    }
}
