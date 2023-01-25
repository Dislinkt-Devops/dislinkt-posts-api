package com.dislinkt.post.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dislinkt.post.dto.BiographyAttributeDTO;
import com.dislinkt.post.enums.BiographyAttributeType;
import com.dislinkt.post.enums.ProfilePrivacy;
import com.dislinkt.post.mapper.BiographyAttributeMapper;
import com.dislinkt.post.model.BiographyAttribute;
import com.dislinkt.post.model.Person;
import com.dislinkt.post.repository.BiographyAttributeRepository;

@Service
public class BiographyAttributeService {

    @Autowired
    private BiographyAttributeRepository repository;

    @Autowired
    private PersonService personService;

    private BiographyAttributeMapper mapper = new BiographyAttributeMapper();

    public BiographyAttributeDTO create(UUID userId, BiographyAttributeDTO dto) throws Exception{
        BiographyAttribute bAttribute = mapper.toEntity(dto);

        Person person = personService.findOne(userId);
        if (person == null)
            throw new Exception("User with given id doesn't exist!");
        bAttribute.setPerson(person);

        bAttribute = repository.save(bAttribute);

        return mapper.toDto(bAttribute);
    }

    public List<BiographyAttributeDTO> findByUser(UUID userId, UUID biographyOwnerId) throws Exception {
        if (personService.findOne(userId) == null)
            throw new Exception("User with given id doesn't exist!");
        if (personService.findOne(biographyOwnerId) == null)
            throw new Exception("Owner with given id doesn't exist!");

        boolean isOwner = userId.compareTo(biographyOwnerId) == 0;
        Person user = personService.findOne(userId);
        Person biographyOwner = personService.findOne(biographyOwnerId);

        if (user.getBlockedBy().contains(biographyOwner))
            throw new Exception("This user has you blocked!");
        if (biographyOwner.getBlockedBy().contains(user))
            throw new Exception("You have blocked this user!");

        boolean isPublic = biographyOwner.getPrivacy() == ProfilePrivacy.PUBLIC;
        boolean isFollowing = user.getFollowing().contains(biographyOwner);
        if (isOwner || isPublic || isFollowing)
            return mapper.toDtoList(repository.findByPersonId(biographyOwnerId));
        else
            throw new Exception("You cannot view this user's posts!");    
    }

    public BiographyAttributeDTO update(UUID userId, UUID attributeId, BiographyAttributeDTO dto) throws Exception{
        if (personService.findOne(userId) == null)
            throw new Exception("User with given id doesn't exist!");

        BiographyAttribute bAttribute = repository.findById(attributeId).orElse(null);
        if (bAttribute == null)
            throw new Exception("Biography attribute with given id doesn't exist!");

        if (!bAttribute.getPerson().getId().equals(userId))
            throw new Exception("You can't edit this user's biography attributes!");

        bAttribute.setAttributeName(dto.getAttributeName());
        bAttribute.setAttributeType(BiographyAttributeType.valueOf(dto.getAttributeType()));
        bAttribute.setAttributeValue(dto.getAttributeValue());

        bAttribute = repository.save(bAttribute);

        return mapper.toDto(bAttribute);
    }

    public void delete(UUID userId, UUID attributeId) throws Exception{
        if (personService.findOne(userId) == null)
            throw new Exception("User with given id doesn't exist!");

        BiographyAttribute bAttribute = repository.findById(attributeId).orElse(null);
        if (bAttribute == null)
            throw new Exception("Biography attribute with given id doesn't exist!");

        if (!bAttribute.getPerson().getId().equals(userId))
            throw new Exception("You can't delete this user's biography attributes!");

        repository.deleteById(attributeId);
    }
    
}
