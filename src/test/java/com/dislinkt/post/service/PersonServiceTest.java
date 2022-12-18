package com.dislinkt.post.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dislinkt.post.dto.PersonDTO;

import com.dislinkt.post.constants.PersonConstants;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-users.properties")
public class PersonServiceTest {

    @Autowired
    private PersonService service;

    @Test
    @Transactional
    @Rollback(true)
    public void testAddUserOk() throws Exception{
        int sizeBefore = service.findAll().size();

        PersonDTO dto = new PersonDTO(
            null, PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME, 
            PersonConstants.GENDER, PersonConstants.PHONE_NUMBER, null, null, 
            PersonConstants.PRIVACY);

        service.create(UUID.fromString(PersonConstants.NEW_ID), dto);
        assertTrue(service.findAll().size() > sizeBefore);
    }

    @Test
    public void testAddUserWithNonUniqueId(){
        PersonDTO dto = new PersonDTO(
            null, PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME, 
            PersonConstants.GENDER, PersonConstants.PHONE_NUMBER, null, null, 
            PersonConstants.PRIVACY);

        try {
            service.create(UUID.fromString(PersonConstants.EXISTING_ID), dto);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "User with given id already exists!");
        }
    }
    
}
