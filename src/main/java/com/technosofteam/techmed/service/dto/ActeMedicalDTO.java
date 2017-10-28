package com.technosofteam.techmed.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ActeMedical entity.
 */
public class ActeMedicalDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String libelle;

    private Long codeCCAMId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Long getCodeCCAMId() {
        return codeCCAMId;
    }

    public void setCodeCCAMId(Long codeCCAMId) {
        this.codeCCAMId = codeCCAMId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActeMedicalDTO acteMedicalDTO = (ActeMedicalDTO) o;
        if(acteMedicalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), acteMedicalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActeMedicalDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
