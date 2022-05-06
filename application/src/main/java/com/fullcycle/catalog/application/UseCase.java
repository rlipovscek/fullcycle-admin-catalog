package com.fullcycle.catalog.application;

import com.fullcycle.catalog.domain.category.Category;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN anIn);
}
