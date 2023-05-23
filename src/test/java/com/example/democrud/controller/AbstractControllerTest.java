package com.example.democrud.controller;

import com.example.democrud.builder.PersonBuilder;
import com.example.democrud.dao.api.PersonDaoAPI;
import com.example.democrud.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractControllerTest {
    protected static final String URL = "/api/v1/";
    protected Person person;
    protected Person person2;
    protected Person person3;
    protected Person person4;
    protected final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    protected PersonDaoAPI personDaoController;

    public void initializeTestData(){
        person = new PersonBuilder().withName("Juan")
                .withSurName("Perez")
                .withAddress("Quito")
                .withPhone("0999774738")
                .build();
        person2 = new PersonBuilder().withName("Jose")
                .withSurName("Arto")
                .withAddress("Quito")
                .withPhone("0999774738")
                .build();
        person3 = new PersonBuilder().withName("Pablo")
                .withSurName("Teran")
                .withAddress("Colombia")
                .withPhone("0935774738")
                .build();
        person4 = new PersonBuilder().withName("Fanny")
                .withSurName("Rentor")
                .withAddress("Rusia")
                .withPhone("0935744738")
                .build();
        personDaoController.saveAll(List.of(person,person2,person3,person4));
    }
    protected void assertSavedPerson(Person savedPerson, Person responsePerson) {
        Assertions.assertNotNull(savedPerson);
        Assertions.assertNotNull(responsePerson);
        Assertions.assertEquals(responsePerson.getName(), savedPerson.getName());
        Assertions.assertEquals(responsePerson.getSurname(), savedPerson.getSurname());
        Assertions.assertEquals(responsePerson.getAddress(), savedPerson.getAddress());
        Assertions.assertEquals(responsePerson.getPhone(), savedPerson.getPhone());
    }
}
