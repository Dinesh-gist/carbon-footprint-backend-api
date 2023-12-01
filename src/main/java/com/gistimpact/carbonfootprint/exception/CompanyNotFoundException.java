package com.gistimpact.carbonfootprint.exception;

import jakarta.validation.constraints.NotEmpty;

public class CompanyNotFoundException extends Exception {
    public CompanyNotFoundException( String s) {
        super(s);
    }
}
