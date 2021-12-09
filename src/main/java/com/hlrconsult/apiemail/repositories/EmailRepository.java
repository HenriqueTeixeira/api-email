package com.hlrconsult.apiemail.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hlrconsult.apiemail.models.EmailModel;

public interface EmailRepository extends JpaRepository<EmailModel, UUID>{		
	
	@Query(value = "SELECT * FROM public.tab_email WHERE status_email = 0 ORDER BY id ASC LIMIT 10", nativeQuery = true)
	List<EmailModel> findByStatusEmail();	
}
