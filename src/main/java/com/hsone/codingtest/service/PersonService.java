package com.hsone.codingtest.service;

import com.hsone.codingtest.dao.PersonDAO;
import com.hsone.codingtest.entity.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    private final PersonDAO personDAO;

    public PersonService(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public ResponseEntity<Object> getPerson(Integer personId) {
        Optional<Person> person = personDAO.findById(personId);
        return person.<ResponseEntity<Object>>map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Cannot find Person with id: " + personId, HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Object> postPerson(Person person) {
        Optional<Person> savedPerson = personDAO.findById(person.getId());
        return savedPerson.<ResponseEntity<Object>>map(
                        value -> new ResponseEntity<>(personDAO.saveAndFlush(person), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(personDAO.saveAndFlush(person), HttpStatus.CREATED));
    }
}