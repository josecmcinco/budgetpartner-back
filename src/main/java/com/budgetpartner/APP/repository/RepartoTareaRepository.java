package com.budgetpartner.APP.repository;

import com.budgetpartner.APP.entity.RepartoTarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RepartoTareaRepository extends JpaRepository<RepartoTarea, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM RepartoTarea rt WHERE rt.miembro.id IN :miembroId")
    void eliminarRepartoTareaPorMiembroId(@Param("miembroId") List<Long> miembroId);

    @Modifying
    @Transactional
    @Query("DELETE FROM RepartoTarea rt WHERE rt.tarea.id IN :tareaId")
    void eliminarRepartoTareaPorTareaId(@Param("tareaId") Long tareaId);
}
