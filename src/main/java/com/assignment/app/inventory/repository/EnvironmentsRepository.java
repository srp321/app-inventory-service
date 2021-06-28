package com.assignment.app.inventory.repository;

import com.assignment.app.inventory.entity.Environments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentsRepository extends JpaRepository<Environments, String>, JpaSpecificationExecutor<Environments> {

}
