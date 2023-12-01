package com.gistimpact.carbonfootprint.service;

import com.gistimpact.carbonfootprint.dto.ApportionedImpact;
import com.gistimpact.carbonfootprint.dto.CompanyInput;
import com.gistimpact.carbonfootprint.dto.PortfolioFootprint;
import com.gistimpact.carbonfootprint.exception.CompanyNotFoundException;
import com.gistimpact.carbonfootprint.exception.ImpactDataNotfound;
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

import java.util.*;

@Service
public class FootPrintService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private SectorRepository sectorRepository;
    @Autowired
    private ImpactDataRepository impactDataRepository;

    // Returns the carbon footprint at company level
    public ResponseEntity<List<ApportionedImpact>> apportionedImpacts(CompanyInput companyInput) throws CompanyNotFoundException, ImpactDataNotfound {

        Set<ApportionedImpact> apportionedImpactList=new HashSet<>();
        Company company=companyRepository.findByCompanyName(companyInput.getCompanyName());
        if(company!=null) {
            List<ImpactData> impactDataList = impactDataRepository.findAllByCompany(company);
            if(!impactDataList.isEmpty()) {
                for (ImpactData impactData : impactDataList) {
                    double footPrint = companyInput.getInvestmentAmount() / impactData.getMarketCapitalization() * impactData.getGhgImpactUsd() / 1e6;
                    apportionedImpactList.add(new ApportionedImpact(company.getCompanyName(), company.getSectorInfo().getSectorName(), impactData.getReportingYear(), footPrint));
                }
                List<ApportionedImpact> apportionedImpacts = new ArrayList<>(apportionedImpactList);
                Collections.sort(apportionedImpacts, new SortByYear());
                return ResponseEntity.ok(apportionedImpacts);
            }
            else{
                throw new ImpactDataNotfound("Company with company name: "+companyInput.getCompanyName()+" has no impact data");
            }
        }
        else {
            throw new CompanyNotFoundException("Company with company name: "+companyInput.getCompanyName()+" not found");
        }
    }


    public ResponseEntity<List<PortfolioFootprint>> calculatePortfolioImpact(List<CompanyInput> portfolioList) throws CompanyNotFoundException, ImpactDataNotfound {
        Map<Integer, Double> yearlyFootprints = new HashMap<>();
        double totalInvestment = 0.0;
        Map<Integer, Double> benchMark=new HashMap<>();
        List<Double> benchMarkGhg=Arrays.asList(38322702900.0,34405284430.00,33998961270.0,36415744330.0);
        List<Double> benchMarkMarketCapMillionUsd=Arrays.asList(23067428.095454957,28349767.01569506,31914785.90541899,37246961.267511);
        for (CompanyInput companyInput : portfolioList) {
            totalInvestment += companyInput.getInvestmentAmount();
            List<ApportionedImpact> apportionedImpacts = apportionedImpacts(companyInput).getBody();

            for (ApportionedImpact apportionedImpact : apportionedImpacts) {
                yearlyFootprints.put(apportionedImpact.getReportingYear(), yearlyFootprints.getOrDefault(apportionedImpact.getReportingYear(), 0.0) + apportionedImpact.getCarbonFootprint());
            }
        }
        benchMark.put(2018,benchMarkGhg.get(0)/benchMarkMarketCapMillionUsd.get(0)*totalInvestment/1e6);
        benchMark.put(2019,benchMarkGhg.get(1)/benchMarkMarketCapMillionUsd.get(1)*totalInvestment/1e6);
        benchMark.put(2020,benchMarkGhg.get(2)/benchMarkMarketCapMillionUsd.get(2)*totalInvestment/1e6);
        benchMark.put(2021,benchMarkGhg.get(3)/benchMarkMarketCapMillionUsd.get(3)*totalInvestment/1e6);
        List<PortfolioFootprint> portfolioFootprints = new ArrayList<>();

        for (Map.Entry<Integer, Double> entry : yearlyFootprints.entrySet()) {
            int year = entry.getKey();
            double carbonFootprint = entry.getValue();
            double carbonFootprintPerMillion = carbonFootprint / totalInvestment* 1e6;

            portfolioFootprints.add(new PortfolioFootprint(year, carbonFootprintPerMillion, carbonFootprint,benchMark.get(year),(1-carbonFootprint/benchMark.get(year))*100));
        }

        return new ResponseEntity<>(portfolioFootprints, HttpStatus.OK);
    }
}

