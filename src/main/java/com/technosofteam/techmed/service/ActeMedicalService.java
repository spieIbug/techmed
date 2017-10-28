package com.technosofteam.techmed.service;

import com.technosofteam.techmed.service.dto.ActeMedicalDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ActeMedical.
 */
public interface ActeMedicalService {

    /**
     * Save a acteMedical.
     *
     * @param acteMedicalDTO the entity to save
     * @return the persisted entity
     */
    ActeMedicalDTO save(ActeMedicalDTO acteMedicalDTO);

    /**
     *  Get all the acteMedicals.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ActeMedicalDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" acteMedical.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ActeMedicalDTO findOne(Long id);

    /**
     *  Delete the "id" acteMedical.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
