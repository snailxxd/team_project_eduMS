package repository;

import entities.Grade;
import entities.Take;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
<<<<<<< HEAD
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
=======
    // 基本方法
    Optional<Grade> findByTakeIdAndGradeType(int takeId, String gradeType);

    // 查询某个takeId的所有成绩
    List<Grade> findByTakeId(int takeId);

    // 查询某种类型的所有成绩
    List<Grade> findByGradeType(String gradeType);

    // 查询某个section的所有成绩(需要Grade实体有sectionId字段)
    @Query("SELECT g FROM Grade g, Take t WHERE g.takeId = t.takeId AND t.sectionId = :sectionId")
    List<Grade> findBySectionId(@Param("sectionId") int sectionId);

    // 查询某个学生的某种类型成绩
    @Query("SELECT g FROM Grade g, Take t WHERE g.takeId = t.takeId AND t.studentId = :studentId AND g.gradeType = :gradeType")
    List<Grade> findByStudentIdAndGradeType(@Param("studentId") int studentId,
                                            @Param("gradeType") String gradeType);

    /**
     * 根据多个sectionId和成绩类型查询成绩记录
     * @param sectionIds section ID列表
     * @param gradeType 成绩类型（如："exam", "homework", "attendance"）
     * @return 符合条件的成绩记录列表
     */
    @Query("SELECT g FROM Grade g, Take t WHERE g.takeId = t.takeId AND t.sectionId IN :sectionIds AND g.gradeType = :gradeType")
    List<Grade> findBySectionIdInAndGradeType(
            @Param("sectionIds") List<Integer> sectionIds,
            @Param("gradeType") String gradeType);
>>>>>>> dcf4e013c125a44b2c4542a2bde8f569ae0c168f
}
