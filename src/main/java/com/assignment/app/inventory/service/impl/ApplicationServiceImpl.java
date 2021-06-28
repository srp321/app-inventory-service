package com.assignment.app.inventory.service.impl;

import com.assignment.app.inventory.entity.Application;
import com.assignment.app.inventory.entity.Environments;
import com.assignment.app.inventory.exception.InvalidUserException;
import com.assignment.app.inventory.exception.UserPrivilegeException;
import com.assignment.app.inventory.model.request.ApplicationUpdateRequest;
import com.assignment.app.inventory.model.response.ApplicationResponse;
import com.assignment.app.inventory.model.response.ApplicationUpdateResponse;
import com.assignment.app.inventory.repository.ApplicationRepository;
import com.assignment.app.inventory.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ApplicationResponse createApplication(Application application) {

        Application app = applicationRepository.save(application);
        log.info("Application data saved");

        return createAppResponse(app);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<ApplicationResponse> getApplications(String name, Boolean env) {

        List<ApplicationResponse> applicationResponseList = new ArrayList<>();
        List<Application> applicationList;

        if (!Objects.isNull(name)) {
            log.info("Fetching Application Data by Name");
            applicationList = applicationRepository.findApplicationByNameLikeIgnoreCase(name);
        } else {
            log.info("Fetching All Application Data");
            applicationList = applicationRepository.findAll();
        }
        if (!CollectionUtils.isEmpty(applicationList)) {
            for (Application app : applicationList) {
                ApplicationResponse appResponse = createAppResponse(app);
                if (env) {
                    log.info("Mapping Environment details");
                    appResponse.setEnvironments(app.getEnvironments());
                }
                applicationResponseList.add(appResponse);
            }
        }

        return applicationResponseList;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public ApplicationResponse getApplication(String id, Boolean env) {

        Application application;
        ApplicationResponse appResponse;
        log.info("Fetching Application Data by Name");
        Optional<Application> appOpt = applicationRepository.findById(id);
        if (appOpt.isPresent()) {
            application = appOpt.get();
            appResponse = createAppResponse(application);
            if (env) {
                log.info("Mapping Environment details");
                appResponse.setEnvironments(application.getEnvironments());
            }
        } else {
            return null;
        }

        return appResponse;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ApplicationUpdateResponse updateApplication(ApplicationUpdateRequest applicationUpdateRequest, String id, String userId) throws Exception {

        if (Objects.isNull(userId) || "".equals(userId)) {
            log.error("No userId present");
            throw new InvalidUserException("User Isn't an admin of the application!");
        }
        log.info("Fetching Application Data by AppID: " + id);
        Application currentApp = applicationRepository.getById(id);

        if (!Objects.isNull(currentApp)) {
            List<String> admins = currentApp.getAdmins();
            if (!admins.contains(userId)) {
                log.error("Current user isn't part of admin for app");
                throw new UserPrivilegeException("User Isn't an admin of the application!");
            }
            List<Environments> envToAdd = applicationUpdateRequest.getEnvironments();
            List<Environments> currentEnv = currentApp.getEnvironments();
            if (!CollectionUtils.isEmpty(envToAdd)) {
                log.info("Updating Env for AppID: " + id);
                currentEnv.addAll(envToAdd);
            }
            applicationRepository.save(currentApp);
            log.info("Updated App data");
        } else {
            log.warn("No App to Update");
            return new ApplicationUpdateResponse("Fail");
        }

        return new ApplicationUpdateResponse("Success");
    }

    private ApplicationResponse createAppResponse(Application app) {
        log.info("Mapping app response for App Id: " + app.getAppId());

        ApplicationResponse appResponse = new ApplicationResponse();
        appResponse.setId(app.getAppId());
        appResponse.setContact(app.getContact());
        appResponse.setDescription(app.getDescription());
        appResponse.setName(app.getName());
        appResponse.setValidFrom(app.getValidFrom());
        appResponse.setAdmins(app.getAdmins());

        return appResponse;
    }

}
