package com.fullcycle.catalog.domain.validation.handler;

import com.fullcycle.catalog.domain.exceptions.DomainException;
import com.fullcycle.catalog.domain.validation.Error;
import com.fullcycle.catalog.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowNewValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(final Error anError) {
        throw DomainException.with(anError);
    }

    @Override
    public ValidationHandler append(final ValidationHandler anHandler) {
        throw DomainException.with(anHandler.getErrors());
    }

    @Override
    public ValidationHandler append(final Validation anValidation) {
        try {
            anValidation.validate();
        }catch (final Exception err){
            throw DomainException.with(new Error(err.getMessage()));
        }

        return this;
    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }
}
