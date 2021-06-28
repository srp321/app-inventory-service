package com.assignment.app.inventory.controller;

import com.assignment.app.inventory.entity.Application;
import com.assignment.app.inventory.model.request.ApplicationUpdateRequest;
import com.assignment.app.inventory.model.response.ApplicationResponse;
import com.assignment.app.inventory.model.response.ApplicationUpdateResponse;
import com.assignment.app.inventory.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/v1")
@CrossOrigin
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @PostMapping(value = "/applications", produces = "application/json")
    public ResponseEntity<?> createApplication(@RequestBody @Valid Application application) {

        ApplicationResponse applicationResponse;
        try {
            log.info("Creating application");
            applicationResponse = applicationService.createApplication(application);
        } catch (Exception ex) {
            log.error("Issue with application creation: " + ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/applications", produces = "application/json")
    public ResponseEntity<?> getApplications(@RequestParam(required = false) String name,
                                             @RequestParam(defaultValue = "false") Boolean env) {

        List<ApplicationResponse> applicationResponseList;
        try {
            log.info("Getting application data");
            applicationResponseList = applicationService.getApplications(name, env);
        } catch (Exception ex) {
            log.error("Issue with getting application data: " + ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(applicationResponseList, HttpStatus.OK);
    }

    @PatchMapping(value = "/applications/{id}", produces = "application/json")
    public ResponseEntity<?> updateApplication(@RequestBody @Valid ApplicationUpdateRequest applicationUpdateRequest,
                                                    @PathVariable String id,
                                                    @RequestHeader("userId") String userId) {

        ApplicationUpdateResponse applicationUpdateResponse;
        try {
            applicationUpdateResponse = applicationService.updateApplication(applicationUpdateRequest, id, userId);

        } catch (Exception ex) {
            log.error("Issue with updating application data: " + ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(applicationUpdateResponse, HttpStatus.OK);
    }

}
