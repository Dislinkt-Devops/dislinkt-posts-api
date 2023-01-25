package com.dislinkt.post.mapper;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.dislinkt.post.dto.PostDTO;
import com.dislinkt.post.model.Post;

public class PostMapper implements MapperInterface<Post, PostDTO> {

    private CommentMapper commentMapper = new CommentMapper();
    private ReactionMapper reactionMapper = new ReactionMapper();

    @Override
    public Post toEntity(PostDTO dto) {
        return new Post(
            dto.getId(), 
            dto.getText(), 
            dto.getImageUrl(), 
            dto.getLinks());
    }

    @Override
    public List<Post> toEntityList(List<PostDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public PostDTO toDto(Post entity) {
         PostDTO dto = new PostDTO(
            entity.getId(), 
            entity.getText(), 
            entity.getImageUrl(), 
            entity.getLinks(), 
            entity.getPerson().getId().toString());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setComments(commentMapper.toDtoList(new ArrayList<>(entity.getComments())));
        dto.setReactions(reactionMapper.toDtoList(new ArrayList<>(entity.getReactions())));
        return dto;
    }

    @Override
    public List<PostDTO> toDtoList(List<Post> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
    
}
