package com.budgetpartner.APP.repository;

import com.budgetpartner.APP.entity.*;
        import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TareaRepository extends JpaRepository<Tarea, Long> {

        @Query(value = "SELECT COUNT(*) " +
                "FROM tarea t " +
                "JOIN miembro_tarea mt ON t.id = mt.tarea_id " +
                "JOIN miembro m ON m.id = mt.miembro_id " +
                "WHERE m.usuario_id = :usuarioId;", nativeQuery = true)
        Integer contarTareasPorUsuarioId(@Param("usuarioId") Long usuario_id);

}