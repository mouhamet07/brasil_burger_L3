package sn.brasilburger.services.Impl;

import java.util.List;
import java.util.Optional;

import sn.brasilburger.entity.Zone;
import sn.brasilburger.repository.ZoneRepository;
import sn.brasilburger.services.ZoneService;

public class ZoneServiceImpl implements ZoneService{
    private ZoneRepository zoneRepository;
    private static ZoneServiceImpl instance = null;
    private ZoneServiceImpl(ZoneRepository zoneRepository){
        this.zoneRepository = zoneRepository;
    }
    public static ZoneServiceImpl getInstance(ZoneRepository zoneRepository){
        if (instance==null) {
            return instance = new ZoneServiceImpl(zoneRepository);
        }
        return instance;
    }
    public List<Zone> getAllZone(){
        return zoneRepository.findAll();
    }
    public Optional<Zone> getZoneById(int id){
        return zoneRepository.findById(id);
    }
    public boolean createZone(Zone zone){
        return zoneRepository.insert(zone);
    }
    public boolean updateZone(Zone zone){
        return zoneRepository.update(zone);
    }
    public boolean archiveZone(Zone zone){
        return zoneRepository.delete(zone);
    }
}
