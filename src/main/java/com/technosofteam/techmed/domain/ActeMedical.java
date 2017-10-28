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
 * A ActeMedical.
 */
@Entity
@Table(name = "acte_medical")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ActeMedical implements Serializable {

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

    @OneToMany(mappedBy = "acteMedical")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tarif> tarifs = new HashSet<>();

    @ManyToMany(mappedBy = "actesMedicalLists")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Consultation> consultationLists = new HashSet<>();

    @ManyToOne
    private CodeCCAM codeCCAM;

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

    public ActeMedical code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public ActeMedical libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Tarif> getTarifs() {
        return tarifs;
    }

    public ActeMedical tarifs(Set<Tarif> tarifs) {
        this.tarifs = tarifs;
        return this;
    }

    public ActeMedical addTarifs(Tarif tarif) {
        this.tarifs.add(tarif);
        tarif.setActeMedical(this);
        return this;
    }

    public ActeMedical removeTarifs(Tarif tarif) {
        this.tarifs.remove(tarif);
        tarif.setActeMedical(null);
        return this;
    }

    public void setTarifs(Set<Tarif> tarifs) {
        this.tarifs = tarifs;
    }

    public Set<Consultation> getConsultationLists() {
        return consultationLists;
    }

    public ActeMedical consultationLists(Set<Consultation> consultations) {
        this.consultationLists = consultations;
        return this;
    }

    public ActeMedical addConsultationList(Consultation consultation) {
        this.consultationLists.add(consultation);
        consultation.getActesMedicalLists().add(this);
        return this;
    }

    public ActeMedical removeConsultationList(Consultation consultation) {
        this.consultationLists.remove(consultation);
        consultation.getActesMedicalLists().remove(this);
        return this;
    }

    public void setConsultationLists(Set<Consultation> consultations) {
        this.consultationLists = consultations;
    }

    public CodeCCAM getCodeCCAM() {
        return codeCCAM;
    }

    public ActeMedical codeCCAM(CodeCCAM codeCCAM) {
        this.codeCCAM = codeCCAM;
        return this;
    }

    public void setCodeCCAM(CodeCCAM codeCCAM) {
        this.codeCCAM = codeCCAM;
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
        ActeMedical acteMedical = (ActeMedical) o;
        if (acteMedical.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), acteMedical.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActeMedical{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
