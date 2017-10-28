package com.technosofteam.techmed.service.mapper;

import com.technosofteam.techmed.domain.*;
import com.technosofteam.techmed.service.dto.CodeCCAMDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CodeCCAM and its DTO CodeCCAMDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CodeCCAMMapper extends EntityMapper<CodeCCAMDTO, CodeCCAM> {

    

    @Mapping(target = "acteMedicalLists", ignore = true)
    CodeCCAM toEntity(CodeCCAMDTO codeCCAMDTO);

    default CodeCCAM fromId(Long id) {
        if (id == null) {
            return null;
        }
        CodeCCAM codeCCAM = new CodeCCAM();
        codeCCAM.setId(id);
        return codeCCAM;
    }
}
