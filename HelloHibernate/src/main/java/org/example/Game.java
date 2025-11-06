package org.example;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
@Entity
@Table(name="games")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String platform;
    private Integer year;
    private String description;
    private Integer user_id;
    private String image_url;


    // Si los nombres de la clase no son los mismo que los de la tabla
    //@Column(name="title")
   //private String title;
}
