package com.technosofteam.techmed.service.impl;

import com.technosofteam.techmed.service.ConsultationService;
import com.technosofteam.techmed.domain.Consultation;
import com.technosofteam.techmed.repository.ConsultationRepository;
import com.technosofteam.techmed.service.dto.ConsultationDTO;
import com.technosofteam.techmed.service.mapper.ConsultationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Consultation.
 */
@Service
@Transactional
public class ConsultationServiceImpl implements ConsultationService{

    private final Logger log = LoggerFactory.getLogger(ConsultationServiceImpl.class);

    private final ConsultationRepository consultationRepository;

    private final ConsultationMapper consultationMapper;

    public ConsultationServiceImpl(ConsultationRepository consultationRepository, ConsultationMapper consultationMapper) {
        this.consultationRepository = consultationRepository;
        this.consultationMapper = consultationMapper;
    }

    /**
     * Save a consultation.
     *
     * @param consultationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConsultationDTO save(ConsultationDTO consultationDTO) {
        log.debug("Request to save Consultation : {}", consultationDTO);
        Consultation consultation = consultationMapper.toEntity(consultationDTO);
        consultation = consultationRepository.save(consultation);
        return consultationMapper.toDto(consultation);
    }

    /**
     *  Get all the consultations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConsultationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Consultations");
        return consultationRepository.findAll(pageable)
            .map(consultationMapper::toDto);
    }

    /**
     *  Get one consultation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConsultationDTO findOne(Long id) {
        log.debug("Request to get Consultation : {}", id);
        Consultation consultation = consultationRepository.findOneWithEagerRelationships(id);
        return consultationMapper.toDto(consultation);
    }

    /**
     *  Delete the  consultation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Consultation : {}", id);
        consultationRepository.delete(id);
    }
}
