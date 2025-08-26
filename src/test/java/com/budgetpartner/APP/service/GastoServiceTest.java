package com.budgetpartner.APP.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.enums.ModoPlan;
import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.enums.NombreRol;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.OrganizacionRepository;
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

    @Mock
    private OrganizacionRepository organizacionRepository;

    @Mock
    private DivisaService divisaService;

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
        Organizacion organizacion = new Organizacion(1L, nombreOrganizacion, descOrganizacion, MonedasDisponibles.EUR, date, date);

        Plan plan = new Plan(
                1L,
                organizacion,
                "Viaje a Tokio",
                "Plan de gastos para el viaje a Tokio",
                LocalDateTime.of(2025, 8, 1, 10, 0),
                LocalDateTime.of(2025, 8, 10, 18, 0),
                ModoPlan.simple,
                35.6895,
                139.6917,
                date,
                date
        );

        //Creación miembro pagador
        Rol rol = new Rol(1L, NombreRol.ROLE_ADMIN, date, date );
        Miembro pagador = new Miembro(1L, null,  organizacion, rol, nickCreador, date, true,false, date, date );

        //Otros miembros
        Miembro miembro1 = new Miembro(2L, null, organizacion, rol, nickCreador, date, true,false, date, date);
        Miembro miembro2 = new Miembro(3L, null, organizacion, rol, nickCreador, date, true,false, date, date);
        Miembro miembro3 = new Miembro(4L, null, organizacion, rol, nickCreador, date, true,false, date, date);

        List<Long> idMiembros = Arrays.asList(1L, 2L, 3L, 4L);

        // Gasto
        double cantidad = 200.0;
        Gasto gasto = new Gasto(10L, null, plan, cantidad, "Gasto1", pagador, "Descripcion", MonedasDisponibles.EUR, null, date, date);

        // Mockear respuestas del repo
        when(miembroRepository.findById(1L)).thenReturn(Optional.of(pagador));
        when(miembroRepository.findById(2L)).thenReturn(Optional.of(miembro1));
        when(miembroRepository.findById(3L)).thenReturn(Optional.of(miembro2));
        when(miembroRepository.findById(4L)).thenReturn(Optional.of(miembro3));

        when(organizacionRepository.obtenerOrganizacionPorPlanId(1L)).thenReturn(Optional.of(organizacion));

        // Mockear divisaService (nunca se debería de usar)
        when(divisaService.convertCurrency(anyDouble(), any(), any()))
                .thenAnswer(invocation -> {
                    double amount = invocation.getArgument(0); // devuelve el primer argumento
                    return amount * 1.2;    // conversión simulada
                });

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
        Organizacion organizacion = new Organizacion(1L, nombreOrganizacion, descOrganizacion, MonedasDisponibles.EUR, date, date);

        Plan plan = new Plan(
                1L,
                organizacion,
                "Viaje a Tokio",
                "Plan de gastos para el viaje a Tokio",
                LocalDateTime.of(2025, 8, 1, 10, 0),
                LocalDateTime.of(2025, 8, 10, 18, 0),
                ModoPlan.simple,
                35.6895,
                139.6917,
                date,
                date
        );
        //Creación miembro pagador
        Rol rol = new Rol(1L, NombreRol.ROLE_ADMIN, date, date );
        Miembro pagador = new Miembro(1L, null,  organizacion, rol, nickCreador, date, true, false, date, date );

        //Otros miembros
        Miembro miembro1 = new Miembro(2L, null, organizacion, rol, nickCreador, date, true, false, date, date);
        Miembro miembro2 = new Miembro(3L, null, organizacion, rol, nickCreador, date, true, false, date, date);
        Miembro miembro3 = new Miembro(4L, null, organizacion, rol, nickCreador, date, true, false, date, date);

        List<Long> idMiembros = Arrays.asList(1L, 2L, 3L, 4L);

        // Gasto
        double cantidad = 4.3;
        Gasto gasto = new Gasto(10L, null, plan, cantidad, "Gasto1", pagador, "Descripcion", MonedasDisponibles.EUR, null, date, date);

        // Mockear respuestas del repo
        when(miembroRepository.findById(1L)).thenReturn(Optional.of(pagador));
        when(miembroRepository.findById(2L)).thenReturn(Optional.of(miembro1));
        when(miembroRepository.findById(3L)).thenReturn(Optional.of(miembro2));
        when(miembroRepository.findById(4L)).thenReturn(Optional.of(miembro3));


        when(organizacionRepository.obtenerOrganizacionPorPlanId(1L)).thenReturn(Optional.of(organizacion));

        // Mockear divisaService (nunca se debería de usar)
        when(divisaService.convertCurrency(anyDouble(), any(), any()))
                .thenAnswer(invocation -> {
                    double amount = invocation.getArgument(0); // devuelve el primer argumento
                    return amount * 1.2;    // conversión simulada
                });

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

    @Test
    void testPostRepartoGastosMonedaDiferenteSinPico() {
        // Creación de organización en EUR
        String nombreOrganizacion = "Organizacion1";
        String descOrganizacion = "Soy una descripción";
        String nickCreador = "nick1";
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 12, 0);
        Organizacion organizacion = new Organizacion(1L, nombreOrganizacion, descOrganizacion, MonedasDisponibles.EUR, date, date);

        Plan plan = new Plan(
                1L,
                organizacion,
                "Viaje a Tokio",
                "Plan de gastos para el viaje a Tokio",
                LocalDateTime.of(2025, 8, 1, 10, 0),
                LocalDateTime.of(2025, 8, 10, 18, 0),
                ModoPlan.simple,
                35.6895,
                139.6917,
                date,
                date
        );

        // Creación miembro pagador
        Rol rol = new Rol(1L, NombreRol.ROLE_ADMIN, date, date);
        Miembro pagador = new Miembro(1L, null, organizacion, rol, nickCreador, date, true, false, date, date);

        // Otros miembros
        Miembro miembro1 = new Miembro(2L, null, organizacion, rol, nickCreador, date, true, false, date, date);
        Miembro miembro2 = new Miembro(3L, null, organizacion, rol, nickCreador, date, true, false, date, date);
        Miembro miembro3 = new Miembro(4L, null, organizacion, rol, nickCreador, date, true, false, date, date);

        List<Long> idMiembros = Arrays.asList(1L, 2L, 3L, 4L);

        // Gasto en USD
        double cantidad = 6.0;
        Gasto gasto = new Gasto(10L, null, plan, cantidad, "Gasto1", pagador, "Descripcion",
                MonedasDisponibles.USD, null, date, date);

        // Mockear miembros
        when(miembroRepository.findById(1L)).thenReturn(Optional.of(pagador));
        when(miembroRepository.findById(2L)).thenReturn(Optional.of(miembro1));
        when(miembroRepository.findById(3L)).thenReturn(Optional.of(miembro2));
        when(miembroRepository.findById(4L)).thenReturn(Optional.of(miembro3));

        // Mockear organización
        when(organizacionRepository.obtenerOrganizacionPorPlanId(plan.getId())).thenReturn(Optional.of(organizacion));

        // Mockear conversión: USD → EUR multiplicando por 0.5 (simulado)
        when(divisaService.convertCurrency(anyDouble(), eq(MonedasDisponibles.USD), eq(MonedasDisponibles.EUR)))
                .thenAnswer(invocation -> {
                    double amount = invocation.getArgument(0);
                    return amount * 0.5; // Simula conversión
                });

        // Ejecución
        gastoService.postRepartoGastos(idMiembros, gasto, pagador);

        // Verify
        ArgumentCaptor<RepartoGasto> captor = ArgumentCaptor.forClass(RepartoGasto.class);
        verify(repartoGastoRepository, times(4)).save(captor.capture());

        List<RepartoGasto> savedRepartos = captor.getAllValues();

        // Revisamos los cálculos: gasto total 6 USD → 3 EUR convertido
        RepartoGasto repartoPagador = savedRepartos.stream()
                .filter(r -> r.getMiembro().getId().equals(1L))
                .findFirst()
                .orElseThrow();

        RepartoGasto repartoOtro = savedRepartos.stream()
                .filter(r -> r.getMiembro().getId().equals(2L))
                .findFirst()
                .orElseThrow();

        // Total convertido 6 USD * 0.5 = 3 EUR
        // Deuda por persona = 3 EUR / 4 = 0.75
        assertEquals(BigDecimal.valueOf(2.25), repartoPagador.getCantidad()); // pagador recibe 3*(3/4) = 2.25
        assertEquals(BigDecimal.valueOf(-0.75), repartoOtro.getCantidad());    // los demás -0.75
    }
}
