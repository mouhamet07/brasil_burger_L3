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
public class Livreur {
    private int id;
    private String nomComplet;
    private String telephone;
    private Zone zone;
    private boolean etat = true ;
}
