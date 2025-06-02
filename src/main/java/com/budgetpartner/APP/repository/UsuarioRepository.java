package com.budgetpartner.APP.repository;

import com.budgetpartner.APP.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
/*
    @Query("SELECT COUNT(m) FROM Miembro m WHERE m.usuario.id = :usuarioId")
    Integer contarMiembrosPorUsuario(@Param("usuarioId") Long usuarioId);

    @Query("SELECT COUNT(o) FROM Organizacion o " +
            "JOIN o.miembros m " +
            "WHERE m.usuario.id = :usuarioId")
    Integer contarOrganizacionesPorUsuario(@Param("usuarioId") Long usuarioId);

    @Query("SELECT COUNT(p) FROM Plan p " +
            "JOIN p.organizacion o " +
            "JOIN o.miembros m " +
            "WHERE m.usuario.id = :usuarioId")
    Integer contarPlanesPorUsuario(@Param("usuarioId") Long usuarioId);

    @Query("SELECT COUNT(t) FROM Tarea t " +
            "JOIN t.miembros m " +
            "WHERE m.usuario.id = :usuarioId")
    Integer contarTareasPorUsuario(@Param("usuarioId") Long usuarioId);

*/



}