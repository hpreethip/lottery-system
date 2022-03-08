package com.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, String> {
	
	List<Ticket> findDistinctByDrawId(String drawId);
	
	Optional<Ticket> findByUserIdAndStatus(String userId, String status);
	
	List<Ticket> findAllByUserId(String userId);

}
