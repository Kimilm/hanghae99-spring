package com.sparta.week01.prac;

public class Person {
    private String name;
    private String address;
    private String job;
    private int age;

    public Person() {
    }

    public Person(String name, String address, String job, int age) {
        this.name = name;
        this.address = address;
        this.job = job;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
