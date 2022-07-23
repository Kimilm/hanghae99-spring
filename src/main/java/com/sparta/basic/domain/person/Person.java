package com.sparta.basic.domain.person;

import com.sparta.basic.domain.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Person extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String job;

    @Column(nullable = false)
    private int age;

    public Person(PersonRequestDto requestDto) {
        this.name = requestDto.getName();
        this.address = requestDto.getAddress();
        this.job = requestDto.getJob();
        this.age = requestDto.getAge();
    }

    public Person(String name, String address, String job, int age) {
        this.name = name;
        this.address = address;
        this.job = job;
        this.age = age;
    }

    public void update(PersonRequestDto requestDto) {
        this.name = requestDto.getName();
        this.address = requestDto.getAddress();
        this.job = requestDto.getJob();
        this.age = requestDto.getAge();
    }
}
