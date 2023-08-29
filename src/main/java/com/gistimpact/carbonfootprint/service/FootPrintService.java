package com.gistimpact.carbonfootprint.service;

import com.gistimpact.carbonfootprint.dto.ApportionedImpact;
import com.gistimpact.carbonfootprint.dto.CompanyInput;
import com.gistimpact.carbonfootprint.dto.PortfolioFootprint;
import com.gistimpact.carbonfootprint.models.Company;
import com.gistimpact.carbonfootprint.models.ImpactData;
import com.gistimpact.carbonfootprint.models.SortByYear;
import com.gistimpact.carbonfootprint.repository.CompanyRepository;
import com.gistimpact.carbonfootprint.repository.ImpactDataRepository;
import com.gistimpact.carbonfootprint.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FootPrintService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private SectorRepository sectorRepository;
    @Autowired
    private ImpactDataRepository impactDataRepository;

    // Returns the carbon footprint at company level
    public List<ApportionedImpact> apportionedImpacts(CompanyInput companyInput) {
        List<ApportionedImpact> apportionedImpactList=new ArrayList<>();
        Company company=companyRepository.findByCompanyName(companyInput.getCompanyName());

        List<ImpactData> impactDataList=impactDataRepository.findAllByCompany(company);
        for(ImpactData impactData:impactDataList){
            double footPrint=companyInput.getInvestmentAmount()/impactData.getMarketCapitalization()*impactData.getGhgImpactUsd();
            apportionedImpactList.add(new ApportionedImpact(company.getCompanyName(),company.getSectorInfo().getSectorName(),impactData.getReportingYear(),footPrint));
        }
        Collections.sort(apportionedImpactList,new SortByYear());
        return apportionedImpactList;
    }

    // Returns the portfolio footprint for years 2019, 2020, 2021
    public ResponseEntity<List<PortfolioFootprint>> calculatePortfolioImpact(List<CompanyInput> portfoiloList) {
        List<List<ApportionedImpact>> apportionedImpactsList=new ArrayList<>();
        double totalInvestment=0.0;
        for(CompanyInput companyInput:portfoiloList){
            totalInvestment+=companyInput.getInvestmentAmount();
            apportionedImpactsList.add(apportionedImpacts(companyInput));
        }
        System.out.println("totalInvestment = " + totalInvestment);
        List<Double> totalCarbonFootprint;
        double carbonFootprint1=0.0;
        double carbonFootprint2=0.0;
        double carbonFootprint3=0.0;
        for(List<ApportionedImpact> apportionedImpacts:apportionedImpactsList){
            for(ApportionedImpact apportionedImpact:apportionedImpacts){
                if(apportionedImpact.getReportingYear()==2019)
                    carbonFootprint1+=apportionedImpact.getCarbonFootprint();
                else if (apportionedImpact.getReportingYear()==2020) {
                    carbonFootprint2+=apportionedImpact.getCarbonFootprint();
                } else if (apportionedImpact.getReportingYear()==2021) {
                    carbonFootprint3+=apportionedImpact.getCarbonFootprint();
                }
            }
        }
        List<PortfolioFootprint> portfolioFootprints=new ArrayList<>();
        portfolioFootprints.add(new PortfolioFootprint(2019,carbonFootprint1/totalInvestment*1e6,carbonFootprint1));
        portfolioFootprints.add(new PortfolioFootprint(2020,carbonFootprint2/totalInvestment*1e6,carbonFootprint2));
        portfolioFootprints.add(new PortfolioFootprint(2021,carbonFootprint3/totalInvestment*1e6,carbonFootprint3));
        System.out.println("portfolioFootprints = " + portfolioFootprints);
//        apportionedImpactsList.forEach(apportionedImpacts -> {
//
//            apportionedImpacts.forEach(apportionedImpact -> {
//                if(apportionedImpact.getReportingYear()==2019)
//                    carbonFootprint1+=apportionedImpact.getCarbonFootprint();
//            });
//        });
        return new ResponseEntity<>(portfolioFootprints,HttpStatus.OK);
    }
}

