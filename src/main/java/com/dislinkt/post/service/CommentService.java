package com.dislinkt.post.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dislinkt.post.dto.CommentDTO;
import com.dislinkt.post.mapper.CommentMapper;
import com.dislinkt.post.model.Comment;
import com.dislinkt.post.model.Person;
import com.dislinkt.post.model.Post;
import com.dislinkt.post.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private PersonService personService;

    @Autowired
    private PostService postService;

    private CommentMapper mapper = new CommentMapper();

    public List<CommentDTO> findAll(){
        return mapper.toDtoList(repository.findAll());
    }

    public List<CommentDTO> findByPostId(Integer id){
        return mapper.toDtoList(repository.findByPostId(id));
    }

    public CommentDTO create(UUID personId, CommentDTO dto) throws Exception{
        Comment comment = mapper.toEntity(dto);

        Person person = personService.findOne(personId);
        if (person == null)
            throw new Exception("User with given id doesn't exist!");
        comment.setPerson(person);

        Post post = postService.findOne(dto.getPostId());
        if (post == null)
            throw new Exception("Post with given id doesn't exist!");
        comment.setPost(post);

        comment = repository.save(comment);        
        return mapper.toDto(comment);
    }

    public CommentDTO update(UUID personId, Integer id, CommentDTO dto)throws Exception{
        Comment updated = repository.findById(id).orElse(null);
        if (updated == null)
            throw new Exception("Comment with given id doesn't exist!");

        if (!updated.getPerson().getId().equals(personId))
            throw new Exception("Only the owner of this comment may edit it!");

        updated.setText(dto.getText());
        
        updated = repository.save(updated);        
        return mapper.toDto(updated);
    }

    public void delete(UUID personId, Integer id) throws Exception{
        Comment forDeletion = repository.findById(id).orElse(null);

        if (!forDeletion.getPerson().getId().equals(personId))
            throw new Exception("Only the user who made the comment may remove it!");

        repository.deleteById(id);
    }
    
}
