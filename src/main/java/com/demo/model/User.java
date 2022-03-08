package com.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table
public class User {

	@Id
	@Column
	private String userId;

	@Column
	private String userName;

	@Column
	private String email;

}
