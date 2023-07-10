package com.mortimer.command;

import static org.assertj.core.api.Assertions.assertThat;
import com.mortimer.command.controller.ProductCommandController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {
    @Autowired
    private ProductCommandController controller;

    @Test
    public void contextLoads(){
        assertThat(controller).isNotNull();
    }
}
