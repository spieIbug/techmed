package com.technosofteam.techmed.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MoyenPaiement entity.
 */
public class MoyenPaiementDTO implements Serializable {

    private Long id;

    @NotNull
    private String mode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MoyenPaiementDTO moyenPaiementDTO = (MoyenPaiementDTO) o;
        if(moyenPaiementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), moyenPaiementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MoyenPaiementDTO{" +
            "id=" + getId() +
            ", mode='" + getMode() + "'" +
            "}";
    }
}
