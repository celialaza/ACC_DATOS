package org.example.game;


import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.user.User;


import java.io.Serializable;

@Entity
@Table(name="games")

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Game implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String platform;
    private Integer year;
    private String description;

    // RELACIÓN MUCHOS A UNO: Muchos juegos pueden pertenecer a UN usuario.
    @ManyToOne
    @JoinColumn(name="user_id")// HIBERNATE: "Crea una columna llamada 'user_id' que será la CLAVE FORÁNEA para conectar con la tabla User".
    private User user;

    private String image_url;

    // toString personalizado para evitar bucles infinitos al imprimir (Usuario imprime juegos -> Juego imprime usuario -> Usuario imprime juegos...)
    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", platform='" + platform + '\'' +
                ", year=" + year +
                ", description='" + description + '\'' +
                ", user=" + user.getEmail() + //Solo se imprime el email
                ", image_url='" + image_url + '\'' +
                '}';
    }
}