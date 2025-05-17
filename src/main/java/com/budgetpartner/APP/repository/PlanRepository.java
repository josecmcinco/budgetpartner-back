package com.budgetpartner.APP.repository;

import com.budgetpartner.APP.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlanRepository extends JpaRepository<Plan, Long> {
}


