package com.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table
public class Draw {

	@Id
	@Column
	private String drawId;

	@Column
	private String name;
	
	@Column
	private String status;

	@Column
	private LocalDateTime startDate;

	@Column
	private LocalDateTime endDate;

	@Column
	private String winnerUserId;
	
}
