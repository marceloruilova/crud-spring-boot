package com.example.democrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.example.democrud.common.GenericServiceImpl;
import com.example.democrud.dao.api.PersonDaoAPI;
import com.example.democrud.model.Person;
import com.example.democrud.service.PersonServiceAPI;

@Service
public class PersonServiceImpl extends GenericServiceImpl<Person, Long> implements PersonServiceAPI {

	@Autowired
	private PersonDaoAPI personDaoAPI;
	
	@Override
	public CrudRepository<Person, Long> getDao() {
		return personDaoAPI;
	}

}
