package com.gistimpact.carbonfootprint.controllers;

import com.gistimpact.carbonfootprint.dto.ApportionedImpact;
import com.gistimpact.carbonfootprint.dto.CompanyInput;
import com.gistimpact.carbonfootprint.service.CompanyListService;
import com.gistimpact.carbonfootprint.service.FootPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
public class FootprintController {

    @Autowired
    private FootPrintService footPrintService;
    @Autowired
    private CompanyListService companyListService;

    @PostMapping("companyInfo")
    public ResponseEntity<List<ApportionedImpact>> getPortfolioList(@RequestBody CompanyInput companyInput){
        return footPrintService.apportionedImpacts(companyInput);

    }
    @GetMapping("getCompanies")
    public ResponseEntity<List<String>> getCompanies(){
        return companyListService.getAllCompanies();
    }


}
