package repository;

import entities.GradeChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeChangeRepository extends JpaRepository<GradeChange, Integer> {
}
