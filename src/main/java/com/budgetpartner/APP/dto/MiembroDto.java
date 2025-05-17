package com.budgetpartner.APP.dto;

import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Rol;
import com.budgetpartner.APP.entity.Usuario;

import java.time.LocalDateTime;

public class MiembroDto {

    private Long id;

    private Usuario usuarioOrigen;

    private Organizacion organizacionOrigen;

    private Rol rolMiembro;

    private String nick;

    private LocalDateTime fechaIngreso;

    private boolean isActivo;




}
