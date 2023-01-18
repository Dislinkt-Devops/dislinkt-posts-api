package com.dislinkt.post.controller;

import java.util.List;
import java.util.UUID;

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

import com.dislinkt.post.dto.ErrorDTO;
import com.dislinkt.post.dto.ReactionDTO;
import com.dislinkt.post.dto.ResponseDTO;
import com.dislinkt.post.service.ReactionService;

@RestController
@CrossOrigin
@RequestMapping("/reactions")
public class ReactionController {

    @Autowired
    private ReactionService service;

    @GetMapping()
    public ResponseEntity<ResponseDTO<List<ReactionDTO>>> getAll(){
        List<ReactionDTO> ret = service.findAll();
        return new ResponseEntity<>(new ResponseDTO<>(ret), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResponseDTO<List<ReactionDTO>>> findByPost(@PathVariable UUID postId){
        List<ReactionDTO> ret = service.findByPostId(postId);
        return new ResponseEntity<>(new ResponseDTO<>(ret), HttpStatus.OK);
    }

    @GetMapping("/{postId}/likes")
    public ResponseEntity<ResponseDTO<List<ReactionDTO>>> findPostLikes(@PathVariable UUID postId){
        List<ReactionDTO> ret = service.findAllLikesForPost(postId);
        return new ResponseEntity<>(new ResponseDTO<>(ret), HttpStatus.OK);
    }

    @GetMapping("/{postId}/dislikes")
    public ResponseEntity<ResponseDTO<List<ReactionDTO>>> findPostDislikes(@PathVariable UUID postId){
        List<ReactionDTO> ret = service.findAllDislikesForPost(postId);
        return new ResponseEntity<>(new ResponseDTO<>(ret), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addReaction(@RequestHeader("X-User-Id") UUID personId, @RequestBody ReactionDTO dto){
        try{
            ReactionDTO ret = service.create(personId, dto);
            return new ResponseEntity<>(new ResponseDTO<>(ret), HttpStatus.OK);
        }
        catch (Exception ex){
            ErrorDTO error = new ErrorDTO(ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{postId}")
    public ResponseEntity changeReaction(@RequestHeader("X-User-Id") UUID personId, @PathVariable UUID postId){
        try{
            ReactionDTO ret = service.update(personId, postId);
            return new ResponseEntity<>(new ResponseDTO<>(ret), HttpStatus.OK);
        }
        catch (Exception ex){
            ErrorDTO error = new ErrorDTO(ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity removeReaction(@RequestHeader("X-User-Id") UUID personId, @PathVariable UUID postId){
        try{
            service.delete(personId, postId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception ex){
            ErrorDTO error = new ErrorDTO(ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

}
