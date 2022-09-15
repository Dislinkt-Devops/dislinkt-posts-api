package com.dislinkt.post.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dislinkt.post.model.Person;
import com.dislinkt.post.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    public List<Person> findAll(){
        return repository.findAll();
    }

    public Person findOne(UUID id){
        return repository.findById(id).orElse(null);
    }

    public Person create(Person person) throws Exception{
        System.out.println(person.getId());
        if (repository.findById(person.getId()).orElse(null) != null){
            throw new Exception("User with given id already exists!");
        }
        if (person.getFirstName().isBlank())
            throw new Exception("First name cannot be empty!");
        if (person.getLastName().isBlank())
            throw new Exception("Last name cannot be empty!");
        if (person.getPhoneNumber().isBlank())
            throw new Exception("Phone number cannot be empty!");
        if (person.getGender() == null)
            throw new Exception("Gender cannot be empty!");
        if (person.getPrivacy() == null)
            throw new Exception("Privacy cannot be empty!");
        return repository.save(person);
    }
    
}
