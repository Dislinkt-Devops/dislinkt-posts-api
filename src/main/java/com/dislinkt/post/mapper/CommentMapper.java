package com.dislinkt.post.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.dislinkt.post.dto.CommentDTO;
import com.dislinkt.post.model.Comment;

public class CommentMapper implements MapperInterface<Comment, CommentDTO>{

    @Override
    public Comment toEntity(CommentDTO dto) {
        return new Comment(
            dto.getText()
        );
    }

    @Override
    public List<Comment> toEntityList(List<CommentDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public CommentDTO toDto(Comment entity) {
        CommentDTO dto = new CommentDTO(
            entity.getId(), 
            entity.getText(), 
            entity.getPerson().getId(),
            entity.getPost().getId());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    @Override
    public List<CommentDTO> toDtoList(List<Comment> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
    
}
