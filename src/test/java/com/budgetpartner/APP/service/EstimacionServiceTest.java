package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.estimacion.EstimacionDtoPostRequest;
import com.budgetpartner.APP.dto.estimacion.EstimacionDtoResponse;
import com.budgetpartner.APP.dto.estimacion.EstimacionDtoUpdateRequest;
import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.enums.TipoEstimacion;
import com.budgetpartner.APP.mapper.EstimacionMapper;
import com.budgetpartner.APP.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

class EstimacionServiceTest {

    @InjectMocks
    private EstimacionService estimacionService;

    @Mock
    private EstimacionRepository estimacionRepository;
    @Mock
    private PlanRepository planRepository;
    @Mock
    private MiembroRepository miembroRepository;
    @Mock
    private GastoRepository gastoRepository;
    @Mock
    private TareaRepository tareaRepository;
    @Mock
    private RepartoGastoRepository repartoGastoRepository;
    @Mock
    private OrganizacionRepository organizacionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostEstimacion() {
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 12, 0);

        // Datos de prueba
        EstimacionDtoPostRequest request = new EstimacionDtoPostRequest(
                1L,  // planId
                null, // tareaId
                2L,  // creadorId
                100.0,
                TipoEstimacion.ESTIMACION_PLAN,
                MonedasDisponibles.EUR,
                "Descripcion prueba",
                3L,  // pagadorId
                4L   // gastoId
        );

        Plan plan = new Plan();
        Miembro creador = new Miembro();
        Miembro pagador = new Miembro();
        Gasto gasto = new Gasto();
        Estimacion estimacion = new Estimacion(1L, plan, null, creador, 100.0, TipoEstimacion.ESTIMACION_PLAN, MonedasDisponibles.EUR, "Descripcion prueba", date, date, pagador, gasto);
        EstimacionDtoResponse dtoResponse = new EstimacionDtoResponse(
                8L,
                1L,  // planId
                null, // tareaId
                2L,  // creadorId
                100.0,
                TipoEstimacion.ESTIMACION_PLAN,
                MonedasDisponibles.EUR,
                "Descripcion prueba",
                3L,  // pagadorId
                4L   // gastoId

        );

        // Mocks
        when(planRepository.findById(1L)).thenReturn(Optional.of(plan));
        when(miembroRepository.findById(2L)).thenReturn(Optional.of(creador));
        when(miembroRepository.findById(3L)).thenReturn(Optional.of(pagador));
        when(gastoRepository.findById(4L)).thenReturn(Optional.of(gasto));

        when(estimacionRepository.save(any(Estimacion.class))).thenReturn(estimacion);

        try (MockedStatic<EstimacionMapper> mapper = mockStatic(EstimacionMapper.class)) {
            mapper.when(() -> EstimacionMapper.toEntity(request, null, plan, creador, pagador, gasto)).thenReturn(estimacion);
            mapper.when(() -> EstimacionMapper.toDtoResponse(estimacion)).thenReturn(dtoResponse);

            EstimacionDtoResponse result = estimacionService.postEstimacion(request);

            assertNotNull(result);
            assertEquals(dtoResponse, result);

            verify(planRepository).findById(1L);
            verify(miembroRepository).findById(2L);
            verify(miembroRepository).findById(3L);
            verify(gastoRepository).findById(4L);
            verify(estimacionRepository).save(estimacion);
        }
    }

    @Test
    void testGetEstimacionDtoById() {

        Estimacion estimacion = new Estimacion();
        EstimacionDtoResponse dtoResponse = new EstimacionDtoResponse(
                8L,
                1L,  // planId
                null, // tareaId
                2L,  // creadorId
                100.0,
                TipoEstimacion.ESTIMACION_PLAN,
                MonedasDisponibles.EUR,
                "Descripcion prueba",
                3L,  // pagadorId
                4L   // gastoId

        );

        when(estimacionRepository.findById(1L)).thenReturn(Optional.of(estimacion));

        try (MockedStatic<EstimacionMapper> mapper = mockStatic(EstimacionMapper.class)) {
            mapper.when(() -> EstimacionMapper.toDtoResponse(estimacion)).thenReturn(dtoResponse);

            EstimacionDtoResponse result = estimacionService.getEstimacionDtoById(1L);

            assertNotNull(result);
            assertEquals(dtoResponse, result);
            verify(estimacionRepository).findById(1L);
        }
    }
    @Test
    void testPatchEstimacion() {

        Estimacion estimacionExistente = new Estimacion();
        EstimacionDtoUpdateRequest updateRequest = new EstimacionDtoUpdateRequest(150.0, MonedasDisponibles.EUR, "Actualizado", 3L, 4L);

        Miembro pagador = new Miembro();
        Gasto gasto = new Gasto();

        when(estimacionRepository.findById(1L)).thenReturn(Optional.of(estimacionExistente));
        when(miembroRepository.findById(3L)).thenReturn(Optional.of(pagador));
        when(gastoRepository.findById(4L)).thenReturn(Optional.of(gasto));

        try (MockedStatic<EstimacionMapper> mapper = mockStatic(EstimacionMapper.class)) {
            // Se espera que updateEntityFromDto sea llamado
            estimacionService.patchEstimacion(updateRequest, 1L);
            mapper.verify(() -> EstimacionMapper.updateEntityFromDto(updateRequest, estimacionExistente, pagador, gasto));
        }

        verify(estimacionRepository).findById(1L);
        verify(miembroRepository).findById(3L);
        verify(gastoRepository).findById(4L);
    }

    @Test
    void testDeleteEstimacionById() {
        Estimacion estimacion = new Estimacion();

        when(estimacionRepository.findById(1L)).thenReturn(Optional.of(estimacion));

        estimacionService.deleteEstimacionById(1L);

        verify(estimacionRepository).findById(1L);
        verify(estimacionRepository).delete(estimacion);
    }



}
