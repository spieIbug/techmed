package com.technosofteam.techmed.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MoyenPaiement.
 */
@Entity
@Table(name = "moyen_paiement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MoyenPaiement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_mode", nullable = false)
    private String mode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public MoyenPaiement mode(String mode) {
        this.mode = mode;
        return this;
    }

    public void setMode(String mode) {
        this.mode = mode;
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
        MoyenPaiement moyenPaiement = (MoyenPaiement) o;
        if (moyenPaiement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), moyenPaiement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MoyenPaiement{" +
            "id=" + getId() +
            ", mode='" + getMode() + "'" +
            "}";
    }
}
