package com.sparta.week01;

import com.sparta.week01.domain.Course;
import com.sparta.week01.domain.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@EnableJpaAuditing
@SpringBootApplication
public class Week01Application {
    public static void main(String[] args) {
        SpringApplication.run(Week01Application.class, args);
    }

    // Week02Application.java 의 main 함수 아래에 붙여주세요.
    @Bean
    public CommandLineRunner demo(CourseRepository repository) {
        return (args) -> {
            Course course1 = new Course("웹개발의 봄 Spring", "남병관");
            repository.save(course1);

            List<Course> courseList = repository.findAll();
            courseList.forEach(c -> System.out.println(c.getTitle() + " " + c.getTutor()));
        };
    }
}
