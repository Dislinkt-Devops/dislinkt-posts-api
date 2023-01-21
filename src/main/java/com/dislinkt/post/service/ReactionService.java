package com.dislinkt.post.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dislinkt.post.dto.ReactionDTO;
import com.dislinkt.post.enums.ReactionType;
import com.dislinkt.post.mapper.ReactionMapper;
import com.dislinkt.post.model.Person;
import com.dislinkt.post.model.Post;
import com.dislinkt.post.model.Reaction;
import com.dislinkt.post.repository.ReactionRepository;

@Service
public class ReactionService {

    @Autowired
    private ReactionRepository repository;

    @Autowired
    private PostService postService;

    @Autowired
    private PersonService personService;

    private ReactionMapper mapper = new ReactionMapper();

    public List<ReactionDTO> findAll(){
        return mapper.toDtoList(repository.findAll());
    }

    public List<ReactionDTO> findByPostId(UUID id){
        return mapper.toDtoList(repository.findByPostId(id));
    }

    public List<ReactionDTO> findAllLikesForPost(UUID postId){
        return mapper.toDtoList(repository.findByTypeAndPostId(ReactionType.LIKE, postId));
    }

    public List<ReactionDTO> findAllDislikesForPost(UUID postId){
        return mapper.toDtoList(repository.findByTypeAndPostId(ReactionType.DISLIKE, postId));
    }

    public Reaction findByPostIdAndPersonId(UUID postId, UUID personId){
        return repository.findByPostIdAndPersonId(postId, personId);
    }

    public ReactionDTO create(UUID personId, ReactionDTO dto) throws Exception{
        Reaction reaction = mapper.toEntity(dto);

        Person person = personService.findOne(personId);
        if (person == null){
            throw new Exception("User with given id doesn't exist!");
        }
        reaction.setPerson(person);

        Post post = postService.findOne(dto.getPostId());
        reaction.setPost(post);
            
        reaction = repository.save(reaction);
        return mapper.toDto(reaction);
    }

    public ReactionDTO update(UUID personId, UUID postId) throws Exception{
        Reaction reaction = repository.findByPostIdAndPersonId(postId, personId);
        if (reaction == null){
            throw new Exception("User hasn't left a reaction on this post!");
        }

        if (!reaction.getPerson().getId().equals(personId)){
            throw new Exception("You can't change another user's reaction!");
        }

        if (reaction.getType() == ReactionType.LIKE)
            reaction.setType(ReactionType.DISLIKE);
        else
            reaction.setType(ReactionType.LIKE);

        reaction = repository.save(reaction);
        return mapper.toDto(reaction);
    }

    public void delete(UUID personId, UUID postId) throws Exception{
        Reaction reaction = repository.findByPostIdAndPersonId(postId, personId);
        if (reaction == null){
            throw new Exception("User hasn't left a reaction on this post!");
        }

        if (!reaction.getPerson().getId().equals(personId)){
            throw new Exception("You can't remove another user's reaction!");
        }

        repository.deleteById(reaction.getId());
    }
    
}
