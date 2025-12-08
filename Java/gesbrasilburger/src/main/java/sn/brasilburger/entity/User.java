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
public class User {
    private int id;
    private String nomComplet;
    private String telephone;
    private String email;
    private String password;
    private RoleUser role;
    private boolean etat = true;
}
