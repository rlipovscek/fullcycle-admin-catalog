package com.fullcycle.catalog.application.category.retrieve.list;

import com.fullcycle.catalog.domain.category.Category;
import com.fullcycle.catalog.domain.category.CategoryID;

import java.time.Instant;

public record CategoryListOutPut(
        CategoryID id,
        String name,
        String description,
        Boolean isActive,
        Instant createdAt,
        Instant deletedAt
) {

    public static CategoryListOutPut from(final Category aCategory){
        return new CategoryListOutPut(
                aCategory.getId(),
                aCategory.getName(),
                aCategory.getDescription(),
                aCategory.isActive(),
                aCategory.getCreatedAt(),
                aCategory.getDeletedAt()
        );
    }

}
