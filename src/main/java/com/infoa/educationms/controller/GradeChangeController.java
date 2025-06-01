package com.infoa.educationms.controller;

import com.infoa.educationms.DTO.GradeChangeDTO;
import com.infoa.educationms.queries.ApiResult;
import com.infoa.educationms.service.EducationMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GradeChangeController {

    @Autowired
    private EducationMSService educationMSService;

    @GetMapping("/grade-changes/pending")
    public ResponseEntity<List<GradeChangeDTO>> getPendingGradeChanges() {
        // 可扩展 service 接口：getPendingGradeChanges()
        return ResponseEntity.ok(ApiResult.success(List.of())); // placeholder
    }

    @PostMapping("/grade-changes")
    public ResponseEntity<GradeChangeDTO> createGradeChange(@RequestBody GradeChangeDTO dto) {
        GradeChangeDTO result = educationMSService.changeGrade(dto.getTakeId(), dto.getNewGrade(), dto.getReason());
        return ResponseEntity.ok(result);
    }

    @PutMapping("/grade-changes/{gradeChangeId}")
    public ResponseEntity<GradeChangeDTO> updateGradeChange(@PathVariable Integer gradeChangeId, @RequestBody GradeChangeDTO dto) {
        GradeChangeDTO result = educationMSService.auditGradeChange(gradeChangeId, dto.isApproved());
        return ResponseEntity.ok(result);
    }
}
