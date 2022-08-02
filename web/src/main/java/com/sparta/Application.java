package com.sparta;

import com.sparta.basic.domain.course.Course;
import com.sparta.basic.domain.course.CourseRequestDto;
import com.sparta.basic.domain.person.Person;
import com.sparta.basic.repository.CourseRepository;
import com.sparta.basic.repository.PersonRepository;
import com.sparta.basic.service.CourseService;
import com.sparta.selectshop.models.user.SignupRequestDto;
import com.sparta.selectshop.repository.UserRepository;
import com.sparta.selectshop.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // Week02Application.java 의 main 함수 아래에 붙여주세요.
    @Bean
    public CommandLineRunner demo(
            CourseRepository courseRepository,
            CourseService service,
            PersonRepository personRepository,
            UserService userService
    ) {
        return (args) -> {
            Course course1 = new Course("프론트엔드의 꽃 리엑트", "임민영");
            courseRepository.save(course1);

            Person person = new Person("손흥민", "런던", "축구선수", 28);
            personRepository.save(person);

            SignupRequestDto signupRequestDto = new SignupRequestDto();
            signupRequestDto.setUsername("kimilm");
            signupRequestDto.setPassword("kimilm");
            signupRequestDto.setEmail("kimilm@kimilm.com");

            SignupRequestDto signupRequestDto2 = new SignupRequestDto();
            signupRequestDto2.setUsername("kimilmKakao");
            signupRequestDto2.setPassword("kimilmKakao");
            signupRequestDto2.setEmail("gnlfla7416@daum.net");

            SignupRequestDto signupRequestDtoAdmin = new SignupRequestDto();
            signupRequestDtoAdmin.setUsername("kimilmAdmin");
            signupRequestDtoAdmin.setPassword("kimilmAdmin");
            signupRequestDtoAdmin.setEmail("kimilmAdmin@kimilm.com");
            signupRequestDtoAdmin.setAdmin(true);
            signupRequestDtoAdmin.setAdminToken("AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC");

            userService.registerUser(signupRequestDto);
            userService.registerUser(signupRequestDto2);
            userService.registerUser(signupRequestDtoAdmin);

            /*
            List<Course> courseList = courseRepository.findAll();
            courseList.forEach(c -> System.out.println(c.getId() + " " + c.getTitle() + " " + c.getTutor()));

            // 데이터 하나 조회하기
            courseRepository.findById(1L).orElseThrow(
                    () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
            );

            // 업데이트
            CourseRequestDto requestDto = new CourseRequestDto("웹개발의 봄 Spring", "남병관");
            service.update(1L, requestDto);

            // 조회해보기
            courseList = courseRepository.findAll();
            courseList.forEach(c -> System.out.println(c.getTitle() + " " + c.getTutor()));

            // 삭제
            // repository.deleteById(1L);
            */
        };
    }
}