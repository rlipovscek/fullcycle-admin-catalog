package com.fullcycle.catalog.application.category.update;

import com.fullcycle.catalog.domain.category.Category;
import com.fullcycle.catalog.domain.category.CategoryGateway;
import com.fullcycle.catalog.domain.category.CategoryID;
import com.fullcycle.catalog.domain.exceptions.DomainException;
import com.fullcycle.catalog.domain.validation.Error;
import com.fullcycle.catalog.domain.validation.handler.Notification;
import io.vavr.control.Either;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

import java.util.Objects;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase{

    private final CategoryGateway categoryGateway;

    private DefaultUpdateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    public static DefaultUpdateCategoryUseCase create(final CategoryGateway aGateway){
        return new DefaultUpdateCategoryUseCase(aGateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOut> execute(UpdateCategoryCommand aCommand) {
        final var anId = CategoryID.from(aCommand.id());
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var isActive = aCommand.isActive();

        final var aCategory = findCategoryById(anId);

        final var notification = Notification.create();

        aCategory
                .update(aName, aDescription, isActive)
                .validate(notification);
        return notification.hasError() ? Left(notification) : update(aCategory);
    }

    private Either<Notification, UpdateCategoryOut> update(final Category aCategory) {
        return Try(() -> this.categoryGateway.update(aCategory))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOut::from);
    }

    private Category findCategoryById(final CategoryID anId) {
         return this.categoryGateway.findById(anId)
                .orElseThrow(() -> DomainException.with(new Error("Not found category with id '%s'".formatted(anId.getValue()))));
    }
}
