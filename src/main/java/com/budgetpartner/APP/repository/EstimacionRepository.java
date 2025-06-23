package com.budgetpartner.APP.repository;

import com.budgetpartner.APP.entity.Estimacion;
import com.budgetpartner.APP.entity.Rol;
import com.budgetpartner.APP.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstimacionRepository extends JpaRepository<Estimacion, Long> {


    @Query(value = "SELECT e.* " +
            "FROM estimacion e " +
            "WHERE e.plan_id = :planId;", nativeQuery = true)
    List<Estimacion> obtenerEstimacionesPorPlanId(@Param("planId") Long plan_Id);

}
