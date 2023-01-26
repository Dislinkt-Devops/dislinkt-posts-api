package com.dislinkt.post.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

import com.dislinkt.post.constants.CommentConstants;
import com.dislinkt.post.constants.PersonConstants;
import com.dislinkt.post.constants.PostConstants;
import com.dislinkt.post.dto.CommentDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-users.properties")
public class CommentServiceTest {

    @Autowired
    private CommentService service;

    @Test
    @Transactional
    @Rollback(true)
    public void testCreateOK() throws Exception{
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, CommentConstants.POST_1);
        int beforeAdd = service.findAll().size();

        dto = service.create(UUID.fromString(PersonConstants.EXISTING_ID), dto);

        assertNotEquals(null, dto.getId());
        assertNotEquals(null, dto.getPersonId());
        assertTrue(service.findAll().size() > beforeAdd);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCreateOKWhenPublic() throws Exception{
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, CommentConstants.POST_1);
        int beforeAdd = service.findAll().size();

        dto = service.create(UUID.fromString(PersonConstants.EXISTING_ID_5), dto);

        assertNotEquals(null, dto.getId());
        assertNotEquals(null, dto.getPersonId());
        assertTrue(service.findAll().size() > beforeAdd);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCreateOKWhenOwnPost() throws Exception{
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, CommentConstants.POST_2);
        int beforeAdd = service.findAll().size();

        dto = service.create(UUID.fromString(PersonConstants.EXISTING_ID), dto);

        assertNotEquals(null, dto.getId());
        assertNotEquals(null, dto.getPersonId());
        assertTrue(service.findAll().size() > beforeAdd);
    }

    @Test
    public void testCreateNonExistingUser(){
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, CommentConstants.POST_1);

        try {
            service.create(UUID.fromString(PersonConstants.NON_EXISTING_ID), dto);
        } catch (Exception e) {
            assertEquals("User with given id doesn't exist!", e.getMessage());
        }
    }

    @Test
    public void testCreateNonExistingPost(){
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, PostConstants.NON_EXISTANT_ID);

        try {
            service.create(UUID.fromString(PersonConstants.EXISTING_ID), dto);
        } catch (Exception e) {
            assertEquals("Post with given id doesn't exist!", e.getMessage());
        }
    }

    @Test
    public void testCreateWhenNotAllowed(){
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, CommentConstants.POST_2);

        try {
            service.create(UUID.fromString(PersonConstants.EXISTING_ID_5), dto);
        } catch (Exception e) {
            assertEquals("You can't leave a comment on this post!", e.getMessage());
        }
    }

    @Test
    public void testCreateWhenBlocked(){
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, CommentConstants.POST_2);

        try {
            service.create(UUID.fromString(PersonConstants.EXISTING_ID_4), dto);
        } catch (Exception e) {
            assertEquals("You are blocking this user!", e.getMessage());
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateOK() throws Exception{
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, null);
        int beforeAdd = service.findAll().size();

        dto = service.update(UUID.fromString(PersonConstants.EXISTING_ID_5), CommentConstants.EXISTING_ID, dto);

        assertEquals(PersonConstants.EXISTING_ID_5, dto.getPersonId().toString());
        assertEquals(CommentConstants.POST_1, dto.getPostId());
        assertEquals(service.findAll().size(), beforeAdd);
        assertEquals(CommentConstants.NEW_TEXT, dto.getText());
    }

    @Test
    public void testUpdateNonExistingId(){
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, null);
        try {
            service.update(UUID.fromString(PersonConstants.EXISTING_ID_5), CommentConstants.NON_EXISTING_ID, dto);
        } catch (Exception e) {
            assertEquals("Comment with given id doesn't exist!", e.getMessage());
        }
    }

    @Test
    public void testUpdateWrongUser(){
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, null);
        try {
            service.update(UUID.fromString(PersonConstants.EXISTING_ID), CommentConstants.EXISTING_ID, dto);
        } catch (Exception e) {
            assertEquals("Only the owner of this comment may edit it!", e.getMessage());
        }
    }

    @Test
    public void testUpdateNonExistingUser(){
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, null);
        try {
            service.update(UUID.fromString(PersonConstants.NON_EXISTING_ID), CommentConstants.EXISTING_ID, dto);
        } catch (Exception e) {
            assertEquals("Only the owner of this comment may edit it!", e.getMessage());
        }
    }

    @Test
    public void testDeleteNonExistingComment(){
        try {
            service.delete(UUID.fromString(PersonConstants.EXISTING_ID_3), CommentConstants.NON_EXISTING_ID);
        } catch (Exception e) {
            assertEquals("Comment with given id doesn't exist!", e.getMessage());
        }
    }

    @Test
    public void testDeleteNonExistingUser(){
        try {
            service.delete(UUID.fromString(PersonConstants.NON_EXISTING_ID), CommentConstants.EXISTING_ID_2);
        } catch (Exception e) {
            assertEquals("Only the user who made the comment may remove it!", e.getMessage());
        }
    }

    @Test
    public void testDeleteWrongUser(){
        try {
            service.delete(UUID.fromString(PersonConstants.EXISTING_ID_4), CommentConstants.EXISTING_ID_2);
        } catch (Exception e) {
            assertEquals("Only the user who made the comment may remove it!", e.getMessage());
        }
    }
    
}
