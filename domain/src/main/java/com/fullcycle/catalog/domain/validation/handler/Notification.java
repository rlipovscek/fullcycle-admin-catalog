package com.fullcycle.catalog.domain.validation.handler;

import com.fullcycle.catalog.domain.exceptions.DomainException;
import com.fullcycle.catalog.domain.validation.Error;
import com.fullcycle.catalog.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    private final List<Error> errors;

    private Notification(final List<Error> aErrorList) {
        this.errors = aErrorList;
    }


    public static Notification create(final Throwable throwable) {
        return Notification.create(new Error(throwable.getMessage()));
    }
    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final Error anError) {
        return new Notification(new ArrayList<>()).append(anError);
    }

    @Override
    public Notification append(final Error anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public Notification append(final ValidationHandler anHandler) {
        this.errors.addAll(anHandler.getErrors());
        return this;
    }

    @Override
    public Notification append(final Validation anValidation) {
        try {

        } catch (final DomainException domainException) {
            errors.add(new Error(domainException.getMessage()));
        } catch (final Throwable throwable) {
            this.errors.add(new Error(throwable.getMessage()));
        }
        return this;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }
}
