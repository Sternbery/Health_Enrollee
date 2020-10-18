package com.collabera.health_enrollee.repo;

import org.springframework.data.repository.CrudRepository;

import com.collabera.health_enrollee.model.Dependent;

public interface DependentRepository extends CrudRepository<Dependent, Long> {
	public Iterable<Dependent> findAllByEnrolleeId(Long enrollee_id);
}
