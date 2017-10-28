package com.technosofteam.techmed.repository;

import com.technosofteam.techmed.domain.MoyenPaiement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MoyenPaiement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoyenPaiementRepository extends JpaRepository<MoyenPaiement, Long> {

}
