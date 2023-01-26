package com.dislinkt.post.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.dislinkt.post.dto.PersonInfo;
import com.dislinkt.post.dto.ReactionDTO;
import com.dislinkt.post.enums.ReactionType;
import com.dislinkt.post.model.Reaction;

public class ReactionMapper implements MapperInterface<Reaction, ReactionDTO> {

    @Override
    public Reaction toEntity(ReactionDTO dto) {
        return new Reaction(ReactionType.valueOf(dto.getType()));
    }

    @Override
    public List<Reaction> toEntityList(List<ReactionDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public ReactionDTO toDto(Reaction entity) {
        ReactionDTO dto =  new ReactionDTO(
            entity.getId(), 
            entity.getType().name(), 
            entity.getPerson().getId(), 
            entity.getPost().getId());
        PersonInfo personInfo = new PersonInfo();
        personInfo.setFirstName(entity.getPerson().getFirstName());
        personInfo.setLastName(entity.getPerson().getLastName());
        dto.setPersonInfo(personInfo);

        return dto;
        
    }

    @Override
    public List<ReactionDTO> toDtoList(List<Reaction> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
    
}
