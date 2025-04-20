package com.info.division.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.info.division.model.Division;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {

}
