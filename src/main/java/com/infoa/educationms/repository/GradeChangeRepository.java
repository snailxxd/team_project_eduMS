package com.infoa.educationms.repository;

import com.infoa.educationms.entities.Student;
import com.infoa.educationms.entities.Course;
import com.infoa.educationms.entities.GradeChange;
import com.infoa.educationms.entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeChangeRepository extends JpaRepository<GradeChange, Integer> {
    List<GradeChange> findByResultIsNull();
    Student findOneByTakesId(int takes_id);
    Course findOnecByTakesId(int takes_id);
}
