package com.JPATest.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Composition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must be not empty!")
    private  String name;

    @NotNull(message = "Duration must be not empty!")
    @Min(value = 0, message = "Duration must be above zero!")
    private Double duration;

    @ManyToOne
    private Album album;
}
