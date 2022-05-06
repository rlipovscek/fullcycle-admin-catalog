package com.fullcycle.catalog.application.category.update;

import com.fullcycle.catalog.domain.category.Category;
import com.fullcycle.catalog.domain.category.CategoryID;

public record UpdateCategoryOut(
        CategoryID categoryId
) {

    public static UpdateCategoryOut from(final Category aCategory){
        return new UpdateCategoryOut(aCategory.getId());
    }
}
