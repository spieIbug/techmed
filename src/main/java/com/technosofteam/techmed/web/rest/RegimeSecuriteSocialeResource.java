package com.technosofteam.techmed.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.technosofteam.techmed.service.RegimeSecuriteSocialeService;
import com.technosofteam.techmed.web.rest.errors.BadRequestAlertException;
import com.technosofteam.techmed.web.rest.util.HeaderUtil;
import com.technosofteam.techmed.web.rest.util.PaginationUtil;
import com.technosofteam.techmed.service.dto.RegimeSecuriteSocialeDTO;
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
 * REST controller for managing RegimeSecuriteSociale.
 */
@RestController
@RequestMapping("/api")
public class RegimeSecuriteSocialeResource {

    private final Logger log = LoggerFactory.getLogger(RegimeSecuriteSocialeResource.class);

    private static final String ENTITY_NAME = "regimeSecuriteSociale";

    private final RegimeSecuriteSocialeService regimeSecuriteSocialeService;

    public RegimeSecuriteSocialeResource(RegimeSecuriteSocialeService regimeSecuriteSocialeService) {
        this.regimeSecuriteSocialeService = regimeSecuriteSocialeService;
    }

    /**
     * POST  /regime-securite-sociales : Create a new regimeSecuriteSociale.
     *
     * @param regimeSecuriteSocialeDTO the regimeSecuriteSocialeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new regimeSecuriteSocialeDTO, or with status 400 (Bad Request) if the regimeSecuriteSociale has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/regime-securite-sociales")
    @Timed
    public ResponseEntity<RegimeSecuriteSocialeDTO> createRegimeSecuriteSociale(@Valid @RequestBody RegimeSecuriteSocialeDTO regimeSecuriteSocialeDTO) throws URISyntaxException {
        log.debug("REST request to save RegimeSecuriteSociale : {}", regimeSecuriteSocialeDTO);
        if (regimeSecuriteSocialeDTO.getId() != null) {
            throw new BadRequestAlertException("A new regimeSecuriteSociale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegimeSecuriteSocialeDTO result = regimeSecuriteSocialeService.save(regimeSecuriteSocialeDTO);
        return ResponseEntity.created(new URI("/api/regime-securite-sociales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /regime-securite-sociales : Updates an existing regimeSecuriteSociale.
     *
     * @param regimeSecuriteSocialeDTO the regimeSecuriteSocialeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated regimeSecuriteSocialeDTO,
     * or with status 400 (Bad Request) if the regimeSecuriteSocialeDTO is not valid,
     * or with status 500 (Internal Server Error) if the regimeSecuriteSocialeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/regime-securite-sociales")
    @Timed
    public ResponseEntity<RegimeSecuriteSocialeDTO> updateRegimeSecuriteSociale(@Valid @RequestBody RegimeSecuriteSocialeDTO regimeSecuriteSocialeDTO) throws URISyntaxException {
        log.debug("REST request to update RegimeSecuriteSociale : {}", regimeSecuriteSocialeDTO);
        if (regimeSecuriteSocialeDTO.getId() == null) {
            return createRegimeSecuriteSociale(regimeSecuriteSocialeDTO);
        }
        RegimeSecuriteSocialeDTO result = regimeSecuriteSocialeService.save(regimeSecuriteSocialeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, regimeSecuriteSocialeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /regime-securite-sociales : get all the regimeSecuriteSociales.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of regimeSecuriteSociales in body
     */
    @GetMapping("/regime-securite-sociales")
    @Timed
    public ResponseEntity<List<RegimeSecuriteSocialeDTO>> getAllRegimeSecuriteSociales(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RegimeSecuriteSociales");
        Page<RegimeSecuriteSocialeDTO> page = regimeSecuriteSocialeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/regime-securite-sociales");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /regime-securite-sociales/:id : get the "id" regimeSecuriteSociale.
     *
     * @param id the id of the regimeSecuriteSocialeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the regimeSecuriteSocialeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/regime-securite-sociales/{id}")
    @Timed
    public ResponseEntity<RegimeSecuriteSocialeDTO> getRegimeSecuriteSociale(@PathVariable Long id) {
        log.debug("REST request to get RegimeSecuriteSociale : {}", id);
        RegimeSecuriteSocialeDTO regimeSecuriteSocialeDTO = regimeSecuriteSocialeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(regimeSecuriteSocialeDTO));
    }

    /**
     * DELETE  /regime-securite-sociales/:id : delete the "id" regimeSecuriteSociale.
     *
     * @param id the id of the regimeSecuriteSocialeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/regime-securite-sociales/{id}")
    @Timed
    public ResponseEntity<Void> deleteRegimeSecuriteSociale(@PathVariable Long id) {
        log.debug("REST request to delete RegimeSecuriteSociale : {}", id);
        regimeSecuriteSocialeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
