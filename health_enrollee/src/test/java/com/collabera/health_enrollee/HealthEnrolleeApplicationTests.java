package com.collabera.health_enrollee;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.collabera.health_enrollee.controller.DependentController;
import com.collabera.health_enrollee.controller.EnrolleeController;
import com.collabera.health_enrollee.model.User;
import com.collabera.health_enrollee.repo.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
class HealthEnrolleeApplicationTests {

	@Autowired
    private MockMvc mvc;
	
	@MockBean
	UserRepository userRepo;
	
	@InjectMocks
	EnrolleeController enrolleeController;
	
	@Autowired
	DependentController dependentController;
	
	@Test
	void contextLoads() {
		assertThat(enrolleeController).isNotNull();
		assertThat(dependentController).isNotNull();
	}
	@Test
	void usesRepo() throws NoSuchAlgorithmException {
		
		
			
		
		User user = new User();
		user.setUsername("Test");
		user.setAndHashApikey("Test");
		user.setUsed(new java.sql.Timestamp(System.currentTimeMillis()));
		user.setCanCreateEnrollee(true);
		user.setCanRetrieveEnrollee(true);
		user.setCanUpdateEnrollee(true);
		user.setCanDeleteEnrollee(true);
		
		Mockito
		.when(userRepo.findByUsernameAndApikey(
				Mockito.matches(user.getUsername()), 
				Mockito.matches(user.getHashedApikey())))
		.thenReturn(Optional.of(user));
//		Mockito
//		.when(userRepo.findByUsernameAndApikey(
//				Mockito.anyString(), Mockito.anyString()))
//		.thenReturn(Optional.empty());	
		
		Assertions.assertNotNull(user);
		Assertions.assertTrue(
				userRepo.findByUsernameAndApikey(
						user.getUsername(), user.getHashedApikey()).isPresent());
		//Assertions.assertEquals(user,userRepo.findByUsernameAndApikey("Test", "Test").get());
		Assertions.assertNotEquals(user,userRepo.findByUsernameAndApikey("Testd", "Test").get());

	}

}
