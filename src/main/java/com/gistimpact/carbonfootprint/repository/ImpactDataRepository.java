package com.gistimpact.carbonfootprint.repository;

import com.gistimpact.carbonfootprint.models.Company;
import com.gistimpact.carbonfootprint.models.ImpactData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImpactDataRepository extends JpaRepository<ImpactData, UUID> {
    //List<ImpactData> findAllByCompanyId(UUID guid);

    //List<ImpactData> findAllByCompany(Company company);

    //List<ImpactData> findAllByCompanyId(int id);

    List<ImpactData> findAllByCompany(Company company);

    //List<ImpactData> findAllByCompanyCompanyName(Company company);

    //List<ImpactData> findAllByCompanyId(int id);

    //List<ImpactData> findByCompany(Company company);
}
