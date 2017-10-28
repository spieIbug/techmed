package com.technosofteam.techmed.repository;

import com.technosofteam.techmed.domain.CodeCCAM;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CodeCCAM entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodeCCAMRepository extends JpaRepository<CodeCCAM, Long> {

}
