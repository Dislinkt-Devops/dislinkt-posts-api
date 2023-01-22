package com.dislinkt.post.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorDTO> ret = new ArrayList<ErrorDTO>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            ErrorDTO dto = new ErrorDTO(error.getDefaultMessage());
            ret.add(dto);
        });
        return ret;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<PersonDTO>>> getAll(){
        ResponseDTO<List<PersonDTO>> ret = new ResponseDTO<>(service.findAll());
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPerson(@RequestHeader("X-User-Id") UUID id, @Valid @RequestBody PersonDTO dto){
        try{
            ResponseDTO<PersonDTO> ret = new ResponseDTO<>(service.create(id, dto));
            return new ResponseEntity<>(ret, HttpStatus.OK);
        }
        catch (Exception ex){
            ErrorDTO error = new ErrorDTO(ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{receiver}")
    public ResponseEntity checkForInteraction(@RequestHeader("X-User-Id") UUID id, @PathVariable UUID receiver){
        try{
            ResponseDTO<Boolean> ret = new ResponseDTO<>(service.canInteractWith(id, receiver));
            return new ResponseEntity<>(ret, HttpStatus.OK);
        }
        catch (Exception ex){
            ErrorDTO error = new ErrorDTO(ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/block/{blockedId}")
    public ResponseEntity<ResponseDTO<Boolean>> blockUser(@RequestHeader("X-User-Id") UUID id, @PathVariable UUID blockedId){
        ResponseDTO<Boolean> ret = new ResponseDTO<>(service.blockPerson(id, blockedId));
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
}
