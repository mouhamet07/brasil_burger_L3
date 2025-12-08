package sn.brasilburger.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Setter
@AllArgsConstructor
public class Burger {
    private int id;
    private String nom;
    private Double prix;
    private String image;
    private Boolean etat = true;
}
