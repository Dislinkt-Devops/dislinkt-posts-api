package com.dislinkt.post;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

import com.dislinkt.post.controller.PersonControllerTest;
import com.dislinkt.post.repository.PostRepositoryTest;
import com.dislinkt.post.service.PersonServiceTest;
import com.dislinkt.post.service.PostServiceTest;

@RunWith(Suite.class)
@SuiteClasses({PersonServiceTest.class, PersonControllerTest.class,
                PostRepositoryTest.class, PostServiceTest.class})
@TestPropertySource("classpath:test-users.properties")
class PostApplicationTests {
	
}
