package com.technosofteam.techmed.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CodeCCAM.
 */
@Entity
@Table(name = "code_ccam")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CodeCCAM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "codeCCAM")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ActeMedical> acteMedicalLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public CodeCCAM code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public CodeCCAM libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<ActeMedical> getActeMedicalLists() {
        return acteMedicalLists;
    }

    public CodeCCAM acteMedicalLists(Set<ActeMedical> acteMedicals) {
        this.acteMedicalLists = acteMedicals;
        return this;
    }

    public CodeCCAM addActeMedicalList(ActeMedical acteMedical) {
        this.acteMedicalLists.add(acteMedical);
        acteMedical.setCodeCCAM(this);
        return this;
    }

    public CodeCCAM removeActeMedicalList(ActeMedical acteMedical) {
        this.acteMedicalLists.remove(acteMedical);
        acteMedical.setCodeCCAM(null);
        return this;
    }

    public void setActeMedicalLists(Set<ActeMedical> acteMedicals) {
        this.acteMedicalLists = acteMedicals;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CodeCCAM codeCCAM = (CodeCCAM) o;
        if (codeCCAM.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), codeCCAM.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CodeCCAM{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
