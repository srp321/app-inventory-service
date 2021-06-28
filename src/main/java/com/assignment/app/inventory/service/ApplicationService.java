package com.assignment.app.inventory.service;

import com.assignment.app.inventory.entity.Application;
import com.assignment.app.inventory.model.request.ApplicationUpdateRequest;
import com.assignment.app.inventory.model.response.ApplicationResponse;
import com.assignment.app.inventory.model.response.ApplicationUpdateResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApplicationService {

    ApplicationResponse createApplication(Application application);

    List<ApplicationResponse> getApplications(String name, Boolean env);

    ApplicationResponse getApplication(String id, Boolean env);

    ApplicationUpdateResponse updateApplication(ApplicationUpdateRequest applicationUpdateRequest, String id, String userId) throws Exception;

}
