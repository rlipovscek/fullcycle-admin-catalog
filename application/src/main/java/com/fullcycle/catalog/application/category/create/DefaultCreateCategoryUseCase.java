package com.fullcycle.catalog.application.category.create;

import com.fullcycle.catalog.domain.category.Category;
import com.fullcycle.catalog.domain.category.CategoryGateway;
import com.fullcycle.catalog.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.*;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {
    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }


    @Override
    public Either<Notification, CreateCategoryOutput> execute(CreateCategoryCommand anCommand) {
        final var aCategory = Category.newCategory(anCommand.name(), anCommand.description(), anCommand.isActive());

        final var notification = Notification.create();
        aCategory.validate(notification);

        return notification.hasError() ? Left(notification) : create(aCategory);
       //
    }

    private Either<Notification, CreateCategoryOutput> create(final Category aCategory) {
        return Try(() -> this.categoryGateway.create(aCategory))
                .toEither()
                .bimap(Notification::create, CreateCategoryOutput::from);
    }
}
