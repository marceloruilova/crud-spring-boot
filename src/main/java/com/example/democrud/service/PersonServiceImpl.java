package com.example.democrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.example.democrud.common.GenericServiceImpl;
import com.example.democrud.dao.api.PersonDao;
import com.example.democrud.model.Person;

@Service
public class PersonServiceImpl extends GenericServiceImpl<Person, Long> implements PersonService {

	@Autowired
	private PersonDao personDao;
	
	@Override
	public CrudRepository<Person, Long> getDao() {
		return personDao;
	}

}
