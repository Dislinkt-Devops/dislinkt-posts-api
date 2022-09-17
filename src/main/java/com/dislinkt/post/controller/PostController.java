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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dislinkt.post.service.PostService;
import com.dislinkt.post.dto.ErrorDTO;
import com.dislinkt.post.dto.PostDTO;
import com.dislinkt.post.dto.ResponseDTO;

@RestController
@CrossOrigin
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService service;

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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO<List<PostDTO>>> getAll(){
        ResponseDTO<List<PostDTO>> ret = new ResponseDTO<>(service.findAll());
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @RequestMapping(value="{personId}", method = RequestMethod.GET)
    public ResponseEntity findByPerson(@PathVariable UUID personId){
        try{
            ResponseDTO<List<PostDTO>> ret = new ResponseDTO<>(service.findByPersonId(personId));
            return new ResponseEntity<>(ret, HttpStatus.OK);
        }
        catch (Exception ex){
            ErrorDTO error = new ErrorDTO(ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPost(@RequestHeader("id") UUID personId, @Valid @RequestBody PostDTO dto){
        try{
            ResponseDTO<PostDTO> ret = new ResponseDTO<>(service.create(personId, dto));
            return new ResponseEntity<>(ret, HttpStatus.OK);
        }
        catch (Exception ex){
            ErrorDTO error = new ErrorDTO(ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
    
}
