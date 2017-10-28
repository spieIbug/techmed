package com.technosofteam.techmed.service;

import com.technosofteam.techmed.service.dto.CodeCCAMDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CodeCCAM.
 */
public interface CodeCCAMService {

    /**
     * Save a codeCCAM.
     *
     * @param codeCCAMDTO the entity to save
     * @return the persisted entity
     */
    CodeCCAMDTO save(CodeCCAMDTO codeCCAMDTO);

    /**
     *  Get all the codeCCAMS.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CodeCCAMDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" codeCCAM.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CodeCCAMDTO findOne(Long id);

    /**
     *  Delete the "id" codeCCAM.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
