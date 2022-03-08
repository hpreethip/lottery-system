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
public class Ticket {

	@Id
	@Column
	private String ticketId;

	@Column
	private String drawId;
	
	@Column
	private String userId;
	
	@Column
	private String status;

	@Column
	private LocalDateTime date;

}
