package com.dislinkt.post.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dislinkt.post.dto.PostDTO;
import com.dislinkt.post.model.Post;
import com.dislinkt.post.constants.PersonConstants;
import com.dislinkt.post.constants.PostConstants;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-users.properties")
public class PostCRUDServiceTest {

    @Autowired
    private PostService service;

    @Test
    @Transactional
    @Rollback(true)
    public void testCreateOK() throws Exception{
        
        PostDTO dto = new PostDTO(null, PostConstants.NEW_TEXT, PostConstants.NEW_URL,
         PostConstants.NEW_LINKS, PersonConstants.EXISTING_ID_5);
        
        int beforeAdd = service.findAll().size();
        int beforeAddforUser = service.findByPersonId(UUID.fromString(PersonConstants.EXISTING_ID_5),
         UUID.fromString(PersonConstants.EXISTING_ID_5)).size();
        
        dto = service.create(UUID.fromString(PersonConstants.EXISTING_ID_5), dto);

        assertNotEquals(null, dto.getId());
        assertTrue(beforeAdd < service.findAll().size());
        assertTrue(beforeAddforUser < service.findByPersonId(UUID.fromString(PersonConstants.EXISTING_ID_5),
        UUID.fromString(PersonConstants.EXISTING_ID_5)).size());

    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCreateEmptyText() throws Exception{
        PostDTO dto = new PostDTO(null, "", PostConstants.NEW_URL,
         PostConstants.NEW_LINKS, PersonConstants.EXISTING_ID_5);
        
         int beforeAdd = service.findAll().size();
         int beforeAddforUser = service.findByPersonId(UUID.fromString(PersonConstants.EXISTING_ID_5),
         UUID.fromString(PersonConstants.EXISTING_ID_5)).size();

        dto = service.create(UUID.fromString(PersonConstants.EXISTING_ID_5), dto);

        assertNotEquals(null, dto.getId());
        assertTrue(beforeAdd < service.findAll().size());
        assertTrue(beforeAddforUser < service.findByPersonId(UUID.fromString(PersonConstants.EXISTING_ID_5),
        UUID.fromString(PersonConstants.EXISTING_ID_5)).size());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCreateEmptyURL() throws Exception{
        PostDTO dto = new PostDTO(null, PostConstants.NEW_TEXT, " ",
         PostConstants.NEW_LINKS, PersonConstants.EXISTING_ID_5);
        
         int beforeAdd = service.findAll().size();
         int beforeAddforUser = service.findByPersonId(UUID.fromString(PersonConstants.EXISTING_ID_5),
         UUID.fromString(PersonConstants.EXISTING_ID_5)).size();

        dto = service.create(UUID.fromString(PersonConstants.EXISTING_ID_5), dto);

        assertNotEquals(null, dto.getId());
        assertTrue(beforeAdd < service.findAll().size());
        assertTrue(beforeAddforUser < service.findByPersonId(UUID.fromString(PersonConstants.EXISTING_ID_5),
        UUID.fromString(PersonConstants.EXISTING_ID_5)).size());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCreateEmptyLinks() throws Exception{
        PostDTO dto = new PostDTO(null, PostConstants.NEW_TEXT, PostConstants.NEW_URL,
         new ArrayList<>(), PersonConstants.EXISTING_ID_5);
        
         int beforeAdd = service.findAll().size();
         int beforeAddforUser = service.findByPersonId(UUID.fromString(PersonConstants.EXISTING_ID_5),
         UUID.fromString(PersonConstants.EXISTING_ID_5)).size();

        dto = service.create(UUID.fromString(PersonConstants.EXISTING_ID_5), dto);

        assertNotEquals(null, dto.getId());
        assertTrue(beforeAdd < service.findAll().size());
        assertTrue(beforeAddforUser < service.findByPersonId(UUID.fromString(PersonConstants.EXISTING_ID_5),
        UUID.fromString(PersonConstants.EXISTING_ID_5)).size());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCreateTwoEmptyFields() throws Exception{
        PostDTO dto = new PostDTO(null, " ", PostConstants.NEW_URL,
         new ArrayList<>(), PersonConstants.EXISTING_ID_5);
        
         int beforeAdd = service.findAll().size();
         int beforeAddforUser = service.findByPersonId(UUID.fromString(PersonConstants.EXISTING_ID_5),
         UUID.fromString(PersonConstants.EXISTING_ID_5)).size();

        dto = service.create(UUID.fromString(PersonConstants.EXISTING_ID_5), dto);

        assertNotEquals(null, dto.getId());
        assertTrue(beforeAdd < service.findAll().size());
        assertTrue(beforeAddforUser < service.findByPersonId(UUID.fromString(PersonConstants.EXISTING_ID_5),
        UUID.fromString(PersonConstants.EXISTING_ID_5)).size());
    }

    @Test
    public void testCreateNonExistentUser(){
        PostDTO dto = new PostDTO(5, PostConstants.NEW_TEXT, PostConstants.NEW_URL,
         new ArrayList<>(), PersonConstants.NON_EXISTING_ID);
        
        try {
            service.create(UUID.fromString(PersonConstants.NON_EXISTING_ID), dto);
        } catch (Exception e) {
            assertEquals("User with given id doesn't exist!", e.getMessage());
        }
    }

    @Test
    public void testCreateEmptyPost(){
        PostDTO dto = new PostDTO(null, "", "",
         new ArrayList<>(), PersonConstants.EXISTING_ID_2);
        
        try {
            service.create(UUID.fromString(PersonConstants.EXISTING_ID_2), dto);
        } catch (Exception e) {
            assertEquals("Cannot submit an empty post!", e.getMessage());
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUdateOk() throws Exception{
        Post post = service.findOne(PostConstants.UPDATE_ID);

        assertNotEquals(PostConstants.NEW_TEXT, post.getText());
        assertNotEquals(PostConstants.NEW_LINKS, post.getLinks());
        assertNotEquals(PostConstants.NEW_URL, post.getImageUrl());

        PostDTO dto = new PostDTO(null, PostConstants.NEW_TEXT, PostConstants.NEW_URL,
         PostConstants.NEW_LINKS, null);
        
        assertTrue(dto.getId() == null);
        dto = service.update(UUID.fromString(PersonConstants.EXISTING_ID_3), PostConstants.UPDATE_ID, dto);
        assertTrue(dto.getId() != null);

        assertEquals(PostConstants.UPDATE_ID, dto.getId());
        assertEquals(PostConstants.NEW_TEXT, dto.getText());
        assertEquals(PostConstants.NEW_LINKS, dto.getLinks());
        assertEquals(PostConstants.NEW_URL, dto.getImageUrl());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUdateEmptyText() throws Exception{
        Post post = service.findOne(PostConstants.UPDATE_ID);

        assertNotEquals("", post.getText());

        PostDTO dto = new PostDTO(null, "", PostConstants.NEW_URL,
         PostConstants.NEW_LINKS, null);
        
        dto = service.update(UUID.fromString(PersonConstants.EXISTING_ID_3), PostConstants.UPDATE_ID, dto);

        assertEquals("", dto.getText());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUdateEmptyURL() throws Exception{
        Post post = service.findOne(PostConstants.UPDATE_ID);

        assertNotEquals("", post.getImageUrl());

        PostDTO dto = new PostDTO(null, PostConstants.NEW_TEXT, "",
         PostConstants.NEW_LINKS, null);
        
        dto = service.update(UUID.fromString(PersonConstants.EXISTING_ID_3), PostConstants.UPDATE_ID, dto);

        assertEquals("", dto.getImageUrl());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUdateEmptyLinks() throws Exception{
        Post post = service.findOne(PostConstants.UPDATE_ID);

        assertNotEquals(0, post.getLinks().size());

        PostDTO dto = new PostDTO(null, PostConstants.NEW_TEXT, PostConstants.NEW_URL,
        new ArrayList<>(), null);
        
        dto = service.update(UUID.fromString(PersonConstants.EXISTING_ID_3), PostConstants.UPDATE_ID, dto);

        assertEquals(0, post.getLinks().size());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUdateTwoEmptyFields() throws Exception{
        Post post = service.findOne(PostConstants.UPDATE_ID);

        assertNotEquals(0, post.getLinks().size());
        assertNotEquals("", post.getText());

        PostDTO dto = new PostDTO(null, "", PostConstants.NEW_URL,
        new ArrayList<>(), null);
        
        dto = service.update(UUID.fromString(PersonConstants.EXISTING_ID_3), PostConstants.UPDATE_ID, dto);

        assertEquals(0, post.getLinks().size());
        assertEquals("", post.getText());
    }

    @Test
    public void testUpdateWrongUser(){
        PostDTO dto = new PostDTO(null, PostConstants.NEW_TEXT, PostConstants.NEW_URL,
         PostConstants.NEW_LINKS, null);

         try {
            service.update(UUID.fromString(PersonConstants.EXISTING_ID_2), PostConstants.UPDATE_ID, dto);
        } catch (Exception e) {
            assertEquals("Only the user who made the post may edit it!", e.getMessage());
        }
    }

    @Test
    public void testUpdateEmptyPost(){
        PostDTO dto = new PostDTO(null, "", "",
         new ArrayList<>(), null);

         try {
            service.update(UUID.fromString(PersonConstants.EXISTING_ID_3), PostConstants.UPDATE_ID, dto);
        } catch (Exception e) {
            assertEquals("Cannot have an empty post!", e.getMessage());
        }
    }

    @Test
    public void testUpdateEmptyPost2(){
        PostDTO dto = new PostDTO(null, null, null,null, null);

         try {
            service.update(UUID.fromString(PersonConstants.EXISTING_ID_3), PostConstants.UPDATE_ID, dto);
        } catch (Exception e) {
            assertEquals("Cannot have an empty post!", e.getMessage());
        }
    }

    @Test
    public void testUpdateNonExistantUser(){
        PostDTO dto = new PostDTO(null, PostConstants.NEW_TEXT, PostConstants.NEW_URL,
         PostConstants.NEW_LINKS, null);

         try {
            service.update(UUID.fromString(PersonConstants.NON_EXISTING_ID), PostConstants.UPDATE_ID, dto);
        } catch (Exception e) {
            assertEquals("User with given id doesn't exist!", e.getMessage());
        }
    }

    @Test
    public void testUpdateNonExistantPost(){
        PostDTO dto = new PostDTO(null, PostConstants.NEW_TEXT, PostConstants.NEW_URL,
         PostConstants.NEW_LINKS, null);

         try {
            service.update(UUID.fromString(PersonConstants.NON_EXISTING_ID), PostConstants.NON_EXISTANT_ID, dto);
        } catch (Exception e) {
            assertEquals("Post with given id doesn't exist!", e.getMessage());
        }
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteOK() throws Exception{
        int sizeBefore = service.findAll().size();
        assertNotEquals(null, service.findOne(PostConstants.DELETE_ID));

        service.delete(UUID.fromString(PersonConstants.EXISTING_ID_4), PostConstants.DELETE_ID);

        assertTrue(service.findAll().size() < sizeBefore);
        assertEquals(null, service.findOne(PostConstants.DELETE_ID));
    }

    @Test
    public void testDeleteNotExistantPost() {
        try {
            service.delete(UUID.fromString(PersonConstants.EXISTING_ID_4), PostConstants.NON_EXISTANT_ID);
        } catch (Exception e) {
            assertEquals("Post with given id doesn't exist!", e.getMessage());
        }
    }

    @Test
    public void testDeleteNotExistantUser() {
        try {
            service.delete(UUID.fromString(PersonConstants.NON_EXISTING_ID), PostConstants.DELETE_ID);
        } catch (Exception e) {
            assertEquals("User with given id doesn't exist!", e.getMessage());
        }
    }

    @Test
    public void testDeleteNoPermission() {
        try {
            service.delete(UUID.fromString(PersonConstants.EXISTING_ID_3), PostConstants.DELETE_ID);
        } catch (Exception e) {
            assertEquals("Only the user who made the post may remove it!", e.getMessage());
        }
    }
    
}
