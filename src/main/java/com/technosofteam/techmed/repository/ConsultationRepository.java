package com.technosofteam.techmed.repository;

import com.technosofteam.techmed.domain.Consultation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Consultation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    @Query("select distinct consultation from Consultation consultation left join fetch consultation.actesMedicalLists")
    List<Consultation> findAllWithEagerRelationships();

    @Query("select consultation from Consultation consultation left join fetch consultation.actesMedicalLists where consultation.id =:id")
    Consultation findOneWithEagerRelationships(@Param("id") Long id);

}
