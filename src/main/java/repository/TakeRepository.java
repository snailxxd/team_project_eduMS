package repository;

import entities.Take;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TakeRepository extends JpaRepository<Take, Integer> {
}
