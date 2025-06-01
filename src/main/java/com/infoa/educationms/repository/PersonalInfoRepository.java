package com.infoa.educationms.repository;

import com.infoa.educationms.entities.PersonalInfor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfor, Integer> {

    PersonalInfor findOneByPersonalInfoId(int id);
    PersonalInfor save(PersonalInfor personalInfor);
}
