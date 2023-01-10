package com.dislinkt.post.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dislinkt.post.dto.PostDTO;
import com.dislinkt.post.enums.ProfilePrivacy;
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

    public List<PostDTO> findByPersonId(UUID userId, UUID personId) throws Exception{
        if (personService.findOne(userId) == null){
            throw new Exception("User with given id doesn't exist!");
        }
        if (personService.findOne(personId) == null){
            throw new Exception("Person with given id doesn't exist!");
        }

        boolean isOwner = userId.compareTo(personId) == 0;
        Person user = personService.findOne(userId);
        Person person = personService.findOne(personId);

        if (user.getBlockedBy().contains(person))
            throw new Exception("This user has you blocked!");
        if (person.getBlockedBy().contains(user))
            throw new Exception("You have blocked this user!");

        boolean isPublic = person.getPrivacy() == ProfilePrivacy.PUBLIC;
        boolean isFollowing = user.getFollowing().contains(person);
        if (isOwner || isPublic || isFollowing)
            return mapper.toDtoList(repository.findByPersonId(personId));
        else
            throw new Exception("You cannot view this user's posts!");
    }

    public Post findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public PostDTO findOneDto(Integer id) {
        return mapper.toDto(repository.findById(id).orElse(null));
    }

    public PostDTO create(UUID personId, PostDTO dto) throws Exception{
        Person person = personService.findOne(personId);
        if (personService.findOne(personId) == null)
            throw new Exception("User with given id doesn't exist!");

        if (dto.getLinks() == null || dto.getText() == null || dto.getImageUrl() == null)
            throw new Exception("Cannot submit an empty post!");

        if (dto.getLinks().size() == 0 && dto.getText().isBlank() && dto.getImageUrl().isBlank())
            throw new Exception("Cannot submit an empty post!");

        Post post = mapper.toEntity(dto);
        post.setPerson(person);
        if (repository.findAll().size()>0)
            post.setId(repository.findAll().get(repository.findAll().size()-1).getId()+1);
        else
            post.setId(1);

        System.out.println("New user id: "+post.getId());

        post = repository.save(post);
        System.out.println("WHHYYYYYYYYYY???????????");
        return mapper.toDto(post);
    }

    public PostDTO update(UUID personId, Integer id, PostDTO dto) throws Exception{
        Post updated = repository.findById(id).orElse(null);

        if (updated == null)
            throw new Exception("Post with given id doesn't exist!");

        if (personService.findOne(personId) == null)
            throw new Exception("User with given id doesn't exist!");

        if (!updated.getPerson().getId().equals(personId))
            throw new Exception("Only the user who made the post may edit it!");

        if (dto.getLinks() == null || dto.getText() == null || dto.getImageUrl() == null)
            throw new Exception("Cannot have an empty post!");

        if (dto.getLinks().size() == 0 && dto.getText().isBlank() && dto.getImageUrl().isBlank())
            throw new Exception("Cannot have an empty post!");

        updated.setImageUrl(dto.getImageUrl());
        updated.setLinks(dto.getLinks());
        updated.setText(dto.getText());

        updated = repository.save(updated);
        return mapper.toDto(updated);
    }

    public void delete(UUID personId, Integer id) throws Exception{
        Post forDeletion = repository.findById(id).orElse(null);

        if (forDeletion == null)
            throw new Exception("Post with given id doesn't exist!");

        if (personService.findOne(personId) == null)
            throw new Exception("User with given id doesn't exist!");

        if (!forDeletion.getPerson().getId().equals(personId))
            throw new Exception("Only the user who made the post may remove it!");

        repository.deleteById(id);
    }
    
}
