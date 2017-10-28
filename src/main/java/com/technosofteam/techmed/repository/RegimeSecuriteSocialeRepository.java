package com.technosofteam.techmed.repository;

import com.technosofteam.techmed.domain.RegimeSecuriteSociale;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RegimeSecuriteSociale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegimeSecuriteSocialeRepository extends JpaRepository<RegimeSecuriteSociale, Long> {

}
