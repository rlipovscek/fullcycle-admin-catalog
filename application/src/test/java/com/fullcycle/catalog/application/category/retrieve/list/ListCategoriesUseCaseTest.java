package com.fullcycle.catalog.application.category.retrieve.list;

import com.fullcycle.catalog.domain.category.Category;
import com.fullcycle.catalog.domain.category.CategoryGateway;
import com.fullcycle.catalog.domain.category.CategorySearchQuery;
import com.fullcycle.catalog.domain.pagination.Pagination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ListCategoriesUseCaseTest {

    @InjectMocks
    private DefaultListCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;


    @BeforeEach
    void cleanUp(){
        Mockito.reset(categoryGateway);
    }


    @Test
    public void givenAValidQuery_whenCallsListCategories_thenShouldReturnCategories(){
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTherms = "";
        final var expectedSort = "CreatedAt";
        final var expectedDirection = "asc";

        final var categories = List.of(
                Category.newCategory("Films", null, Boolean.TRUE),
                Category.newCategory("Series", null, Boolean.TRUE)
        );

        final var categoriesPage = new Pagination<Category>(
                expectedPage,
                expectedPerPage,
                categories.size(),
                categories
        );

        final var aQuery = CategorySearchQuery.create(
                expectedPage,
                expectedPerPage,
                expectedTherms,
                expectedSort,
                expectedDirection
        );

        final var expectedItemsCount = 2;
        final var expectedResults = categoriesPage.map(CategoryListOutPut::from);

        Mockito.when(categoryGateway.findAll(Mockito.eq(aQuery)))
                .thenReturn(categoriesPage);

       final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedResults, actualResult);
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(categories.size(), actualResult.total());

    }

    @Test
    public void givenAValidQuery_whenHasNoResults_thenShouldReturnEmptyCategories(){
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTherms = "";
        final var expectedSort = "CreatedAt";
        final var expectedDirection = "asc";

        final var categories = List.<Category>of();

        final var categoriesPage = new Pagination<Category>(
                expectedPage,
                expectedPerPage,
                categories.size(),
                categories
        );

        final var aQuery = CategorySearchQuery.create(
                expectedPage,
                expectedPerPage,
                expectedTherms,
                expectedSort,
                expectedDirection
        );

        final var expectedItemsCount = 0;
        final var expectedResults = categoriesPage.map(CategoryListOutPut::from);

        Mockito.when(categoryGateway.findAll(Mockito.eq(aQuery)))
                .thenReturn(categoriesPage);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedResults, actualResult);
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(categories.size(), actualResult.total());
    }

    @Test
    public void givenAValidQuery_whenGatewayThrowsException_thenShouldReturnAException(){
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTherms = "";
        final var expectedSort = "CreatedAt";
        final var expectedDirection = "asc";

        final var errorMessage = "Gateway is out";


        final var aQuery = CategorySearchQuery.create(
                expectedPage,
                expectedPerPage,
                expectedTherms,
                expectedSort,
                expectedDirection
        );

        Mockito.when(categoryGateway.findAll(Mockito.eq(aQuery)))
                .thenThrow(new IllegalStateException(errorMessage));

        final var actualException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> this.useCase.execute(aQuery)
        );

        Assertions.assertEquals(errorMessage, actualException.getMessage());
    }



}
