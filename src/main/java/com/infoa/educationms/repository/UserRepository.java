package com.infoa.educationms.repository;

import com.infoa.educationms.entities.User;
import com.infoa.educationms.entities.UserRole;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // 根据账号和密码查询用户
    boolean existsByAccountNumber(String accountNumber);

    List<User> findByAccountNumberContainingAndUserType(String accountNumber, UserRole userType);

    // 根据账号查询用户
    User findByAccountNumber(String accountNumber);
}
