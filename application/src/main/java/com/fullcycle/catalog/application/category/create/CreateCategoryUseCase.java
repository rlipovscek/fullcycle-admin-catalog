package com.fullcycle.catalog.application.category.create;

import com.fullcycle.catalog.application.UseCase;
import com.fullcycle.catalog.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {
}
