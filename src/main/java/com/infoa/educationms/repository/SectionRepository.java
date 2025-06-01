package com.infoa.educationms.repository;

import com.infoa.educationms.entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {
    /**
     * 根据课程ID查询所有section
     * @param courseId 课程ID
     * @return 该课程的所有section列表
     */
    List<Section> findByCourseId(int courseId);

    /**
     * 根据教师ID查询所有授课的section
     * @param teacherId 教师ID
     * @return 该教师授课的section列表
     */
    List<Section> findByTeacherId(Integer teacherId);

    /**
     * 根据课程ID查询第一个section（按section_id排序）
     * @param courseId 课程ID
     * @return 符合条件的section（Optional包装）
     */
    Optional<Section> findFirstByCourseId(Integer courseId);

    Arrays findByCourseIdIn(List<Integer> courseIds);
}
