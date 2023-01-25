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
import org.springframework.web.bind.annotation.DeleteMapping;
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

import com.dislinkt.post.dto.BiographyAttributeDTO;
import com.dislinkt.post.dto.ErrorDTO;
import com.dislinkt.post.dto.ResponseDTO;
import com.dislinkt.post.service.BiographyAttributeService;

@RestController
@CrossOrigin
@RequestMapping("/attributes")
public class BiographyAttributeController {

    @Autowired
    private BiographyAttributeService service;

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
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addAttribute(@RequestHeader("X-User-Id") UUID id, @Valid @RequestBody BiographyAttributeDTO dto){
        try{
            BiographyAttributeDTO ret = service.create(id, dto);
            return new ResponseEntity<>(new ResponseDTO<>(ret), HttpStatus.OK);
        }
        catch (Exception ex){
            ErrorDTO error = new ErrorDTO(ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{biographyOwnerId}")
    public ResponseEntity<?> findByPerson(@RequestHeader("X-User-Id") UUID userId, @PathVariable UUID biographyOwnerId){
        try{
            List<BiographyAttributeDTO> ret = service.findByUser(userId, biographyOwnerId);
            return new ResponseEntity<>(new ResponseDTO<>(ret), HttpStatus.OK);
        }
        catch (Exception ex){
            ErrorDTO error = new ErrorDTO(ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editAttribute(@RequestHeader("X-User-Id") UUID userId, @PathVariable UUID id, @Valid @RequestBody BiographyAttributeDTO dto){
        try{
            BiographyAttributeDTO ret = service.update(userId, id, dto);
            return new ResponseEntity<>(new ResponseDTO<>(ret), HttpStatus.OK);
        }
        catch (Exception ex){
            ErrorDTO error = new ErrorDTO(ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAttribute(@RequestHeader("X-User-Id") UUID userId, @PathVariable UUID id){
        try{
            service.delete(userId, id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception ex){
            ErrorDTO error = new ErrorDTO(ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
    
}
