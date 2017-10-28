package com.technosofteam.techmed.web.rest;

import com.technosofteam.techmed.TechmedApp;

import com.technosofteam.techmed.domain.ActeMedical;
import com.technosofteam.techmed.repository.ActeMedicalRepository;
import com.technosofteam.techmed.service.ActeMedicalService;
import com.technosofteam.techmed.service.dto.ActeMedicalDTO;
import com.technosofteam.techmed.service.mapper.ActeMedicalMapper;
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
 * Test class for the ActeMedicalResource REST controller.
 *
 * @see ActeMedicalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechmedApp.class)
public class ActeMedicalResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private ActeMedicalRepository acteMedicalRepository;

    @Autowired
    private ActeMedicalMapper acteMedicalMapper;

    @Autowired
    private ActeMedicalService acteMedicalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActeMedicalMockMvc;

    private ActeMedical acteMedical;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActeMedicalResource acteMedicalResource = new ActeMedicalResource(acteMedicalService);
        this.restActeMedicalMockMvc = MockMvcBuilders.standaloneSetup(acteMedicalResource)
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
    public static ActeMedical createEntity(EntityManager em) {
        ActeMedical acteMedical = new ActeMedical()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return acteMedical;
    }

    @Before
    public void initTest() {
        acteMedical = createEntity(em);
    }

    @Test
    @Transactional
    public void createActeMedical() throws Exception {
        int databaseSizeBeforeCreate = acteMedicalRepository.findAll().size();

        // Create the ActeMedical
        ActeMedicalDTO acteMedicalDTO = acteMedicalMapper.toDto(acteMedical);
        restActeMedicalMockMvc.perform(post("/api/acte-medicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acteMedicalDTO)))
            .andExpect(status().isCreated());

        // Validate the ActeMedical in the database
        List<ActeMedical> acteMedicalList = acteMedicalRepository.findAll();
        assertThat(acteMedicalList).hasSize(databaseSizeBeforeCreate + 1);
        ActeMedical testActeMedical = acteMedicalList.get(acteMedicalList.size() - 1);
        assertThat(testActeMedical.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testActeMedical.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createActeMedicalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = acteMedicalRepository.findAll().size();

        // Create the ActeMedical with an existing ID
        acteMedical.setId(1L);
        ActeMedicalDTO acteMedicalDTO = acteMedicalMapper.toDto(acteMedical);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActeMedicalMockMvc.perform(post("/api/acte-medicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acteMedicalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActeMedical in the database
        List<ActeMedical> acteMedicalList = acteMedicalRepository.findAll();
        assertThat(acteMedicalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = acteMedicalRepository.findAll().size();
        // set the field null
        acteMedical.setCode(null);

        // Create the ActeMedical, which fails.
        ActeMedicalDTO acteMedicalDTO = acteMedicalMapper.toDto(acteMedical);

        restActeMedicalMockMvc.perform(post("/api/acte-medicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acteMedicalDTO)))
            .andExpect(status().isBadRequest());

        List<ActeMedical> acteMedicalList = acteMedicalRepository.findAll();
        assertThat(acteMedicalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = acteMedicalRepository.findAll().size();
        // set the field null
        acteMedical.setLibelle(null);

        // Create the ActeMedical, which fails.
        ActeMedicalDTO acteMedicalDTO = acteMedicalMapper.toDto(acteMedical);

        restActeMedicalMockMvc.perform(post("/api/acte-medicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acteMedicalDTO)))
            .andExpect(status().isBadRequest());

        List<ActeMedical> acteMedicalList = acteMedicalRepository.findAll();
        assertThat(acteMedicalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActeMedicals() throws Exception {
        // Initialize the database
        acteMedicalRepository.saveAndFlush(acteMedical);

        // Get all the acteMedicalList
        restActeMedicalMockMvc.perform(get("/api/acte-medicals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acteMedical.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getActeMedical() throws Exception {
        // Initialize the database
        acteMedicalRepository.saveAndFlush(acteMedical);

        // Get the acteMedical
        restActeMedicalMockMvc.perform(get("/api/acte-medicals/{id}", acteMedical.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(acteMedical.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActeMedical() throws Exception {
        // Get the acteMedical
        restActeMedicalMockMvc.perform(get("/api/acte-medicals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActeMedical() throws Exception {
        // Initialize the database
        acteMedicalRepository.saveAndFlush(acteMedical);
        int databaseSizeBeforeUpdate = acteMedicalRepository.findAll().size();

        // Update the acteMedical
        ActeMedical updatedActeMedical = acteMedicalRepository.findOne(acteMedical.getId());
        updatedActeMedical
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        ActeMedicalDTO acteMedicalDTO = acteMedicalMapper.toDto(updatedActeMedical);

        restActeMedicalMockMvc.perform(put("/api/acte-medicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acteMedicalDTO)))
            .andExpect(status().isOk());

        // Validate the ActeMedical in the database
        List<ActeMedical> acteMedicalList = acteMedicalRepository.findAll();
        assertThat(acteMedicalList).hasSize(databaseSizeBeforeUpdate);
        ActeMedical testActeMedical = acteMedicalList.get(acteMedicalList.size() - 1);
        assertThat(testActeMedical.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testActeMedical.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingActeMedical() throws Exception {
        int databaseSizeBeforeUpdate = acteMedicalRepository.findAll().size();

        // Create the ActeMedical
        ActeMedicalDTO acteMedicalDTO = acteMedicalMapper.toDto(acteMedical);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActeMedicalMockMvc.perform(put("/api/acte-medicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acteMedicalDTO)))
            .andExpect(status().isCreated());

        // Validate the ActeMedical in the database
        List<ActeMedical> acteMedicalList = acteMedicalRepository.findAll();
        assertThat(acteMedicalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActeMedical() throws Exception {
        // Initialize the database
        acteMedicalRepository.saveAndFlush(acteMedical);
        int databaseSizeBeforeDelete = acteMedicalRepository.findAll().size();

        // Get the acteMedical
        restActeMedicalMockMvc.perform(delete("/api/acte-medicals/{id}", acteMedical.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActeMedical> acteMedicalList = acteMedicalRepository.findAll();
        assertThat(acteMedicalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActeMedical.class);
        ActeMedical acteMedical1 = new ActeMedical();
        acteMedical1.setId(1L);
        ActeMedical acteMedical2 = new ActeMedical();
        acteMedical2.setId(acteMedical1.getId());
        assertThat(acteMedical1).isEqualTo(acteMedical2);
        acteMedical2.setId(2L);
        assertThat(acteMedical1).isNotEqualTo(acteMedical2);
        acteMedical1.setId(null);
        assertThat(acteMedical1).isNotEqualTo(acteMedical2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActeMedicalDTO.class);
        ActeMedicalDTO acteMedicalDTO1 = new ActeMedicalDTO();
        acteMedicalDTO1.setId(1L);
        ActeMedicalDTO acteMedicalDTO2 = new ActeMedicalDTO();
        assertThat(acteMedicalDTO1).isNotEqualTo(acteMedicalDTO2);
        acteMedicalDTO2.setId(acteMedicalDTO1.getId());
        assertThat(acteMedicalDTO1).isEqualTo(acteMedicalDTO2);
        acteMedicalDTO2.setId(2L);
        assertThat(acteMedicalDTO1).isNotEqualTo(acteMedicalDTO2);
        acteMedicalDTO1.setId(null);
        assertThat(acteMedicalDTO1).isNotEqualTo(acteMedicalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(acteMedicalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(acteMedicalMapper.fromId(null)).isNull();
    }
}
