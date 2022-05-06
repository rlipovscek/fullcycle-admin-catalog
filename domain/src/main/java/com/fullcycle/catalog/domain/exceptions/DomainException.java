package com.fullcycle.catalog.domain.exceptions;

import com.fullcycle.catalog.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStacktraceException{

    private final List<Error> errors;

    private DomainException(final String aMessage, final List<Error> anErrors){
        super(aMessage);
        this.errors = anErrors;
    }

    public static DomainException with(List<Error> anErrors){
        return new DomainException("", anErrors);
    }
    public static DomainException with(Error aError){
        return new DomainException(aError.message(), List.of(aError));
    }

    public Error getFirstError(){
        Error aError = null;

        if(this.getErrors() != null && this.getErrors().size() > 0){
            aError = this.getErrors().get(0);
        }

        return aError;
    }

    public static Error getFirstError(DomainException aDomainException){
        return aDomainException.getFirstError();
    }

    public List<Error> getErrors() {
        return errors;
    }
}
