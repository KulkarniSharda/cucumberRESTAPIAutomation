package com.agileautomation.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CreateUserResponsePojo {


    private String name;
    private String job;
    private String id;
    private String createdAts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAts() {
        return createdAts;
    }

    public void setCreatedAts(String createdAts) {
        this.createdAts = createdAts;
    }
}

