package com.example.democrud.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.democrud.builders.PersonBuilder;
import com.example.democrud.dao.api.PersonDaoAPI;
import com.example.democrud.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class PersonRestControllerTest {
    private static final String URL = "/api/v1/";
    private Person person;
    private Person person2;
    private Person person3;
    private Person person4;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonDaoAPI personDaoController;

    @BeforeEach
    public void initialize()  {
        personDaoController.deleteAll();
        initializeTestData();
    }
    @Test
    public void testGetAll() throws Exception {
        // GIVEN

        // WHEN
        RequestBuilder requestBuilder= MockMvcRequestBuilders.get(URL + "all");

        // THEN
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        List<Person> personList = objectMapper.readValue(responseBody, new TypeReference<List<Person>>() {});
        Assertions.assertNotNull(personList);
        Assertions.assertEquals(4, personList.size());
    }
    @Test
    public void testGetAllEntityNotFound() throws Exception {
        // GIVEN
        personDaoController.deleteAll();

        // WHEN
        RequestBuilder requestBuilder= MockMvcRequestBuilders.get(URL + "all");

        // THEN
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound()).andReturn();
    }
    @Test
    public void testFind() throws Exception {
        // GIVEN

        // WHEN
        RequestBuilder requestBuilder= MockMvcRequestBuilders.get(URL + "find/{id}", person2.getId());

        // THEN
        Person responsePerson = getPerson(requestBuilder);
        assertSavedPerson(person2,responsePerson);
    }
    @Test
    public void testFindStringId() throws Exception {
        // GIVEN

        // WHEN
        RequestBuilder requestBuilder= MockMvcRequestBuilders.get(URL + "find/{id}", person2.getName());

        // THEN
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest()).andReturn();
    }
    @Test
    public void testFindEntityNotFoundId() throws Exception {
        // GIVEN
        personDaoController.deleteAll();

        // WHEN
        RequestBuilder requestBuilder= MockMvcRequestBuilders.get(URL + "find/{id}", person.getId());

        // THEN
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound()).andReturn();
    }
    @Test
    public void testSave() throws Exception {
        // GIVEN

        // WHEN
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL + "save", person3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person3));

        // THEN
        Person responsePerson = getPerson(requestBuilder);
        assertSavedPerson(person3,responsePerson);
    }
    @Test
    public void testSaveBadRequest() throws Exception {
        // GIVEN
        Person fakePerson = new Person();
        fakePerson.setName("John");

        // WHEN
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL + "save", fakePerson)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fakePerson));

        // THEN
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest()).andReturn();
    }
    @Test
    public void testDelete() throws Exception {
        // GIVEN

        // WHEN
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URL + "delete/{id}", person4.getId());

        // THEN
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        Assertions.assertFalse(personDaoController.existsById(person4.getId()));
    }
    @Test
    public void testDeleteEntityNotFoundId() throws Exception {
        // GIVEN
        personDaoController.deleteAll();

        // WHEN
        RequestBuilder requestBuilder= MockMvcRequestBuilders.delete(URL + "delete/{id}", person.getId());

        // THEN
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound()).andReturn();
    }
    @Test
    public void testDeleteStringId() throws Exception {
        // GIVEN
        personDaoController.deleteAll();

        // WHEN
        RequestBuilder requestBuilder= MockMvcRequestBuilders.delete(URL + "delete/{id}", person.getName());

        // THEN
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest()).andReturn();
    }
    public Person getPerson(RequestBuilder requestBuilder) throws Exception {
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        return objectMapper.readValue(responseBody, Person.class);
    }
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
    private void assertSavedPerson(Person savedPerson, Person responsePerson) {
        Assertions.assertNotNull(savedPerson);
        Assertions.assertNotNull(responsePerson);
        Assertions.assertEquals(responsePerson.getName(), savedPerson.getName());
        Assertions.assertEquals(responsePerson.getSurname(), savedPerson.getSurname());
        Assertions.assertEquals(responsePerson.getAddress(), savedPerson.getAddress());
        Assertions.assertEquals(responsePerson.getPhone(), savedPerson.getPhone());
    }
}
