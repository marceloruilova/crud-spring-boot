package com.example.democrud.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.democrud.model.Person;
import com.example.democrud.service.api.PersonServiceAPI;

@RestController
@RequestMapping(value = "/api/v1/")
@CrossOrigin("*")
public class PersonRestController {

	@Autowired
	private PersonServiceAPI personServiceAPI;

	@GetMapping(value = "/all")
	public ResponseEntity<List<Person>> getAll() {
		try {
			List<Person> persons = personServiceAPI.getAll();
			if (persons.isEmpty()) {
				throw new EntityNotFoundException();
			}
			return ResponseEntity.ok(persons);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping(value = "/find/{id}")
	public ResponseEntity<Person> find(@PathVariable Long id) {
		try {
			if(personServiceAPI.get(id)==null){
				throw new EntityNotFoundException();
			}
			Person person = personServiceAPI.get(id);
			return ResponseEntity.ok(person);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping(value = "/save")
	public ResponseEntity<Person> save(@RequestBody @Valid Person person) {
		try {
			Person obj = personServiceAPI.save(person);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		} catch (ValidationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Person> delete(@PathVariable Long id) {
		try {
			if (personServiceAPI.get(id)==null) {
				throw new EntityNotFoundException();
			}
			personServiceAPI.delete(id);
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
