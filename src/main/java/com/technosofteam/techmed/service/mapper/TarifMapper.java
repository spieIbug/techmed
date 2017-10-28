package com.technosofteam.techmed.service.mapper;

import com.technosofteam.techmed.domain.*;
import com.technosofteam.techmed.service.dto.TarifDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tarif and its DTO TarifDTO.
 */
@Mapper(componentModel = "spring", uses = {ActeMedicalMapper.class})
public interface TarifMapper extends EntityMapper<TarifDTO, Tarif> {

    @Mapping(source = "acteMedical.id", target = "acteMedicalId")
    TarifDTO toDto(Tarif tarif); 

    @Mapping(source = "acteMedicalId", target = "acteMedical")
    Tarif toEntity(TarifDTO tarifDTO);

    default Tarif fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tarif tarif = new Tarif();
        tarif.setId(id);
        return tarif;
    }
}
