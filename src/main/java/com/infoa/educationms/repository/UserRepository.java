package com.infoa.educationms.repository;

import com.infoa.educationms.entities.User;
import com.infoa.educationms.entities.UserRole;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByAccountNumber(String accountNumber);
    List<User> findByAccountNumberContainingAndUserType(String accountNumber, UserRole userType);
}
