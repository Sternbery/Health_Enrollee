package com.collabera.health_enrollee.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Dependent {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="enrollee_id")
	private Long enrolleeId;
	private String name;
	private java.sql.Date birthdate;
	
	protected Dependent() {}

	public Dependent(Long enrollee_id, String name, Date birthdate) {
		super();
		this.enrolleeId = enrollee_id;
		this.name = name;
		this.birthdate = birthdate;
	}

	public Long getEnrolleeId() {
		return enrolleeId;
	}

	public void setEnrolleeId(Long enrollee_id) {
		this.enrolleeId = enrollee_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public java.sql.Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(java.sql.Date birthdate) {
		this.birthdate = birthdate;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Dependent [id=" + id + ", enrollee_id=" + enrolleeId + ", name=" + name + ", birthdate=" + birthdate
				+ "]";
	}
	
	
}
