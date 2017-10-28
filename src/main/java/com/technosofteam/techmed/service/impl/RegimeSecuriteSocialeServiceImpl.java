package com.technosofteam.techmed.service.impl;

import com.technosofteam.techmed.service.RegimeSecuriteSocialeService;
import com.technosofteam.techmed.domain.RegimeSecuriteSociale;
import com.technosofteam.techmed.repository.RegimeSecuriteSocialeRepository;
import com.technosofteam.techmed.service.dto.RegimeSecuriteSocialeDTO;
import com.technosofteam.techmed.service.mapper.RegimeSecuriteSocialeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing RegimeSecuriteSociale.
 */
@Service
@Transactional
public class RegimeSecuriteSocialeServiceImpl implements RegimeSecuriteSocialeService{

    private final Logger log = LoggerFactory.getLogger(RegimeSecuriteSocialeServiceImpl.class);

    private final RegimeSecuriteSocialeRepository regimeSecuriteSocialeRepository;

    private final RegimeSecuriteSocialeMapper regimeSecuriteSocialeMapper;

    public RegimeSecuriteSocialeServiceImpl(RegimeSecuriteSocialeRepository regimeSecuriteSocialeRepository, RegimeSecuriteSocialeMapper regimeSecuriteSocialeMapper) {
        this.regimeSecuriteSocialeRepository = regimeSecuriteSocialeRepository;
        this.regimeSecuriteSocialeMapper = regimeSecuriteSocialeMapper;
    }

    /**
     * Save a regimeSecuriteSociale.
     *
     * @param regimeSecuriteSocialeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RegimeSecuriteSocialeDTO save(RegimeSecuriteSocialeDTO regimeSecuriteSocialeDTO) {
        log.debug("Request to save RegimeSecuriteSociale : {}", regimeSecuriteSocialeDTO);
        RegimeSecuriteSociale regimeSecuriteSociale = regimeSecuriteSocialeMapper.toEntity(regimeSecuriteSocialeDTO);
        regimeSecuriteSociale = regimeSecuriteSocialeRepository.save(regimeSecuriteSociale);
        return regimeSecuriteSocialeMapper.toDto(regimeSecuriteSociale);
    }

    /**
     *  Get all the regimeSecuriteSociales.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RegimeSecuriteSocialeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RegimeSecuriteSociales");
        return regimeSecuriteSocialeRepository.findAll(pageable)
            .map(regimeSecuriteSocialeMapper::toDto);
    }

    /**
     *  Get one regimeSecuriteSociale by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RegimeSecuriteSocialeDTO findOne(Long id) {
        log.debug("Request to get RegimeSecuriteSociale : {}", id);
        RegimeSecuriteSociale regimeSecuriteSociale = regimeSecuriteSocialeRepository.findOne(id);
        return regimeSecuriteSocialeMapper.toDto(regimeSecuriteSociale);
    }

    /**
     *  Delete the  regimeSecuriteSociale by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegimeSecuriteSociale : {}", id);
        regimeSecuriteSocialeRepository.delete(id);
    }
}
