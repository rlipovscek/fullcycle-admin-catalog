package com.fullcycle.catalog.application;

public abstract class UnitUseCase<IN> {
    public abstract void execute(IN anIn);
}
