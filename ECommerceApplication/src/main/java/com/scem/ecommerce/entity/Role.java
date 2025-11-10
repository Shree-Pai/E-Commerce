package com.scem.ecommerce.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scem.ecommerce.entity.enums.RoleName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;
	
	@Enumerated(EnumType.STRING)
	@Column(unique = true, nullable = false)
	private RoleName roleName;


	@ManyToMany(mappedBy = "roles")
	@JsonIgnore
	private Set<User> users = new HashSet<>();

	public Long getRoleId() {
		return roleId;
	}


	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}


	public RoleName getRoleName() {
		return roleName;
	}


	public void setRoleName(RoleName roleName) {
		this.roleName = roleName;
	}
	
	


	public Set<User> getUsers() {
		return users;
	}


	public void setUsers(Set<User> users) {
		this.users = users;
	}


	public Role() {
		
	}

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }
	
	
	@Override
	public int hashCode() {
		
		return Objects.hash(roleId);
	}


	
	

}
