package com.gistimpact.carbonfootprint.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CompanyInput {
    private String companyName;
    private int investmentAmount;
}
