package com.fullcycle.catalog.application.category.retrieve.list;

import com.fullcycle.catalog.domain.category.CategoryGateway;
import com.fullcycle.catalog.domain.category.CategorySearchQuery;
import com.fullcycle.catalog.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoryUseCase extends ListCategoryUseCase {

    private final CategoryGateway categoryGateway;

    private DefaultListCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }


    public static DefaultListCategoryUseCase create(final CategoryGateway categoryGateway){
        return new DefaultListCategoryUseCase(categoryGateway);
    }


    @Override
    public Pagination<CategoryListOutPut> execute(final CategorySearchQuery aQuery) {
        return this.categoryGateway
                .findAll(aQuery)
                .map(CategoryListOutPut::from);
    }
}
