package com.sparta.week01.controller;

import com.sparta.week01.domain.person.Person;
import com.sparta.week01.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonController {

    private final PersonRepository personRepository;

    @GetMapping("/api/persons")
    public List<Person> getPerson() {
        return personRepository.findAll();
    }
}
