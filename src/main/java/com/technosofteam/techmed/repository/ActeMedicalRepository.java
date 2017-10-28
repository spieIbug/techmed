package com.technosofteam.techmed.repository;

import com.technosofteam.techmed.domain.ActeMedical;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ActeMedical entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActeMedicalRepository extends JpaRepository<ActeMedical, Long> {

}
