package com.gistimpact.carbonfootprint.controllers;

import com.gistimpact.carbonfootprint.dto.ApportionedImpact;
import com.gistimpact.carbonfootprint.dto.CompanyInput;
import com.gistimpact.carbonfootprint.service.FootPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FootprintController {


    @Autowired
    private FootPrintService footPrintService;
    @PostMapping("companyInfo")
    public ResponseEntity<List<ApportionedImpact>> getPortfolioList(@RequestBody CompanyInput companyInput){
        return footPrintService.apportionedImpacts(companyInput);

    }
}
