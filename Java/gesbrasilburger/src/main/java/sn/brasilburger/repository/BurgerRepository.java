package sn.brasilburger.repository;
import java.util.Collection;
import java.util.Optional;

import sn.brasilburger.entity.*;
public interface BurgerRepository {
    Collection<Burger> findAll();
    Optional<Burger> findById(int id);
    boolean insert(Burger burger);
    boolean update(Burger burger);
    boolean delete(Burger burger);
}
