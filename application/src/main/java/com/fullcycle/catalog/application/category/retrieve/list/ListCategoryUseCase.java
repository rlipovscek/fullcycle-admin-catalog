package com.fullcycle.catalog.application.category.retrieve.list;

import com.fullcycle.catalog.application.UseCase;
import com.fullcycle.catalog.domain.category.CategorySearchQuery;
import com.fullcycle.catalog.domain.pagination.Pagination;

public abstract class ListCategoryUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutPut>> {
}
