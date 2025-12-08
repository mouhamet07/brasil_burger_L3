package sn.brasilburger.repository;

import java.util.Collection;
import java.util.Optional;

import sn.brasilburger.entity.*;

public interface ComplementRepository {
    Collection<Complement> findAll();
    Optional<Complement> findById(int id);
    boolean insert(Complement complement);
    boolean update(Complement complement);
    boolean delete(Complement complement);
}
