package com.technosofteam.techmed.service.impl;

import com.technosofteam.techmed.service.PaiementService;
import com.technosofteam.techmed.domain.Paiement;
import com.technosofteam.techmed.repository.PaiementRepository;
import com.technosofteam.techmed.service.dto.PaiementDTO;
import com.technosofteam.techmed.service.mapper.PaiementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Paiement.
 */
@Service
@Transactional
public class PaiementServiceImpl implements PaiementService{

    private final Logger log = LoggerFactory.getLogger(PaiementServiceImpl.class);

    private final PaiementRepository paiementRepository;

    private final PaiementMapper paiementMapper;

    public PaiementServiceImpl(PaiementRepository paiementRepository, PaiementMapper paiementMapper) {
        this.paiementRepository = paiementRepository;
        this.paiementMapper = paiementMapper;
    }

    /**
     * Save a paiement.
     *
     * @param paiementDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PaiementDTO save(PaiementDTO paiementDTO) {
        log.debug("Request to save Paiement : {}", paiementDTO);
        Paiement paiement = paiementMapper.toEntity(paiementDTO);
        paiement = paiementRepository.save(paiement);
        return paiementMapper.toDto(paiement);
    }

    /**
     *  Get all the paiements.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaiementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Paiements");
        return paiementRepository.findAll(pageable)
            .map(paiementMapper::toDto);
    }

    /**
     *  Get one paiement by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PaiementDTO findOne(Long id) {
        log.debug("Request to get Paiement : {}", id);
        Paiement paiement = paiementRepository.findOne(id);
        return paiementMapper.toDto(paiement);
    }

    /**
     *  Delete the  paiement by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Paiement : {}", id);
        paiementRepository.delete(id);
    }
}
