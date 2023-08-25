package com.gistimpact.carbonfootprint.service;

import com.gistimpact.carbonfootprint.dto.ApportionedImpact;
import com.gistimpact.carbonfootprint.dto.CompanyInput;
import com.gistimpact.carbonfootprint.models.Company;
import com.gistimpact.carbonfootprint.models.ImpactData;
import com.gistimpact.carbonfootprint.repository.CompanyRepository;
import com.gistimpact.carbonfootprint.repository.ImpactDataRepository;
import com.gistimpact.carbonfootprint.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FootPrintService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private SectorRepository sectorRepository;
    @Autowired
    private ImpactDataRepository impactDataRepository;
    public ResponseEntity<List<ApportionedImpact>> apportionedImpacts(CompanyInput companyInput) {
        List<ApportionedImpact> apportionedImpactList=new ArrayList<>();
        Company company=companyRepository.findByCompanyName(companyInput.getCompanyName());

        List<ImpactData> impactDataList=impactDataRepository.findAllByCompany(company);
//        impactDataList.stream()
//                .map(impactData ->companyInput.getInvestmentAmount()/impactData.getMarketCapitalization()*impactData.getGhgImpactUsd())
//                .forEach(impactData->System.out.println(impactData));
              //  .forEach(aportionedData->apportionedImpactList.add(new ApportionedImpact(company.getCompanyName(),company.getSectorInfo().getSectorName(),impactData.getReportingYear(),footPrint)));
        for(ImpactData impactData:impactDataList){
            double footPrint=companyInput.getInvestmentAmount()/impactData.getMarketCapitalization()*impactData.getGhgImpactUsd();
            apportionedImpactList.add(new ApportionedImpact(company.getCompanyName(),company.getSectorInfo().getSectorName(),impactData.getReportingYear(),footPrint));
        }
        return new ResponseEntity<>(apportionedImpactList, HttpStatus.OK);
    }
}

