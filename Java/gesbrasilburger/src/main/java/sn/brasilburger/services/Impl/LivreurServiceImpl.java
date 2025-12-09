package sn.brasilburger.services.Impl;

import java.util.List;
import java.util.Optional;

import sn.brasilburger.entity.Livreur;
import sn.brasilburger.repository.LivreurRepository;
import sn.brasilburger.services.LivreurService;

public class LivreurServiceImpl implements LivreurService{
    private LivreurRepository livreurRepository;
    private static LivreurServiceImpl instance = null;
    private LivreurServiceImpl(LivreurRepository livreurRepository){
        this.livreurRepository = livreurRepository;
    }
    public static LivreurServiceImpl getInstance(LivreurRepository livreurRepository){
        if (instance==null) {
            return instance = new LivreurServiceImpl(livreurRepository);
        }
        return instance;
    }
    public List<Livreur> getAllLivreur(){
        return livreurRepository.findAll();
    }
    public Optional<Livreur> getLivreurById(int id){
        return livreurRepository.findById(id);
    }
    public boolean createLivreur(Livreur livreur){
        return livreurRepository.insert(livreur);
    }
    public boolean updateLivreur(Livreur livreur){
        return livreurRepository.update(livreur);
    }
    public boolean archiveLivreur(Livreur livreur){
        return livreurRepository.delete(livreur);
    }
}
