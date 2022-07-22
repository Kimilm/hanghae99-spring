package com.sparta.week01.service;

import com.sparta.week01.domain.person.Person;
import com.sparta.week01.domain.person.PersonRequestDto;
import com.sparta.week01.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    @Transactional
    public Long update(Long id, PersonRequestDto requestDto) {
        Person person = personRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
        person.update(requestDto);
        return person.getId();
    }
}
