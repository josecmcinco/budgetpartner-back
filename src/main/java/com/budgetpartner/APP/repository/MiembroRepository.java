package com.budgetpartner.APP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.budgetpartner.APP.entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface MiembroRepository extends JpaRepository<Miembro, Long> {

    @Query("SELECT m FROM Miembro m WHERE m.usuario.id = :usuario_id")
    List<Miembro> obtenerMiembrosPorUsuarioId(@Param("usuario_id") Long usuario_id);

    @Query(value = "SELECT COUNT(*) FROM miembro m where m.usuario_id = :usuarioId", nativeQuery = true)
    Integer contarMiembrosPorUsuarioId(@Param("usuarioId") Long usuario_id);

    @Query(value = "SELECT COUNT(*) FROM miembro m where m.organizacion_Id = :organizacionId AND m.is_activo = TRUE", nativeQuery = true)
    Integer contarMiembrosPorOrganizacionId(@Param("organizacionId") Long organizacionId);

    @Query(value = "SELECT m.id FROM miembro m where m.organizacion_Id = :organizacionId AND m.is_activo = TRUE", nativeQuery = true)
    List<Long> obtenerMiembrosActivosIdPorOrganizacionId(@Param("organizacionId") Long organizacionId);

    @Query(value = "SELECT m.* FROM miembro m where m.organizacion_Id = :organizacionId AND m.is_asociado = FALSE", nativeQuery = true)
    List<Miembro> obtenerMiembrosInactivosIdPorOrganizacionId(@Param("organizacionId") Long organizacionId);


    @Query(value = "SELECT m.* FROM miembro m where m.organizacion_Id = :organizacionId", nativeQuery = true)
    List<Miembro> obtenerMiembrosPorOrganizacionId(@Param("organizacionId") Long organizacionId);

    @Query("SELECT m FROM Miembro m WHERE m.usuario.id = :usuario_id AND m.organizacion.id = :organizacion_id")
    Optional<Miembro> obtenerMiembroPorUsuarioYOrgId(@Param("usuario_id") Long usuario_id, @Param("organizacion_id") Long organizacion_id);


}