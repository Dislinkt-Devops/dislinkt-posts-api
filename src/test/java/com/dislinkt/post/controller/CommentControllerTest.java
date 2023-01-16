package com.dislinkt.post.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

import com.dislinkt.post.constants.CommentConstants;
import com.dislinkt.post.constants.PersonConstants;
import com.dislinkt.post.constants.PostConstants;
import com.dislinkt.post.dto.CommentDTO;
import com.dislinkt.post.dto.ErrorDTO;
import com.dislinkt.post.dto.ResponseDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-users.properties")
public class CommentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders httpHeaders;

    @Test
    public void testCreateOK(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID);
        
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, CommentConstants.POST_1);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange("/comments", HttpMethod.POST,
         httpEntity, ResponseDTO.class);

        ResponseDTO response = responseEntity.getBody();

        assertNotEquals(null, response.getData());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateOKWhenPublic(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_5);
        
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, CommentConstants.POST_1);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange("/comments", HttpMethod.POST,
         httpEntity, ResponseDTO.class);

        ResponseDTO response = responseEntity.getBody();

        assertNotEquals(null, response.getData());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateOKWhenOwnPost(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID);
        
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, CommentConstants.POST_2);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange("/comments", HttpMethod.POST,
         httpEntity, ResponseDTO.class);

        ResponseDTO response = responseEntity.getBody();

        assertNotEquals(null, response.getData());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateNonExistingUser(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.NON_EXISTING_ID);
        
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, CommentConstants.POST_1);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/comments", HttpMethod.POST,
         httpEntity, ErrorDTO.class);

         ErrorDTO response = responseEntity.getBody();

        assertEquals("User with given id doesn't exist!", response.getError());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateNonExistingPost(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID);
        
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, PostConstants.NON_EXISTANT_ID);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/comments", HttpMethod.POST,
         httpEntity, ErrorDTO.class);

         ErrorDTO response = responseEntity.getBody();

        assertEquals("Post with given id doesn't exist!", response.getError());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateWhenNotAllowed(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_5);
        
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, CommentConstants.POST_2);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/comments", HttpMethod.POST,
         httpEntity, ErrorDTO.class);

         ErrorDTO response = responseEntity.getBody();

        assertEquals("You can't leave a comment on this post!", response.getError());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateWhenBlocked(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_4);
        
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, CommentConstants.POST_2);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/comments", HttpMethod.POST,
         httpEntity, ErrorDTO.class);

         ErrorDTO response = responseEntity.getBody();

        assertEquals("You are blocking this user!", response.getError());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateWhenEmptyComment(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID);
        
        CommentDTO dto = new CommentDTO(null, "  ", null, CommentConstants.POST_1);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        ResponseEntity<ErrorDTO[]> responseEntity = restTemplate.exchange("/comments", HttpMethod.POST,
         httpEntity, ErrorDTO[].class);

         ErrorDTO[] response = responseEntity.getBody();

        assertEquals("You can't leave an empty comment!", response[0].getError());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateOK(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_5);
        
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange("/comments/"+CommentConstants.EXISTING_ID,
         HttpMethod.PUT, httpEntity, ResponseDTO.class);

        ResponseDTO response = responseEntity.getBody();

        assertNotEquals(null, response.getData());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateNonExistingId(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_5);
        
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/comments/"+CommentConstants.NON_EXISTING_ID,
         HttpMethod.PUT, httpEntity, ErrorDTO.class);

         ErrorDTO response = responseEntity.getBody();

        assertEquals("Comment with given id doesn't exist!", response.getError());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateWrongUser(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID);
        
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/comments/"+CommentConstants.EXISTING_ID,
         HttpMethod.PUT, httpEntity, ErrorDTO.class);

         ErrorDTO response = responseEntity.getBody();

        assertEquals("Only the owner of this comment may edit it!", response.getError());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateNonExistingUser(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.NON_EXISTING_ID);
        
        CommentDTO dto = new CommentDTO(null, CommentConstants.NEW_TEXT, null, null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/comments/"+CommentConstants.EXISTING_ID,
         HttpMethod.PUT, httpEntity, ErrorDTO.class);

         ErrorDTO response = responseEntity.getBody();

        assertEquals("Only the owner of this comment may edit it!", response.getError());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateWhenEmptyComment(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_5);
        
        CommentDTO dto = new CommentDTO(null, "  ", null, null);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(dto, httpHeaders);

        ResponseEntity<ErrorDTO[]> responseEntity = restTemplate.exchange("/comments/" + CommentConstants.EXISTING_ID, 
         HttpMethod.PUT, httpEntity, ErrorDTO[].class);

         ErrorDTO[] response = responseEntity.getBody();

        assertEquals("You can't leave an empty comment!", response[0].getError());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteOK(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_3);
        
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);

        ResponseEntity<Void> responseEntity = restTemplate.exchange("/comments/" + CommentConstants.EXISTING_ID_2, 
         HttpMethod.DELETE, httpEntity, Void.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteNonExistingComment(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_3);
        
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);

        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/comments/" + CommentConstants.NON_EXISTING_ID, 
         HttpMethod.DELETE, httpEntity, ErrorDTO.class);

        ErrorDTO response = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Comment with given id doesn't exist!", response.getError());
    }

    @Test
    public void testDeleteNonExistingUser(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.NON_EXISTING_ID);
        
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);

        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/comments/" + CommentConstants.EXISTING_ID_2, 
         HttpMethod.DELETE, httpEntity, ErrorDTO.class);

        ErrorDTO response = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Only the user who made the comment may remove it!", response.getError());
    }

    @Test
    public void testDeleteWrongUser(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID_4);
        
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);

        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/comments/" + CommentConstants.EXISTING_ID_2, 
         HttpMethod.DELETE, httpEntity, ErrorDTO.class);

        ErrorDTO response = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Only the user who made the comment may remove it!", response.getError());
    }
    
}
