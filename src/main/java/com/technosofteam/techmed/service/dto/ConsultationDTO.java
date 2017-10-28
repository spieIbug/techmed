package com.technosofteam.techmed.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Consultation entity.
 */
public class ConsultationDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime dateActe;

    @NotNull
    private Double montantTTC;

    @Lob
    private String lock;

    private Long patientId;

    private Long medecinId;

    private Long regimeSecuriteSocialeId;

    private Set<ActeMedicalDTO> actesMedicalLists = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateActe() {
        return dateActe;
    }

    public void setDateActe(ZonedDateTime dateActe) {
        this.dateActe = dateActe;
    }

    public Double getMontantTTC() {
        return montantTTC;
    }

    public void setMontantTTC(Double montantTTC) {
        this.montantTTC = montantTTC;
    }

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getMedecinId() {
        return medecinId;
    }

    public void setMedecinId(Long userId) {
        this.medecinId = userId;
    }

    public Long getRegimeSecuriteSocialeId() {
        return regimeSecuriteSocialeId;
    }

    public void setRegimeSecuriteSocialeId(Long regimeSecuriteSocialeId) {
        this.regimeSecuriteSocialeId = regimeSecuriteSocialeId;
    }

    public Set<ActeMedicalDTO> getActesMedicalLists() {
        return actesMedicalLists;
    }

    public void setActesMedicalLists(Set<ActeMedicalDTO> acteMedicals) {
        this.actesMedicalLists = acteMedicals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConsultationDTO consultationDTO = (ConsultationDTO) o;
        if(consultationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsultationDTO{" +
            "id=" + getId() +
            ", dateActe='" + getDateActe() + "'" +
            ", montantTTC='" + getMontantTTC() + "'" +
            ", lock='" + getLock() + "'" +
            "}";
    }
}
