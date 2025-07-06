package com.budgetpartner.APP.repository;

import com.budgetpartner.APP.entity.*;
        import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TareaRepository extends JpaRepository<Tarea, Long> {

        @Query(value = "SELECT COUNT(*) " +
                "FROM tarea t " +
                "JOIN reparto_tarea rt ON t.id = rt.tarea_id " +
                "JOIN miembro m ON m.id = rt.miembro_id " +
                "WHERE m.usuario_id = :usuarioId;", nativeQuery = true)
        Integer contarTareasPorUsuarioId(@Param("usuarioId") Long usuario_id);

        @Query(value = "SELECT t.* " +
                "FROM tarea t " +
                "WHERE t.plan_id = :planId;", nativeQuery = true)
        List<Tarea> obtenerTareasPorPlanId(@Param("planId") Long plan_Id);

}