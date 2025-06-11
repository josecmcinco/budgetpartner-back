package com.budgetpartner.APP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.budgetpartner.APP.entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MiembroRepository extends JpaRepository<Miembro, Long> {
    List<Miembro> findByusuarioOrigenId(Long usuario_id);

    @Query(value = "SELECT COUNT(*) FROM miembro m where m.usuario_id = :ususarioId", nativeQuery = true)
    Integer contarMiembrosPorUsuario(@Param("usuarioId") Long usuarioId);
}