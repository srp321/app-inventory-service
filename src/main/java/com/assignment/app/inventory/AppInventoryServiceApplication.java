package com.assignment.app.inventory;

import com.assignment.app.inventory.controller.ApplicationController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Import({ApplicationController.class})
@EntityScan({"com.assignment.app.inventory.entity"})
@EnableJpaRepositories(basePackages = {"com.assignment.app.inventory.entity", "com.assignment.app.inventory.repository"})
public class AppInventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppInventoryServiceApplication.class, args);
    }

}
