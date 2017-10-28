package com.technosofteam.techmed.service.impl;

import com.technosofteam.techmed.service.CodeCCAMService;
import com.technosofteam.techmed.domain.CodeCCAM;
import com.technosofteam.techmed.repository.CodeCCAMRepository;
import com.technosofteam.techmed.service.dto.CodeCCAMDTO;
import com.technosofteam.techmed.service.mapper.CodeCCAMMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CodeCCAM.
 */
@Service
@Transactional
public class CodeCCAMServiceImpl implements CodeCCAMService{

    private final Logger log = LoggerFactory.getLogger(CodeCCAMServiceImpl.class);

    private final CodeCCAMRepository codeCCAMRepository;

    private final CodeCCAMMapper codeCCAMMapper;

    public CodeCCAMServiceImpl(CodeCCAMRepository codeCCAMRepository, CodeCCAMMapper codeCCAMMapper) {
        this.codeCCAMRepository = codeCCAMRepository;
        this.codeCCAMMapper = codeCCAMMapper;
    }

    /**
     * Save a codeCCAM.
     *
     * @param codeCCAMDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CodeCCAMDTO save(CodeCCAMDTO codeCCAMDTO) {
        log.debug("Request to save CodeCCAM : {}", codeCCAMDTO);
        CodeCCAM codeCCAM = codeCCAMMapper.toEntity(codeCCAMDTO);
        codeCCAM = codeCCAMRepository.save(codeCCAM);
        return codeCCAMMapper.toDto(codeCCAM);
    }

    /**
     *  Get all the codeCCAMS.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CodeCCAMDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CodeCCAMS");
        return codeCCAMRepository.findAll(pageable)
            .map(codeCCAMMapper::toDto);
    }

    /**
     *  Get one codeCCAM by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CodeCCAMDTO findOne(Long id) {
        log.debug("Request to get CodeCCAM : {}", id);
        CodeCCAM codeCCAM = codeCCAMRepository.findOne(id);
        return codeCCAMMapper.toDto(codeCCAM);
    }

    /**
     *  Delete the  codeCCAM by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CodeCCAM : {}", id);
        codeCCAMRepository.delete(id);
    }
}
