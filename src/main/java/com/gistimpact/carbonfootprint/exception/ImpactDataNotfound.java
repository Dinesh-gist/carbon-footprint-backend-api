package com.gistimpact.carbonfootprint.exception;

import jakarta.validation.constraints.NotEmpty;

public class ImpactDataNotfound extends Exception {
    public ImpactDataNotfound(String s) {
        super(s);
    }
}
