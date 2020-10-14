package com.collabera.health_enrollee.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.lang.Nullable;

import com.sun.istack.NotNull;

@Entity
public class Enrollee {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotNull
	private String name;
	@Nullable
	private String phone_number;
	private Boolean activationStatus;
	
	@OneToMany( cascade = CascadeType.ALL,
				orphanRemoval=true )
	@JoinColumn(name = "enrollee_id")
	private List<Dependent> dependents = new ArrayList<>();
	

	protected Enrollee() {}

	public Enrollee(String name, Boolean activationStatus) {
		this.name=name;
		this.activationStatus=activationStatus;
	}

	@Override
	public String toString() {
		return String.format(
				"Customer[id=%d, name='%s', activationStatus='%s']",
				id, name, activationStatus.toString());
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActivationStatus() {
		return activationStatus;
	}

	public void setActivationStatus(Boolean activationStatus) {
		this.activationStatus = activationStatus;
	}

	public List<Dependent> getDependents() {
		return dependents;
	}

	public void setDependents(List<Dependent> dependents) {
		this.dependents = dependents;
	}
}
