package com.budgetpartner.APP.repository;

import com.budgetpartner.config.PobladorTestConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(PobladorTestConfig.class)
public class EstimacionRepositoryTest {
}
