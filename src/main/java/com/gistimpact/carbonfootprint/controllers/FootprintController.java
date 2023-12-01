package com.gistimpact.carbonfootprint.controllers;

import com.gistimpact.carbonfootprint.dto.ApportionedImpact;
import com.gistimpact.carbonfootprint.dto.CompanyInput;
import com.gistimpact.carbonfootprint.dto.PortfolioFootprint;
import com.gistimpact.carbonfootprint.exception.CompanyNotFoundException;
import com.gistimpact.carbonfootprint.exception.ImpactDataNotfound;
import com.gistimpact.carbonfootprint.service.CompanyListService;
import com.gistimpact.carbonfootprint.service.FootPrintService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
@Validated
public class FootprintController {

    @Autowired
    private FootPrintService footPrintService;
    @Autowired
    private CompanyListService companyListService;

    @PostMapping("companyInfo")
    public ResponseEntity<List<ApportionedImpact>> getPortfolioList(@RequestBody @Valid CompanyInput companyInput) throws CompanyNotFoundException, ImpactDataNotfound {
        return footPrintService.apportionedImpacts(companyInput);

    }
    @GetMapping("getCompanies")
    public ResponseEntity<List<String>> getCompanies(){
        return companyListService.getAllCompanies();
    }
    @PostMapping("portfolioInfo")
    public ResponseEntity<List<PortfolioFootprint>> calculatePortfolioImpact(@RequestBody @NotEmpty(message = "portfolio should not be empty") @Valid List<CompanyInput> portfoiloList) throws CompanyNotFoundException, ImpactDataNotfound {

        return footPrintService.calculatePortfolioImpact(portfoiloList);

    }


}
