package com.example.democrud.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.democrud.model.Person;

public interface PersonDao extends CrudRepository<Person, Long> {

}
