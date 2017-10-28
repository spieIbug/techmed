package com.technosofteam.techmed.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Consultation.
 */
@Entity
@Table(name = "consultation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Consultation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_acte", nullable = false)
    private ZonedDateTime dateActe;

    @NotNull
    @Column(name = "montant_ttc", nullable = false)
    private Double montantTTC;

    @Lob
    @Column(name = "jhi_lock")
    private String lock;

    @OneToOne
    @JoinColumn(unique = true)
    private Patient patient;

    @OneToOne
    @JoinColumn(unique = true)
    private User medecin;

    @OneToOne
    @JoinColumn(unique = true)
    private RegimeSecuriteSociale regimeSecuriteSociale;

    @OneToMany(mappedBy = "consultation")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Paiement> paiements = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "consultation_actes_medical_list",
               joinColumns = @JoinColumn(name="consultations_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="actes_medical_lists_id", referencedColumnName="id"))
    private Set<ActeMedical> actesMedicalLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateActe() {
        return dateActe;
    }

    public Consultation dateActe(ZonedDateTime dateActe) {
        this.dateActe = dateActe;
        return this;
    }

    public void setDateActe(ZonedDateTime dateActe) {
        this.dateActe = dateActe;
    }

    public Double getMontantTTC() {
        return montantTTC;
    }

    public Consultation montantTTC(Double montantTTC) {
        this.montantTTC = montantTTC;
        return this;
    }

    public void setMontantTTC(Double montantTTC) {
        this.montantTTC = montantTTC;
    }

    public String getLock() {
        return lock;
    }

    public Consultation lock(String lock) {
        this.lock = lock;
        return this;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

    public Patient getPatient() {
        return patient;
    }

    public Consultation patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public User getMedecin() {
        return medecin;
    }

    public Consultation medecin(User user) {
        this.medecin = user;
        return this;
    }

    public void setMedecin(User user) {
        this.medecin = user;
    }

    public RegimeSecuriteSociale getRegimeSecuriteSociale() {
        return regimeSecuriteSociale;
    }

    public Consultation regimeSecuriteSociale(RegimeSecuriteSociale regimeSecuriteSociale) {
        this.regimeSecuriteSociale = regimeSecuriteSociale;
        return this;
    }

    public void setRegimeSecuriteSociale(RegimeSecuriteSociale regimeSecuriteSociale) {
        this.regimeSecuriteSociale = regimeSecuriteSociale;
    }

    public Set<Paiement> getPaiements() {
        return paiements;
    }

    public Consultation paiements(Set<Paiement> paiements) {
        this.paiements = paiements;
        return this;
    }

    public Consultation addPaiements(Paiement paiement) {
        this.paiements.add(paiement);
        paiement.setConsultation(this);
        return this;
    }

    public Consultation removePaiements(Paiement paiement) {
        this.paiements.remove(paiement);
        paiement.setConsultation(null);
        return this;
    }

    public void setPaiements(Set<Paiement> paiements) {
        this.paiements = paiements;
    }

    public Set<ActeMedical> getActesMedicalLists() {
        return actesMedicalLists;
    }

    public Consultation actesMedicalLists(Set<ActeMedical> acteMedicals) {
        this.actesMedicalLists = acteMedicals;
        return this;
    }

    public Consultation addActesMedicalList(ActeMedical acteMedical) {
        this.actesMedicalLists.add(acteMedical);
        acteMedical.getConsultationLists().add(this);
        return this;
    }

    public Consultation removeActesMedicalList(ActeMedical acteMedical) {
        this.actesMedicalLists.remove(acteMedical);
        acteMedical.getConsultationLists().remove(this);
        return this;
    }

    public void setActesMedicalLists(Set<ActeMedical> acteMedicals) {
        this.actesMedicalLists = acteMedicals;
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
        Consultation consultation = (Consultation) o;
        if (consultation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Consultation{" +
            "id=" + getId() +
            ", dateActe='" + getDateActe() + "'" +
            ", montantTTC='" + getMontantTTC() + "'" +
            ", lock='" + getLock() + "'" +
            "}";
    }
}
