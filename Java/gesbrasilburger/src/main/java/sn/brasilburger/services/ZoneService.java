package sn.brasilburger.services;

import java.util.Collection;
import java.util.Optional;

import sn.brasilburger.entity.Zone;

public interface ZoneService {
    Collection<Zone> getAllZone();
    Optional<Zone> getZoneById(int id);
    boolean createZone(Zone zone);
    boolean updateZone(Zone zone);
    boolean archiveZone(Zone zone);
}
