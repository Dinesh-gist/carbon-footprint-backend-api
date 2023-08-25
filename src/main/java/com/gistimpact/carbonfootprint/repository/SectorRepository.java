package com.gistimpact.carbonfootprint.repository;

import com.gistimpact.carbonfootprint.models.SectorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SectorRepository extends JpaRepository<SectorInfo, UUID> {

}
