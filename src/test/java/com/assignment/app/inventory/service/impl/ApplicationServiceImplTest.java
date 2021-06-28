package com.assignment.app.inventory.service.impl;

import com.assignment.app.inventory.entity.Application;
import com.assignment.app.inventory.entity.Environments;
import com.assignment.app.inventory.exception.InvalidUserException;
import com.assignment.app.inventory.exception.UserPrivilegeException;
import com.assignment.app.inventory.model.request.ApplicationUpdateRequest;
import com.assignment.app.inventory.model.response.ApplicationResponse;
import com.assignment.app.inventory.model.response.ApplicationUpdateResponse;
import com.assignment.app.inventory.repository.ApplicationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.Silent.class)
public class ApplicationServiceImplTest {

    @Mock
    ApplicationRepository applicationRepository;

    @InjectMocks
    ApplicationServiceImpl applicationServiceImpl;

    @Test
    public void testCreateApplication() {
        // Setup
        Application application = new Application();
        application.setName("appName");
        application.setValidFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        application.setDescription("description");
        application.setContact("contact");
        application.setAdmins(Arrays.asList("admin"));

        ApplicationResponse expectedResult = new ApplicationResponse();
        expectedResult.setName("appName");
        expectedResult.setId("appId");
        expectedResult.setValidFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        expectedResult.setDescription("description");
        expectedResult.setContact("contact");
        expectedResult.setAdmins(Arrays.asList("admin"));

        Application application1 = new Application();
        application1.setAppId("appId");
        application1.setName("appName");
        application1.setValidFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        application1.setDescription("description");
        application1.setContact("contact");
        application1.setAdmins(Arrays.asList("admin"));

        when(applicationRepository.save(application)).thenReturn(application1);

        // Run the test
        ApplicationResponse result = applicationServiceImpl.createApplication(application);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetApplicationsByNameWithEnv() {
        // Setup
        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setName("appName");
        applicationResponse.setId("appId");
        applicationResponse.setValidFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        applicationResponse.setDescription("description");
        applicationResponse.setContact("contact");
        applicationResponse.setAdmins(Arrays.asList("admin"));
        Environments environments = new Environments();
        environments.setId(0);
        environments.setName("envName");
        environments.setProd(false);
        environments.setUrl("url");

        applicationResponse.setEnvironments(Arrays.asList(environments));
        List<ApplicationResponse> expectedResult = Arrays.asList(applicationResponse);

        Application application1 = new Application();
        application1.setAppId("appId");
        application1.setName("appName");
        application1.setValidFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        application1.setDescription("description");
        application1.setContact("contact");
        application1.setAdmins(Arrays.asList("admin"));
        Environments environments1 = new Environments();
        environments1.setId(0);
        environments1.setName("envName");
        environments1.setProd(false);
        environments1.setUrl("url");
        application1.setEnvironments(Arrays.asList(environments1));
        List<Application> applicationList = Arrays.asList(application1);
        when(applicationRepository.findApplicationByNameLikeIgnoreCase("name")).thenReturn(applicationList);

        // Run the test
        List<ApplicationResponse> result = applicationServiceImpl.getApplications("name", true);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetApplicationsByNameWithoutEnv() {
        // Setup
        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setName("appName");
        applicationResponse.setId("appId");
        applicationResponse.setValidFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        applicationResponse.setDescription("description");
        applicationResponse.setContact("contact");
        applicationResponse.setAdmins(Arrays.asList("admin"));

        List<ApplicationResponse> expectedResult = Arrays.asList(applicationResponse);

        Application application1 = new Application();
        application1.setAppId("appId");
        application1.setName("appName");
        application1.setValidFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        application1.setDescription("description");
        application1.setContact("contact");
        application1.setAdmins(Arrays.asList("admin"));
        List<Application> applicationList = Arrays.asList(application1);
        when(applicationRepository.findApplicationByNameLikeIgnoreCase("name")).thenReturn(applicationList);

        // Run the test
        List<ApplicationResponse> result = applicationServiceImpl.getApplications("name", false);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetApplicationsWithoutEnv() {
        // Setup
        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setName("appName");
        applicationResponse.setId("appId");
        applicationResponse.setValidFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        applicationResponse.setDescription("description");
        applicationResponse.setContact("contact");
        applicationResponse.setAdmins(Arrays.asList("admin"));

        List<ApplicationResponse> expectedResult = Arrays.asList(applicationResponse);

        Application application1 = new Application();
        application1.setAppId("appId");
        application1.setName("appName");
        application1.setValidFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        application1.setDescription("description");
        application1.setContact("contact");
        application1.setAdmins(Arrays.asList("admin"));
        List<Application> applicationList = Arrays.asList(application1);
        when(applicationRepository.findAll()).thenReturn(applicationList);

        // Run the test
        List<ApplicationResponse> result = applicationServiceImpl.getApplications(null, false);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetApplications_ApplicationRepositoryFindAllReturnsNoItems() {
        // Setup
        when(applicationRepository.findApplicationByNameLikeIgnoreCase("name")).thenReturn(Collections.emptyList());

        when(applicationRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        List<ApplicationResponse> result = applicationServiceImpl.getApplications(null, false);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testGetApplications_ApplicationRepositoryFindByNameReturnsNoItems() {
        // Setup
        when(applicationRepository.findApplicationByNameLikeIgnoreCase("name")).thenReturn(Collections.emptyList());

        // Run the test
        List<ApplicationResponse> result = applicationServiceImpl.getApplications("name", false);

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testUpdateApplication() throws Exception {
        // Setup
        ApplicationUpdateRequest applicationUpdateRequest = new ApplicationUpdateRequest();
        applicationUpdateRequest.setName("name");
        applicationUpdateRequest.setDescription("description");
        applicationUpdateRequest.setContact("contact");
        applicationUpdateRequest.setAdmins(Arrays.asList("userId"));
        Environments environments = new Environments();
        environments.setId(0);
        environments.setName("name");
        environments.setProd(false);
        environments.setUrl("url");
        Application application = new Application();
        application.setAppId("appId");
        application.setName("name");
        application.setValidFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        application.setDescription("description");
        application.setContact("contact");
        application.setAdmins(Arrays.asList("userId"));

        Application application1 = new Application();
        application1.setAppId("appId");
        application1.setName("name");
        application1.setValidFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        application1.setDescription("description");
        application1.setContact("contact");
        application1.setAdmins(Arrays.asList("userId"));
        Environments environments1 = new Environments();
        environments1.setId(0);
        environments1.setName("name");
        environments1.setProd(false);
        environments1.setUrl("url");
        application1.setEnvironments(Arrays.asList(environments1));
        when(applicationRepository.getById("id")).thenReturn(application1);

        Application application2 = new Application();
        application2.setAppId("appId");
        application2.setName("name");
        application2.setValidFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        application2.setDescription("description");
        application2.setContact("contact");
        application2.setAdmins(Arrays.asList("userId"));
        Environments environments2 = new Environments();
        environments2.setId(0);
        environments2.setName("name");
        environments2.setProd(false);
        environments2.setUrl("url");
        application2.setEnvironments(Arrays.asList(environments2));
        when(applicationRepository.save(new Application())).thenReturn(application2);

        // Run the test
        ApplicationUpdateResponse result = applicationServiceImpl.updateApplication(applicationUpdateRequest, "id", "userId");

        // Verify the results
        assertEquals("Success", result.getStatus());
    }

    @Test
    public void testUpdateApplicationFail() throws Exception {
        // Setup
        ApplicationUpdateRequest applicationUpdateRequest = new ApplicationUpdateRequest();
        applicationUpdateRequest.setName("name");
        applicationUpdateRequest.setDescription("description");
        applicationUpdateRequest.setContact("contact");
        applicationUpdateRequest.setAdmins(Arrays.asList("userId"));
        when(applicationRepository.getById("id")).thenReturn(null);

        // Run the test
        ApplicationUpdateResponse result = applicationServiceImpl.updateApplication(applicationUpdateRequest, "id", "userId");

        // Verify the results
        assertEquals("Fail", result.getStatus());
    }

    @Test(expected = UserPrivilegeException.class)
    public void testUpdateApplication_ThrowsUserPrivilegeException() throws Exception {
        // Setup
        ApplicationUpdateRequest applicationUpdateRequest = new ApplicationUpdateRequest();
        applicationUpdateRequest.setName("name");
        applicationUpdateRequest.setDescription("description");
        applicationUpdateRequest.setContact("contact");
        applicationUpdateRequest.setAdmins(Arrays.asList("value"));

        Application application1 = new Application();
        application1.setAppId("appId");
        application1.setName("name");
        application1.setValidFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        application1.setDescription("description");
        application1.setContact("contact");
        application1.setAdmins(Arrays.asList("value"));

        when(applicationRepository.getById("id")).thenReturn(application1);

        // Run the test
        applicationServiceImpl.updateApplication(applicationUpdateRequest, "id", "userId");

    }

    @Test(expected = InvalidUserException.class)
    public void testUpdateApplication_ThrowsInvalidUserException() throws Exception {
        // Setup
        ApplicationUpdateRequest applicationUpdateRequest = new ApplicationUpdateRequest();
        applicationUpdateRequest.setName("name");
        applicationUpdateRequest.setDescription("description");
        applicationUpdateRequest.setContact("contact");
        applicationUpdateRequest.setAdmins(Arrays.asList("value"));

        // Run the test
        applicationServiceImpl.updateApplication(applicationUpdateRequest, "id", null);

    }

}
