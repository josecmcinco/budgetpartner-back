package com.budgetpartner.APP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.budgetpartner.APP.entity.*;

import java.util.List;


public interface MiembroRepository extends JpaRepository<Miembro, Long> {
    List<Long> findByusuarioOrigen_id(Long id_usuario);
}