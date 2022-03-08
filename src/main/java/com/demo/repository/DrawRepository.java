package com.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.Draw;

@Repository
public interface DrawRepository extends CrudRepository<Draw, String> {

	List<Draw> findAllByEndDateLessThanEqualAndStatus(LocalDateTime currentDate, String status);

	List<Draw> findAllByStatusOrderByStartDateDesc(String status);
	
	List<Draw> findAllByDrawIdInOrderByStartDateDesc(List<String> drawIds);
		
}
