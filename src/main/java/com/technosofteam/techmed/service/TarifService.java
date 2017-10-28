package com.technosofteam.techmed.service;

import com.technosofteam.techmed.service.dto.TarifDTO;
import java.util.List;

/**
 * Service Interface for managing Tarif.
 */
public interface TarifService {

    /**
     * Save a tarif.
     *
     * @param tarifDTO the entity to save
     * @return the persisted entity
     */
    TarifDTO save(TarifDTO tarifDTO);

    /**
     *  Get all the tarifs.
     *
     *  @return the list of entities
     */
    List<TarifDTO> findAll();

    /**
     *  Get the "id" tarif.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TarifDTO findOne(Long id);

    /**
     *  Delete the "id" tarif.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
