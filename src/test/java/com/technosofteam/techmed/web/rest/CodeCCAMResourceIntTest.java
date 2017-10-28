package com.technosofteam.techmed.web.rest;

import com.technosofteam.techmed.TechmedApp;

import com.technosofteam.techmed.domain.CodeCCAM;
import com.technosofteam.techmed.repository.CodeCCAMRepository;
import com.technosofteam.techmed.service.CodeCCAMService;
import com.technosofteam.techmed.service.dto.CodeCCAMDTO;
import com.technosofteam.techmed.service.mapper.CodeCCAMMapper;
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
 * Test class for the CodeCCAMResource REST controller.
 *
 * @see CodeCCAMResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TechmedApp.class)
public class CodeCCAMResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private CodeCCAMRepository codeCCAMRepository;

    @Autowired
    private CodeCCAMMapper codeCCAMMapper;

    @Autowired
    private CodeCCAMService codeCCAMService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCodeCCAMMockMvc;

    private CodeCCAM codeCCAM;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CodeCCAMResource codeCCAMResource = new CodeCCAMResource(codeCCAMService);
        this.restCodeCCAMMockMvc = MockMvcBuilders.standaloneSetup(codeCCAMResource)
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
    public static CodeCCAM createEntity(EntityManager em) {
        CodeCCAM codeCCAM = new CodeCCAM()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return codeCCAM;
    }

    @Before
    public void initTest() {
        codeCCAM = createEntity(em);
    }

    @Test
    @Transactional
    public void createCodeCCAM() throws Exception {
        int databaseSizeBeforeCreate = codeCCAMRepository.findAll().size();

        // Create the CodeCCAM
        CodeCCAMDTO codeCCAMDTO = codeCCAMMapper.toDto(codeCCAM);
        restCodeCCAMMockMvc.perform(post("/api/code-ccams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codeCCAMDTO)))
            .andExpect(status().isCreated());

        // Validate the CodeCCAM in the database
        List<CodeCCAM> codeCCAMList = codeCCAMRepository.findAll();
        assertThat(codeCCAMList).hasSize(databaseSizeBeforeCreate + 1);
        CodeCCAM testCodeCCAM = codeCCAMList.get(codeCCAMList.size() - 1);
        assertThat(testCodeCCAM.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCodeCCAM.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createCodeCCAMWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = codeCCAMRepository.findAll().size();

        // Create the CodeCCAM with an existing ID
        codeCCAM.setId(1L);
        CodeCCAMDTO codeCCAMDTO = codeCCAMMapper.toDto(codeCCAM);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodeCCAMMockMvc.perform(post("/api/code-ccams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codeCCAMDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CodeCCAM in the database
        List<CodeCCAM> codeCCAMList = codeCCAMRepository.findAll();
        assertThat(codeCCAMList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = codeCCAMRepository.findAll().size();
        // set the field null
        codeCCAM.setCode(null);

        // Create the CodeCCAM, which fails.
        CodeCCAMDTO codeCCAMDTO = codeCCAMMapper.toDto(codeCCAM);

        restCodeCCAMMockMvc.perform(post("/api/code-ccams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codeCCAMDTO)))
            .andExpect(status().isBadRequest());

        List<CodeCCAM> codeCCAMList = codeCCAMRepository.findAll();
        assertThat(codeCCAMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = codeCCAMRepository.findAll().size();
        // set the field null
        codeCCAM.setLibelle(null);

        // Create the CodeCCAM, which fails.
        CodeCCAMDTO codeCCAMDTO = codeCCAMMapper.toDto(codeCCAM);

        restCodeCCAMMockMvc.perform(post("/api/code-ccams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codeCCAMDTO)))
            .andExpect(status().isBadRequest());

        List<CodeCCAM> codeCCAMList = codeCCAMRepository.findAll();
        assertThat(codeCCAMList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCodeCCAMS() throws Exception {
        // Initialize the database
        codeCCAMRepository.saveAndFlush(codeCCAM);

        // Get all the codeCCAMList
        restCodeCCAMMockMvc.perform(get("/api/code-ccams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codeCCAM.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getCodeCCAM() throws Exception {
        // Initialize the database
        codeCCAMRepository.saveAndFlush(codeCCAM);

        // Get the codeCCAM
        restCodeCCAMMockMvc.perform(get("/api/code-ccams/{id}", codeCCAM.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(codeCCAM.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCodeCCAM() throws Exception {
        // Get the codeCCAM
        restCodeCCAMMockMvc.perform(get("/api/code-ccams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCodeCCAM() throws Exception {
        // Initialize the database
        codeCCAMRepository.saveAndFlush(codeCCAM);
        int databaseSizeBeforeUpdate = codeCCAMRepository.findAll().size();

        // Update the codeCCAM
        CodeCCAM updatedCodeCCAM = codeCCAMRepository.findOne(codeCCAM.getId());
        updatedCodeCCAM
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        CodeCCAMDTO codeCCAMDTO = codeCCAMMapper.toDto(updatedCodeCCAM);

        restCodeCCAMMockMvc.perform(put("/api/code-ccams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codeCCAMDTO)))
            .andExpect(status().isOk());

        // Validate the CodeCCAM in the database
        List<CodeCCAM> codeCCAMList = codeCCAMRepository.findAll();
        assertThat(codeCCAMList).hasSize(databaseSizeBeforeUpdate);
        CodeCCAM testCodeCCAM = codeCCAMList.get(codeCCAMList.size() - 1);
        assertThat(testCodeCCAM.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCodeCCAM.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingCodeCCAM() throws Exception {
        int databaseSizeBeforeUpdate = codeCCAMRepository.findAll().size();

        // Create the CodeCCAM
        CodeCCAMDTO codeCCAMDTO = codeCCAMMapper.toDto(codeCCAM);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCodeCCAMMockMvc.perform(put("/api/code-ccams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(codeCCAMDTO)))
            .andExpect(status().isCreated());

        // Validate the CodeCCAM in the database
        List<CodeCCAM> codeCCAMList = codeCCAMRepository.findAll();
        assertThat(codeCCAMList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCodeCCAM() throws Exception {
        // Initialize the database
        codeCCAMRepository.saveAndFlush(codeCCAM);
        int databaseSizeBeforeDelete = codeCCAMRepository.findAll().size();

        // Get the codeCCAM
        restCodeCCAMMockMvc.perform(delete("/api/code-ccams/{id}", codeCCAM.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CodeCCAM> codeCCAMList = codeCCAMRepository.findAll();
        assertThat(codeCCAMList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodeCCAM.class);
        CodeCCAM codeCCAM1 = new CodeCCAM();
        codeCCAM1.setId(1L);
        CodeCCAM codeCCAM2 = new CodeCCAM();
        codeCCAM2.setId(codeCCAM1.getId());
        assertThat(codeCCAM1).isEqualTo(codeCCAM2);
        codeCCAM2.setId(2L);
        assertThat(codeCCAM1).isNotEqualTo(codeCCAM2);
        codeCCAM1.setId(null);
        assertThat(codeCCAM1).isNotEqualTo(codeCCAM2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodeCCAMDTO.class);
        CodeCCAMDTO codeCCAMDTO1 = new CodeCCAMDTO();
        codeCCAMDTO1.setId(1L);
        CodeCCAMDTO codeCCAMDTO2 = new CodeCCAMDTO();
        assertThat(codeCCAMDTO1).isNotEqualTo(codeCCAMDTO2);
        codeCCAMDTO2.setId(codeCCAMDTO1.getId());
        assertThat(codeCCAMDTO1).isEqualTo(codeCCAMDTO2);
        codeCCAMDTO2.setId(2L);
        assertThat(codeCCAMDTO1).isNotEqualTo(codeCCAMDTO2);
        codeCCAMDTO1.setId(null);
        assertThat(codeCCAMDTO1).isNotEqualTo(codeCCAMDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(codeCCAMMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(codeCCAMMapper.fromId(null)).isNull();
    }
}
