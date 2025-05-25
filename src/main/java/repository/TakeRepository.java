package repository;
import java.util.List;
import entities.Take;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TakeRepository extends JpaRepository<Take, Integer> {
    List<Take> findByStudentId(Integer studentId);
    List<Take> findBySectionId(Integer sectionId);
}
