package com.technosofteam.techmed.service.mapper;

import com.technosofteam.techmed.domain.*;
import com.technosofteam.techmed.service.dto.PaiementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Paiement and its DTO PaiementDTO.
 */
@Mapper(componentModel = "spring", uses = {ConsultationMapper.class, MoyenPaiementMapper.class})
public interface PaiementMapper extends EntityMapper<PaiementDTO, Paiement> {

    @Mapping(source = "consultation.id", target = "consultationId")
    @Mapping(source = "moyen.id", target = "moyenId")
    PaiementDTO toDto(Paiement paiement); 

    @Mapping(source = "consultationId", target = "consultation")
    @Mapping(source = "moyenId", target = "moyen")
    Paiement toEntity(PaiementDTO paiementDTO);

    default Paiement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Paiement paiement = new Paiement();
        paiement.setId(id);
        return paiement;
    }
}
