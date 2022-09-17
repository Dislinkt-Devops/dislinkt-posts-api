package com.dislinkt.post.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dislinkt.post.dto.PostDTO;
import com.dislinkt.post.mapper.PostMapper;
import com.dislinkt.post.model.Post;
import com.dislinkt.post.repository.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    @Autowired
    private PersonService personService;

    private PostMapper mapper = new PostMapper();

    public List<PostDTO> findAll(){
        return mapper.toDtoList(repository.findAll());
    }

    public List<PostDTO> findByPersonId(UUID id){
        return mapper.toDtoList(repository.findByPersonId(id));
    }

    public PostDTO create(UUID personId, PostDTO dto) throws Exception{
        if (personService.findOne(personId) == null)
            throw new Exception("User with given id doesn't exist!");
        if (dto.getLinks().size() == 0 && dto.getText().isBlank() && dto.getImageUrl().isBlank())
            throw new Exception("Cannot submit an empty post!");
        Post post = mapper.toEntity(dto);
        post.setPerson(personService.findOne(personId));
        post = repository.save(post);
        return mapper.toDto(post);
    }
    
}
