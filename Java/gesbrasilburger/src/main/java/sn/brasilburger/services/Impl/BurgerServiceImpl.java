package sn.brasilburger.services.Impl;

import java.util.Collection;
import java.util.Optional;

import sn.brasilburger.entity.Burger;
import sn.brasilburger.repository.BurgerRepository;
import sn.brasilburger.services.BurgerService;

public class BurgerServiceImpl implements BurgerService{
    private BurgerRepository burgerRepository;
    private static BurgerServiceImpl instance = null;
    private BurgerServiceImpl(BurgerRepository burgerRepository){
        this.burgerRepository = burgerRepository;
    }
    public static BurgerServiceImpl getInstance(BurgerRepository burgerRepository){
        if (instance==null) {
            return instance = new BurgerServiceImpl(burgerRepository);
        }
        return instance;
    }
    public Collection<Burger> getAllBurger(){
        return burgerRepository.findAll();
    }
    public Optional<Burger> getBurgerById(int id){
        return burgerRepository.findById(id);
    }
    public boolean createBurger(Burger burger){
        return burgerRepository.insert(burger);
    }
    public boolean updateBurger(Burger burger){
        return burgerRepository.update(burger);
    }
    public boolean archiveBurger(Burger burger){
        return burgerRepository.delete(burger);
    }
}
