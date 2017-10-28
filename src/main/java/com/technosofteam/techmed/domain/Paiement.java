package com.technosofteam.techmed.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Paiement.
 */
@Entity
@Table(name = "paiement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Paiement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_transation", nullable = false)
    private ZonedDateTime dateTransation;

    @NotNull
    @Column(name = "montant_ttc", nullable = false)
    private Double montantTTC;

    @ManyToOne
    private Consultation consultation;

    @OneToOne
    @JoinColumn(unique = true)
    private MoyenPaiement moyen;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateTransation() {
        return dateTransation;
    }

    public Paiement dateTransation(ZonedDateTime dateTransation) {
        this.dateTransation = dateTransation;
        return this;
    }

    public void setDateTransation(ZonedDateTime dateTransation) {
        this.dateTransation = dateTransation;
    }

    public Double getMontantTTC() {
        return montantTTC;
    }

    public Paiement montantTTC(Double montantTTC) {
        this.montantTTC = montantTTC;
        return this;
    }

    public void setMontantTTC(Double montantTTC) {
        this.montantTTC = montantTTC;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public Paiement consultation(Consultation consultation) {
        this.consultation = consultation;
        return this;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public MoyenPaiement getMoyen() {
        return moyen;
    }

    public Paiement moyen(MoyenPaiement moyenPaiement) {
        this.moyen = moyenPaiement;
        return this;
    }

    public void setMoyen(MoyenPaiement moyenPaiement) {
        this.moyen = moyenPaiement;
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
        Paiement paiement = (Paiement) o;
        if (paiement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paiement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Paiement{" +
            "id=" + getId() +
            ", dateTransation='" + getDateTransation() + "'" +
            ", montantTTC='" + getMontantTTC() + "'" +
            "}";
    }
}
