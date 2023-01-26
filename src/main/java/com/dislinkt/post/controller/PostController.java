package com.dislinkt.post.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("feed")
    public ResponseEntity<?> getAll(@RequestHeader(value = "X-User-Id", required = false) UUID userId) {
        try {
            List<PostDTO> ret = service.findAllByViewer(userId);
            return new ResponseEntity<>(new ResponseDTO<>(ret), HttpStatus.OK);
        }
        catch (Exception ex){
            ErrorDTO error = new ErrorDTO(ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/profile/{personId}")
    public ResponseEntity<?> findByPerson(@RequestHeader(value = "X-User-Id", required = false) UUID userId, @PathVariable UUID personId){
        try{
            List<PostDTO> ret = service.findByPersonId(userId, personId);
            return new ResponseEntity<>(new ResponseDTO<>(ret), HttpStatus.OK);
        }
        catch (Exception ex){
            ErrorDTO error = new ErrorDTO(ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addPost(@RequestHeader("X-User-Id") UUID personId, @Valid @RequestBody PostDTO dto){
        try{
            PostDTO ret = service.create(personId, dto);
            return new ResponseEntity<>(new ResponseDTO<>(ret), HttpStatus.OK);
        }
        catch (Exception ex){
            ErrorDTO error = new ErrorDTO(ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value="{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editPost(@RequestHeader("X-User-Id") UUID personId, @PathVariable UUID id, @Valid @RequestBody PostDTO dto){
        try{
            PostDTO ret = service.update(personId, id, dto);
            return new ResponseEntity<>(new ResponseDTO<>(ret), HttpStatus.OK);
        }
        catch (Exception ex){
            ErrorDTO error = new ErrorDTO(ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@RequestHeader("X-User-Id") UUID personId, @PathVariable UUID id){
        try{
            service.delete(personId, id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception ex){
            ErrorDTO error = new ErrorDTO(ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
    
}
