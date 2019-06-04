package com.myApplication.data;

import javax.validation.constraints.NotBlank;

public class Organization {

    private Long id;

    @NotBlank(message = "Name must be not empty!")
    private String name;

    public Organization() {

    }

    public Organization(Long id, @NotBlank(message = "Name must be not empty!") String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
