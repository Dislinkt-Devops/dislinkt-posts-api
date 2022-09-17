package com.dislinkt.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dislinkt.post.dto.PostDTO;
import com.dislinkt.post.mapper.PostMapper;
import com.dislinkt.post.model.Person;
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

    public List<PostDTO> findByPersonId(UUID personId) throws Exception{
        if (personService.findOne(personId) == null)
            throw new Exception("User with given id doesn't exist!");
        Person person = personService.findOne(personId);
        List<Post> ret = new ArrayList<>();
        for (Post p: person.getPosts())
            ret.add(p);
        return mapper.toDtoList(ret);
    }

    public PostDTO create(UUID personId, PostDTO dto) throws Exception{
        if (personService.findOne(personId) == null)
            throw new Exception("User with given id doesn't exist!");
        if (dto.getLinks().size() == 0 && dto.getText().isBlank() && dto.getImageUrl().isBlank())
            throw new Exception("Cannot submit an empty post!");
        Post post = mapper.toEntity(dto);
        Person person = personService.findOne(personId);
        post.setPerson(person);
        post = repository.save(post);
        personService.addPost(personId, post);
        System.out.println("person posts size: "+person.getPosts().size());
        return mapper.toDto(post);
    }
    
}
