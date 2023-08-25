package com.gistimpact.carbonfootprint;

import com.gistimpact.carbonfootprint.models.Company;
import com.gistimpact.carbonfootprint.models.ImpactData;
import com.gistimpact.carbonfootprint.models.SectorInfo;
import com.gistimpact.carbonfootprint.repository.CompanyRepository;
import com.gistimpact.carbonfootprint.repository.ImpactDataRepository;
import com.gistimpact.carbonfootprint.repository.SectorRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class CarbonfootprintApplication{
	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	ImpactDataRepository impactDataRepository;
	@Autowired
	SectorRepository sectorRepository;

	public static void main(String[] args) {

		SpringApplication.run(CarbonfootprintApplication.class, args);

	}
//	@Override
//	public void run(String... args) throws Exception {
//		SectorInfo sectorInfo=new SectorInfo();
//		sectorInfo.setSectorCode("AAMA");
//		sectorInfo.setGuid(UUID.fromString("9a9998bf-98f0-4ace-a6fc-0f12d25e7a04"));
//		//sectorInfo.setId(1);
//		sectorInfo.setSectorName("Alumina and Aluminum Production");
//
//		Company company = new Company();
//		company.setCompanyName("Aluminum Corp. of China Ltd.");
//		company.setSectorInfo(sectorInfo);
//		company.setGuid(UUID.fromString("95ddf29e-4666-45ea-88ec-4bc1af2fd8c8"));
//		//company.setId(1);
//
//		ImpactData impactData=new ImpactData();
//		impactData.setGuid(UUID.fromString("f34f85a5-5d01-4912-a800-7feae2990695"));
//		//impactData.setId(1);
//		//impactData.setCompany(company);
//		impactData.setGhgImpactUsd(53307.3);
//		impactData.setMarketCapitalization(5000000);
//		impactData.setCompany(company);
//		impactData.setReportingYear(2019);
//
//		List<ImpactData> impactDataList=new ArrayList<>();
//		impactDataList.add(impactData);
//		company.setImpactDataList(impactDataList);
//		companyRepository.save(company);
//		List<Company> companyList=new ArrayList<>();
//		companyList.add(company);
//		sectorInfo.setCompanies(companyList);
//		sectorRepository.save(sectorInfo);
//
//
//
//
//
//
//	}


}
