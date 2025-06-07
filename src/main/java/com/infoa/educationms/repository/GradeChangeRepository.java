package com.infoa.educationms.repository;

import com.infoa.educationms.entities.GradeChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeChangeRepository extends JpaRepository<GradeChange, Integer> {
    @Query("SELECT g.result FROM GradeChange g WHERE g.gradeId = :gradeId")
    List<Boolean> findResultByGradeId(Integer gradeId);

    List<GradeChange> findByGradeId(Integer gradeId);

    List<GradeChange> findByResultIsNull();

    GradeChange findOneByChangeId(int gradeChangeId);
}
