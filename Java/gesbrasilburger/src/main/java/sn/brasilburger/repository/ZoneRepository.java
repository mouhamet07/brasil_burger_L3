package sn.brasilburger.repository;
import  sn.brasilburger.entity.*;
import java.util.Collection;
import java.util.Optional;

public interface ZoneRepository {
    Collection<Zone> findAll();
    Optional<Zone> findById(int id);
    boolean insert(Zone zone);
    boolean update(Zone zone);
    boolean delete(Zone zone);
}
