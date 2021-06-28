package com.assignment.app.inventory.model.request;

import com.assignment.app.inventory.entity.Environments;
import lombok.Data;

import java.util.List;

@Data
public class ApplicationUpdateRequest {

    private String name;
    private String description;
    private String contact;
    private List<String> admins;
    private List<Environments> environments;

}
