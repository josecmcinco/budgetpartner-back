package com.budgetpartner.APP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.budgetpartner.APP.entity.*;
import org.springframework.security.core.parameters.P;

import java.util.Optional;


public interface GastoRepository extends JpaRepository<Gasto, Long> {
    Optional<Plan> findByplanId(Long plan_id);
    Optional<Tarea> findBytareaId(Long tarea_id);

}
