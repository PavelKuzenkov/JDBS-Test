package com.JPATest.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must be not empty!")
    private  String name;

    @NotBlank(message = "Country must be not empty!")
    private  String country;

    @NotBlank(message = "Genre must be not empty!")
    private  String genre;

    @OneToMany
//            (mappedBy = "group", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Performer> performers;

    @OneToMany
//            (mappedBy = "group", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Album> albums;
}

