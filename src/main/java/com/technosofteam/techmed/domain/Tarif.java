package com.technosofteam.techmed.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Tarif.
 */
@Entity
@Table(name = "tarif")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tarif implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "prix_ht", nullable = false)
    private Double prixHT;

    @NotNull
    @Column(name = "tva", nullable = false)
    private Double tva;

    @NotNull
    @Column(name = "prix_ttc", nullable = false)
    private Double prixTTC;

    @Column(name = "actif")
    private Boolean actif;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private ZonedDateTime dateDebut;

    @Column(name = "date_fin")
    private ZonedDateTime dateFin;

    @ManyToOne
    private ActeMedical acteMedical;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrixHT() {
        return prixHT;
    }

    public Tarif prixHT(Double prixHT) {
        this.prixHT = prixHT;
        return this;
    }

    public void setPrixHT(Double prixHT) {
        this.prixHT = prixHT;
    }

    public Double getTva() {
        return tva;
    }

    public Tarif tva(Double tva) {
        this.tva = tva;
        return this;
    }

    public void setTva(Double tva) {
        this.tva = tva;
    }

    public Double getPrixTTC() {
        return prixTTC;
    }

    public Tarif prixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
        return this;
    }

    public void setPrixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
    }

    public Boolean isActif() {
        return actif;
    }

    public Tarif actif(Boolean actif) {
        this.actif = actif;
        return this;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public ZonedDateTime getDateDebut() {
        return dateDebut;
    }

    public Tarif dateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ZonedDateTime getDateFin() {
        return dateFin;
    }

    public Tarif dateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public ActeMedical getActeMedical() {
        return acteMedical;
    }

    public Tarif acteMedical(ActeMedical acteMedical) {
        this.acteMedical = acteMedical;
        return this;
    }

    public void setActeMedical(ActeMedical acteMedical) {
        this.acteMedical = acteMedical;
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
        Tarif tarif = (Tarif) o;
        if (tarif.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tarif.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tarif{" +
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
