package com.technosofteam.techmed.service.impl;

import com.technosofteam.techmed.service.ActeMedicalService;
import com.technosofteam.techmed.domain.ActeMedical;
import com.technosofteam.techmed.repository.ActeMedicalRepository;
import com.technosofteam.techmed.service.dto.ActeMedicalDTO;
import com.technosofteam.techmed.service.mapper.ActeMedicalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ActeMedical.
 */
@Service
@Transactional
public class ActeMedicalServiceImpl implements ActeMedicalService{

    private final Logger log = LoggerFactory.getLogger(ActeMedicalServiceImpl.class);

    private final ActeMedicalRepository acteMedicalRepository;

    private final ActeMedicalMapper acteMedicalMapper;

    public ActeMedicalServiceImpl(ActeMedicalRepository acteMedicalRepository, ActeMedicalMapper acteMedicalMapper) {
        this.acteMedicalRepository = acteMedicalRepository;
        this.acteMedicalMapper = acteMedicalMapper;
    }

    /**
     * Save a acteMedical.
     *
     * @param acteMedicalDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ActeMedicalDTO save(ActeMedicalDTO acteMedicalDTO) {
        log.debug("Request to save ActeMedical : {}", acteMedicalDTO);
        ActeMedical acteMedical = acteMedicalMapper.toEntity(acteMedicalDTO);
        acteMedical = acteMedicalRepository.save(acteMedical);
        return acteMedicalMapper.toDto(acteMedical);
    }

    /**
     *  Get all the acteMedicals.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ActeMedicalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ActeMedicals");
        return acteMedicalRepository.findAll(pageable)
            .map(acteMedicalMapper::toDto);
    }

    /**
     *  Get one acteMedical by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ActeMedicalDTO findOne(Long id) {
        log.debug("Request to get ActeMedical : {}", id);
        ActeMedical acteMedical = acteMedicalRepository.findOne(id);
        return acteMedicalMapper.toDto(acteMedical);
    }

    /**
     *  Delete the  acteMedical by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActeMedical : {}", id);
        acteMedicalRepository.delete(id);
    }
}
