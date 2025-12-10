package sn.brasilburger.services.Impl;

import java.util.List;
import java.util.Optional;

import sn.brasilburger.entity.Complement;
import sn.brasilburger.repository.ComplementRepository;
import sn.brasilburger.services.ComplementService;

public class ComplementServiceImpl implements ComplementService{
    private ComplementRepository complementRepository;
    private static ComplementServiceImpl instance = null;
    private ComplementServiceImpl(ComplementRepository complementRepository){
        this.complementRepository = complementRepository;
    }
    public static ComplementServiceImpl getInstance(ComplementRepository complementRepository){
        if (instance==null) {
            return instance = new ComplementServiceImpl(complementRepository);
        }
        return instance;
    }
    public List<Complement> getAllComplement(){
        return complementRepository.findAll();
    }
    public Optional<Complement> getComplementById(int id){
        return complementRepository.findById(id);
    }
    public boolean createComplement(Complement complement){
        return complementRepository.insert(complement);
    }
    public boolean updateComplement(Complement complement){
        return complementRepository.update(complement);
    }
    public boolean archiveComplement(Complement complement){
        return complementRepository.delete(complement);
    }
    public List<Complement> getComplementsByMenu(int idMenu){
        return complementRepository.getComplementsByMenu(idMenu);
    }
}
