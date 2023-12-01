package com.gistimpact.carbonfootprint.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CompanyInput {
    @NotEmpty(message = "name should not be empty")
    private String companyName;
    @NotNull(message = "Amount should not be zero")
    private Long investmentAmount;
}
