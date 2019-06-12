package com.JPATest.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must be not empty!")
    private  String name;

    @OneToMany
//            (mappedBy = "album", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Composition> compositions;

    @ManyToOne
    private Group group;

    @NotNull(message = "Year must be not empty!")
    @PastOrPresent(message = "Date must be a date in the past or in the present!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date = LocalDate.now();
}