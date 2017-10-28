package com.technosofteam.techmed.service.mapper;

import com.technosofteam.techmed.domain.*;
import com.technosofteam.techmed.service.dto.ActeMedicalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ActeMedical and its DTO ActeMedicalDTO.
 */
@Mapper(componentModel = "spring", uses = {CodeCCAMMapper.class})
public interface ActeMedicalMapper extends EntityMapper<ActeMedicalDTO, ActeMedical> {

    @Mapping(source = "codeCCAM.id", target = "codeCCAMId")
    ActeMedicalDTO toDto(ActeMedical acteMedical); 

    @Mapping(target = "tarifs", ignore = true)
    @Mapping(target = "consultationLists", ignore = true)
    @Mapping(source = "codeCCAMId", target = "codeCCAM")
    ActeMedical toEntity(ActeMedicalDTO acteMedicalDTO);

    default ActeMedical fromId(Long id) {
        if (id == null) {
            return null;
        }
        ActeMedical acteMedical = new ActeMedical();
        acteMedical.setId(id);
        return acteMedical;
    }
}
