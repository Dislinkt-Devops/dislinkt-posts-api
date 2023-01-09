package com.dislinkt.post.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.dislinkt.post.constants.PersonConstants;
import com.dislinkt.post.constants.PostConstants;
import com.dislinkt.post.dto.ErrorDTO;
import com.dislinkt.post.dto.PostDTO;
import com.dislinkt.post.dto.ResponseDTO;
import com.dislinkt.post.model.Post;
import com.dislinkt.post.service.PostService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-users.properties")
public class PostControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostService service;

    private HttpHeaders httpHeaders;

    @Test
    public void testCreateOK() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_5);

        PostDTO dto = new PostDTO(null, PostConstants.NEW_TEXT, PostConstants.NEW_URL,
         PostConstants.NEW_LINKS, null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        int beforeAdd = service.findAll().size();

        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange("/posts", HttpMethod.POST,
         httpEntity, ResponseDTO.class);

        ResponseDTO<PostDTO> response = responseEntity.getBody();
        assertNotEquals(null, response);
        assertTrue(beforeAdd < service.findAll().size());
    }

    @Test
    public void testCreateEmptyText() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_5);

        PostDTO dto = new PostDTO(null, " ", PostConstants.NEW_URL,
         PostConstants.NEW_LINKS, null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        int beforeAdd = service.findAll().size();

        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange("/posts", HttpMethod.POST,
         httpEntity, ResponseDTO.class);

        ResponseDTO<PostDTO> response = responseEntity.getBody();
        assertNotEquals(null, response);
        assertTrue(beforeAdd < service.findAll().size());
    }

    @Test
    public void testCreateEmptyURL() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_5);

        PostDTO dto = new PostDTO(null, PostConstants.NEW_TEXT, "   ",
         PostConstants.NEW_LINKS, null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        int beforeAdd = service.findAll().size();

        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange("/posts", HttpMethod.POST,
         httpEntity, ResponseDTO.class);

        ResponseDTO<PostDTO> response = responseEntity.getBody();
        assertNotEquals(null, response);
        assertTrue(beforeAdd < service.findAll().size());
    }

    @Test
    public void testCreateEmptyLinks() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_5);

        PostDTO dto = new PostDTO(null, PostConstants.NEW_TEXT, PostConstants.NEW_URL,
         new ArrayList<>(), null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        int beforeAdd = service.findAll().size();

        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange("/posts", HttpMethod.POST,
         httpEntity, ResponseDTO.class);

        ResponseDTO<PostDTO> response = responseEntity.getBody();
        assertNotEquals(null, response);
        assertTrue(beforeAdd < service.findAll().size());
    }

    @Test
    public void testCreateTwoEmptyFields() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_5);

        PostDTO dto = new PostDTO(null, "  ", PostConstants.NEW_URL,
         new ArrayList<>(), null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        int beforeAdd = service.findAll().size();

        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange("/posts", HttpMethod.POST,
         httpEntity, ResponseDTO.class);

        ResponseDTO<PostDTO> response = responseEntity.getBody();
        assertNotEquals(null, response);
        assertTrue(beforeAdd < service.findAll().size());
    }

    @Test
    public void testCreateNonExistentUser() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.NON_EXISTING_ID);

        PostDTO dto = new PostDTO(null, PostConstants.NEW_TEXT, PostConstants.NEW_URL,
         PostConstants.NEW_LINKS, null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        int beforeAdd = service.findAll().size();

        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/posts", HttpMethod.POST,
         httpEntity, ErrorDTO.class);

         ErrorDTO response = responseEntity.getBody();
        assertEquals("User with given id doesn't exist!", response.getError());
        assertEquals(beforeAdd, service.findAll().size());
    }

    @Test
    public void testCreateEmptyPost() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_5);

        PostDTO dto = new PostDTO(null, " ", "",
         new ArrayList<>(), null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        int beforeAdd = service.findAll().size();

        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/posts", HttpMethod.POST,
         httpEntity, ErrorDTO.class);

         ErrorDTO response = responseEntity.getBody();
        assertEquals("Cannot submit an empty post!", response.getError());
        assertEquals(beforeAdd, service.findAll().size());
    }

    @Test
    public void testUpdateOK() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_3);

        PostDTO dto = new PostDTO(null, PostConstants.NEW_TEXT, PostConstants.NEW_URL,
         PostConstants.NEW_LINKS, null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);


        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange("/posts/"+PostConstants.UPDATE_ID, HttpMethod.PUT,
         httpEntity, ResponseDTO.class);

        ResponseDTO<PostDTO> response = responseEntity.getBody();
        assertNotEquals(null, response);
        
        PostDTO updated = service.findOneDto(PostConstants.UPDATE_ID);
        assertEquals(PostConstants.NEW_TEXT, updated.getText());
        assertEquals(PostConstants.NEW_URL, updated.getImageUrl());
    }

    @Test
    public void testUpdateEmptyText() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_3);

        PostDTO dto = new PostDTO(null, "", PostConstants.NEW_URL,
         PostConstants.NEW_LINKS, null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);


        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange("/posts/"+PostConstants.UPDATE_ID, HttpMethod.PUT,
         httpEntity, ResponseDTO.class);

        ResponseDTO<PostDTO> response = responseEntity.getBody();
        assertNotEquals(null, response);
        
        Post updated = service.findOne(PostConstants.UPDATE_ID);
        assertEquals("", updated.getText());
    }

    @Test
    public void testUpdateEmptyUrl() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_3);

        PostDTO dto = new PostDTO(null, PostConstants.NEW_TEXT, "",
         PostConstants.NEW_LINKS, null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);


        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange("/posts/"+PostConstants.UPDATE_ID, HttpMethod.PUT,
         httpEntity, ResponseDTO.class);

        ResponseDTO<PostDTO> response = responseEntity.getBody();
        assertNotEquals(null, response);
        
        PostDTO updated = service.findOneDto(PostConstants.UPDATE_ID);
        assertEquals("", updated.getImageUrl());
    }

    @Test
    public void testUpdateEmptyList() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_3);

        PostDTO dto = new PostDTO(null, PostConstants.NEW_TEXT, PostConstants.NEW_URL,
         new ArrayList<>(), null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);


        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange("/posts/"+PostConstants.UPDATE_ID, HttpMethod.PUT,
         httpEntity, ResponseDTO.class);

        ResponseDTO<PostDTO> response = responseEntity.getBody();
        assertNotEquals(null, response);
        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateTwoEmptyFields() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_3);

        PostDTO dto = new PostDTO(null, "", PostConstants.NEW_URL,
         new ArrayList<>(), null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);


        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange("/posts/"+PostConstants.UPDATE_ID, HttpMethod.PUT,
         httpEntity, ResponseDTO.class);

        ResponseDTO<PostDTO> response = responseEntity.getBody();
        assertNotEquals(null, response);
        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateWrongUser() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_2);

        PostDTO dto = new PostDTO(null, PostConstants.NEW_TEXT, PostConstants.NEW_URL,
         PostConstants.NEW_LINKS, null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);


        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/posts/"+PostConstants.UPDATE_ID, HttpMethod.PUT,
         httpEntity, ErrorDTO.class);

        ErrorDTO response = responseEntity.getBody();
        assertEquals("Only the user who made the post may edit it!", response.getError());
    }

    @Test
    public void testUpdateEmptyPost() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_3);

        PostDTO dto = new PostDTO(null, " ", "",
         new ArrayList<>(), null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);


        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/posts/"+PostConstants.UPDATE_ID, HttpMethod.PUT,
         httpEntity, ErrorDTO.class);

        ErrorDTO response = responseEntity.getBody();
        assertEquals("Cannot have an empty post!", response.getError());
    }

    @Test
    public void testUpdateNonExistantUser() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.NON_EXISTING_ID);

        PostDTO dto = new PostDTO(null, PostConstants.NEW_TEXT, PostConstants.NEW_URL,
         PostConstants.NEW_LINKS, null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);


        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/posts/"+PostConstants.UPDATE_ID, HttpMethod.PUT,
         httpEntity, ErrorDTO.class);

        ErrorDTO response = responseEntity.getBody();
        assertEquals("User with given id doesn't exist!", response.getError());
    }

    @Test
    public void testUpdateNonExistantPost() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_3);

        PostDTO dto = new PostDTO(null, PostConstants.NEW_TEXT, PostConstants.NEW_URL,
         PostConstants.NEW_LINKS, null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);


        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/posts/"+PostConstants.NON_EXISTANT_ID, HttpMethod.PUT,
         httpEntity, ErrorDTO.class);

        ErrorDTO response = responseEntity.getBody();
        assertEquals("Post with given id doesn't exist!", response.getError());
    }

    @Test
    public void testDeleteOK() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_4);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);

        int beforeDelete = service.findAll().size();

        ResponseEntity<Void> responseEntity = restTemplate.exchange("/posts/"+PostConstants.DELETE_ID, HttpMethod.DELETE,
         httpEntity, Void.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(beforeDelete > service.findAll().size());
    }

    @Test
    public void testDeleteNotExistantPost() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_4);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);

        int beforeDelete = service.findAll().size();

        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/posts/"+PostConstants.NON_EXISTANT_ID, HttpMethod.DELETE,
         httpEntity, ErrorDTO.class);

        ErrorDTO response = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(beforeDelete, service.findAll().size());
        assertEquals("Post with given id doesn't exist!", response.getError());
    }

    @Test
    public void testDeleteNotExistantUser() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.NON_EXISTING_ID);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);

        int beforeDelete = service.findAll().size();

        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/posts/"+PostConstants.DELETE_ID, HttpMethod.DELETE,
         httpEntity, ErrorDTO.class);

        ErrorDTO response = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(beforeDelete, service.findAll().size());
        assertEquals("User with given id doesn't exist!", response.getError());
    }

    @Test
    public void testDeleteNoPermission() {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_3);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);

        int beforeDelete = service.findAll().size();

        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/posts/"+PostConstants.DELETE_ID, HttpMethod.DELETE,
         httpEntity, ErrorDTO.class);

        ErrorDTO response = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(beforeDelete, service.findAll().size());
        assertEquals("Only the user who made the post may remove it!", response.getError());
    }
    
}
