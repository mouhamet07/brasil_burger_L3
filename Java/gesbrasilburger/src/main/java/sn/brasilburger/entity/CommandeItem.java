package sn.brasilburger.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommandeItem {
    private int id;
    private Commande commande;
    private TypeCommandeItem type;
    private int produitId; 
    private int quantite;
    private Double prixUnitaire;
}
