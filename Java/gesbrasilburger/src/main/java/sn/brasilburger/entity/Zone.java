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
public class Zone {
    private int id;
    private String nom;
    private Double prixLivraison;
    private boolean etat = true;
}
