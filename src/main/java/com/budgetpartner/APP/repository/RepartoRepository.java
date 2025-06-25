package com.budgetpartner.APP.repository;

import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.entity.RepartoGasto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepartoRepository extends JpaRepository<RepartoGasto, Long> {
}
