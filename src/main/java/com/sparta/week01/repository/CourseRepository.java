package com.sparta.week01.repository;

import com.sparta.week01.domain.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}