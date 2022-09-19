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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dislinkt.post.dto.CommentDTO;
import com.dislinkt.post.dto.ErrorDTO;
import com.dislinkt.post.dto.ResponseDTO;
import com.dislinkt.post.service.CommentService;

@RestController
@CrossOrigin
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService service;

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

    @GetMapping()
    public ResponseEntity<ResponseDTO<List<CommentDTO>>> getAll(){
        List<CommentDTO> ret = service.findAll();
        return new ResponseEntity<>(new ResponseDTO<>(ret), HttpStatus.OK);
    }
    
    @GetMapping("/{postId}")
    public ResponseEntity<ResponseDTO<List<CommentDTO>>> findByPost(@PathVariable Integer postId){
        List<CommentDTO> ret = service.findByPostId(postId);
        return new ResponseEntity<>(new ResponseDTO<>(ret), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addComment(@RequestHeader("X-User-Id") UUID personId, @Valid @RequestBody CommentDTO dto){
        try{
            CommentDTO ret = service.create(personId, dto);
            return new ResponseEntity<>(new ResponseDTO<>(ret), HttpStatus.OK);
        }
        catch (Exception ex){
            ErrorDTO error = new ErrorDTO(ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}
