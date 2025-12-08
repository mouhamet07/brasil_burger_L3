package sn.brasilburger.services;

import java.util.Collection;
import java.util.Optional;

import sn.brasilburger.entity.Menu;

public interface MenuService {
    Collection<Menu> getAllMenu();
    Optional<Menu> getMenuById(int id);
    boolean createMenu(Menu menu);
    boolean updateMenu(Menu menu);
    boolean archiveMenu(Menu menu);
}
