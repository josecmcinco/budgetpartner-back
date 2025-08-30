package com.budgetpartner.APP.service;

import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.enums.NombreRol;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.repository.GastoRepository;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.PlanRepository;
import com.budgetpartner.APP.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Servicio de autorización y permisos.
 * Se encarga de obtener el usuario autenticado y verificar permisos jerárquicos sobre organizaciones y planes.
 */
@Service("authService") // "authService" es el nombre con el que se usará en @PreAuthorize
public class AutorizacionService {

    private final UsuarioRepository usuarioRepository;
    private final MiembroRepository miembroRepository;
    private final PlanRepository planRepository;

    public AutorizacionService(UsuarioRepository usuarioRepository,
                                MiembroRepository miembroRepository,
                                PlanRepository planRepository) {
        this.usuarioRepository = usuarioRepository;
        this.miembroRepository = miembroRepository;
        this.planRepository = planRepository;
    }


    /**
     * Devuelve el usuario autenticado a partir del token en el contexto de seguridad.
     *
     * @return usuario autenticado
     * @throws NotFoundException si no se encuentra el usuario
     */
    public Usuario devolverUsuarioAutenticado(){
        String usuarioEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        return usuarioRepository.obtenerUsuarioPorEmail(usuarioEmail)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + usuarioEmail));
    }

    /**
     * Determina si un rol tiene jerarquía suficiente sobre otro.
     *
     * @param actual   rol actual del usuario
     * @param requerido rol requerido para la acción
     * @return true si tiene permisos, false si no
     */
    private boolean tienePermisoJerarquico(NombreRol actual, NombreRol requerido) {
        return switch (actual) {
            case ROLE_SUPER -> true; // Super siempre puede todo
            case ROLE_ADMIN -> requerido == NombreRol.ROLE_ADMIN || requerido == NombreRol.ROLE_MEMBER;
            case ROLE_MEMBER -> requerido == NombreRol.ROLE_MEMBER;
            default -> false;
        };
    }

    /**
     * Verifica si el usuario autenticado tiene permiso sobre una organización específica.
     *
     * @param orgId       id de la organización
     * @param rolRequerido rol mínimo requerido
     * @return true si tiene permisos, false si no
     */
    public boolean hasPermission(Long orgId, NombreRol rolRequerido) {
        Usuario usuario = devolverUsuarioAutenticado();

        return miembroRepository.obtenerMiembroPorUsuarioYOrgId(usuario.getId(), orgId)
                .map(miembro -> tienePermisoJerarquico(miembro.getRol().getNombre(), rolRequerido))
                .orElse(false);
    }

    /**
     * Verifica si el usuario autenticado tiene permiso sobre un plan específico.
     *
     * @param planId       id del plan
     * @param rolRequerido rol mínimo requerido
     * @return true si tiene permisos, false si no
     */
    public boolean hasPermissionOverPlan(Long planId, NombreRol rolRequerido) {
        return planRepository.findById(planId)
                .map(plan -> hasPermission(plan.getOrganizacion().getId(), rolRequerido))
                .orElse(false);
    }

}