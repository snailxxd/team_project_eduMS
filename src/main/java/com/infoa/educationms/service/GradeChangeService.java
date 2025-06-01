package com.infoa.educationms.service;

import com.infoa.educationms.DTO.*;
import com.infoa.educationms.entities.*;

import java.util.List;

public interface GradeChangeService {

    List<GradeChangeDTO> getPendingGradeChanges();

    GradeChangeDTO createGradeChange(GradeChangeDTO gradeChangeDTO);

    GradeChangeDTO updateGradeChange(Integer gradeChangeId, GradeChangeDTO gradeChangeDTO);

}
