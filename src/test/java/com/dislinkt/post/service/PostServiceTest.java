package com.dislinkt.post.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.dislinkt.post.dto.PostDTO;
import com.dislinkt.post.constants.PersonConstants;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-users.properties")
public class PostServiceTest {

    @Autowired
    private PostService service;

    @Test
    public void testGetByUserIdWhenIsUser() throws Exception {
        List<PostDTO> ret = service.findByPersonId(UUID.fromString(PersonConstants.EXISTING_ID), UUID.fromString(PersonConstants.EXISTING_ID));
        assertTrue(ret.size() < service.findAll().size());
        assertEquals(2, ret.size());
    }

    @Test
    public void testGetByUserIdWhenIsPublic() throws Exception {
        List<PostDTO> ret = service.findByPersonId(UUID.fromString(PersonConstants.EXISTING_ID_3), UUID.fromString(PersonConstants.EXISTING_ID_2));
        assertTrue(ret.size() < service.findAll().size());
        assertEquals(1, ret.size());
    }

    @Test
    public void testGetByUserIdWhenIsFollowingPrivate() throws Exception {
        List<PostDTO> ret = service.findByPersonId(UUID.fromString(PersonConstants.EXISTING_ID), UUID.fromString(PersonConstants.EXISTING_ID_3));
        assertTrue(ret.size() < service.findAll().size());
        assertEquals(1, ret.size());
    }

    @Test
    public void testGetByUserIdWhenNonExistingPerson() {
        try {
            service.findByPersonId(UUID.fromString(PersonConstants.EXISTING_ID), UUID.fromString(PersonConstants.NON_EXISTING_ID));
        } catch (Exception e) {
            assertEquals("Person with given id doesn't exist!", e.getMessage());
        }
    }

    @Test
    public void testGetByUserIdWhenCantView() {
        try {
            service.findByPersonId(UUID.fromString(PersonConstants.EXISTING_ID_3), UUID.fromString(PersonConstants.EXISTING_ID));
        } catch (Exception e) {
            assertEquals("You cannot view this user's posts!", e.getMessage());
        }
    }

    @Test
    public void testGetByUserIdWhenIsBlocked() {
        try {
            service.findByPersonId(UUID.fromString(PersonConstants.EXISTING_ID), UUID.fromString(PersonConstants.EXISTING_ID_4));
        } catch (Exception e) {
            assertEquals("This user has you blocked!", e.getMessage());
        }
    }

    @Test
    public void testGetByUserIdWhenIsBlocking() {
        try {
            service.findByPersonId(UUID.fromString(PersonConstants.EXISTING_ID_4), UUID.fromString(PersonConstants.EXISTING_ID));
        } catch (Exception e) {
            assertEquals("You have blocked this user!", e.getMessage());
        }
    }
    
}
