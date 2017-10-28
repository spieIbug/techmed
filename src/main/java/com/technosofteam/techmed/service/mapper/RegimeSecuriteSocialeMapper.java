package com.technosofteam.techmed.service.mapper;

import com.technosofteam.techmed.domain.*;
import com.technosofteam.techmed.service.dto.RegimeSecuriteSocialeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RegimeSecuriteSociale and its DTO RegimeSecuriteSocialeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RegimeSecuriteSocialeMapper extends EntityMapper<RegimeSecuriteSocialeDTO, RegimeSecuriteSociale> {

    

    

    default RegimeSecuriteSociale fromId(Long id) {
        if (id == null) {
            return null;
        }
        RegimeSecuriteSociale regimeSecuriteSociale = new RegimeSecuriteSociale();
        regimeSecuriteSociale.setId(id);
        return regimeSecuriteSociale;
    }
}
