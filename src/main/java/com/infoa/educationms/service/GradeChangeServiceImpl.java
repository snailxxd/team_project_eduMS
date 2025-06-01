package com.infoa.educationms.service;

import com.infoa.educationms.DTO.GradeChangeDTO;
import com.infoa.educationms.entities.GradeChange;
import com.infoa.educationms.entities.PersonalInfor;
import com.infoa.educationms.entities.Student;
import com.infoa.educationms.entities.User;
import com.infoa.educationms.entities.Course;
import com.infoa.educationms.entities.Grade;
import com.infoa.educationms.repository.GradeChangeRepository;
import com.infoa.educationms.repository.PersonalInfoRepository;
import com.infoa.educationms.repository.GradeRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GradeChangeServiceImpl implements GradeChangeService {
    
    @Autowired
    private GradeChangeRepository gradeChangeRepository;

    @Autowired
    private PersonalInfoRepository personalInfoRepository;
    
    @Autowired
    private  GradeRepository gradeRepository;

    // 时间格式转换器
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public List<GradeChangeDTO> getPendingGradeChanges() {
        // 获取所有待审核的申请（result 为 null）
        List<GradeChange> pendingChanges = gradeChangeRepository.findByResultIsNull();
        
        return pendingChanges.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GradeChangeDTO createGradeChange(GradeChangeDTO gradeChangeDTO) {
        GradeChange gradeChange = convertToEntity(gradeChangeDTO);
        
        // 设置申请时间为当前时间
        gradeChange.setApplyTime(LocalDateTime.now());
        
        // 初始状态为待审核（result = false）
        gradeChange.setResult(false);
        
        GradeChange savedChange = gradeChangeRepository.save(gradeChange);
        return convertToDTO(savedChange);
    }

    @Override
    public GradeChangeDTO updateGradeChange(Integer gradeChangeId, GradeChangeDTO gradeChangeDTO) {
        return gradeChangeRepository.findById(gradeChangeId)
                .map(existingChange -> {
                    // 只允许更新审核结果和审核时间
                    if (gradeChangeDTO.getResult() != null) {
                        existingChange.setResult(gradeChangeDTO.getResult());
                        existingChange.setCheckTime(LocalDateTime.now());
                    }
                    
                    // 其他字段不允许更新
                    GradeChange updatedChange = gradeChangeRepository.save(existingChange);
                    return convertToDTO(updatedChange);
                })
                .orElseThrow(() -> new IllegalArgumentException("成绩修改申请不存在: " + gradeChangeId));
    }

    // 实体转DTO（包含时间格式转换）
    private GradeChangeDTO convertToDTO(GradeChange gradeChange) {
        GradeChangeDTO dto = new GradeChangeDTO();
        
        Student student = gradeChangeRepository.findOneByTakesId(gradeChange.getTakeId());
        Course course = gradeChangeRepository.findOnecByTakesId(gradeChange.getTakeId());
        PersonalInfor personalInfor = personalInfoRepository.findOneByPersonalInfoId(student.getPersonalInfoId());
        Grade grade = gradeRepository.findOneByGradeId(gradeChange.getGradeId());
        // 复制基础字段
        dto.setGradeChangeId(gradeChange.getChangeId());
        dto.setTakesId(gradeChange.getTakeId());
        dto.setTeacherId(gradeChange.getTeacherId());
        dto.setResult(gradeChange.getResult());
        dto.setNewGrade(gradeChange.getNewGrade());
        dto.setGradeId(gradeChange.getGradeId());
        dto.setStudentId(student.getUserId());
        dto.setStudentName(personalInfor.getName());
        dto.setCourseId(course.getCourseId());
        dto.setCourseName(course.getTitle());
        dto.setOriginalGrade(grade.getGrade());
        dto.setReason(gradeChange.getReason());
        
        // 转换时间格式
        if (gradeChange.getApplyTime() != null) {
            dto.setApplyTime(gradeChange.getApplyTime().format(formatter));
        }
        if (gradeChange.getCheckTime() != null) {
            dto.setCheckTime(gradeChange.getCheckTime().format(formatter));
        }
        
        return dto;
    }

    // DTO转实体（包含时间格式转换）
    private GradeChange convertToEntity(GradeChangeDTO dto) {
        GradeChange entity = new GradeChange();
        
        // 复制基础字段
        entity.setChangeId(dto.getGradeChangeId());
        entity.setTakeId(dto.getTakesId());
        entity.setTeacherId(dto.getTeacherId());
        entity.setResult(dto.getResult());
        entity.setNewGrade(dto.getNewGrade());
        entity.setGradeId(dto.getGradeId());
        
        // 转换时间格式
        if (dto.getApplyTime() != null) {
            entity.setApplyTime(LocalDateTime.parse(dto.getApplyTime(), formatter));
        }
        if (dto.getCheckTime() != null) {
            entity.setCheckTime(LocalDateTime.parse(dto.getCheckTime(), formatter));
        }
        
        return entity;
    }
}