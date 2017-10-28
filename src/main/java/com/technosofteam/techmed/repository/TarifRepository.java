package com.technosofteam.techmed.repository;

import com.technosofteam.techmed.domain.Tarif;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Tarif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarifRepository extends JpaRepository<Tarif, Long> {

}
