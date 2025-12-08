package sn.brasilburger.repository;

import java.util.Collection;
import java.util.Optional;

import sn.brasilburger.entity.Livreur;

public interface LivreurRepository {
    Collection<Livreur> findAll();
    Optional<Livreur> findById(int id);
    boolean insert(Livreur livreur);
    boolean update(Livreur livreur);
    boolean delete(Livreur livreur);
}
