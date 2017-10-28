package com.technosofteam.techmed.service;

import com.technosofteam.techmed.service.dto.RegimeSecuriteSocialeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RegimeSecuriteSociale.
 */
public interface RegimeSecuriteSocialeService {

    /**
     * Save a regimeSecuriteSociale.
     *
     * @param regimeSecuriteSocialeDTO the entity to save
     * @return the persisted entity
     */
    RegimeSecuriteSocialeDTO save(RegimeSecuriteSocialeDTO regimeSecuriteSocialeDTO);

    /**
     *  Get all the regimeSecuriteSociales.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RegimeSecuriteSocialeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" regimeSecuriteSociale.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RegimeSecuriteSocialeDTO findOne(Long id);

    /**
     *  Delete the "id" regimeSecuriteSociale.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
