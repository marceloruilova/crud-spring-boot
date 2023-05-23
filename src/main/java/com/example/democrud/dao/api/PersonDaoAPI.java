package com.example.democrud.dao.api;

import org.springframework.data.repository.CrudRepository;

import com.example.democrud.model.Person;

public interface PersonDaoAPI extends CrudRepository<Person, Long> {

}
