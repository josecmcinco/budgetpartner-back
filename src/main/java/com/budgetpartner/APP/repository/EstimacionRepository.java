package com.budgetpartner.APP.repository;

import com.budgetpartner.APP.entity.Estimacion;
import com.budgetpartner.APP.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstimacionRepository extends JpaRepository<Estimacion, Long> {
}
