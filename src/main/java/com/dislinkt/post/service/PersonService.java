package com.dislinkt.post.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dislinkt.post.dto.PersonDTO;
import com.dislinkt.post.mapper.PersonMapper;
import com.dislinkt.post.model.Person;
import com.dislinkt.post.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    private PersonMapper mapper = new PersonMapper();

    public List<PersonDTO> findAll(){
        return mapper.toDtoList(repository.findAll());
    }

    private Person findOne(UUID id){
        for (Person person: repository.findAll()){
            if (person.getId().equals(id))
                return person;
        }
        return null;
    }

    public PersonDTO create(UUID id, PersonDTO dto) throws Exception{
        Person person = mapper.toEntity(dto);
        person.setId(id);
        if (findOne(person.getId()) != null)
            throw new Exception("User with given id already exists!");
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
        person = repository.save(person);
        return mapper.toDto(person);
    }
    
}
