package com.fullcycle.catalog.domain.category;

import com.fullcycle.catalog.domain.validation.Error;
import com.fullcycle.catalog.domain.validation.ValidationHandler;
import com.fullcycle.catalog.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;
    private static final String NAME_WITH_NULL_VALUE_MESSAGE = "'Name' shouldn't be null";
    private static final String NAME_WITH_EMPTY_VALUE_MESSAGE = "'Name' shouldn't be empty";
    private static final String NAME_WITH_WRONG_LENGTH_MESSAGE = "'Name' most be between 3 and 255 characters";
    private static final Integer NAME_MAX_LENGTH = 255;
    private static final Integer NAME_MIN_LENGTH = 3;

    public CategoryValidator(final Category aCategory, final ValidationHandler aHandler) {
        super(aHandler);
        this.category = aCategory;
    }

    @Override
    public void validate() {
        checkNameConstraints();

    }

    private void checkNameConstraints() {
        final var name= this.category.getName();

        if( name == null){
            this.getValidationHandler().append(new Error(NAME_WITH_NULL_VALUE_MESSAGE));
            return;
        }

        if( name.isBlank()){
            this.getValidationHandler().append(new Error(NAME_WITH_EMPTY_VALUE_MESSAGE));
            return;
        }

        final var nameLength = name.trim().length();

        if(nameLength > NAME_MAX_LENGTH || nameLength < NAME_MIN_LENGTH){
            this.getValidationHandler().append(new Error(NAME_WITH_WRONG_LENGTH_MESSAGE));
        }

    }


}
