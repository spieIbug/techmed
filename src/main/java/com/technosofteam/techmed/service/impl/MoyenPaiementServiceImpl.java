package com.technosofteam.techmed.service.impl;

import com.technosofteam.techmed.service.MoyenPaiementService;
import com.technosofteam.techmed.domain.MoyenPaiement;
import com.technosofteam.techmed.repository.MoyenPaiementRepository;
import com.technosofteam.techmed.service.dto.MoyenPaiementDTO;
import com.technosofteam.techmed.service.mapper.MoyenPaiementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MoyenPaiement.
 */
@Service
@Transactional
public class MoyenPaiementServiceImpl implements MoyenPaiementService{

    private final Logger log = LoggerFactory.getLogger(MoyenPaiementServiceImpl.class);

    private final MoyenPaiementRepository moyenPaiementRepository;

    private final MoyenPaiementMapper moyenPaiementMapper;

    public MoyenPaiementServiceImpl(MoyenPaiementRepository moyenPaiementRepository, MoyenPaiementMapper moyenPaiementMapper) {
        this.moyenPaiementRepository = moyenPaiementRepository;
        this.moyenPaiementMapper = moyenPaiementMapper;
    }

    /**
     * Save a moyenPaiement.
     *
     * @param moyenPaiementDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MoyenPaiementDTO save(MoyenPaiementDTO moyenPaiementDTO) {
        log.debug("Request to save MoyenPaiement : {}", moyenPaiementDTO);
        MoyenPaiement moyenPaiement = moyenPaiementMapper.toEntity(moyenPaiementDTO);
        moyenPaiement = moyenPaiementRepository.save(moyenPaiement);
        return moyenPaiementMapper.toDto(moyenPaiement);
    }

    /**
     *  Get all the moyenPaiements.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MoyenPaiementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MoyenPaiements");
        return moyenPaiementRepository.findAll(pageable)
            .map(moyenPaiementMapper::toDto);
    }

    /**
     *  Get one moyenPaiement by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MoyenPaiementDTO findOne(Long id) {
        log.debug("Request to get MoyenPaiement : {}", id);
        MoyenPaiement moyenPaiement = moyenPaiementRepository.findOne(id);
        return moyenPaiementMapper.toDto(moyenPaiement);
    }

    /**
     *  Delete the  moyenPaiement by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MoyenPaiement : {}", id);
        moyenPaiementRepository.delete(id);
    }
}
