package com.budgetpartner.APP.repository;

import com.budgetpartner.APP.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Query(value = "SELECT COUNT(*)" +
            "FROM plan p" +
            "JOIN organizacion o ON p.organizacion_id = o.id" +
            "JOIN miembro m ON m.organizacion_id = o.id" +
            "WHERE m.usuario_id = :usuarioId;", nativeQuery = true)
    Integer contarPlanesPorUsuario(@Param("usuarioId") Long usuarioId);
}


