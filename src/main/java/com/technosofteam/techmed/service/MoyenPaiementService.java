package com.technosofteam.techmed.service;

import com.technosofteam.techmed.service.dto.MoyenPaiementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MoyenPaiement.
 */
public interface MoyenPaiementService {

    /**
     * Save a moyenPaiement.
     *
     * @param moyenPaiementDTO the entity to save
     * @return the persisted entity
     */
    MoyenPaiementDTO save(MoyenPaiementDTO moyenPaiementDTO);

    /**
     *  Get all the moyenPaiements.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MoyenPaiementDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" moyenPaiement.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MoyenPaiementDTO findOne(Long id);

    /**
     *  Delete the "id" moyenPaiement.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
