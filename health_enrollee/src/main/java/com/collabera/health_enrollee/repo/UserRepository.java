package com.collabera.health_enrollee.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.collabera.health_enrollee.model.User;

public interface UserRepository extends CrudRepository<User,Long>{
	public Optional<User> findByApikey(String apikey);
	public Optional<User> findByUsernameAndApikey(String username, String apikey);
}
