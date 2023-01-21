package com.dislinkt.post.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.dislinkt.post.constants.CommentConstants;
import com.dislinkt.post.constants.PostConstants;
import com.dislinkt.post.model.Comment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-users.properties")
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository repository;

    @Test
    public void testFindByPostId(){
        List<Comment> ret = repository.findByPostId(CommentConstants.POST_1);
        assertTrue(ret.size() < repository.findAll().size());
        assertEquals(3, ret.size());

        ret = repository.findByPostId(CommentConstants.POST_2);
        assertTrue(ret.size() < repository.findAll().size());
        assertEquals(2, ret.size());

        ret = repository.findByPostId(PostConstants.DELETE_ID);
        assertTrue(ret.size() < repository.findAll().size());
        assertEquals(0, ret.size());
    }
    
}
