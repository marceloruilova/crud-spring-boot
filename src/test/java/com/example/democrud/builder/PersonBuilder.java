package com.example.democrud.builder;

import com.example.democrud.model.Person;

public class PersonBuilder {
    private String name;
    private String surname;
    private String address;
    private String phone;

    public PersonBuilder() {
    }

    public PersonBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public PersonBuilder withSurName(String surname) {
        this.surname = surname;
        return this;
    }
    public PersonBuilder withAddress(String address) {
        this.address = address;
        return this;
    }
    public PersonBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }
    public Person build() {
        Person person = new Person();
        person.setName(name);
        person.setSurname(surname);
        person.setAddress(address);
        person.setPhone(phone);
        return person;
    }
}
