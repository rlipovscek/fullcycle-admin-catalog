package com.fullcycle.catalog.domain.category;

import com.fullcycle.catalog.domain.AggregateRoot;
import com.fullcycle.catalog.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.UUID;

public class Category extends AggregateRoot<CategoryID> implements Cloneable{
    private String name;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
    private Boolean active;


    private Category(final CategoryID anId,
                     final String aName,
                     final String aDescription,
                     final Instant aCreationDate,
                     final Instant aUpdateDate,
                     final Instant aDeletionDate,
                     final Boolean isActive
    ){

        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.createdAt = aCreationDate;
        this.updatedAt = aUpdateDate;
        this.deletedAt = aDeletionDate;
        this.active = isActive;
    }

    public static Category newCategory(final String aName, final String aDescription,final Boolean aIsActive){
        final var id = CategoryID.unique();
        final var now = Instant.now();
        final var aDeletedDate = aIsActive ? null : now;
        return new Category(id, aName, aDescription, now, now, aDeletedDate, aIsActive );
    }


    @Override
    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public Category deactivate(){
        if(deletedAt == null){
            this.deletedAt = Instant.now();
        }

        this.updatedAt = Instant.now();
        this.active = Boolean.FALSE;

        return this;
    }

    public Category update(
            final String aName,
            final String aDescription,
            final Boolean isActivate
    ){
        this.name = aName;
        this.description = aDescription;
        if(isActivate){
            activate();
        }else{
            deactivate();
        }

        this.updatedAt = Instant.now();

        return this;
    }

    public Category activate(){
        this.deletedAt = null;
        this.updatedAt = Instant.now();
        this.active = Boolean.TRUE;
        return this;
    }

    public CategoryID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public Boolean isActive() {
        return active;
    }

    public static Category clone(Category aCategory) { return aCategory.clone(); }

    @Override
    public Category clone() {
        try {
            return (Category) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
