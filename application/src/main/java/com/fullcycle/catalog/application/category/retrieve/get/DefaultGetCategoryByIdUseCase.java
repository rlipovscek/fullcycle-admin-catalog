package com.fullcycle.catalog.application.category.retrieve.get;

import com.fullcycle.catalog.domain.category.CategoryGateway;
import com.fullcycle.catalog.domain.category.CategoryID;
import com.fullcycle.catalog.domain.exceptions.DomainException;
import com.fullcycle.catalog.domain.validation.Error;

import java.util.Objects;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

    private final CategoryGateway categoryGateway;

    private DefaultGetCategoryByIdUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    static DefaultGetCategoryByIdUseCase create(CategoryGateway categoryGateway) {
        return new DefaultGetCategoryByIdUseCase(categoryGateway);
    }

    @Override
    public CategoryOutput execute(String anId) {
        final var aCategoryId = CategoryID.from(anId);
        return this.execute(aCategoryId);
    }

    public CategoryOutput execute(CategoryID anId) {
        return this.findCategoryById(anId);
    }

    private CategoryOutput findCategoryById(final CategoryID anId) {
        return this.categoryGateway.findById(anId)
                .map(CategoryOutput::from)
                .orElseThrow(() ->
                        DomainException.with(new Error("Not found category with id '%s'"
                                .formatted(anId.getValue())
                        ))
                );
    }
}
