package com.fullcycle.catalog.application.category.retrieve.get;

import com.fullcycle.catalog.domain.category.Category;
import com.fullcycle.catalog.domain.category.CategoryGateway;
import com.fullcycle.catalog.domain.category.CategoryID;
import com.fullcycle.catalog.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCategoryByUseCaseTest {

    @InjectMocks
    private DefaultGetCategoryByIdUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        reset(categoryGateway);
    }

    @Test
    void givenAValidCategoryID_whenCallGetMethod_thenShouldReturnACategory() {
        final var expectedName = "Films";
        final var expectedDescription = "Most popular category";
        final var expectedIsActive = Boolean.TRUE;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var expectedId = aCategory.getId();

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Category.clone(aCategory)));

        final var actualCategory = useCase.execute(expectedId.getValue());

        Assertions.assertEquals(aCategory.getId(), actualCategory.id());
        Assertions.assertEquals(expectedName, actualCategory.name());
        Assertions.assertEquals(expectedDescription, actualCategory.description());
        Assertions.assertEquals(aCategory.isActive(), actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.createdAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.deletedAt());

    }

    @Test
    void givenAInvalidCategoryID_whenCallGetCategoryMethod_thenShouldThrowANotFoundException() {
        final var expectedName = "Films";
        final var expectedDescription = "Most popular category";
        final var expectedIsActive = Boolean.TRUE;
        final var invalidId = "1234";
        final var expectedErrorMessage = "Not found category with id '%s'".formatted(invalidId);

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var expectedId = aCategory.getId();

        when(categoryGateway.findById(eq(CategoryID.from(invalidId))))
                .thenReturn(Optional.empty());

        final var expectedException = Assertions.assertThrows(
                DomainException.class,
                () -> useCase.execute(invalidId)
        );
        Assertions.assertEquals(expectedErrorMessage, expectedException.getMessage());
    }

    @Test
    void givenAValidCategoryId_whenCallGetCategoryMethod_thenGatewayReturnAException() {
        final var expectedErrorMessage = "Gateway error";

        when(categoryGateway.findById(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var expectedException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> useCase.execute("1234")
        );
        Assertions.assertEquals(expectedErrorMessage, expectedException.getMessage());

    }
}
