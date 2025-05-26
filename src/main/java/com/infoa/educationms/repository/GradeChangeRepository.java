package com.infoa.educationms.repository;

import com.infoa.educationms.entities.GradeChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeChangeRepository extends JpaRepository<GradeChange, Integer> {
}
