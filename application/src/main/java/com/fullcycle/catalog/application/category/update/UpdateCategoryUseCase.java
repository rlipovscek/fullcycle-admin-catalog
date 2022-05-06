package com.fullcycle.catalog.application.category.update;

import com.fullcycle.catalog.application.UseCase;
import com.fullcycle.catalog.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOut>> {

}
