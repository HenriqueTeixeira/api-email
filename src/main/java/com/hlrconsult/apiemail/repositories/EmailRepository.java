package com.hlrconsult.apiemail.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hlrconsult.apiemail.models.EmailModel;

public interface EmailRepository extends JpaRepository<EmailModel, UUID>{
	
}
