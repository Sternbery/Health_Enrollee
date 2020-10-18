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
import org.springframework.web.bind.annotation.RestController;

import com.collabera.health_enrollee.model.Dependent;
import com.collabera.health_enrollee.model.User;
import com.collabera.health_enrollee.repo.DependentRepository;
import com.collabera.health_enrollee.repo.UserRepository;

@RestController
public class DependentController {

	@Autowired
	private DependentRepository dependent_repo;
	@Autowired
	private UserRepository user_repo;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public boolean testValidUser(String username, String password) throws NoSuchAlgorithmException {
		Optional<User> ouser = user_repo.findByUsernameAndApikey(username, User.hashkey(password));
		if(ouser.isEmpty())
			return false;
		if(!ouser.get().getCanRetrieveEnrollee())
			return false;
		return true;
	}
	
	//create
	@PostMapping("/dependent/s")
	public String thing() {
		return "hello";
	}
	
	@PostMapping("/dependent")
	public ResponseEntity<Dependent> postNewDependent(
			@RequestParam String username, 
			@RequestParam String apikey,
			@RequestParam Long enrollee_id,
			@RequestParam String dependent_name,
			@RequestParam java.sql.Date dependent_birthdate) 
			throws NoSuchAlgorithmException
	{
		if(!this.testValidUser(username, apikey))
			return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
		
		return new ResponseEntity<>(
					dependent_repo.save(new Dependent(
						enrollee_id,
						dependent_name,
						dependent_birthdate
					)),
					HttpStatus.ACCEPTED);
	}
	@PostMapping("/dependent/body")
	public ResponseEntity<Dependent> postNewDependent(
			@RequestParam String username, 
			@RequestParam String apikey,
			@RequestBody Dependent dependent) 
					throws NoSuchAlgorithmException
	{
		this.logger.debug(dependent.toString());
		if(!this.testValidUser(username, apikey))
			return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<>(this.dependent_repo.save(dependent),HttpStatus.ACCEPTED);
	}
	
	//retrieve
	@GetMapping("/dependent/{id}")
	public ResponseEntity<Dependent> getDependent(
			@RequestParam String username, 
			@RequestParam String apikey,
			@PathVariable Long id) throws NoSuchAlgorithmException {
		if(!this.testValidUser(username, apikey))
			return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
		return ResponseEntity.of(this.dependent_repo.findById(id));
	}
	@GetMapping("/dependent")
	public ResponseEntity<Iterable<Dependent>> getAllDependents(
			@RequestParam String username, 
			@RequestParam String apikey
			) throws NoSuchAlgorithmException{
		if(!this.testValidUser(username, apikey))
			return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<>(this.dependent_repo.findAll(),HttpStatus.ACCEPTED);
	}
	
	//update
	@PutMapping("/dependent/body/{id}")
	public ResponseEntity<Dependent> putUpdateDependent(
			@RequestParam String username, 
			@RequestParam String apikey,
			@PathVariable Long id,
			@RequestBody Dependent dependent) throws NoSuchAlgorithmException {
		
		if(!this.testValidUser(username, apikey))
			return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
		
		Dependent indb = this.dependent_repo.findById(id).orElseThrow();
		indb.setEnrolleeId(dependent.getEnrolleeId());
		indb.setName(dependent.getName());
		indb.setBirthdate(dependent.getBirthdate());
		return new ResponseEntity<>(this.dependent_repo.save(indb),HttpStatus.ACCEPTED);
	}
	@PutMapping("/dependent/{id}")
	public ResponseEntity<Dependent> putUpdateDependent(
			@RequestParam String username, 
			@RequestParam String apikey,
			@PathVariable Long id,
			@RequestParam Long enrollee_id,
			@RequestParam String dependent_name,
			@RequestParam java.sql.Date dependent_birthdate) throws NoSuchAlgorithmException {
		
		if(!this.testValidUser(username, apikey))
			return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
		
		Dependent indb = this.dependent_repo.findById(id).orElseThrow();
		indb.setEnrolleeId(enrollee_id);
		indb.setName(dependent_name);
		indb.setBirthdate(dependent_birthdate);
		return new ResponseEntity<>(this.dependent_repo.save(indb),HttpStatus.ACCEPTED);
	}
	@PostMapping("/dependent/body/{id}")
	public ResponseEntity<Dependent> postUpdateDependent(
			@RequestParam String username, 
			@RequestParam String apikey,
			@PathVariable Long id,
			@RequestBody Dependent dependent) throws NoSuchAlgorithmException {
		return this.putUpdateDependent(username,apikey,id, dependent);
	}
	@PostMapping("/dependent/{id}")
	public ResponseEntity<Dependent> postUpdateDependent(
			@RequestParam String username, 
			@RequestParam String apikey,
			@PathVariable Long id,
			@RequestParam Long enrollee_id,
			@RequestParam String dependent_name,
			@RequestParam java.sql.Date dependent_birthdate) throws NoSuchAlgorithmException {
		return this.putUpdateDependent(username, apikey, id, enrollee_id, dependent_name, dependent_birthdate);
	}
	
	//delete
	@DeleteMapping("/dependent/{id}")
	public ResponseEntity<Boolean> deleteDependent(
			@RequestParam String username, 
			@RequestParam String apikey,
			@PathVariable Long id) throws NoSuchAlgorithmException {
		if(!this.testValidUser(username, apikey))
			return new ResponseEntity<>(false,HttpStatus.UNAUTHORIZED);
		
		Optional<Dependent> oe =this.dependent_repo.findById(id);
		if(oe.isEmpty()) return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
		this.dependent_repo.delete(oe.get());
		return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
	}
	
}
