package com.fullcycle.catalog.application.category.create;

import com.fullcycle.catalog.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase defaultCreateCategoryUseCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp(){
        Mockito.reset(categoryGateway);
    }


    @Test
    public void givenAValidCommand_whenCallCreateCategory_shouldReturnACategoryID() {
        final var expectedName = "Films";
        final var expectedDescription = "Most popular category";
        final var expectedIsActive = Boolean.TRUE;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());


        final var actualOutput = defaultCreateCategoryUseCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1)).create(
                argThat(aCategory ->
                        Objects.equals(expectedName, aCategory.getName())
                                && Objects.equals(expectedDescription, aCategory.getDescription())
                                && Objects.nonNull(aCategory.getCreatedAt())
                                && Objects.nonNull(aCategory.getUpdatedAt())
                                && Objects.isNull(aCategory.getDeletedAt())
                                && Objects.nonNull(aCategory.getId())
                )
        );
    }

    @Test
    public void givenAInvalidName_whenCallsCreationCategoryUseCase_thenShouldReturnsADomainException() {
        final String expectedName = null;
        final var expectedDescription = "Most popular category";
        final var expectedIsActive = Boolean.TRUE;
        final var expectedErrorMessage = "'Name' shouldn't be null";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCategoryCommand
                .with(expectedName, expectedDescription, expectedIsActive);

        final var notification = defaultCreateCategoryUseCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorMessage, notification.findFirstError().message());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());

        verify(categoryGateway, times(0)).create(any());
    }

    @Test
    public void givenAValidCommandWithInactive_whenCallCreateCategory_shouldReturnAInactiveCategoryID() {
        final var expectedName = "Films";
        final var expectedDescription = "Most popular category";
        final var expectedIsActive = Boolean.FALSE;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());


        final var actualOutput = defaultCreateCategoryUseCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1)).create(
                argThat(aCategory ->
                        Objects.equals(expectedName, aCategory.getName())
                                && Objects.equals(expectedDescription, aCategory.getDescription())
                                && Objects.nonNull(aCategory.getCreatedAt())
                                && Objects.nonNull(aCategory.getUpdatedAt())
                                && Objects.nonNull(aCategory.getDeletedAt())
                                && Objects.nonNull(aCategory.getId())
                )
        );
    }

    @Test
    public void givenAValidCommandWithInactive_whenGatewayThrowsRandomException_shouldReturnAException() {
        final var expectedName = "Films";
        final var expectedDescription = "Most popular category";
        final var expectedIsActive = Boolean.FALSE;
        final var expectedErrorMessage = "Gateway error";

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = defaultCreateCategoryUseCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorMessage, notification.findFirstError().message());
        verify(categoryGateway, times(1)).create(any());
    }
}
