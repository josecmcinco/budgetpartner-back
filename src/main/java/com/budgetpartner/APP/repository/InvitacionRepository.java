package com.budgetpartner.APP.repository;

import com.budgetpartner.APP.entity.Gasto;
import com.budgetpartner.APP.entity.Invitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InvitacionRepository extends JpaRepository<Invitacion, String> {

    @Query("SELECT i FROM Invitacion i WHERE i.organizacion.id = :organizacionId AND i.isActiva = true")
    Optional<Invitacion> obtenerInvitacionPorOrganizacionId(@Param("organizacionId") Long organizacionId);
}
