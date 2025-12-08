package sn.brasilburger.entity;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Menu {
    private int id;
    private String nom;
    private String image;
    private Boolean etat = true;
    private Double montant;
    private Set<Complement> complements;
}
