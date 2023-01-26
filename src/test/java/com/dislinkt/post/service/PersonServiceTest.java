package com.dislinkt.post.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import com.dislinkt.post.repository.PersonRepository;
import com.dislinkt.post.constants.PersonConstants;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-users.properties")
public class PersonServiceTest {

    @Autowired
    private PersonService service;

    @Autowired
    private PersonRepository repository;

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

    @Test
    public void testCanInteractWithOK(){
        try {
            assertTrue(service.canInteractWith(UUID.fromString(PersonConstants.EXISTING_ID), UUID.fromString(PersonConstants.EXISTING_ID_2)));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCanInteractWithNewUser(){
        PersonDTO dto = new PersonDTO(
            null, PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME, 
            PersonConstants.GENDER, PersonConstants.PHONE_NUMBER, null, null, 
            PersonConstants.PRIVACY);

        try {
            service.create(UUID.fromString(PersonConstants.NEW_ID), dto);
            assertFalse(service.canInteractWith(UUID.fromString(PersonConstants.NEW_ID), UUID.fromString(PersonConstants.EXISTING_ID)));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testCanInteractWithOnesidedFollowing(){
        try {
            assertFalse(service.canInteractWith(UUID.fromString(PersonConstants.EXISTING_ID), UUID.fromString(PersonConstants.EXISTING_ID_3)));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testCanInteractWithABlocking(){
        try {
            assertFalse(service.canInteractWith(UUID.fromString(PersonConstants.EXISTING_ID), UUID.fromString(PersonConstants.EXISTING_ID_4)));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testCanInteractWithNonExistingSender(){
        try {
            service.canInteractWith(UUID.fromString(PersonConstants.NON_EXISTING_ID), UUID.fromString(PersonConstants.EXISTING_ID));
        } catch (Exception e) {
            assertEquals("sender with given id doesn't exist", e.getMessage());
        }
    }

    @Test
    public void testCanInteractWithNonExistingReceiver(){
        try {
            service.canInteractWith(UUID.fromString(PersonConstants.EXISTING_ID), UUID.fromString(PersonConstants.NON_EXISTING_ID));
        } catch (Exception e) {
            assertEquals("receiver with given id doesn't exist", e.getMessage());
        }
    }
    
}
