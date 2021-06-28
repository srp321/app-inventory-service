package com.assignment.app.inventory.controller;

import com.assignment.app.inventory.entity.Application;
import com.assignment.app.inventory.entity.Environments;
import com.assignment.app.inventory.model.request.ApplicationUpdateRequest;
import com.assignment.app.inventory.model.response.ApplicationResponse;
import com.assignment.app.inventory.model.response.ApplicationUpdateResponse;
import com.assignment.app.inventory.service.ApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationControllerTest {

    MockMvc mockMvc;

    @Mock
    ApplicationService applicationService;

    @InjectMocks
    ApplicationController applicationController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(applicationController).build();
    }

    @Test
    public void testCreateApplication() throws Exception {
        // Setup
        Application application = new Application();
        application.setName("appName");
        application.setValidFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        application.setDescription("description");
        application.setContact("contact@contact.com");
        application.setAdmins(Arrays.asList("admin"));

        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setName("appName");
        applicationResponse.setId("appId");
        applicationResponse.setValidFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        applicationResponse.setDescription("description");
        applicationResponse.setContact("contact@contact.com");
        applicationResponse.setAdmins(Arrays.asList("admin"));

        when(applicationService.createApplication(application)).thenReturn(applicationResponse);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/v1/applications")
                                                        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
                                                        .characterEncoding("UTF-8").content(mapper.writeValueAsString(applicationResponse));

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("appName")))
                .andExpect(jsonPath("$.id", is("appId")));

    }

    @Test
    public void testCreateApplicationErrorResponse() throws Exception {
        // Setup
        Application application = new Application();
        application.setName("appName");
        application.setValidFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        application.setDescription("description");
        application.setContact("contact");
        application.setAdmins(Arrays.asList("admin"));

        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setName("appName");
        applicationResponse.setId("appId");
        applicationResponse.setValidFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        applicationResponse.setDescription("description");
        applicationResponse.setContact("contact");
        applicationResponse.setAdmins(Arrays.asList("admin"));

        when(applicationService.createApplication(application)).thenReturn(applicationResponse);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/v1/applications")
                                                        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
                                                        .characterEncoding("UTF-8").content(mapper.writeValueAsString(applicationResponse));

        mockMvc.perform(builder)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetApplications() throws Exception {
        // Setup
        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setName("appName");
        applicationResponse.setId("appId");
        applicationResponse.setValidFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        applicationResponse.setDescription("description");
        applicationResponse.setContact("contact@contact.com");
        applicationResponse.setAdmins(Arrays.asList("admin"));
        List<ApplicationResponse> applicationResponseList = Arrays.asList(applicationResponse);

        when(applicationService.getApplications("appName", false)).thenReturn(applicationResponseList);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/v1/applications")
                                                        .param("name", "appName")
                                                        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
                                                        .characterEncoding("UTF-8").content(mapper.writeValueAsString(applicationResponseList));

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name", is("appName")))
                .andExpect(jsonPath("$.[0].id", is("appId")));
    }

    @Test
    public void testGetApplicationsWithEnv() throws Exception {
        // Setup
        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setName("appName");
        applicationResponse.setId("appId");
        applicationResponse.setValidFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0));
        applicationResponse.setDescription("description");
        applicationResponse.setContact("contact@contact.com");
        applicationResponse.setAdmins(Arrays.asList("admin"));
        Environments environments = new Environments();
        environments.setName("envName");
        environments.setProd(true);
        environments.setUrl("abc.xyz.com");
        applicationResponse.setEnvironments(Arrays.asList(environments));
        List<ApplicationResponse> applicationResponseList = Arrays.asList(applicationResponse);

        when(applicationService.getApplications("appName", true)).thenReturn(applicationResponseList);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/v1/applications")
                                                        .param("name", "appName")
                                                        .param("env", "true")
                                                        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
                                                        .characterEncoding("UTF-8").content(mapper.writeValueAsString(applicationResponseList));

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name", is("appName")))
                .andExpect(jsonPath("$.[0].id", is("appId")))
                .andExpect(jsonPath("$.[0].environments[0].name", is("envName")));
    }

    @Test
    public void testUpdateApplication() throws Exception {
        // Setup
        ApplicationUpdateResponse applicationUpdateResponse = new ApplicationUpdateResponse("Success");

        ApplicationUpdateRequest applicationUpdateRequest = new ApplicationUpdateRequest();
        applicationUpdateRequest.setDescription("new description");

        when(applicationService.updateApplication(applicationUpdateRequest, "appId", "userId")).thenReturn(applicationUpdateResponse);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/v1/applications/app1")
                                                        .header("userId", "userId")
                                                        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
                                                        .characterEncoding("UTF-8").content(mapper.writeValueAsString(applicationUpdateResponse));

        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }
}
