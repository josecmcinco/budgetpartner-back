package com.budgetpartner.APP.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.enums.NombreRol;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.RepartoGastoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class GastoServiceTest {

    @Mock
    private MiembroRepository miembroRepository;

    @Mock
    private RepartoGastoRepository repartoGastoRepository;

    @InjectMocks
    private GastoService gastoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostRepartoGastosRedondo() {
        //Creación de organización
        String nombreOrganizacion = "Organizacion1";
        String descOrganizacion = "Soy una descripción";
        String nickCreador = "nick1";
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 12, 0);
        Organizacion organizacion = new Organizacion(1L, nombreOrganizacion, descOrganizacion, date, date);

        //Creación miembro pagador
        Rol rol = new Rol(1L, NombreRol.ROLE_ADMIN, date, date );
        Miembro pagador = new Miembro(1L, null,  organizacion, rol, nickCreador, date, true, date, date );

        //Otros miembros
        Miembro miembro1 = new Miembro(2L, null, organizacion, rol, nickCreador, date, true, date, date);
        Miembro miembro2 = new Miembro(3L, null, organizacion, rol, nickCreador, date, true, date, date);
        Miembro miembro3 = new Miembro(4L, null, organizacion, rol, nickCreador, date, true, date, date);

        List<Long> idMiembros = Arrays.asList(1L, 2L, 3L, 4L);

        // Gasto
        double cantidad = 200.0;
        Gasto gasto = new Gasto(10L, null, null, cantidad, "Gasto1", pagador, "Descripcion", null, date, date);

        // Mockear respuestas del repo
        when(miembroRepository.findById(1L)).thenReturn(Optional.of(pagador));
        when(miembroRepository.findById(2L)).thenReturn(Optional.of(miembro1));
        when(miembroRepository.findById(3L)).thenReturn(Optional.of(miembro2));
        when(miembroRepository.findById(4L)).thenReturn(Optional.of(miembro3));

        //Ejecución
        gastoService.postRepartoGastos(idMiembros, gasto, pagador);

        //Verify
        ArgumentCaptor<RepartoGasto> captor = ArgumentCaptor.forClass(RepartoGasto.class);
        verify(repartoGastoRepository, times(4)).save(captor.capture());

        List<RepartoGasto> savedRepartos = captor.getAllValues();

        // Revisamos los cálculos:
        // - cada uno paga 50, pero el pico (si existiera) se asigna al pagador
        RepartoGasto repartoPagador = savedRepartos.stream()
                .filter(r -> r.getMiembro().getId().equals(1L))
                .findFirst()
                .orElseThrow();

        RepartoGasto repartoOtro = savedRepartos.stream()
                .filter(r -> r.getMiembro().getId().equals(2L))
                .findFirst()
                .orElseThrow();

        assertEquals(BigDecimal.valueOf(150.0), repartoPagador.getCantidad());
        assertEquals(BigDecimal.valueOf(-50.0), repartoOtro.getCantidad());
    }

    @Test
    void testPostRepartoGastosConPico() {
        //Creación de organización
        String nombreOrganizacion = "Organizacion1";
        String descOrganizacion = "Soy una descripción";
        String nickCreador = "nick1";
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 12, 0);
        Organizacion organizacion = new Organizacion(1L, nombreOrganizacion, descOrganizacion, date, date);

        //Creación miembro pagador
        Rol rol = new Rol(1L, NombreRol.ROLE_ADMIN, date, date );
        Miembro pagador = new Miembro(1L, null,  organizacion, rol, nickCreador, date, true, date, date );

        //Otros miembros
        Miembro miembro1 = new Miembro(2L, null, organizacion, rol, nickCreador, date, true, date, date);
        Miembro miembro2 = new Miembro(3L, null, organizacion, rol, nickCreador, date, true, date, date);
        Miembro miembro3 = new Miembro(4L, null, organizacion, rol, nickCreador, date, true, date, date);

        List<Long> idMiembros = Arrays.asList(1L, 2L, 3L, 4L);

        // Gasto
        double cantidad = 4.3;
        Gasto gasto = new Gasto(10L, null, null, cantidad, "Gasto1", pagador, "Descripcion", null, date, date);

        // Mockear respuestas del repo
        when(miembroRepository.findById(1L)).thenReturn(Optional.of(pagador));
        when(miembroRepository.findById(2L)).thenReturn(Optional.of(miembro1));
        when(miembroRepository.findById(3L)).thenReturn(Optional.of(miembro2));
        when(miembroRepository.findById(4L)).thenReturn(Optional.of(miembro3));

        //Ejecución
        gastoService.postRepartoGastos(idMiembros, gasto, pagador);

        //Verify
        ArgumentCaptor<RepartoGasto> captor = ArgumentCaptor.forClass(RepartoGasto.class);
        verify(repartoGastoRepository, times(4)).save(captor.capture());

        List<RepartoGasto> savedRepartos = captor.getAllValues();

        // Revisamos los cálculos:
        // - cada uno paga 50, pero el pico (si existiera) se asigna al pagador
        RepartoGasto repartoPagador = savedRepartos.stream()
                .filter(r -> r.getMiembro().getId().equals(1L))
                .findFirst()
                .orElseThrow();

        RepartoGasto repartoOtro = savedRepartos.stream()
                .filter(r -> r.getMiembro().getId().equals(2L))
                .findFirst()
                .orElseThrow();

        assertEquals(BigDecimal.valueOf(3.0), repartoPagador.getCantidad());
        assertEquals(BigDecimal.valueOf(-1.0), repartoOtro.getCantidad());
    }
}
