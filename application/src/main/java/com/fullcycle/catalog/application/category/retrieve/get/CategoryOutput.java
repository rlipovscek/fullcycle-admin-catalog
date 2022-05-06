package com.fullcycle.catalog.application.category.retrieve.get;

import com.fullcycle.catalog.domain.category.Category;
import com.fullcycle.catalog.domain.category.CategoryID;

import java.time.Instant;

public record CategoryOutput(CategoryID id, String name, String description, Boolean isActive, Instant createdAt,
                             Instant updatedAt, Instant deletedAt) {

    public static CategoryOutput from(
            final CategoryID anId,
            final String aName,
            final String aDescription,
            final Boolean isActive,
            final Instant aCreatedAt,
            final Instant aUpdatedAt,
            final Instant aDeletedAt) {
        return new CategoryOutput(anId, aName, aDescription, isActive, aCreatedAt, aUpdatedAt, aDeletedAt);
    }

    public static CategoryOutput from(
            final Category aCategory) {
        return CategoryOutput.from(
                aCategory.getId(),
                aCategory.getName(),
                aCategory.getDescription(),
                aCategory.isActive(),
                aCategory.getCreatedAt(),
                aCategory.getUpdatedAt(),
                aCategory.getDeletedAt()
        );
    }
}
