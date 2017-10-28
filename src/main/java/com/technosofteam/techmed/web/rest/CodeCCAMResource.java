package com.technosofteam.techmed.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.technosofteam.techmed.service.CodeCCAMService;
import com.technosofteam.techmed.web.rest.errors.BadRequestAlertException;
import com.technosofteam.techmed.web.rest.util.HeaderUtil;
import com.technosofteam.techmed.web.rest.util.PaginationUtil;
import com.technosofteam.techmed.service.dto.CodeCCAMDTO;
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
 * REST controller for managing CodeCCAM.
 */
@RestController
@RequestMapping("/api")
public class CodeCCAMResource {

    private final Logger log = LoggerFactory.getLogger(CodeCCAMResource.class);

    private static final String ENTITY_NAME = "codeCCAM";

    private final CodeCCAMService codeCCAMService;

    public CodeCCAMResource(CodeCCAMService codeCCAMService) {
        this.codeCCAMService = codeCCAMService;
    }

    /**
     * POST  /code-ccams : Create a new codeCCAM.
     *
     * @param codeCCAMDTO the codeCCAMDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new codeCCAMDTO, or with status 400 (Bad Request) if the codeCCAM has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/code-ccams")
    @Timed
    public ResponseEntity<CodeCCAMDTO> createCodeCCAM(@Valid @RequestBody CodeCCAMDTO codeCCAMDTO) throws URISyntaxException {
        log.debug("REST request to save CodeCCAM : {}", codeCCAMDTO);
        if (codeCCAMDTO.getId() != null) {
            throw new BadRequestAlertException("A new codeCCAM cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CodeCCAMDTO result = codeCCAMService.save(codeCCAMDTO);
        return ResponseEntity.created(new URI("/api/code-ccams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /code-ccams : Updates an existing codeCCAM.
     *
     * @param codeCCAMDTO the codeCCAMDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated codeCCAMDTO,
     * or with status 400 (Bad Request) if the codeCCAMDTO is not valid,
     * or with status 500 (Internal Server Error) if the codeCCAMDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/code-ccams")
    @Timed
    public ResponseEntity<CodeCCAMDTO> updateCodeCCAM(@Valid @RequestBody CodeCCAMDTO codeCCAMDTO) throws URISyntaxException {
        log.debug("REST request to update CodeCCAM : {}", codeCCAMDTO);
        if (codeCCAMDTO.getId() == null) {
            return createCodeCCAM(codeCCAMDTO);
        }
        CodeCCAMDTO result = codeCCAMService.save(codeCCAMDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, codeCCAMDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /code-ccams : get all the codeCCAMS.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of codeCCAMS in body
     */
    @GetMapping("/code-ccams")
    @Timed
    public ResponseEntity<List<CodeCCAMDTO>> getAllCodeCCAMS(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CodeCCAMS");
        Page<CodeCCAMDTO> page = codeCCAMService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/code-ccams");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /code-ccams/:id : get the "id" codeCCAM.
     *
     * @param id the id of the codeCCAMDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the codeCCAMDTO, or with status 404 (Not Found)
     */
    @GetMapping("/code-ccams/{id}")
    @Timed
    public ResponseEntity<CodeCCAMDTO> getCodeCCAM(@PathVariable Long id) {
        log.debug("REST request to get CodeCCAM : {}", id);
        CodeCCAMDTO codeCCAMDTO = codeCCAMService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(codeCCAMDTO));
    }

    /**
     * DELETE  /code-ccams/:id : delete the "id" codeCCAM.
     *
     * @param id the id of the codeCCAMDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/code-ccams/{id}")
    @Timed
    public ResponseEntity<Void> deleteCodeCCAM(@PathVariable Long id) {
        log.debug("REST request to delete CodeCCAM : {}", id);
        codeCCAMService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
