package com.assignment.app.inventory.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@DynamicUpdate
public class Environments implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 10)
    private int id;
    private String name;
    private boolean isProd;
    private String url;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "app_id", insertable = false, updatable = false)
    private Application application;

}
