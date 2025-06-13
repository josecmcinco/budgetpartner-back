package com.budgetpartner.APP.repository;

import com.budgetpartner.APP.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Optional<Usuario> findByEmail(String email);
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> obtenerUsuarioPorEmail(@Param("email") String email);


}