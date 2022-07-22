package com.sparta.week01.controller;

import com.sparta.week01.prac.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    @GetMapping("/myinfo")
    public Person getPerson() {
        Person person = new Person("손흥민", "런던", "축구선수", 28);
        return person;
    }
}
