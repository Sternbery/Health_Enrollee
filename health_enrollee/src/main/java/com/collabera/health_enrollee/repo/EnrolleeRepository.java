package com.collabera.health_enrollee.repo;

import org.springframework.data.repository.CrudRepository;

import com.collabera.health_enrollee.model.Enrollee;

public interface EnrolleeRepository extends CrudRepository<Enrollee, Long> {

}
