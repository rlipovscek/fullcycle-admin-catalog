package com.fullcycle.catalog.application.category.delete;

import com.fullcycle.catalog.domain.category.Category;
import com.fullcycle.catalog.domain.category.CategoryGateway;
import com.fullcycle.catalog.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTest {

    @InjectMocks
    private DefaultDeleteCategoryUseCase deleteCategoryUseCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp(){
        reset(categoryGateway);
    }

    @Test
    void givenAValidCategoryID_whenCallDeleteMethod_thenShouldReturnDeleteBeOk(){
        final var aCategory = Category.newCategory("Films", "A new Category", Boolean.TRUE);
        final var expectedID = aCategory.getId();

        doNothing().when(categoryGateway).deleteById(eq(expectedID));

        Assertions.assertDoesNotThrow(() -> deleteCategoryUseCase.execute(expectedID.getValue()));
        verify(categoryGateway, times(1)).deleteById(eq(expectedID));

    }

    @Test
    void givenAInvalidCategoryID_whenCallDeleteMethod_thenShouldReturnDeleteBeOk(){
        var dummyId = CategoryID.from("1234");

        doNothing().when(categoryGateway).deleteById(eq(dummyId));

        Assertions.assertDoesNotThrow(() -> deleteCategoryUseCase.execute(dummyId.getValue()));
        verify(categoryGateway, times(1)).deleteById(eq(dummyId));
    }


}
