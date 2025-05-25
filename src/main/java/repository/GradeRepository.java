package repository;

import entities.Grade;
import entities.Take;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
    // 根据 takeId 直接查询成绩
    List<Grade> findByTakeId(int takeId);

    // 通过学生ID查询成绩
    @Query("SELECT g FROM Grade g " +
          "JOIN g.take t " +
          "WHERE t.studentId = :studentId")
    List<Grade> findByStudentId(@Param("studentId") int studentId);

    // 通过章节ID查询成绩
    @Query("SELECT g FROM Grade g " +
          "JOIN g.take t " +
          "WHERE t.sectionId = :sectionId")
    List<Grade> findBySectionId(@Param("sectionId") int sectionId);
}
