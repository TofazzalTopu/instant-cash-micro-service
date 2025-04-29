package com.info.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.info.bank.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
