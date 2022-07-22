package com.sparta.week01.controller;

import com.sparta.week01.repository.CourseRepository;
import com.sparta.week01.domain.course.Course;
import com.sparta.week01.domain.course.CourseRequestDto;
import com.sparta.week01.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CourseController {

    private final CourseRepository courseRepository;
    private final CourseService courseService;

    @GetMapping("/api/courses")
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    // @RequestBody 어노테이션으로 넘어오는 데이터를 변환
    @PostMapping("/api/courses")
    public Course createCourses(@RequestBody CourseRequestDto requestDto) {
        Course course = new Course(requestDto);

        return courseRepository.save(course);
    }

    // {id}로 넘어오는 값을 @PathVariable로 받음
    @PutMapping("/api/courses/{id}")
    public Long updateCourses(@PathVariable Long id, @RequestBody CourseRequestDto requestDto) {
        return courseService.update(id, requestDto);
    }

    @DeleteMapping("/api/courses/{id}")
    public Long deleteCourses(@PathVariable Long id) {
        courseRepository.deleteById(id);
        return id;
    }
}
