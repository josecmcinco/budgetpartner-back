package com.budgetpartner.APP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.budgetpartner.APP.entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MiembroRepository extends JpaRepository<Miembro, Long> {

    @Query("SELECT m FROM Miembro m WHERE m.usuario.id = :usuario_id")
    List<Miembro> obtenerMiembrosPorUsuarioId(@Param("usuario_id") Long usuario_id);

    @Query(value = "SELECT COUNT(*) FROM miembro m where m.usuario_id = :usuarioId", nativeQuery = true)
    Integer contarMiembrosPorUsuarioId(@Param("usuarioId") Long usuario_id);

    @Query(value = "SELECT COUNT(*) FROM miembro m where m.organizacion_Id = :organizacionId", nativeQuery = true)
    Integer contarMiembrosPorOrganizacionId(@Param("organizacionId") Long organizacionId);

    @Query(value = "SELECT m.* FROM miembro m where m.organizacion_Id = :organizacionId", nativeQuery = true)
    List<Miembro> obtenerMiembrosPorOrganizacionId(@Param("organizacionId") Long organizacionId);



}