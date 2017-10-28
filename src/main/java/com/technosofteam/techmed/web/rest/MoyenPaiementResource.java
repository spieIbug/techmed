package com.technosofteam.techmed.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.technosofteam.techmed.service.MoyenPaiementService;
import com.technosofteam.techmed.web.rest.errors.BadRequestAlertException;
import com.technosofteam.techmed.web.rest.util.HeaderUtil;
import com.technosofteam.techmed.web.rest.util.PaginationUtil;
import com.technosofteam.techmed.service.dto.MoyenPaiementDTO;
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
 * REST controller for managing MoyenPaiement.
 */
@RestController
@RequestMapping("/api")
public class MoyenPaiementResource {

    private final Logger log = LoggerFactory.getLogger(MoyenPaiementResource.class);

    private static final String ENTITY_NAME = "moyenPaiement";

    private final MoyenPaiementService moyenPaiementService;

    public MoyenPaiementResource(MoyenPaiementService moyenPaiementService) {
        this.moyenPaiementService = moyenPaiementService;
    }

    /**
     * POST  /moyen-paiements : Create a new moyenPaiement.
     *
     * @param moyenPaiementDTO the moyenPaiementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new moyenPaiementDTO, or with status 400 (Bad Request) if the moyenPaiement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/moyen-paiements")
    @Timed
    public ResponseEntity<MoyenPaiementDTO> createMoyenPaiement(@Valid @RequestBody MoyenPaiementDTO moyenPaiementDTO) throws URISyntaxException {
        log.debug("REST request to save MoyenPaiement : {}", moyenPaiementDTO);
        if (moyenPaiementDTO.getId() != null) {
            throw new BadRequestAlertException("A new moyenPaiement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MoyenPaiementDTO result = moyenPaiementService.save(moyenPaiementDTO);
        return ResponseEntity.created(new URI("/api/moyen-paiements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /moyen-paiements : Updates an existing moyenPaiement.
     *
     * @param moyenPaiementDTO the moyenPaiementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated moyenPaiementDTO,
     * or with status 400 (Bad Request) if the moyenPaiementDTO is not valid,
     * or with status 500 (Internal Server Error) if the moyenPaiementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/moyen-paiements")
    @Timed
    public ResponseEntity<MoyenPaiementDTO> updateMoyenPaiement(@Valid @RequestBody MoyenPaiementDTO moyenPaiementDTO) throws URISyntaxException {
        log.debug("REST request to update MoyenPaiement : {}", moyenPaiementDTO);
        if (moyenPaiementDTO.getId() == null) {
            return createMoyenPaiement(moyenPaiementDTO);
        }
        MoyenPaiementDTO result = moyenPaiementService.save(moyenPaiementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, moyenPaiementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /moyen-paiements : get all the moyenPaiements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of moyenPaiements in body
     */
    @GetMapping("/moyen-paiements")
    @Timed
    public ResponseEntity<List<MoyenPaiementDTO>> getAllMoyenPaiements(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MoyenPaiements");
        Page<MoyenPaiementDTO> page = moyenPaiementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/moyen-paiements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /moyen-paiements/:id : get the "id" moyenPaiement.
     *
     * @param id the id of the moyenPaiementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the moyenPaiementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/moyen-paiements/{id}")
    @Timed
    public ResponseEntity<MoyenPaiementDTO> getMoyenPaiement(@PathVariable Long id) {
        log.debug("REST request to get MoyenPaiement : {}", id);
        MoyenPaiementDTO moyenPaiementDTO = moyenPaiementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(moyenPaiementDTO));
    }

    /**
     * DELETE  /moyen-paiements/:id : delete the "id" moyenPaiement.
     *
     * @param id the id of the moyenPaiementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/moyen-paiements/{id}")
    @Timed
    public ResponseEntity<Void> deleteMoyenPaiement(@PathVariable Long id) {
        log.debug("REST request to delete MoyenPaiement : {}", id);
        moyenPaiementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
