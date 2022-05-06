package com.fullcycle.catalog.domain.category;

import com.fullcycle.catalog.domain.exceptions.DomainException;
import com.fullcycle.catalog.domain.validation.handler.ThrowNewValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void givenAValidParams_whenCallNewCategory_thenInstantiateANewCategory(){
        final var expectedName = "Films";
        final var expectedDescription = "Most popular category";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryWithInvalidName_thenThrowAInvalidParamsException(){
        final var expectedErrorMessage = "'Name' shouldn't be null";
        final var expectedErrorCount = 1;
        final var expectedDescription = "Most popular category";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(null, expectedDescription, expectedIsActive);
        final var receivedException =
                Assertions.assertThrowsExactly(DomainException.class, () -> actualCategory.validate(new ThrowNewValidationHandler()));
        Assertions.assertEquals(expectedErrorCount, receivedException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, receivedException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategoryWithInvalidName_thenThrowAInvalidParamsException(){
        final var expectedName = "   ";
        final var expectedErrorMessage = "'Name' shouldn't be empty";
        final var expectedErrorCount = 1;
        final var expectedDescription = "Most popular category";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var receivedException =
                Assertions.assertThrowsExactly(DomainException.class, () -> actualCategory.validate(new ThrowNewValidationHandler()));
        Assertions.assertEquals(expectedErrorCount, receivedException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, receivedException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameLengthLessThan3_whenCallNewCategoryWithInvalidName_thenThrowAInvalidParamsException(){
        final var expectedName = "Ro ";
        final var expectedErrorMessage = "'Name' most be between 3 and 255 characters";
        final var expectedErrorCount = 1;
        final var expectedDescription = "Most popular category";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var receivedException =
                Assertions.assertThrowsExactly(DomainException.class, () -> actualCategory.validate(new ThrowNewValidationHandler()));
        Assertions.assertEquals(expectedErrorCount, receivedException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, receivedException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameLengthLessMoreThan255_whenCallNewCategoryWithInvalidName_thenThrowAInvalidParamsException(){
        final var expectedName = """
                Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, 
                making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more 
                obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered 
                the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) 
                by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum,
                 "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32.
               """;
        final var expectedErrorMessage = "'Name' most be between 3 and 255 characters";
        final var expectedErrorCount = 1;
        final var expectedDescription = "Most popular category";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var receivedException =
                Assertions.assertThrowsExactly(DomainException.class, () -> actualCategory.validate(new ThrowNewValidationHandler()));
        Assertions.assertEquals(expectedErrorCount, receivedException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, receivedException.getErrors().get(0).message());
    }

    @Test
    public void givenAValidEmptyDescription_whenCallNewCategoryWithInvalidName_thenInstantiateANewCategory(){
        final var expectedName = "Brand new movie";
        final var expectedDescription = "  ";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(()-> actualCategory.validate(new ThrowNewValidationHandler()));

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidFalseToIsActive_whenCallNewCategoryWithInvalidName_thenInstantiateANewCategory(){
        final var expectedName = "Brand new movie";
        final var expectedDescription = "Most popular category";
        final var expectedIsActive = Boolean.FALSE;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(()-> actualCategory.validate(new ThrowNewValidationHandler()));

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallDeactivateMethod_thenReturnAInactivatedCategory(){
        final var expectedName = "Brand new movie";
        final var expectedDescription = "Most popular category";
        final var expectedIsActive = Boolean.FALSE;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, Boolean.TRUE);

        Assertions.assertDoesNotThrow(()-> aCategory.validate(new ThrowNewValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();

        Assertions.assertNull(aCategory.getDeletedAt());
        Assertions.assertTrue(aCategory.isActive());

        final var actualCategory = aCategory.deactivate();

        Assertions.assertDoesNotThrow(()-> actualCategory.validate(new ThrowNewValidationHandler()));

        Assertions.assertEquals(aCategory.getId(),actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(Boolean.FALSE, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidInactivatedCategory_whenCallActivateMethod_thenReturnAActivatedCategory(){
        final var expectedName = "Brand new movie";
        final var expectedDescription = "Most popular category";
        final var expectedIsActive = Boolean.TRUE;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, Boolean.FALSE);

        Assertions.assertDoesNotThrow(()-> aCategory.validate(new ThrowNewValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();

        Assertions.assertNotNull(aCategory.getDeletedAt());
        Assertions.assertFalse(aCategory.isActive());

        final var actualCategory = aCategory.activate();

        Assertions.assertDoesNotThrow(()-> actualCategory.validate(new ThrowNewValidationHandler()));

        Assertions.assertEquals(aCategory.getId(),actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(Boolean.TRUE, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_givenAUpdatedCategory(){
        final var originalName = "Brand new movie";
        final var originalDescription = "Most popular category";
        final var originalActivate = Boolean.TRUE;

        final var expectedName = "A New name";
        final var expectedDescription = " A Brand new description";
        final var expectedActivated = Boolean.TRUE;

        final var aCategory = Category.newCategory(originalName, originalDescription, originalActivate);

        Assertions.assertDoesNotThrow(()-> aCategory.validate(new ThrowNewValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedActivated);
        Assertions.assertEquals(aCategory.getId(),actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(Boolean.TRUE, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdateAsInactive_givenAUpdatedCategory(){
        final var originalName = "Brand new movie";
        final var originalDescription = "Most popular category";
        final var originalActivate = Boolean.TRUE;

        final var expectedName = "A New name";
        final var expectedDescription = " A Brand new description";
        final var expectedActivated = Boolean.FALSE;

        final var aCategory = Category.newCategory(originalName, originalDescription, originalActivate);

        Assertions.assertDoesNotThrow(()-> aCategory.validate(new ThrowNewValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedActivated);
        Assertions.assertEquals(aCategory.getId(),actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertFalse(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdateWithInvalidParameter_givenAUpdatedCategory(){
        final var originalName = "Brand new movie";
        final var originalDescription = "Most popular category";
        final var originalActivate = Boolean.TRUE;

        final String expectedName = null;
        final var expectedDescription = " A Brand new description";
        final var expectedActivated = Boolean.FALSE;

        final var aCategory = Category.newCategory(originalName, originalDescription, originalActivate);

        Assertions.assertDoesNotThrow(()-> aCategory.validate(new ThrowNewValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedActivated);
        Assertions.assertEquals(aCategory.getId(),actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertFalse(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }
}
