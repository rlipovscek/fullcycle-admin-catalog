package com.fullcycle.catalog.domain.validation;

public abstract class Validator {
    private final ValidationHandler validationHandler;

    public Validator(ValidationHandler aHandler){
        this.validationHandler = aHandler;
    }

    public ValidationHandler getValidationHandler() {
        return validationHandler;
    }

    public abstract void validate();

}
