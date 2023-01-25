package com.dislinkt.post.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dislinkt.post.dto.BiographyAttributeDTO;
import com.dislinkt.post.mapper.BiographyAttributeMapper;
import com.dislinkt.post.model.BiographyAttribute;
import com.dislinkt.post.model.Person;
import com.dislinkt.post.repository.BiographyAttributeRepository;

@Service
public class BiographyAttributeService {

    @Autowired
    private BiographyAttributeRepository repository;

    @Autowired
    private PersonService personService;

    private BiographyAttributeMapper mapper = new BiographyAttributeMapper();

    public BiographyAttributeDTO create(UUID personId, BiographyAttributeDTO dto) throws Exception{
        BiographyAttribute bAttribute = mapper.toEntity(dto);

        Person person = personService.findOne(personId);
        if (person == null)
            throw new Exception("User with given id doesn't exist!");
        bAttribute.setPerson(person);

        bAttribute = repository.save(bAttribute);

        return mapper.toDto(bAttribute);
    }
    
}
