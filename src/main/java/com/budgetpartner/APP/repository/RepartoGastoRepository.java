package com.budgetpartner.APP.repository;

import com.budgetpartner.APP.entity.RepartoGasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RepartoGastoRepository extends JpaRepository<RepartoGasto, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM RepartoGasto rg WHERE rg.miembro.id IN :miembroId")
    void eliminarRepartoGastoPorMiembroId(@Param("miembroId") List<Long> miembroId);

    @Modifying
    @Transactional
    @Query("DELETE FROM RepartoGasto rg WHERE rg.gasto.id IN :gastoId")
    void eliminarRepartoGastoPorGastoId(@Param("gastoId") Long gastoId);
}
