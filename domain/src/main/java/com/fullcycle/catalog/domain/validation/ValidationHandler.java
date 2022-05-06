package com.fullcycle.catalog.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler anHandler);

    ValidationHandler append(Validation anValidation);

    List<Error> getErrors();

    default boolean hasError(){
        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error findFirstError(){
        Error error = null;
        if(hasError()){
            error = getErrors().get(0);
        }

        return error;
    }

    public interface  Validation{
        void validate();
    }
}
