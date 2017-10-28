package com.technosofteam.techmed.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Paiement entity.
 */
public class PaiementDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime dateTransation;

    @NotNull
    private Double montantTTC;

    private Long consultationId;

    private Long moyenId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateTransation() {
        return dateTransation;
    }

    public void setDateTransation(ZonedDateTime dateTransation) {
        this.dateTransation = dateTransation;
    }

    public Double getMontantTTC() {
        return montantTTC;
    }

    public void setMontantTTC(Double montantTTC) {
        this.montantTTC = montantTTC;
    }

    public Long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }

    public Long getMoyenId() {
        return moyenId;
    }

    public void setMoyenId(Long moyenPaiementId) {
        this.moyenId = moyenPaiementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaiementDTO paiementDTO = (PaiementDTO) o;
        if(paiementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paiementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaiementDTO{" +
            "id=" + getId() +
            ", dateTransation='" + getDateTransation() + "'" +
            ", montantTTC='" + getMontantTTC() + "'" +
            "}";
    }
}
