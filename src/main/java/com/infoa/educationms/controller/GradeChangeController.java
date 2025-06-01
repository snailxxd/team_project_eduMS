package com.infoa.educationms.controller;

import com.infoa.educationms.queries.ApiResult;
import com.infoa.educationms.service.EducationMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GradeChangeController {

    @Autowired
    private EducationMSService educationMSService;

    @GetMapping("/grade-changes/pending")
    public ResponseEntity<ApiResult> getPendingGradeChanges() {
        // 可扩展 service 接口：getPendingGradeChanges()
        return ResponseEntity.ok(ApiResult.success(List.of())); // placeholder
    }

    @PostMapping("/grade-changes")
    public ResponseEntity<ApiResult> createGradeChange(@RequestBody GradeChangeDTO dto) {
        ApiResult result = educationMSService.changeGrade(dto.getTakeId(), dto.getNewGrade(), dto.getReason());
        return ResponseEntity.ok(result);
    }

    @PutMapping("/grade-changes/{gradeChangeId}")
    public ResponseEntity<ApiResult> updateGradeChange(@PathVariable Integer gradeChangeId, @RequestBody GradeChangeDTO dto) {
        ApiResult result = educationMSService.auditGradeChange(gradeChangeId, dto.isApproved());
        return ResponseEntity.ok(result);
    }
}
