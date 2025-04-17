package com.info.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.info.userservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
