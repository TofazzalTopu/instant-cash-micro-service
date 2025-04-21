package com.info.userservice.repository;

import com.info.userservice.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface BranchRepository extends JpaRepository<Branch, Long> {

	@Query(value = "SELECT m FROM Branch m WHERE m.branchKey.entityNumber = :entity_number and m.branchKey.branchCode = :branch_code")
	Optional<Branch> getBranch(@Param("entity_number") short entityNumber, @Param("branch_code")int branchCode);

	@Query(value = "SELECT DISTINCT m FROM Branch m WHERE m.routingNumber in (:routingNumbers)")
	List<Branch> findAllByRoutingNumber(@Param("routingNumbers") List<Integer> routingNumbers);
	Branch findByRoutingNumber(@Param("routingNumber") Integer routingNumber);
}
