package com.CloneShopee.services.sale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiceTest {
    public static void main(String[] args) {
        // Country c1 = new Country(1, "Thanh Hóa");
        // Country c2 = new Country(1, "Thanh Hóa");
        // List<Person> people = Arrays.asList(
        // new Person("Alice", 25, 1),
        // new Person("Bob", 30, 2),
        // new Person("Charlie", 25, 2),
        // new Person("David", 30, 2),
        // new Person("Eve", 35, 1),
        // new Person("Alice", 25, 1));
        // List<Country> countries = people.stream()
        // .collect(Collectors.groupingBy(
        // Person::getCountryId,
        // Collectors.toList()))
        // .entrySet().stream()
        // .map(entry -> new Country(entry.getKey(), "Country " + entry.getKey(),
        // entry.getValue()))
        // .collect(Collectors.toList());
        // countries.forEach(v -> {
        // System.out.println(v);
        // });

    }
}

class Person {
    String name;
    int age;
    Integer countryId;

    public Person(String name, int age, Integer countryId) {
        this.name = name;
        this.age = age;
        this.countryId = countryId;
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

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}

class Country {
    private Integer id;
    private String countryName;
    private List<Person> person;

    public Country(Integer id, String name) {
        this.id = id;
        this.countryName = name;
    }

    public Country(Integer id, String name, List<Person> people) {
        this.id = id;
        this.countryName = name;
        this.person = people;
    }

    @Override
    public String toString() {
        return "Country{id=" + id + ", name='" + countryName + "', people=" + person + "}";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<Person> getPerson() {
        return person;
    }

    public void setPerson(List<Person> person) {
        this.person = person;
    }

}