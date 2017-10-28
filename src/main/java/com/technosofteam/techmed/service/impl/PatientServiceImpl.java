package com.technosofteam.techmed.service.impl;

import com.technosofteam.techmed.service.PatientService;
import com.technosofteam.techmed.domain.Patient;
import com.technosofteam.techmed.repository.PatientRepository;
import com.technosofteam.techmed.service.dto.PatientDTO;
import com.technosofteam.techmed.service.mapper.PatientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Patient.
 */
@Service
@Transactional
public class PatientServiceImpl implements PatientService{

    private final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    /**
     * Save a patient.
     *
     * @param patientDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PatientDTO save(PatientDTO patientDTO) {
        log.debug("Request to save Patient : {}", patientDTO);
        Patient patient = patientMapper.toEntity(patientDTO);
        patient = patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    /**
     *  Get all the patients.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PatientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Patients");
        return patientRepository.findAll(pageable)
            .map(patientMapper::toDto);
    }

    /**
     *  Get one patient by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PatientDTO findOne(Long id) {
        log.debug("Request to get Patient : {}", id);
        Patient patient = patientRepository.findOne(id);
        return patientMapper.toDto(patient);
    }

    /**
     *  Delete the  patient by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Patient : {}", id);
        patientRepository.delete(id);
    }
}
