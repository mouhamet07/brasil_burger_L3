package sn.brasilburger.repository;

import java.util.Collection;
import java.util.Optional;

import sn.brasilburger.entity.Menu;

public interface MenuRepository {
    Collection<Menu> findAll();
    Optional<Menu> findById(int id);
    boolean insert(Menu menu);
    boolean update(Menu menu);
    boolean delete(Menu menu);
}
