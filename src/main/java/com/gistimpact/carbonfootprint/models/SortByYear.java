package com.gistimpact.carbonfootprint.models;

import com.gistimpact.carbonfootprint.dto.ApportionedImpact;

import java.util.Comparator;

public class SortByYear implements Comparator<ApportionedImpact> {
    public int compare(ApportionedImpact apportionedImpact1,ApportionedImpact apportionedImpact2){
        return apportionedImpact1.getReportingYear()-apportionedImpact2.getReportingYear();
    }
}
