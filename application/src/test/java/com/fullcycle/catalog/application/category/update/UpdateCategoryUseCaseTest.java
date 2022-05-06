package com.fullcycle.catalog.application.category.update;

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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase defaultUpdateCategoryUseCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp(){
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidCommand_whenCallUpdateCategory_thenReturnACategoryID() {
        final var aCategory = Category.newCategory("Film", null, true);

        final var expectedName = "Films";
        final var expectedDescription = "Most popular category";
        final var expectedIsActive = Boolean.TRUE;

        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);


        when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(Category.clone(aCategory)));
        when(categoryGateway.update(eq(aCategory))).thenAnswer(returnsFirstArg());

        final var actualOutput = defaultUpdateCategoryUseCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);

        Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(categoryGateway, times(1)).update(
                argThat(updateCategory -> Objects.equals(expectedName, updateCategory.getName())
                        && Objects.equals(expectedDescription, updateCategory.getDescription())
                        && Objects.equals(aCategory.getCreatedAt(), updateCategory.getCreatedAt())
                        && aCategory.getUpdatedAt().isBefore(updateCategory.getUpdatedAt())
                        && Objects.isNull(updateCategory.getDeletedAt())
                        && Objects.equals(aCategory.getId(), updateCategory.getId())
                )
                );
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateCategoryUseCase_thenShouldReturnsADomainException() {
        final var aCategory = Category.newCategory("Film", null, true);

        final String expectedName = null;
        final var expectedDescription = "Most popular category";
        final var expectedIsActive = Boolean.TRUE;
        final var expectedErrorMessage = "'Name' shouldn't be null";
        final var expectedErrorCount = 1;

        final var expectedId = aCategory.getId();

        when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(Category.clone(aCategory)));

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        final var notification = defaultUpdateCategoryUseCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorMessage, notification.findFirstError().message());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());

        verify(categoryGateway, times(0)).create(any());
    }

    @Test
    public void givenAValidInactivateCommand_whenCallUpdateCategory_thenReturnACategoryID() {
        final var aCategory = Category.newCategory("Film", null, true);

        final var expectedName = "Films";
        final var expectedDescription = "Most popular category";
        final var expectedIsActive = Boolean.TRUE;

        final var expectedId = aCategory.getId();

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, Boolean.FALSE);


        when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(Category.clone(aCategory)));
        when(categoryGateway.update(eq(aCategory))).thenAnswer(returnsFirstArg());

        final var actualOutput = defaultUpdateCategoryUseCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);

        Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(categoryGateway, times(1)).update(
                argThat(updateCategory -> Objects.equals(expectedName, updateCategory.getName())
                        && Objects.equals(expectedDescription, updateCategory.getDescription())
                        && Objects.equals(aCategory.getCreatedAt(), updateCategory.getCreatedAt())
                        && aCategory.getUpdatedAt().isBefore(updateCategory.getUpdatedAt())
                        && Objects.nonNull(updateCategory.getDeletedAt())
                        && Objects.equals(aCategory.getId(), updateCategory.getId())
                        && Objects.equals(updateCategory.isActive(), Boolean.FALSE)
                )
        );
    }

    @Test
    public void givenAValidInactivateCommand_whenCallUpdateCategory_thenReturnAIllegalException() {
        final var aCategory = Category.newCategory("Film", null, true);

        final var expectedName = "Films";
        final var expectedDescription = "Most popular category";
        final var expectedIsActive = Boolean.TRUE;

        final var expectedId = aCategory.getId();

        final var expectedErrorMessage = "Gateway out";

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, Boolean.FALSE);


        when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(Category.clone(aCategory)));
        when(categoryGateway.update(eq(aCategory))).thenThrow(new IllegalArgumentException(expectedErrorMessage));

        final var notification = defaultUpdateCategoryUseCase.execute(aCommand).getLeft();

        Assertions.assertNotNull(notification);

        Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));
        Assertions.assertEquals(expectedErrorMessage, notification.findFirstError().message());
    }

    @Test
    public void givenAValidCommandWithAInvalidId_whenCallUpdateCategory_thenReturnADomainException() {
        final var expectedName = "Films";
        final var expectedDescription = "Most popular category";
        final var wrongId = "1234";
        final var errorCount = 1;

        final var expectedErrorMessage = "Not found category with id '%s'".formatted(wrongId);

        final var aCommand = UpdateCategoryCommand.with(wrongId, expectedName, expectedDescription, Boolean.FALSE);


        when(categoryGateway.findById(eq(CategoryID.from(wrongId)))).thenReturn(Optional.empty());

       final var expectedException =  Assertions.assertThrows(DomainException.class, () -> defaultUpdateCategoryUseCase.execute(aCommand).getLeft());
       Assertions.assertEquals(expectedErrorMessage, DomainException.getFirstError(expectedException).message());
       Assertions.assertEquals(errorCount, expectedException.getErrors().size());
    }
}
