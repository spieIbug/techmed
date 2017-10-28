package com.technosofteam.techmed.service;

import com.technosofteam.techmed.service.dto.PaiementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Paiement.
 */
public interface PaiementService {

    /**
     * Save a paiement.
     *
     * @param paiementDTO the entity to save
     * @return the persisted entity
     */
    PaiementDTO save(PaiementDTO paiementDTO);

    /**
     *  Get all the paiements.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PaiementDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" paiement.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PaiementDTO findOne(Long id);

    /**
     *  Delete the "id" paiement.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
