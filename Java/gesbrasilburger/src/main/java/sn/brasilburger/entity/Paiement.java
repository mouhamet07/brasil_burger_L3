package sn.brasilburger.entity;

import java.time.LocalDateTime;

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

public class Paiement {
    private Integer id;
    private LocalDateTime datePaiement;
    private Double montant;
    private ModePaiement mode;
    private Commande commande;
}