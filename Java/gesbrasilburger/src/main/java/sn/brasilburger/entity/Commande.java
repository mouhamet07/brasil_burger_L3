package sn.brasilburger.entity;

import java.time.LocalDateTime;
import java.util.List;

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
public class Commande {
    private int id;
    private LocalDateTime dateCommande;
    private EtatCommande etat;
    private TypeCommande type;
    private Double montantTotal;
    private User client;
    private Zone zone;
    private Livreur livreur;
    private List<CommandeItem> items;
    private Paiement paiement;
}
