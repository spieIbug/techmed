package com.technosofteam.techmed.service.mapper;

import com.technosofteam.techmed.domain.*;
import com.technosofteam.techmed.service.dto.ConsultationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Consultation and its DTO ConsultationDTO.
 */
@Mapper(componentModel = "spring", uses = {PatientMapper.class, UserMapper.class, RegimeSecuriteSocialeMapper.class, ActeMedicalMapper.class})
public interface ConsultationMapper extends EntityMapper<ConsultationDTO, Consultation> {

    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "medecin.id", target = "medecinId")
    @Mapping(source = "regimeSecuriteSociale.id", target = "regimeSecuriteSocialeId")
    ConsultationDTO toDto(Consultation consultation); 

    @Mapping(source = "patientId", target = "patient")
    @Mapping(source = "medecinId", target = "medecin")
    @Mapping(source = "regimeSecuriteSocialeId", target = "regimeSecuriteSociale")
    @Mapping(target = "paiements", ignore = true)
    Consultation toEntity(ConsultationDTO consultationDTO);

    default Consultation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Consultation consultation = new Consultation();
        consultation.setId(id);
        return consultation;
    }
}
