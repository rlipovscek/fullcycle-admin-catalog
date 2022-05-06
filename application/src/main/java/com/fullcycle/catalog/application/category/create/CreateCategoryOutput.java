package com.fullcycle.catalog.application.category.create;

import com.fullcycle.catalog.domain.category.Category;
import com.fullcycle.catalog.domain.category.CategoryID;

public record CreateCategoryOutput(
        CategoryID id
) {
    public static CreateCategoryOutput from(Category aCategory){
        return new CreateCategoryOutput(aCategory.getId());
    }
}
