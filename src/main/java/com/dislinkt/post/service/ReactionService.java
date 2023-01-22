package com.dislinkt.post.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dislinkt.post.dto.ReactionDTO;
import com.dislinkt.post.enums.ProfilePrivacy;
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

    public ReactionDTO findUserReaction(UUID postId, UUID personId){
        return mapper.toDto(repository.findByPostIdAndPersonId(postId, personId));
    }

    public Boolean toggleReaction(UUID personId, ReactionDTO dto){

        Person person = personService.findOne(personId);
        if (person == null){
            return Boolean.FALSE;
        }

        Post post = postService.findOne(dto.getPostId());
        if (post == null){
            return Boolean.FALSE;
        }

        // checks if user can leave a reaction
        Person postPublisher = personService.findOne(post.getPerson().getId());
        if (postPublisher.getBlockedBy().contains(person))
        {
            return Boolean.FALSE;
        }
        if (person.getBlockedBy().contains(postPublisher))
        {
            return Boolean.FALSE;
        }
        if (postPublisher.getPrivacy() == ProfilePrivacy.PRIVATE)
        {
            if (postPublisher.getId().compareTo(personId) != 0 && !postPublisher.getFollowers().contains(person))
            {
                return Boolean.FALSE;
            }
        }

        // checks wheter to add, remove or change user's reaction
        Reaction reaction = repository.findByPostIdAndPersonId(post.getId(), personId);
        if (reaction == null)
        {
            reaction = mapper.toEntity(dto);
            reaction.setPerson(person);
            reaction.setPost(post);
            reaction = repository.save(reaction);
        }
        else if (reaction.getType().name().equals(dto.getType()))
        {
            repository.deleteById(reaction.getId());
        }
        else
        {
            reaction.setType(ReactionType.valueOf(dto.getType()));
            reaction = repository.save(reaction);
        }

        return Boolean.TRUE;
    }
    
}
