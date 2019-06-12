package com.JPATest.data;

import com.JPATest.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Performer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name must be not empty!")
    private  String firstName;

    @NotBlank(message = "Last name must be not empty!")
    private  String lastName;

    @NotBlank(message = "Middle name must be not empty!")
    private  String middleName;

    @NotNull(message = "Gender must be not empty!")
    private Gender gender;

    @NotNull(message = "Age must be not empty!")
    private Integer age;

    @NotBlank(message = "Role name must be not empty!")
    private String role;

    @ManyToOne
    private Group group;
}
