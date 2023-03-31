package com.daria.realestate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class RealEstateApplicationTest {

    @Test
    void contextLoads() {
    }

}