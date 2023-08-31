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
    public List<ApportionedImpact> apportionedImpacts(CompanyInput companyInput) {

        Set<ApportionedImpact> apportionedImpactList=new HashSet<>();
        Company company=companyRepository.findByCompanyName(companyInput.getCompanyName());

        List<ImpactData> impactDataList=impactDataRepository.findAllByCompany(company);
        for(ImpactData impactData:impactDataList){
            double footPrint=companyInput.getInvestmentAmount()/impactData.getMarketCapitalization()*impactData.getGhgImpactUsd()/1e6;
            apportionedImpactList.add(new ApportionedImpact(company.getCompanyName(),company.getSectorInfo().getSectorName(),impactData.getReportingYear(),footPrint));
        }
        List<ApportionedImpact> apportionedImpacts=new ArrayList<>(apportionedImpactList);
        Collections.sort(apportionedImpacts,new SortByYear());
        return apportionedImpacts;
    }

    // Returns the portfolio footprint for years 2019, 2020, 2021
//    public ResponseEntity<List<PortfolioFootprint>> calculatePortfolioImpact(List<CompanyInput> portfoiloList) {
//        List<List<ApportionedImpact>> apportionedImpactsList=new ArrayList<>();
//        double totalInvestment=0.0;
//        for(CompanyInput companyInput:portfoiloList){
//            totalInvestment+=companyInput.getInvestmentAmount();
//            apportionedImpactsList.add(apportionedImpacts(companyInput));
//        }
//        System.out.println("totalInvestment = " + totalInvestment);
//        List<Double> totalCarbonFootprint;
//        double carbonFootprint1=0.0;
//        double carbonFootprint2=0.0;
//        double carbonFootprint3=0.0;
//        for(List<ApportionedImpact> apportionedImpacts:apportionedImpactsList){
//            for(ApportionedImpact apportionedImpact:apportionedImpacts){
//                if(apportionedImpact.getReportingYear()==2019)
//                    carbonFootprint1+=apportionedImpact.getCarbonFootprint();
//                else if (apportionedImpact.getReportingYear()==2020) {
//                    carbonFootprint2+=apportionedImpact.getCarbonFootprint();
//                } else if (apportionedImpact.getReportingYear()==2021) {
//                    carbonFootprint3+=apportionedImpact.getCarbonFootprint();
//                }
//            }
//        }
//        List<PortfolioFootprint> portfolioFootprints=new ArrayList<>();
//        portfolioFootprints.add(new PortfolioFootprint(2019,carbonFootprint1/totalInvestment*1e6,carbonFootprint1));
//        portfolioFootprints.add(new PortfolioFootprint(2020,carbonFootprint2/totalInvestment*1e6,carbonFootprint2));
//        portfolioFootprints.add(new PortfolioFootprint(2021,carbonFootprint3/totalInvestment*1e6,carbonFootprint3));
//        System.out.println("portfolioFootprints = " + portfolioFootprints);
//        return new ResponseEntity<>(portfolioFootprints,HttpStatus.OK);
//    }

    public ResponseEntity<List<PortfolioFootprint>> calculatePortfolioImpact(List<CompanyInput> portfolioList) {
        Map<Integer, Double> yearlyFootprints = new HashMap<>();
        double totalInvestment = 0.0;
        Map<Integer, Double> benchMark=new HashMap<>();
        List<Double> benchMarkGhg=Arrays.asList(38322702900.0,34405284430.00,33998961270.0,36415744330.0);
        List<Double> benchMarkMarketCapMillionUsd=Arrays.asList(23067428.095454957,28349767.01569506,31914785.90541899,37246961.267511);
        for (CompanyInput companyInput : portfolioList) {
            totalInvestment += companyInput.getInvestmentAmount();
            List<ApportionedImpact> apportionedImpacts = apportionedImpacts(companyInput);

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

            portfolioFootprints.add(new PortfolioFootprint(year, carbonFootprintPerMillion, carbonFootprint,benchMark.get(year)));
        }

        return new ResponseEntity<>(portfolioFootprints, HttpStatus.OK);
    }
}

