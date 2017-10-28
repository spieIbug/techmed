package com.technosofteam.techmed.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Tarif entity.
 */
public class TarifDTO implements Serializable {

    private Long id;

    @NotNull
    private Double prixHT;

    @NotNull
    private Double tva;

    @NotNull
    private Double prixTTC;

    private Boolean actif;

    @NotNull
    private ZonedDateTime dateDebut;

    private ZonedDateTime dateFin;

    private Long acteMedicalId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(Double prixHT) {
        this.prixHT = prixHT;
    }

    public Double getTva() {
        return tva;
    }

    public void setTva(Double tva) {
        this.tva = tva;
    }

    public Double getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
    }

    public Boolean isActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public ZonedDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ZonedDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public Long getActeMedicalId() {
        return acteMedicalId;
    }

    public void setActeMedicalId(Long acteMedicalId) {
        this.acteMedicalId = acteMedicalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TarifDTO tarifDTO = (TarifDTO) o;
        if(tarifDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tarifDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TarifDTO{" +
            "id=" + getId() +
            ", prixHT='" + getPrixHT() + "'" +
            ", tva='" + getTva() + "'" +
            ", prixTTC='" + getPrixTTC() + "'" +
            ", actif='" + isActif() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            "}";
    }
}
