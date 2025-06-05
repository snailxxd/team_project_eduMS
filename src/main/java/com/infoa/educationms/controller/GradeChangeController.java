package com.infoa.educationms.controller;

import com.infoa.educationms.DTO.GradeChangeDTO;
import com.infoa.educationms.service.GradeChangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GradeChangeController {

    private final GradeChangeService gradeChangeService;

    public GradeChangeController(GradeChangeService gradeChangeService) {
        this.gradeChangeService = gradeChangeService;
    }
    //管理员获得申请列表
    @GetMapping("/grade-changes/pending")
    public ResponseEntity<List<GradeChangeDTO>> getPendingGradeChanges() {
        List<GradeChangeDTO> pendingChanges = gradeChangeService.getPendingGradeChanges();
        return ResponseEntity.ok(pendingChanges);
    }
    //教师申请
    @PostMapping("/grade-changes")
    public ResponseEntity<GradeChangeDTO> createGradeChange(
            @RequestBody GradeChangeDTO gradeChangeDTO) {
        GradeChangeDTO createdChange = gradeChangeService.createGradeChange(gradeChangeDTO);
        return ResponseEntity.ok(createdChange);
    }
    //管理员审核
    @PutMapping("/grade-changes/{gradeChangeId}")
    public ResponseEntity<GradeChangeDTO> updateGradeChange(
            @PathVariable Integer gradeChangeId,
            @RequestBody GradeChangeDTO gradeChangeDTO) {
        GradeChangeDTO updatedChange = gradeChangeService.updateGradeChange(gradeChangeId, gradeChangeDTO);
        return ResponseEntity.ok(updatedChange);
    }
}