package com.collabera.health_enrollee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.collabera.health_enrollee.model.Enrollee;
import com.collabera.health_enrollee.repo.EnrolleeRepository;

@RestController
public class EnrolleeController {
	
	@Autowired
	private EnrolleeRepository enrollee_repo;
	
	@GetMapping("/")
	public @ResponseBody Iterable<Enrollee> getEnrollees(){
		return enrollee_repo.findAll();
	}
	
	@PostMapping("/add")
	public Enrollee addNewEnrollee( @RequestParam(value="name") String name,
								@RequestParam(value="activationStatus", defaultValue="false") boolean activationStatus) {
		return enrollee_repo.save(new Enrollee(name,activationStatus));
	}
}
