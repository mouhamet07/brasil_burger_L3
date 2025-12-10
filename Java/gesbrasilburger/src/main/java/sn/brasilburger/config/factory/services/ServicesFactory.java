package sn.brasilburger.config.factory.services;

import  sn.brasilburger.config.factory.EntityName;
import  sn.brasilburger.config.factory.repository.RepositoryFactory;
import  sn.brasilburger.repository.*;
import  sn.brasilburger.services.Impl.*;

public final  class ServicesFactory {
    private ServicesFactory(){}
    public static Object createServices(EntityName entity){
        switch (entity) {
            case BURGER:
                BurgerRepository burgerRepo = (BurgerRepository)RepositoryFactory.createRepository(entity);
                return BurgerServiceImpl.getInstance(burgerRepo);
            case COMPLEMENT:
                ComplementRepository complementRepo = (ComplementRepository)RepositoryFactory.createRepository(entity);
                return ComplementServiceImpl.getInstance(complementRepo);
            case LIVREUR:
                LivreurRepository livreurRepo = (LivreurRepository)RepositoryFactory.createRepository(entity);
                return LivreurServiceImpl.getInstance(livreurRepo);
            case LOGIN:
                LoginRepository loginRepo = (LoginRepository)RepositoryFactory.createRepository(entity);
                return LoginServiceImpl.getInstance(loginRepo);
            case MENU:
                MenuRepository menuRepo = (MenuRepository)RepositoryFactory.createRepository(entity);
                return MenuServiceImpl.getInstance(menuRepo);
            case ZONE:
                ZoneRepository zoneRepo = (ZoneRepository)RepositoryFactory.createRepository(entity);
                return ZoneServiceImpl.getInstance(zoneRepo);
            case MENU_COMPLEMENT:
                MenuComplementRepository menuComplementRepo = (MenuComplementRepository)RepositoryFactory.createRepository(entity);
                return MenuComplementServiceImpl.getInstance(menuComplementRepo);
            default:
            throw new IllegalArgumentException("Unknow entity: "+ entity);
        }
    }
}

