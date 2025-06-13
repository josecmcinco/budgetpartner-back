package com.budgetpartner.APP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.budgetpartner.APP.entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Optional;


public interface GastoRepository extends JpaRepository<Gasto, Long> {

    @Query("SELECT p FROM Plan p WHERE p.id = :plan_id")
    Optional<Plan> obtenerGastoPorPlanId(@Param("plan_id") Long plan_id);

    @Query("SELECT t FROM Tarea t WHERE t.id = :tarea_id")
    Optional<Tarea> obtenerGastoFromTareaId(@Param("tarea_id") Long tarea_id);

}
