package com.technosofteam.techmed.service.mapper;

import com.technosofteam.techmed.domain.*;
import com.technosofteam.techmed.service.dto.MoyenPaiementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MoyenPaiement and its DTO MoyenPaiementDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MoyenPaiementMapper extends EntityMapper<MoyenPaiementDTO, MoyenPaiement> {

    

    

    default MoyenPaiement fromId(Long id) {
        if (id == null) {
            return null;
        }
        MoyenPaiement moyenPaiement = new MoyenPaiement();
        moyenPaiement.setId(id);
        return moyenPaiement;
    }
}
