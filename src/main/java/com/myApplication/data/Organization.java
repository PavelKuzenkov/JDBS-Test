package com.myApplication.data;

import javax.validation.constraints.NotBlank;

public class Organization {

    private Long id;

    @NotBlank(message = "Name must be not empty!")
    private String name;

}
