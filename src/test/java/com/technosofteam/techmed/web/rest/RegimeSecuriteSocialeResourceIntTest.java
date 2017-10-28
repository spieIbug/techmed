package com.technosofteam.techmed.web.rest;

import com.technosofteam.techmed.TechmedApp;

import com.technosofteam.techmed.domain.RegimeSecuriteSociale;
import com.technosofteam.techmed.repository.RegimeSecuriteSocialeRepository;
import com.technosofteam.techmed.service.RegimeSecuriteSocialeService;
import com.technosofteam.techmed.service.dto.RegimeSecuriteSocialeDTO;
import com.technosofteam.techmed.service.mapper.RegimeSecuriteSocialeMapper;
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
 * Test class for the RegimeSecuriteSocialeResource REST controller.
 *
 * @see RegimeSecuriteSocialeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechmedApp.class)
public class RegimeSecuriteSocialeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private RegimeSecuriteSocialeRepository regimeSecuriteSocialeRepository;

    @Autowired
    private RegimeSecuriteSocialeMapper regimeSecuriteSocialeMapper;

    @Autowired
    private RegimeSecuriteSocialeService regimeSecuriteSocialeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRegimeSecuriteSocialeMockMvc;

    private RegimeSecuriteSociale regimeSecuriteSociale;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegimeSecuriteSocialeResource regimeSecuriteSocialeResource = new RegimeSecuriteSocialeResource(regimeSecuriteSocialeService);
        this.restRegimeSecuriteSocialeMockMvc = MockMvcBuilders.standaloneSetup(regimeSecuriteSocialeResource)
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
    public static RegimeSecuriteSociale createEntity(EntityManager em) {
        RegimeSecuriteSociale regimeSecuriteSociale = new RegimeSecuriteSociale()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return regimeSecuriteSociale;
    }

    @Before
    public void initTest() {
        regimeSecuriteSociale = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegimeSecuriteSociale() throws Exception {
        int databaseSizeBeforeCreate = regimeSecuriteSocialeRepository.findAll().size();

        // Create the RegimeSecuriteSociale
        RegimeSecuriteSocialeDTO regimeSecuriteSocialeDTO = regimeSecuriteSocialeMapper.toDto(regimeSecuriteSociale);
        restRegimeSecuriteSocialeMockMvc.perform(post("/api/regime-securite-sociales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimeSecuriteSocialeDTO)))
            .andExpect(status().isCreated());

        // Validate the RegimeSecuriteSociale in the database
        List<RegimeSecuriteSociale> regimeSecuriteSocialeList = regimeSecuriteSocialeRepository.findAll();
        assertThat(regimeSecuriteSocialeList).hasSize(databaseSizeBeforeCreate + 1);
        RegimeSecuriteSociale testRegimeSecuriteSociale = regimeSecuriteSocialeList.get(regimeSecuriteSocialeList.size() - 1);
        assertThat(testRegimeSecuriteSociale.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRegimeSecuriteSociale.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createRegimeSecuriteSocialeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regimeSecuriteSocialeRepository.findAll().size();

        // Create the RegimeSecuriteSociale with an existing ID
        regimeSecuriteSociale.setId(1L);
        RegimeSecuriteSocialeDTO regimeSecuriteSocialeDTO = regimeSecuriteSocialeMapper.toDto(regimeSecuriteSociale);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegimeSecuriteSocialeMockMvc.perform(post("/api/regime-securite-sociales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimeSecuriteSocialeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegimeSecuriteSociale in the database
        List<RegimeSecuriteSociale> regimeSecuriteSocialeList = regimeSecuriteSocialeRepository.findAll();
        assertThat(regimeSecuriteSocialeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = regimeSecuriteSocialeRepository.findAll().size();
        // set the field null
        regimeSecuriteSociale.setCode(null);

        // Create the RegimeSecuriteSociale, which fails.
        RegimeSecuriteSocialeDTO regimeSecuriteSocialeDTO = regimeSecuriteSocialeMapper.toDto(regimeSecuriteSociale);

        restRegimeSecuriteSocialeMockMvc.perform(post("/api/regime-securite-sociales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimeSecuriteSocialeDTO)))
            .andExpect(status().isBadRequest());

        List<RegimeSecuriteSociale> regimeSecuriteSocialeList = regimeSecuriteSocialeRepository.findAll();
        assertThat(regimeSecuriteSocialeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = regimeSecuriteSocialeRepository.findAll().size();
        // set the field null
        regimeSecuriteSociale.setLibelle(null);

        // Create the RegimeSecuriteSociale, which fails.
        RegimeSecuriteSocialeDTO regimeSecuriteSocialeDTO = regimeSecuriteSocialeMapper.toDto(regimeSecuriteSociale);

        restRegimeSecuriteSocialeMockMvc.perform(post("/api/regime-securite-sociales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimeSecuriteSocialeDTO)))
            .andExpect(status().isBadRequest());

        List<RegimeSecuriteSociale> regimeSecuriteSocialeList = regimeSecuriteSocialeRepository.findAll();
        assertThat(regimeSecuriteSocialeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegimeSecuriteSociales() throws Exception {
        // Initialize the database
        regimeSecuriteSocialeRepository.saveAndFlush(regimeSecuriteSociale);

        // Get all the regimeSecuriteSocialeList
        restRegimeSecuriteSocialeMockMvc.perform(get("/api/regime-securite-sociales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regimeSecuriteSociale.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getRegimeSecuriteSociale() throws Exception {
        // Initialize the database
        regimeSecuriteSocialeRepository.saveAndFlush(regimeSecuriteSociale);

        // Get the regimeSecuriteSociale
        restRegimeSecuriteSocialeMockMvc.perform(get("/api/regime-securite-sociales/{id}", regimeSecuriteSociale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(regimeSecuriteSociale.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRegimeSecuriteSociale() throws Exception {
        // Get the regimeSecuriteSociale
        restRegimeSecuriteSocialeMockMvc.perform(get("/api/regime-securite-sociales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegimeSecuriteSociale() throws Exception {
        // Initialize the database
        regimeSecuriteSocialeRepository.saveAndFlush(regimeSecuriteSociale);
        int databaseSizeBeforeUpdate = regimeSecuriteSocialeRepository.findAll().size();

        // Update the regimeSecuriteSociale
        RegimeSecuriteSociale updatedRegimeSecuriteSociale = regimeSecuriteSocialeRepository.findOne(regimeSecuriteSociale.getId());
        updatedRegimeSecuriteSociale
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        RegimeSecuriteSocialeDTO regimeSecuriteSocialeDTO = regimeSecuriteSocialeMapper.toDto(updatedRegimeSecuriteSociale);

        restRegimeSecuriteSocialeMockMvc.perform(put("/api/regime-securite-sociales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimeSecuriteSocialeDTO)))
            .andExpect(status().isOk());

        // Validate the RegimeSecuriteSociale in the database
        List<RegimeSecuriteSociale> regimeSecuriteSocialeList = regimeSecuriteSocialeRepository.findAll();
        assertThat(regimeSecuriteSocialeList).hasSize(databaseSizeBeforeUpdate);
        RegimeSecuriteSociale testRegimeSecuriteSociale = regimeSecuriteSocialeList.get(regimeSecuriteSocialeList.size() - 1);
        assertThat(testRegimeSecuriteSociale.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRegimeSecuriteSociale.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingRegimeSecuriteSociale() throws Exception {
        int databaseSizeBeforeUpdate = regimeSecuriteSocialeRepository.findAll().size();

        // Create the RegimeSecuriteSociale
        RegimeSecuriteSocialeDTO regimeSecuriteSocialeDTO = regimeSecuriteSocialeMapper.toDto(regimeSecuriteSociale);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRegimeSecuriteSocialeMockMvc.perform(put("/api/regime-securite-sociales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimeSecuriteSocialeDTO)))
            .andExpect(status().isCreated());

        // Validate the RegimeSecuriteSociale in the database
        List<RegimeSecuriteSociale> regimeSecuriteSocialeList = regimeSecuriteSocialeRepository.findAll();
        assertThat(regimeSecuriteSocialeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRegimeSecuriteSociale() throws Exception {
        // Initialize the database
        regimeSecuriteSocialeRepository.saveAndFlush(regimeSecuriteSociale);
        int databaseSizeBeforeDelete = regimeSecuriteSocialeRepository.findAll().size();

        // Get the regimeSecuriteSociale
        restRegimeSecuriteSocialeMockMvc.perform(delete("/api/regime-securite-sociales/{id}", regimeSecuriteSociale.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RegimeSecuriteSociale> regimeSecuriteSocialeList = regimeSecuriteSocialeRepository.findAll();
        assertThat(regimeSecuriteSocialeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegimeSecuriteSociale.class);
        RegimeSecuriteSociale regimeSecuriteSociale1 = new RegimeSecuriteSociale();
        regimeSecuriteSociale1.setId(1L);
        RegimeSecuriteSociale regimeSecuriteSociale2 = new RegimeSecuriteSociale();
        regimeSecuriteSociale2.setId(regimeSecuriteSociale1.getId());
        assertThat(regimeSecuriteSociale1).isEqualTo(regimeSecuriteSociale2);
        regimeSecuriteSociale2.setId(2L);
        assertThat(regimeSecuriteSociale1).isNotEqualTo(regimeSecuriteSociale2);
        regimeSecuriteSociale1.setId(null);
        assertThat(regimeSecuriteSociale1).isNotEqualTo(regimeSecuriteSociale2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegimeSecuriteSocialeDTO.class);
        RegimeSecuriteSocialeDTO regimeSecuriteSocialeDTO1 = new RegimeSecuriteSocialeDTO();
        regimeSecuriteSocialeDTO1.setId(1L);
        RegimeSecuriteSocialeDTO regimeSecuriteSocialeDTO2 = new RegimeSecuriteSocialeDTO();
        assertThat(regimeSecuriteSocialeDTO1).isNotEqualTo(regimeSecuriteSocialeDTO2);
        regimeSecuriteSocialeDTO2.setId(regimeSecuriteSocialeDTO1.getId());
        assertThat(regimeSecuriteSocialeDTO1).isEqualTo(regimeSecuriteSocialeDTO2);
        regimeSecuriteSocialeDTO2.setId(2L);
        assertThat(regimeSecuriteSocialeDTO1).isNotEqualTo(regimeSecuriteSocialeDTO2);
        regimeSecuriteSocialeDTO1.setId(null);
        assertThat(regimeSecuriteSocialeDTO1).isNotEqualTo(regimeSecuriteSocialeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(regimeSecuriteSocialeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(regimeSecuriteSocialeMapper.fromId(null)).isNull();
    }
}
