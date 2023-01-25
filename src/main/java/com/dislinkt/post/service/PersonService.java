package com.dislinkt.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.dislinkt.post.dto.PersonDTO;
import com.dislinkt.post.enums.Gender;
import com.dislinkt.post.enums.ProfilePrivacy;
import com.dislinkt.post.mapper.PersonMapper;
import com.dislinkt.post.model.Person;
import com.dislinkt.post.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private HttpService httpService;

    private PersonMapper mapper = new PersonMapper();

    public List<PersonDTO> findAll(){
        return mapper.toDtoList(repository.findAll());
    }

    public Person findOne(UUID id){
        return repository.findById(id).orElse(null);
    }

    @Transactional(rollbackFor = { HttpClientErrorException.class })
    public PersonDTO create(UUID id, PersonDTO dto) throws Exception{
        Person person = mapper.toEntity(dto);
        person.setId(id);

        if (findOne(person.getId()) != null)
            throw new Exception("User with given id already exists!");

        person = repository.save(person);

        httpService.activateUser(id);

        return mapper.toDto(person);
    }

    public Boolean canInteractWith(UUID id, UUID receiverId) throws Exception{
        Person sender = findOne(id);
        if (sender == null)
            throw new Exception("sender with given id doesn't exist");

        Person receiver = findOne(receiverId);
        if (receiver == null)
            throw new Exception("receiver with given id doesn't exist");

        if (!sender.getFollowing().contains(receiver) || !receiver.getFollowing().contains(sender))
            return Boolean.FALSE;

        if (sender.getBlockedBy().contains(receiver) || receiver.getBlockedBy().contains(sender))
            return Boolean.FALSE;

        return Boolean.TRUE;
    }

    public Boolean blockPerson(UUID id, UUID blockedId){
        if (id.equals(blockedId))
            return Boolean.FALSE;

        Person user = findOne(id);
        if (user == null)
            return Boolean.FALSE;

        Person blockedPerson = findOne(blockedId);
        if (blockedPerson == null)
            return Boolean.FALSE;

        if (user.getBlocked().contains(blockedPerson))
            return Boolean.FALSE;

        if (user.getFollowers().contains(blockedPerson))
        {
            Set<Person> followers = user.getFollowers();
            followers.remove(blockedPerson);
            user.setFollowers(followers);
            user = repository.save(user);
        }
        if (blockedPerson.getFollowers().contains(user))
        {
            Set<Person> followers = blockedPerson.getFollowers();
            followers.remove(user);
            blockedPerson.setFollowers(followers);
            blockedPerson = repository.save(blockedPerson);
        }

        Set<Person> blockedList = user.getBlocked();
        blockedList.add(blockedPerson);
        user.setBlocked(blockedList);
        user = repository.save(user);

        return Boolean.TRUE;
    }

    public Boolean unblockPerson(UUID id, UUID blockedId) {
        if (id.equals(blockedId))
            return Boolean.FALSE;
        
        Person user = findOne(id);
        if (user == null)
            return Boolean.FALSE;

        Person blockedPerson = findOne(blockedId);
        if (blockedPerson == null)
            return Boolean.FALSE;

        if (!user.getBlocked().contains(blockedPerson))
            return Boolean.FALSE;

        Set<Person> blockedList = user.getBlocked();
        blockedList.remove(blockedPerson);
        user.setBlocked(blockedList);
        user = repository.save(user);

        return Boolean.TRUE;
    }

    public List<PersonDTO> getBlockedList(UUID id) throws Exception {
        Person user = repository.findById(id).orElse(null);
        if (user == null)
            throw new Exception("User with given id doesn't exist!");

        List<Person> ret = new ArrayList<>();
        for (Person person : user.getBlocked()) {
            ret.add(person);
        }
        return mapper.toDtoList(ret);
    }

    public PersonDTO getMyProfile(UUID id) throws Exception {
        Person user = repository.findById(id).orElse(null);
        if (user == null)
            throw new Exception("User with given id doesn't exist!");

        return mapper.toDto(user);
    }

    public PersonDTO getProfile(UUID userId, UUID searchedId) throws Exception {
        Person searched = repository.findById(searchedId).orElse(null);
        if (searched == null)
            throw new Exception("User with given id doesn't exist!");

        boolean isPublic = searched.getPrivacy() == ProfilePrivacy.PUBLIC;
        
        if (userId == null){
            if (isPublic)
                return mapper.toDto(searched);
            else
                throw new Exception("You cannot view this user's profile!");
        }

        boolean isOwner = userId.equals(searchedId);

        Person user = repository.findById(userId).orElse(null);
        if (user == null)
            throw new Exception("User with given id doesn't exist!");

        if (user.getBlockedBy().contains(searched))
            throw new Exception("This user has you blocked!");
        if (searched.getBlockedBy().contains(user))
            throw new Exception("You have blocked this user!");

        boolean isFollowing = user.getFollowing().contains(searched);

        if (isOwner || isPublic || isFollowing)
            return mapper.toDto(searched);
        else
            throw new Exception("You cannot view this user's profile!");
    }

    public PersonDTO editMyProfile(UUID id, PersonDTO dto) throws Exception {
        Person user = repository.findById(id).orElse(null);
        if (user == null)
            throw new Exception("User with given id doesn't exist!");

        user.setBio(dto.getBio());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setGender(Gender.valueOf(dto.getGender()));
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPrivacy(ProfilePrivacy.valueOf(dto.getPrivacy()));
        user.setDateOfBirth(dto.getDateOfBirth());

        user = repository.save(user);

        return mapper.toDto(user);

    }

    public List<PersonDTO> searchPublicProfiles(String keyword) {
        if (keyword.isBlank()) {
            return new ArrayList<PersonDTO>();
        }
        
        List<UUID> userIds = this.httpService.getUserIdsByUsername(keyword); 

        return mapper.toDtoList(repository
            .findProfilesByAllNameTypes(keyword, userIds));
    }
    
}
