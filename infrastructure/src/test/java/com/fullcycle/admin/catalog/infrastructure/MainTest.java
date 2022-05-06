package com.fullcycle.admin.catalog.infrastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    public void newMain(){
        Assertions.assertNotNull(new Main());
    }
}
