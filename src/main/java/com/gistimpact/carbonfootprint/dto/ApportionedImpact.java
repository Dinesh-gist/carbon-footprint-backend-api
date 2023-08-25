package com.gistimpact.carbonfootprint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApportionedImpact {
    private String companyName;
    private String sectorName;
    private int reportingYear;
    private double carbonFootprint;

}
