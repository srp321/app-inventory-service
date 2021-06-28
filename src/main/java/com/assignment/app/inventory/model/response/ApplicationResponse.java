package com.assignment.app.inventory.model.response;

import com.assignment.app.inventory.entity.Environments;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApplicationResponse {

    private String name;
    private String id;
    private LocalDateTime validFrom;
    private String description;
    private String contact;
    private List<String> admins;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Environments> environments;

}
