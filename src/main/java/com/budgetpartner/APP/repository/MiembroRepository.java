package com.budgetpartner.APP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.budgetpartner.APP.entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MiembroRepository extends JpaRepository<Miembro, Long> {
    //List<Miembro> findByusuarioOrigenId(Long usuario_id);
    @Query("SELECT m FROM Miembro m WHERE m.usuarioOrigen.id = :usuario_id")
    List<Miembro> obtenerMiembroPorUsuarioId(@Param("usuario_id") Long usuario_id);

    @Query(value = "SELECT COUNT(*) FROM miembro m where m.usuario_id = :usuarioId", nativeQuery = true)
    Integer contarMiembrosPorUsuarioId(@Param("usuarioId") Long usuario_id);

    @Query(value = "SELECT * FROM miembro m where m.usuario_id = :usuarioId", nativeQuery = true)
    List<Miembro> obtenerMiembrosPorUsuarioId(@Param("usuarioId") Long usuario_id);
}