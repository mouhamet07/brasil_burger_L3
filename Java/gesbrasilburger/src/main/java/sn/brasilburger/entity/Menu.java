package sn.brasilburger.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Menu {
    private int id;
    private String nom;
    private String image;
    private Boolean etat = true;
    private Double montant;
    private List<Complement> complements;
    @Override
    public String toString() {
    return "Menu [ID: " + id +", Nom: " + nom +", Image: " + image +", Montant: " + montant +", Etat: " + etat +"]";
}
}
