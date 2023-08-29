package com.gistimpact.carbonfootprint.repository;

import com.gistimpact.carbonfootprint.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CompanyListRepository extends JpaRepository<Company, UUID> {
}