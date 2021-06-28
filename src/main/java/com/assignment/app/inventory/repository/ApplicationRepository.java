package com.assignment.app.inventory.repository;

import com.assignment.app.inventory.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String>, JpaSpecificationExecutor<Application> {

    List<Application> findApplicationByNameLikeIgnoreCase(String name);

}
