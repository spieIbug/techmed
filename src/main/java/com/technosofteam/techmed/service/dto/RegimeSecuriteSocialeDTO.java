package com.technosofteam.techmed.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RegimeSecuriteSociale entity.
 */
public class RegimeSecuriteSocialeDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String libelle;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegimeSecuriteSocialeDTO regimeSecuriteSocialeDTO = (RegimeSecuriteSocialeDTO) o;
        if(regimeSecuriteSocialeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), regimeSecuriteSocialeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegimeSecuriteSocialeDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
