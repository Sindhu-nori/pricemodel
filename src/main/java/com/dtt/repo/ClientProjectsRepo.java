package com.dtt.repo;

import com.dtt.model.ClientProjects;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientProjectsRepo extends JpaRepository<ClientProjects,Long> {
	
	//ClientProjects findByClientProjectId(String clientProjectId);
	
	Optional<ClientProjects> findByClientProjectId(String clientProjectId);

}
