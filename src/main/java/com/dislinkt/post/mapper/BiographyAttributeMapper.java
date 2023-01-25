package com.dislinkt.post.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.dislinkt.post.dto.BiographyAttributeDTO;
import com.dislinkt.post.enums.BiographyAttributeType;
import com.dislinkt.post.model.BiographyAttribute;

public class BiographyAttributeMapper implements MapperInterface<BiographyAttribute, BiographyAttributeDTO>{

    @Override
    public BiographyAttribute toEntity(BiographyAttributeDTO dto) {
        return new BiographyAttribute(
            BiographyAttributeType.valueOf(dto.getAttributeType()), 
            dto.getAttributeName(), 
            dto.getAttributeValue() 
        );
    }

    @Override
    public List<BiographyAttribute> toEntityList(List<BiographyAttributeDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public BiographyAttributeDTO toDto(BiographyAttribute entity) {
        return new BiographyAttributeDTO(
            entity.getId(), 
            entity.getAttributeName(),
            entity.getAttributeValue(), 
            entity.getAttributeType().toString(), 
            entity.getPerson().getId()
        );
    }

    @Override
    public List<BiographyAttributeDTO> toDtoList(List<BiographyAttribute> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
    
}
