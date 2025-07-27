package com.budgetpartner.APP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.budgetpartner.APP.entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface OrganizacionRepository extends JpaRepository<Organizacion, Long> {

    @Query(value = "SELECT o.* " +
    "FROM organizacion o " +
    "JOIN miembro m ON m.organizacion_id = o.id " +
     "WHERE m.usuario_id = :usuarioId", nativeQuery = true)
    List<Organizacion> obtenerOrganizacionesPorUsuarioId(@Param("usuarioId") Long usuario_id);

    @Query(value = "SELECT o.* " +
            "FROM organizacion o " +
            "WHERE o.plan_id = :planId ", nativeQuery = true)
    Optional<Organizacion> obtenerOrganizacionPorPlanId(@Param("planId") Long plan_id);

}