package com.technosofteam.techmed.service;

import com.technosofteam.techmed.service.dto.ConsultationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Consultation.
 */
public interface ConsultationService {

    /**
     * Save a consultation.
     *
     * @param consultationDTO the entity to save
     * @return the persisted entity
     */
    ConsultationDTO save(ConsultationDTO consultationDTO);

    /**
     *  Get all the consultations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ConsultationDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" consultation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ConsultationDTO findOne(Long id);

    /**
     *  Delete the "id" consultation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
