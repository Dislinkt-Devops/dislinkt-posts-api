package com.dislinkt.post.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.dislinkt.post.dto.PersonDTO;
import com.dislinkt.post.enums.Gender;
import com.dislinkt.post.enums.ProfilePrivacy;
import com.dislinkt.post.model.Person;

public class PersonMapper implements MapperInterface<Person, PersonDTO> {

    @Override
    public Person toEntity(PersonDTO dto) {
        return new Person(
            dto.getFirstName(),
            dto.getLastName(),
            Gender.valueOf(dto.getGender()),
            dto.getPhoneNumber(),
            dto.getDateOfBirth(),
            dto.getBio(),
            ProfilePrivacy.valueOf(dto.getPrivacy()));
    }

    @Override
    public List<Person> toEntityList(List<PersonDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public PersonDTO toDto(Person entity) {
        return new PersonDTO(
            entity.getId().toString(),
            entity.getFirstName(),
            entity.getLastName(), 
            entity.getGender().toString(), 
            entity.getPhoneNumber(), 
            entity.getDateOfBirth(), 
            entity.getBio(), 
            entity.getPrivacy().toString());
    }

    @Override
    public List<PersonDTO> toDtoList(List<Person> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
    
}
