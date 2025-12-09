package sn.brasilburger.services;

import java.util.List;
import java.util.Optional;

import sn.brasilburger.entity.Burger;

public interface BurgerService {
    List<Burger> getAllBurger();
    Optional<Burger> getBurgerById(int id);
    boolean createBurger(Burger burger);
    boolean updateBurger(Burger burger);
    boolean archiveBurger(Burger burger);
}
