package sn.brasilburger.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Complement {
    private int id;
    private String nom;
    private Double prix;
    private String image;
    private Boolean etat = true;
    private CategorieComplement categorie;
}
