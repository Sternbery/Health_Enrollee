package com.collabera.health_enrollee.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.collabera.health_enrollee.model.Enrollee;
import com.collabera.health_enrollee.model.User;
import com.collabera.health_enrollee.repo.EnrolleeRepository;
import com.collabera.health_enrollee.repo.UserRepository;

@RestController
public class EnrolleeController {
	
	@Autowired
	private EnrolleeRepository enrollee_repo;
	
	@Autowired
	private UserRepository user_repo;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public boolean testValidUser(String username, String password) throws NoSuchAlgorithmException {
		Optional<User> ouser = user_repo.findByUsernameAndApikey(username, User.hashkey(password));
		if(ouser.isEmpty())
			return false;
		if(!ouser.get().getCanRetrieveEnrollee())
			return false;
		return true;
	}
	
	//create
	@PostMapping("/enrollee")
	public ResponseEntity<Enrollee> postCreateEnrollee( 
			@RequestParam String username, 
			@RequestParam String apikey,
			@RequestParam(value="name") String name,
			@RequestParam(value="activationStatus", defaultValue="false") boolean activationStatus)
					throws NoSuchAlgorithmException {
		if(!testValidUser(username,apikey))
			return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
		
		return new ResponseEntity<>(enrollee_repo.save(new Enrollee(name,activationStatus)), HttpStatus.ACCEPTED);
	}

	//retrieve
	@GetMapping("/enrollee")
	public @ResponseBody ResponseEntity<Iterable<Enrollee>> getEnrollees(	@RequestParam String username, 
																			@RequestParam String apikey)
				throws NoSuchAlgorithmException
	{
		logger.info("getEnrollees()");
		
		if(!testValidUser(username,apikey))
			return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
		
		return new ResponseEntity<>(enrollee_repo.findAll(),HttpStatus.ACCEPTED);
	}
	@GetMapping("/enrollee/{id}")
	public @ResponseBody ResponseEntity<Enrollee> getEnrollee(	@RequestParam String username,
																@RequestParam String apikey,
																@PathVariable Long id)
				throws NoSuchAlgorithmException, IllegalAccessException
	{
		logger.info("getEnrollee()");
		if(!testValidUser(username,apikey))
			return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
		
		return ResponseEntity.of(enrollee_repo.findById(id));
		//return new ResponseEntity<>(enrollee_repo.findById(id).get(),HttpStatus.ACCEPTED);
	}
	
	//update
	@PutMapping("/enrollee/{id}")
	public ResponseEntity<Enrollee> putUpdateEnrollee(	@RequestParam String username,
														@RequestParam String apikey,
														@PathVariable Long id, 
														@RequestBody Enrollee update) 
					throws NoSuchAlgorithmException
	{
		logger.info("putUpdateEnrollee");
		
		if(!testValidUser(username,apikey))
			return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
		
		Enrollee e = enrollee_repo.findById(id).orElseThrow();
		e.setName(update.getName());
		e.setActivationStatus(update.getActivationStatus());
		
		e.setPhoneNumber(update.getPhoneNumber());
		
		return new ResponseEntity<>(enrollee_repo.save(e),HttpStatus.ACCEPTED);
	}
	@PostMapping("/enrollee/{id}")
	public ResponseEntity<Enrollee> postEnrollee(	@RequestParam String username,
													@RequestParam String apikey,
													@PathVariable Long id,
													@RequestBody Enrollee update)
					throws NoSuchAlgorithmException {
		return putUpdateEnrollee(username,apikey,id,update);
	}
	
	//delete
	@DeleteMapping("/enrollee/{id}")
	public ResponseEntity<Boolean> deleteEnrollee(	@RequestParam String username,
													@RequestParam String apikey,
													@PathVariable Long id)
									throws NoSuchAlgorithmException
	{
		if(!testValidUser(username,apikey))
			return new ResponseEntity<>(false,HttpStatus.UNAUTHORIZED);
		
		Optional<Enrollee> oe =this.enrollee_repo.findById(id);
		if(oe.isEmpty()) return new ResponseEntity<>(false,HttpStatus.ACCEPTED);
		
		this.enrollee_repo.delete(oe.get());
		return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
	}
}
