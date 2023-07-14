package com.springcrud.user.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.springcrud.user.entities.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>{

	// Page<User> findAll(Pageable pageable);
	
}
