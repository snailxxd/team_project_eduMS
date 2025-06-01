package com.infoa.educationms.repository;

import com.infoa.educationms.entities.PersonalInfor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfor, Integer> {
}
