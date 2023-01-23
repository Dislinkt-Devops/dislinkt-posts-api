package com.dislinkt.post.service;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dislinkt.post.dto.PersonDTO;
import com.dislinkt.post.enums.Gender;
import com.dislinkt.post.enums.ProfilePrivacy;
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

    public Person findOne(UUID id){
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

        person = repository.save(person);
        return mapper.toDto(person);
    }

    public Boolean canInteractWith(UUID id, UUID receiverId) throws Exception{
        Person sender = findOne(id);
        if (sender == null)
            throw new Exception("sender with given id doesn't exist");

        Person receiver = findOne(receiverId);
        if (receiver == null)
            throw new Exception("receiver with given id doesn't exist");

        if (!sender.getFollowing().contains(receiver) || !receiver.getFollowing().contains(sender))
            return Boolean.FALSE;

        if (sender.getBlockedBy().contains(receiver) || receiver.getBlockedBy().contains(sender))
            return Boolean.FALSE;

        return Boolean.TRUE;
    }

    public PersonDTO getMyProfile(UUID id) throws Exception {
        Person user = repository.findById(id).orElse(null);
        if (user == null)
            throw new Exception("User with given id doesn't exist!");

        return mapper.toDto(user);
    }

    public PersonDTO editMyProfile(UUID id, PersonDTO dto) throws Exception {
        Person user = repository.findById(id).orElse(null);
        if (user == null)
            throw new Exception("User with given id doesn't exist!");

        user.setBio(dto.getBio());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setGender(Gender.valueOf(dto.getGender()));
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPrivacy(ProfilePrivacy.valueOf(dto.getPrivacy()));

        user = repository.save(user);
        
        return null;
    }
    
}
