package com.technosofteam.techmed.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.technosofteam.techmed.service.ActeMedicalService;
import com.technosofteam.techmed.web.rest.errors.BadRequestAlertException;
import com.technosofteam.techmed.web.rest.util.HeaderUtil;
import com.technosofteam.techmed.web.rest.util.PaginationUtil;
import com.technosofteam.techmed.service.dto.ActeMedicalDTO;
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
 * REST controller for managing ActeMedical.
 */
@RestController
@RequestMapping("/api")
public class ActeMedicalResource {

    private final Logger log = LoggerFactory.getLogger(ActeMedicalResource.class);

    private static final String ENTITY_NAME = "acteMedical";

    private final ActeMedicalService acteMedicalService;

    public ActeMedicalResource(ActeMedicalService acteMedicalService) {
        this.acteMedicalService = acteMedicalService;
    }

    /**
     * POST  /acte-medicals : Create a new acteMedical.
     *
     * @param acteMedicalDTO the acteMedicalDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new acteMedicalDTO, or with status 400 (Bad Request) if the acteMedical has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/acte-medicals")
    @Timed
    public ResponseEntity<ActeMedicalDTO> createActeMedical(@Valid @RequestBody ActeMedicalDTO acteMedicalDTO) throws URISyntaxException {
        log.debug("REST request to save ActeMedical : {}", acteMedicalDTO);
        if (acteMedicalDTO.getId() != null) {
            throw new BadRequestAlertException("A new acteMedical cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActeMedicalDTO result = acteMedicalService.save(acteMedicalDTO);
        return ResponseEntity.created(new URI("/api/acte-medicals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /acte-medicals : Updates an existing acteMedical.
     *
     * @param acteMedicalDTO the acteMedicalDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated acteMedicalDTO,
     * or with status 400 (Bad Request) if the acteMedicalDTO is not valid,
     * or with status 500 (Internal Server Error) if the acteMedicalDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/acte-medicals")
    @Timed
    public ResponseEntity<ActeMedicalDTO> updateActeMedical(@Valid @RequestBody ActeMedicalDTO acteMedicalDTO) throws URISyntaxException {
        log.debug("REST request to update ActeMedical : {}", acteMedicalDTO);
        if (acteMedicalDTO.getId() == null) {
            return createActeMedical(acteMedicalDTO);
        }
        ActeMedicalDTO result = acteMedicalService.save(acteMedicalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, acteMedicalDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /acte-medicals : get all the acteMedicals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of acteMedicals in body
     */
    @GetMapping("/acte-medicals")
    @Timed
    public ResponseEntity<List<ActeMedicalDTO>> getAllActeMedicals(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ActeMedicals");
        Page<ActeMedicalDTO> page = acteMedicalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/acte-medicals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /acte-medicals/:id : get the "id" acteMedical.
     *
     * @param id the id of the acteMedicalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the acteMedicalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/acte-medicals/{id}")
    @Timed
    public ResponseEntity<ActeMedicalDTO> getActeMedical(@PathVariable Long id) {
        log.debug("REST request to get ActeMedical : {}", id);
        ActeMedicalDTO acteMedicalDTO = acteMedicalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(acteMedicalDTO));
    }

    /**
     * DELETE  /acte-medicals/:id : delete the "id" acteMedical.
     *
     * @param id the id of the acteMedicalDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/acte-medicals/{id}")
    @Timed
    public ResponseEntity<Void> deleteActeMedical(@PathVariable Long id) {
        log.debug("REST request to delete ActeMedical : {}", id);
        acteMedicalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
