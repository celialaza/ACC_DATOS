package org.example.gestorjuegosfx.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game implements Serializable {
    private Integer id;
    private String title;
    private String platform;
    private Integer year;
    private String description;
    private Integer user_id;
    private String image_url;
}
