package com.technosofteam.techmed.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.technosofteam.techmed.service.TarifService;
import com.technosofteam.techmed.web.rest.errors.BadRequestAlertException;
import com.technosofteam.techmed.web.rest.util.HeaderUtil;
import com.technosofteam.techmed.service.dto.TarifDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tarif.
 */
@RestController
@RequestMapping("/api")
public class TarifResource {

    private final Logger log = LoggerFactory.getLogger(TarifResource.class);

    private static final String ENTITY_NAME = "tarif";

    private final TarifService tarifService;

    public TarifResource(TarifService tarifService) {
        this.tarifService = tarifService;
    }

    /**
     * POST  /tarifs : Create a new tarif.
     *
     * @param tarifDTO the tarifDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tarifDTO, or with status 400 (Bad Request) if the tarif has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tarifs")
    @Timed
    public ResponseEntity<TarifDTO> createTarif(@Valid @RequestBody TarifDTO tarifDTO) throws URISyntaxException {
        log.debug("REST request to save Tarif : {}", tarifDTO);
        if (tarifDTO.getId() != null) {
            throw new BadRequestAlertException("A new tarif cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TarifDTO result = tarifService.save(tarifDTO);
        return ResponseEntity.created(new URI("/api/tarifs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tarifs : Updates an existing tarif.
     *
     * @param tarifDTO the tarifDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tarifDTO,
     * or with status 400 (Bad Request) if the tarifDTO is not valid,
     * or with status 500 (Internal Server Error) if the tarifDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tarifs")
    @Timed
    public ResponseEntity<TarifDTO> updateTarif(@Valid @RequestBody TarifDTO tarifDTO) throws URISyntaxException {
        log.debug("REST request to update Tarif : {}", tarifDTO);
        if (tarifDTO.getId() == null) {
            return createTarif(tarifDTO);
        }
        TarifDTO result = tarifService.save(tarifDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tarifDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tarifs : get all the tarifs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tarifs in body
     */
    @GetMapping("/tarifs")
    @Timed
    public List<TarifDTO> getAllTarifs() {
        log.debug("REST request to get all Tarifs");
        return tarifService.findAll();
        }

    /**
     * GET  /tarifs/:id : get the "id" tarif.
     *
     * @param id the id of the tarifDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tarifDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tarifs/{id}")
    @Timed
    public ResponseEntity<TarifDTO> getTarif(@PathVariable Long id) {
        log.debug("REST request to get Tarif : {}", id);
        TarifDTO tarifDTO = tarifService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tarifDTO));
    }

    /**
     * DELETE  /tarifs/:id : delete the "id" tarif.
     *
     * @param id the id of the tarifDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tarifs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTarif(@PathVariable Long id) {
        log.debug("REST request to delete Tarif : {}", id);
        tarifService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
