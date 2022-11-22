package com.dislinkt.post;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

import com.dislinkt.post.controller.PersonControllerTest;
import com.dislinkt.post.service.PersonServiceTest;

@RunWith(Suite.class)
@SuiteClasses({PersonServiceTest.class, PersonControllerTest.class})
@TestPropertySource("classpath:test-users.properties")
class PostApplicationTests {
	
}
