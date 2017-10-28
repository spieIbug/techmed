package com.technosofteam.techmed.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.technosofteam.techmed.service.PatientService;
import com.technosofteam.techmed.web.rest.errors.BadRequestAlertException;
import com.technosofteam.techmed.web.rest.util.HeaderUtil;
import com.technosofteam.techmed.web.rest.util.PaginationUtil;
import com.technosofteam.techmed.service.dto.PatientDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Patient.
 */
@RestController
@RequestMapping("/api")
public class PatientResource {

    private final Logger log = LoggerFactory.getLogger(PatientResource.class);

    private static final String ENTITY_NAME = "patient";

    private final PatientService patientService;

    public PatientResource(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * POST  /patients : Create a new patient.
     *
     * @param patientDTO the patientDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patientDTO, or with status 400 (Bad Request) if the patient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patients")
    @Timed
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO patientDTO) throws URISyntaxException {
        log.debug("REST request to save Patient : {}", patientDTO);
        if (patientDTO.getId() != null) {
            throw new BadRequestAlertException("A new patient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientDTO result = patientService.save(patientDTO);
        return ResponseEntity.created(new URI("/api/patients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patients : Updates an existing patient.
     *
     * @param patientDTO the patientDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patientDTO,
     * or with status 400 (Bad Request) if the patientDTO is not valid,
     * or with status 500 (Internal Server Error) if the patientDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patients")
    @Timed
    public ResponseEntity<PatientDTO> updatePatient(@Valid @RequestBody PatientDTO patientDTO) throws URISyntaxException {
        log.debug("REST request to update Patient : {}", patientDTO);
        if (patientDTO.getId() == null) {
            return createPatient(patientDTO);
        }
        PatientDTO result = patientService.save(patientDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, patientDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patients : get all the patients.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of patients in body
     */
    @GetMapping("/patients")
    @Timed
    public ResponseEntity<List<PatientDTO>> getAllPatients(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Patients");
        Page<PatientDTO> page = patientService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/patients");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /patients/:id : get the "id" patient.
     *
     * @param id the id of the patientDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientDTO, or with status 404 (Not Found)
     */
    @GetMapping("/patients/{id}")
    @Timed
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Long id) {
        log.debug("REST request to get Patient : {}", id);
        PatientDTO patientDTO = patientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(patientDTO));
    }

    /**
     * DELETE  /patients/:id : delete the "id" patient.
     *
     * @param id the id of the patientDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patients/{id}")
    @Timed
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        log.debug("REST request to delete Patient : {}", id);
        patientService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
