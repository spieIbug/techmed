package com.technosofteam.techmed.service.impl;

import com.technosofteam.techmed.service.TarifService;
import com.technosofteam.techmed.domain.Tarif;
import com.technosofteam.techmed.repository.TarifRepository;
import com.technosofteam.techmed.service.dto.TarifDTO;
import com.technosofteam.techmed.service.mapper.TarifMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Tarif.
 */
@Service
@Transactional
public class TarifServiceImpl implements TarifService{

    private final Logger log = LoggerFactory.getLogger(TarifServiceImpl.class);

    private final TarifRepository tarifRepository;

    private final TarifMapper tarifMapper;

    public TarifServiceImpl(TarifRepository tarifRepository, TarifMapper tarifMapper) {
        this.tarifRepository = tarifRepository;
        this.tarifMapper = tarifMapper;
    }

    /**
     * Save a tarif.
     *
     * @param tarifDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TarifDTO save(TarifDTO tarifDTO) {
        log.debug("Request to save Tarif : {}", tarifDTO);
        Tarif tarif = tarifMapper.toEntity(tarifDTO);
        tarif = tarifRepository.save(tarif);
        return tarifMapper.toDto(tarif);
    }

    /**
     *  Get all the tarifs.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TarifDTO> findAll() {
        log.debug("Request to get all Tarifs");
        return tarifRepository.findAll().stream()
            .map(tarifMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one tarif by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TarifDTO findOne(Long id) {
        log.debug("Request to get Tarif : {}", id);
        Tarif tarif = tarifRepository.findOne(id);
        return tarifMapper.toDto(tarif);
    }

    /**
     *  Delete the  tarif by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tarif : {}", id);
        tarifRepository.delete(id);
    }
}
