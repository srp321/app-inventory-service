package com.assignment.app.inventory.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@DynamicUpdate
public class Application implements Serializable {

    @Id
    @GenericGenerator(name = "app_seq_id", strategy = "com.assignment.app.inventory.utility.AppIdGenerator")
    @GeneratedValue(generator = "app_seq_id")
    @Column(name = "app_id", length = 10)
    private String appId;

    @NotBlank(message = "Application Name is Mandatory")
    @Column(nullable = false)
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime validFrom;

    private String description;

    @NotBlank(message = "Contact Email is Mandatory")
    @Email(message = "Contact Email should be Valid")
    @Column(nullable = false)
    private String contact;

    @NotNull(message = "Admin Field is Mandatory")
    @Column(nullable = false)
    @ElementCollection
    private List<String> admins;

    @OneToMany(targetEntity = Environments.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id")
    private List<Environments> environments;

}
