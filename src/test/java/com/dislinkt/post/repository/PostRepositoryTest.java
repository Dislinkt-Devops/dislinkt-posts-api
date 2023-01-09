package com.dislinkt.post.repository;

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

import com.dislinkt.post.constants.PersonConstants;
import com.dislinkt.post.model.Post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-users.properties")
public class PostRepositoryTest {

    @Autowired
    private PostRepository repository;

    @Test
    public void testFindByPersonId(){
        List<Post> ret = repository.findByPersonId(UUID.fromString(PersonConstants.EXISTING_ID));
        assertTrue(ret.size() < repository.findAll().size());
        assertEquals(2, ret.size());

        ret = repository.findByPersonId(UUID.fromString(PersonConstants.EXISTING_ID_2));
        assertTrue(ret.size() < repository.findAll().size());
        assertEquals(1, ret.size());

        ret = repository.findByPersonId(UUID.fromString(PersonConstants.NEW_ID));
        assertTrue(ret.size() == 0);
    }
    
}
