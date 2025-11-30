package org.example.user;





import jakarta.persistence.*;
import lombok.Data;
import org.example.game.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String password;

    @Column(name="is_admin")
    private Boolean isAdmin;

    // RELACIÓN UNO A MUCHOS: Un usuario tiene MUCHOS juegos.
    // cascade = ALL: Si borro al usuario, borra sus juegos automáticamente.
    // fetch = EAGER: Cuando cargues al usuario de la BD, tráeme INMEDIATAMENTE su lista de juegos (no esperes a que te la pida).
    // mappedBy = "user": "La otra clase (Game) es la dueña de la relación, mira el campo 'user' allí".
    @OneToMany(cascade={CascadeType.ALL}, mappedBy = "user", fetch = FetchType.EAGER)
    private List<Game> games = new ArrayList<>();

    // Método auxiliar para mantener la coherencia en Java:
    // Al añadir un juego a la lista, le decimos al juego "tu dueño soy yo".
    public void addGame(Game g){
        g.setUser(this);
        this.games.add(g);
    }
}