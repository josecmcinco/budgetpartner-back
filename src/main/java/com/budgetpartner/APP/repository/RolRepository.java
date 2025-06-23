package com.budgetpartner.APP.repository;

import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.enums.NombreRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface RolRepository extends JpaRepository<Rol, Long> {
    @Query("SELECT r FROM Rol r WHERE r.nombre = :rol_nombre")
    Optional<Rol> obtenerRolPorNombre(@Param("rol_nombre") NombreRol rol_nombre);

}