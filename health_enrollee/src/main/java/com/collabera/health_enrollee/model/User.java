package com.collabera.health_enrollee.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotNull
	private String username;
	@NotNull
	private String apikey;
	@NotNull
	private String salt;
	
	private java.sql.Timestamp used;
	
	@NotNull
	private Boolean canCreateEnrollee = false;
	@NotNull
	private Boolean canRetrieveEnrollee = false;
	@NotNull
	private Boolean canUpdateEnrollee = false;
	@NotNull
	private Boolean canDeleteEnrollee = false;
	
	@NotNull private Boolean canCreateDependent = false;
	@NotNull private Boolean canRetrieveDependent = false;
	@NotNull private Boolean canUpdateDependent = false;
	@NotNull private Boolean canDeleteDependent = false;
	
	
	public User() {}
	public User(String username, String apikey) {
		super();
		this.username = username;
		this.apikey = apikey;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHashedApikey() {
		return apikey;
	}
	public void setAndHashApikey(String apikey) throws NoSuchAlgorithmException {
		this.apikey = User.hashkey(apikey);
	}
	public java.sql.Timestamp getUsed() {
		return used;
	}
	public void setUsed(java.sql.Timestamp used) {
		this.used = used;
	}
	public Long getId() {
		return id;
	}
	
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public Boolean getCanCreateEnrollee() {
		return canCreateEnrollee;
	}
	public void setCanCreateEnrollee(Boolean canCreateEnrollee) {
		this.canCreateEnrollee = canCreateEnrollee;
	}
	public Boolean getCanRetrieveEnrollee() {
		return canRetrieveEnrollee;
	}
	public void setCanRetrieveEnrollee(Boolean canRetrieveEnrollee) {
		this.canRetrieveEnrollee = canRetrieveEnrollee;
	}
	public Boolean getCanUpdateEnrollee() {
		return canUpdateEnrollee;
	}
	public void setCanUpdateEnrollee(Boolean canUpdateEnrollee) {
		this.canUpdateEnrollee = canUpdateEnrollee;
	}
	public Boolean getCanDeleteEnrollee() {
		return canDeleteEnrollee;
	}
	public void setCanDeleteEnrollee(Boolean canDeleteEnrollee) {
		this.canDeleteEnrollee = canDeleteEnrollee;
	}
	public Boolean getCanCreateDependent() {
		return canCreateDependent;
	}
	public void setCanCreateDependent(Boolean canCreateDependent) {
		this.canCreateDependent = canCreateDependent;
	}
	public Boolean getCanRetrieveDependent() {
		return canRetrieveDependent;
	}
	public void setCanRetrieveDependent(Boolean canRetrieveDependent) {
		this.canRetrieveDependent = canRetrieveDependent;
	}
	public Boolean getCanUpdateDependent() {
		return canUpdateDependent;
	}
	public void setCanUpdateDependent(Boolean canUpdateDependent) {
		this.canUpdateDependent = canUpdateDependent;
	}
	public Boolean getCanDeleteDependent() {
		return canDeleteDependent;
	}
	public void setCanDeleteDependent(Boolean canDeleteDependent) {
		this.canDeleteDependent = canDeleteDependent;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", used=" + used + "]";
	}
	
	public static String hashkey(String key) throws NoSuchAlgorithmException {
		return new String(MessageDigest.getInstance("SHA-256").digest(key.getBytes()));
	}
	
}
