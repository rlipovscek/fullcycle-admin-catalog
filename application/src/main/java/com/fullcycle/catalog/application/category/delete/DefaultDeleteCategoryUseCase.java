package com.fullcycle.catalog.application.category.delete;

import com.fullcycle.catalog.domain.category.CategoryGateway;
import com.fullcycle.catalog.domain.category.CategoryID;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase{

    private final CategoryGateway categoryGateway;

    private DefaultDeleteCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    public DefaultDeleteCategoryUseCase create(final CategoryGateway categoryGateway){
        return new DefaultDeleteCategoryUseCase(categoryGateway);
    }

    @Override
    public void execute(String anId) {
        this.execute(CategoryID.from(anId));
    }

    public void execute(CategoryID anId) {
        this.categoryGateway.deleteById(anId);
    }
}
