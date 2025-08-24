package com.budgetpartner.APP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.budgetpartner.APP.entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface GastoRepository extends JpaRepository<Gasto, Long> {

    @Query("SELECT g FROM Gasto g WHERE g.plan.id = :plan_id")
    List<Gasto> obtenerGastosPorPlanId(@Param("plan_id") Long plan_id);

    @Query("SELECT g FROM Gasto g WHERE g.tarea.id = :tarea_id")
    List<Gasto> obtenerGastosPorTareaId(@Param("tarea_id") Long tarea_id);

    @Query("SELECT rg.miembro FROM RepartoGasto rg WHERE rg.gasto.id = :gastoId")
    List<Miembro> findMiembrosByGastoId(@Param("gastoId") Long gastoId);

}
