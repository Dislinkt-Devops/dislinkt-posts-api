package com.dislinkt.post.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dislinkt.post.dto.ErrorDTO;
import com.dislinkt.post.dto.PersonDTO;
import com.dislinkt.post.dto.ResponseDTO;
import com.dislinkt.post.service.PersonService;

@RestController
@CrossOrigin
@RequestMapping("/people")
public class PersonController {
    
    @Autowired
    private PersonService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO<List<PersonDTO>>> getAll(){
        ResponseDTO<List<PersonDTO>> ret = new ResponseDTO<>(service.findAll());
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPerson(@RequestHeader("id") UUID id, @RequestBody PersonDTO dto){
        try{
            ResponseDTO<PersonDTO> ret = new ResponseDTO<>(service.create(id, dto));
            return new ResponseEntity<>(ret, HttpStatus.OK);
        }
        catch (Exception ex){
            ErrorDTO error = new ErrorDTO(ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}
