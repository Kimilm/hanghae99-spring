package com.sparta.week01;

import com.sparta.week01.domain.Course;
import com.sparta.week01.domain.CourseRepository;
import com.sparta.week01.domain.CourseRequestDto;
import com.sparta.week01.service.CourseService;
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
    public CommandLineRunner demo(CourseRepository repository, CourseService service) {
        return (args) -> {
            Course course1 = new Course("프론트엔드의 꽃 리엑트", "임민영");
            repository.save(course1);

            List<Course> courseList = repository.findAll();
            courseList.forEach(c -> System.out.println(c.getTitle() + " " + c.getTutor()));

            // 데이터 하나 조회하기
            Course course = repository.findById(1L).orElseThrow(
                    () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
            );

            // 업데이트
            CourseRequestDto requestDto = new CourseRequestDto("웹개발의 봄 Spring", "남병관");
            service.update(1L, requestDto);

            // 조회해보기
            courseList = repository.findAll();
            courseList.forEach(c -> System.out.println(c.getTitle() + " " + c.getTutor()));

            // 삭제
            repository.deleteById(1L);
        };
    }
}
