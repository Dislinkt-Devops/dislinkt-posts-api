package com.dislinkt.post.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.dislinkt.post.constants.PersonConstants;
import com.dislinkt.post.dto.ErrorDTO;
import com.dislinkt.post.dto.PersonDTO;
import com.dislinkt.post.dto.ResponseDTO;
import com.dislinkt.post.model.Person;
import com.dislinkt.post.service.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-users.properties")
public class PersonControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PersonService service;

    private HttpHeaders httpHeaders;

    @Test
    public void testAddUserOk(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.NEW_ID);
        
        PersonDTO newPerson = new PersonDTO(null, PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME,
        PersonConstants.GENDER, PersonConstants.PHONE_NUMBER, null, null, PersonConstants.PRIVACY);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(newPerson, httpHeaders);

        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange("/people", HttpMethod.POST,
         httpEntity, ResponseDTO.class);

        ResponseDTO response = responseEntity.getBody();
        Object dto = response.getData();

        assertNotEquals(dto, null);
        
        Person addedPerson = service.findOne(UUID.fromString(PersonConstants.NEW_ID));
        assertNotEquals(addedPerson, null);

        assertEquals(addedPerson.getFirstName(), PersonConstants.FIRST_NAME);
        assertEquals(addedPerson.getLastName(), PersonConstants.LAST_NAME);
        assertEquals(addedPerson.getGender().name(), PersonConstants.GENDER);
        assertEquals(addedPerson.getPhoneNumber(), PersonConstants.PHONE_NUMBER);
        assertEquals(addedPerson.getPrivacy().name(), PersonConstants.PRIVACY);
        assertEquals(addedPerson.getId().toString(), PersonConstants.NEW_ID);
    }

    @Test
    public void testAddUserWithNonUniqueId(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID);

        PersonDTO newPerson = new PersonDTO(null, PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME,
        PersonConstants.GENDER, PersonConstants.PHONE_NUMBER, null, null, PersonConstants.PRIVACY);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(newPerson, httpHeaders);

        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange("/people", HttpMethod.POST,
         httpEntity, ErrorDTO.class);

        ErrorDTO error = responseEntity.getBody();
        String message = error.getError();

        assertEquals(message, "User with given id already exists!");
    }

    @Test
    public void testAddUserWithEmptyFirstName(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.NEW_ID);

        PersonDTO newPerson = new PersonDTO(null, "  ", PersonConstants.LAST_NAME,
        PersonConstants.GENDER, PersonConstants.PHONE_NUMBER, null, null, PersonConstants.PRIVACY);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(newPerson, httpHeaders);

        ResponseEntity<ErrorDTO[]> responseEntity = restTemplate.exchange("/people", HttpMethod.POST,
         httpEntity, ErrorDTO[].class);

        ErrorDTO[] errors = responseEntity.getBody();
        String message = errors[0].getError();

        assertEquals(message, "First name can't be blank");
    }

    @Test
    public void testAddUserWithEmptyLastName(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.NEW_ID);

        PersonDTO newPerson = new PersonDTO(null, PersonConstants.FIRST_NAME, "  ",
        PersonConstants.GENDER, PersonConstants.PHONE_NUMBER, null, null, PersonConstants.PRIVACY);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(newPerson, httpHeaders);

        ResponseEntity<ErrorDTO[]> responseEntity = restTemplate.exchange("/people", HttpMethod.POST,
         httpEntity, ErrorDTO[].class);

        ErrorDTO[] errors = responseEntity.getBody();
        String message = errors[0].getError();

        assertEquals(message, "Last name can't be blank");
    }

    @Test
    public void testAddUserWithEmptyGender(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.NEW_ID);

        PersonDTO newPerson = new PersonDTO(null, PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME,
        "  ", PersonConstants.PHONE_NUMBER, null, null, PersonConstants.PRIVACY);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(newPerson, httpHeaders);

        ResponseEntity<ErrorDTO[]> responseEntity = restTemplate.exchange("/people", HttpMethod.POST,
         httpEntity, ErrorDTO[].class);

        ErrorDTO[] errors = responseEntity.getBody();
        String message = errors[0].getError();

        assertEquals(message, "Gender can't be blank");
    }

    @Test
    public void testAddUserWithEmptyPhoneNumber(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.NEW_ID);

        PersonDTO newPerson = new PersonDTO(null, PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME,
        PersonConstants.GENDER, "   ", null, null, PersonConstants.PRIVACY);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(newPerson, httpHeaders);

        ResponseEntity<ErrorDTO[]> responseEntity = restTemplate.exchange("/people", HttpMethod.POST,
         httpEntity, ErrorDTO[].class);

        ErrorDTO[] errors = responseEntity.getBody();
        String message = errors[0].getError();

        assertEquals(message, "Phone number can't be blank");
    }

    @Test
    public void testAddUserWithEmptyPrivacy(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.NEW_ID);

        PersonDTO newPerson = new PersonDTO(null, PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME,
        PersonConstants.GENDER, PersonConstants.PHONE_NUMBER, null, null, "  ");
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(newPerson, httpHeaders);

        ResponseEntity<ErrorDTO[]> responseEntity = restTemplate.exchange("/people", HttpMethod.POST,
         httpEntity, ErrorDTO[].class);

        ErrorDTO[] errors = responseEntity.getBody();
        String message = errors[0].getError();

        assertEquals(message, "Privacy can't be blank");
    }

    @Test
    public void testCanInteractWithOK(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        String url = "/people/"+PersonConstants.EXISTING_ID_2;
        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
         httpEntity, ResponseDTO.class);

        ResponseDTO<Boolean> response = responseEntity.getBody();
        
        assertEquals(Boolean.TRUE, response.getData());
    }

    @Test
    public void testCanInteractWithNonExisingUser(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.NEW_ID);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        String url = "/people/"+PersonConstants.EXISTING_ID_2;
        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
         httpEntity, ErrorDTO.class);

        ErrorDTO response = responseEntity.getBody();
        
        assertEquals("sender with given id doesn't exist", response.getError());
    }

    @Test
    public void testCanInteractWithNonExisingReceiver(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        String url = "/people/"+PersonConstants.NEW_ID;
        ResponseEntity<ErrorDTO> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
         httpEntity, ErrorDTO.class);

        ErrorDTO response = responseEntity.getBody();
        
        assertEquals("receiver with given id doesn't exist", response.getError());
    }

    @Test
    public void testCanInteractWithOneSidedFollowing(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        String url = "/people/"+PersonConstants.EXISTING_ID_3;
        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
         httpEntity, ResponseDTO.class);

        ResponseDTO<Boolean> response = responseEntity.getBody();
        
        assertEquals(Boolean.FALSE, response.getData());
    }

    @Test
    public void testCanInteractWithBlocking(){
        httpHeaders = new HttpHeaders();
        httpHeaders.add("X-User-Id", PersonConstants.EXISTING_ID);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(httpHeaders);
        String url = "/people/"+PersonConstants.EXISTING_ID_4;
        ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
         httpEntity, ResponseDTO.class);

        ResponseDTO<Boolean> response = responseEntity.getBody();
        
        assertEquals(Boolean.FALSE, response.getData());
    }
    
}
