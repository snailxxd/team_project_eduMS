package control;

import entities.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CourseResourceService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CourseResourceController {

    private final CourseResourceService service;

    @Autowired
    public CourseResourceController(CourseResourceService service) {
        this.service = service;
    }

    // 设置作业成绩占比
    @PostMapping("/courses/{courseId}/homework/proportion")
    public ResponseEntity<Void> setHomeworkProportion(
            @PathVariable Integer courseId,
            @RequestBody Map<String, Double> requestBody) {
        Double proportion = requestBody.get("proportion");
        if (proportion == null || proportion < 0 || proportion > 1) {
            return ResponseEntity.badRequest().build();
        }
        service.setHomeworkProportion(courseId, proportion);
        return ResponseEntity.ok().build();
    }

    // 设置作业成绩
    @PostMapping("/courses/{courseId}/students/{studentId}/homework")
    public ResponseEntity<Void> setHomeworkScore(
            @PathVariable Integer courseId,
            @PathVariable Integer studentId,
            @RequestBody Map<String, Integer> requestBody) {
        Integer score = requestBody.get("score");
        if (score == null || score < 0 || score > 100) {
            return ResponseEntity.badRequest().build();
        }
        service.setHomeworkScore(courseId, studentId, score);
        return ResponseEntity.ok().build();
    }

    // 设置考勤占比
    @PostMapping("/courses/{courseId}/attendance/proportion")
    public ResponseEntity<Void> setAttendanceProportion(
            @PathVariable Integer courseId,
            @RequestBody Map<String, Double> requestBody) {
        Double proportion = requestBody.get("proportion");
        if (proportion == null || proportion < 0 || proportion > 1) {
            return ResponseEntity.badRequest().build();
        }
        service.setAttendanceProportion(courseId, proportion);
        return ResponseEntity.ok().build();
    }

    // 设置考勤成绩
    @PostMapping("/courses/{courseId}/students/{studentId}/attendance")
    public ResponseEntity<Void> setAttendanceScore(
            @PathVariable Integer courseId,
            @PathVariable Integer studentId,
            @RequestBody Map<String, Integer> requestBody) {
        Integer score = requestBody.get("score");
        if (score == null || score < 0 || score > 100) {
            return ResponseEntity.badRequest().build();
        }
        service.setAttendanceScore(courseId, studentId, score);
        return ResponseEntity.ok().build();
    }
}