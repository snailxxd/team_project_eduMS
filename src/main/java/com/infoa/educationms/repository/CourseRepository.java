package com.infoa.educationms.repository;

import com.infoa.educationms.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT c FROM Course c WHERE c.courseId IN :courseIds")
    List<Course> findByCourseIdIn(@Param("courseIds") Collection<Integer> courseIds);

    @Query("SELECT DISTINCT c FROM Course c JOIN Section s ON c.courseId = s.courseId WHERE s.teacherId = :teacherId")
    List<Course> findByTeacherId(@Param("teacherId") Integer teacherId);
}
