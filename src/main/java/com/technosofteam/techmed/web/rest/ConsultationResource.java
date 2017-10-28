package com.technosofteam.techmed.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.technosofteam.techmed.service.ConsultationService;
import com.technosofteam.techmed.web.rest.errors.BadRequestAlertException;
import com.technosofteam.techmed.web.rest.util.HeaderUtil;
import com.technosofteam.techmed.web.rest.util.PaginationUtil;
import com.technosofteam.techmed.service.dto.ConsultationDTO;
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
 * REST controller for managing Consultation.
 */
@RestController
@RequestMapping("/api")
public class ConsultationResource {

    private final Logger log = LoggerFactory.getLogger(ConsultationResource.class);

    private static final String ENTITY_NAME = "consultation";

    private final ConsultationService consultationService;

    public ConsultationResource(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    /**
     * POST  /consultations : Create a new consultation.
     *
     * @param consultationDTO the consultationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new consultationDTO, or with status 400 (Bad Request) if the consultation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/consultations")
    @Timed
    public ResponseEntity<ConsultationDTO> createConsultation(@Valid @RequestBody ConsultationDTO consultationDTO) throws URISyntaxException {
        log.debug("REST request to save Consultation : {}", consultationDTO);
        if (consultationDTO.getId() != null) {
            throw new BadRequestAlertException("A new consultation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsultationDTO result = consultationService.save(consultationDTO);
        return ResponseEntity.created(new URI("/api/consultations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /consultations : Updates an existing consultation.
     *
     * @param consultationDTO the consultationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated consultationDTO,
     * or with status 400 (Bad Request) if the consultationDTO is not valid,
     * or with status 500 (Internal Server Error) if the consultationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/consultations")
    @Timed
    public ResponseEntity<ConsultationDTO> updateConsultation(@Valid @RequestBody ConsultationDTO consultationDTO) throws URISyntaxException {
        log.debug("REST request to update Consultation : {}", consultationDTO);
        if (consultationDTO.getId() == null) {
            return createConsultation(consultationDTO);
        }
        ConsultationDTO result = consultationService.save(consultationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, consultationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consultations : get all the consultations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of consultations in body
     */
    @GetMapping("/consultations")
    @Timed
    public ResponseEntity<List<ConsultationDTO>> getAllConsultations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Consultations");
        Page<ConsultationDTO> page = consultationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/consultations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /consultations/:id : get the "id" consultation.
     *
     * @param id the id of the consultationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the consultationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/consultations/{id}")
    @Timed
    public ResponseEntity<ConsultationDTO> getConsultation(@PathVariable Long id) {
        log.debug("REST request to get Consultation : {}", id);
        ConsultationDTO consultationDTO = consultationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(consultationDTO));
    }

    /**
     * DELETE  /consultations/:id : delete the "id" consultation.
     *
     * @param id the id of the consultationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/consultations/{id}")
    @Timed
    public ResponseEntity<Void> deleteConsultation(@PathVariable Long id) {
        log.debug("REST request to delete Consultation : {}", id);
        consultationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
