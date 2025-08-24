package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoPostRequest;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoResponse;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoUpdateRequest;
import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.enums.ModoPlan;
import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.enums.NombreRol;
import com.budgetpartner.APP.mapper.MiembroMapper;
import com.budgetpartner.APP.mapper.OrganizacionMapper;
import com.budgetpartner.APP.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrganizacionServiceTest {

    @InjectMocks
    private OrganizacionService organizacionService;

    @Mock
    private UsuarioService usuarioService;
    @Mock
    private OrganizacionRepository organizacionRepository;
    @Mock
    private MiembroRepository miembroRepository;
    @Mock
    private PlanRepository planRepository;
    @Mock
    private GastoRepository gastoRepository;
    @Mock
    private RolRepository rolRepository;
    @Mock
    private RepartoGastoRepository repartoGastoRepository;
    @Mock
    private TareaRepository tareaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostOrganizacion() {

        String nombreOrganizacion = "Organizacion1";
        String descOrganizacion = "Soy una descripción";
        String nickCreador = "nick1";
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 12, 0);

        OrganizacionDtoPostRequest request = new OrganizacionDtoPostRequest(nombreOrganizacion, descOrganizacion, nickCreador, MonedasDisponibles.EUR);
        Usuario usuario = new Usuario(3L, "carlos.martinez@mail.com", "Carlos", "Martínez", "contraseña789", date, date);
        Organizacion organizacion = new Organizacion(4L, nombreOrganizacion, descOrganizacion, MonedasDisponibles.EUR, date, date);
        Rol rol = new Rol(1L, NombreRol.ROLE_ADMIN, date, date );
        Miembro miembro = new Miembro(8L, usuario,  organizacion, rol, nickCreador, date, true, date, date );

        when(usuarioService.devolverUsuarioAutenticado()).thenReturn(usuario);
        when(organizacionRepository.save(any(Organizacion.class))).thenReturn(organizacion);
        when(rolRepository.obtenerRolPorNombre(any())).thenReturn(Optional.of(rol));
        when(miembroRepository.save(any(Miembro.class))).thenReturn(miembro);

        // Mock de mappers (si son métodos estáticos)
        try (MockedStatic<OrganizacionMapper> orgMapper = mockStatic(OrganizacionMapper.class);
             MockedStatic<MiembroMapper> miembroMapper = mockStatic(MiembroMapper.class)) {

            OrganizacionDtoResponse dtoResponse = new OrganizacionDtoResponse(4L, nombreOrganizacion, descOrganizacion, MonedasDisponibles.EUR);
            MiembroDtoResponse miembroDtoResponse = new MiembroDtoResponse(8L, rol.getId(), nickCreador, date, true, true);

            orgMapper.when(() -> OrganizacionMapper.toEntity(request)).thenReturn(organizacion);
            orgMapper.when(() -> OrganizacionMapper.toDtoResponse(organizacion)).thenReturn(dtoResponse);
            miembroMapper.when(() -> MiembroMapper.toDtoResponse(miembro)).thenReturn(miembroDtoResponse);

            //Ejecución del métod a probar
            OrganizacionDtoResponse result = organizacionService.postOrganizacion(request);

            //Comprobación del resultado
            assertNotNull(result);
            assertEquals(4L, result.getId());
            assertEquals(nombreOrganizacion, result.getNombre());
            assertEquals(descOrganizacion, result.getDescripcion());

            assertNotNull(result.getMiembros());
            assertEquals(1, result.getMiembros().size());
            assertEquals(miembroDtoResponse.getId(), result.getMiembros().get(0).getId());
            assertEquals(nickCreador, result.getMiembros().get(0).getNick());

            //Verificación de interacción con mocks
            verify(usuarioService).devolverUsuarioAutenticado();
            verify(organizacionRepository).save(any(Organizacion.class));
            verify(rolRepository).obtenerRolPorNombre(NombreRol.ROLE_ADMIN); // o el rol que esperes
            verify(miembroRepository).save(any(Miembro.class));

        }
    }


    @Test
    void testGetOrganizacionById() {
        Long orgId = 4L;
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 12, 0);

        Organizacion organizacion = new Organizacion(orgId, "Organizacion1", "Descripción de prueba", MonedasDisponibles.EUR, date, date);
        OrganizacionDtoResponse dtoResponse = new OrganizacionDtoResponse(orgId, organizacion.getNombre(), organizacion.getDescripcion(), MonedasDisponibles.EUR);

        when(organizacionRepository.findById(orgId)).thenReturn(Optional.of(organizacion));

        try (MockedStatic<OrganizacionMapper> orgMapper = mockStatic(OrganizacionMapper.class)) {
            orgMapper.when(() -> OrganizacionMapper.toDtoResponse(organizacion)).thenReturn(dtoResponse);

            OrganizacionDtoResponse result = organizacionService.getOrganizacionDtoById(orgId);

            assertNotNull(result);
            assertEquals(orgId, result.getId());
            assertEquals("Organizacion1", result.getNombre());
            assertEquals("Descripción de prueba", result.getDescripcion());

            verify(organizacionRepository).findById(orgId);
        }
    }

    @Test
    void testDeleteOrganizacionById() {
        Long orgId = 4L;
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 12, 0);

        Organizacion organizacion = new Organizacion(orgId, "Organizacion1", "Descripción", MonedasDisponibles.EUR,  date, date);

        // Crear planes con el constructor completo
        Plan plan1 = new Plan(1L, organizacion, "Plan1", "Desc Plan1", date, date, ModoPlan.simple, 0.0, 0.0, date, date);
        Plan plan2 = new Plan(2L, organizacion, "Plan2", "Desc Plan2", date, date, ModoPlan.simple, 0.0, 0.0, date, date);
        List<Plan> listaPlanes = List.of(plan1, plan2);

        // Mocks
        when(organizacionRepository.findById(orgId)).thenReturn(Optional.of(organizacion));
        when(planRepository.obtenerPlanesPorOrganizacionId(orgId)).thenReturn(listaPlanes);

        // Ejecución
        Organizacion result = organizacionService.deleteOrganizacionById(orgId);

        // Comprobaciones
        assertNotNull(result);
        assertEquals(orgId, result.getId());

        // Verificación de interacciones
        verify(organizacionRepository).findById(orgId);
        verify(planRepository).obtenerPlanesPorOrganizacionId(orgId);
        verify(planRepository).delete(plan1);
        verify(planRepository).delete(plan2);
        verify(organizacionRepository).delete(organizacion);
    }



    @Test
void testPatchOrganizacion() {
    Long orgId = 4L;
    LocalDateTime date = LocalDateTime.of(2025, 1, 1, 12, 0);

    Organizacion organizacionExistente = new Organizacion(orgId, "Organizacion1", "Desc antigua", MonedasDisponibles.EUR, date, date);
    OrganizacionDtoUpdateRequest updateRequest = new OrganizacionDtoUpdateRequest("Nuevo nombre", "Nueva descripción", MonedasDisponibles.EUR);

    when(organizacionRepository.findById(orgId)).thenReturn(Optional.of(organizacionExistente));
    when(organizacionRepository.save(any(Organizacion.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // ejecución
    organizacionService.patchOrganizacion(updateRequest, orgId);

    // comprobación
    assertEquals("Nuevo nombre", organizacionExistente.getNombre());
    assertEquals("Nueva descripción", organizacionExistente.getDescripcion());

    verify(organizacionRepository).findById(orgId);
    verify(organizacionRepository).save(organizacionExistente);
}
    @Test
    void testGetOrganizacionesDtoByUsuarioId() {
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 12, 0);

        // Usuario autenticado
        Usuario usuario = new Usuario(3L, "carlos.martinez@mail.com", "Carlos", "Martínez", "contraseña789", date, date);

        // Organizaciones
        Organizacion organizacion1 = new Organizacion(4L, "Organizacion1", "Descripción 1", MonedasDisponibles.EUR, date, date);
        Organizacion organizacion2 = new Organizacion(5L, "Organizacion2", "Descripción 2", MonedasDisponibles.EUR,  date, date);
        List<Organizacion> organizaciones = List.of(organizacion1, organizacion2);

        // DTOs de salida
        OrganizacionDtoResponse dto1 = new OrganizacionDtoResponse(4L, "Organizacion1", "Descripción 1", MonedasDisponibles.EUR);
        OrganizacionDtoResponse dto2 = new OrganizacionDtoResponse(5L, "Organizacion2", "Descripción 2", MonedasDisponibles.EUR);
        List<OrganizacionDtoResponse> listaDto = new ArrayList<>(List.of(dto1, dto2));

        // Mocks
        when(usuarioService.devolverUsuarioAutenticado()).thenReturn(usuario);
        when(organizacionRepository.obtenerOrganizacionesPorUsuarioId(usuario.getId())).thenReturn(organizaciones);
        when(miembroRepository.contarMiembrosPorOrganizacionId(4L)).thenReturn(3);
        when(miembroRepository.contarMiembrosPorOrganizacionId(5L)).thenReturn(5);

        try (MockedStatic<OrganizacionMapper> orgMapper = mockStatic(OrganizacionMapper.class)) {
            orgMapper.when(() -> OrganizacionMapper.toDtoResponseListOrganizacion(organizaciones)).thenReturn(listaDto);

            // Ejecución
            List<OrganizacionDtoResponse> result = organizacionService.getOrganizacionesDtoByUsuarioId();

            // Comprobaciones
            assertNotNull(result);
            assertEquals(2, result.size());

            assertEquals(4L, result.get(0).getId());
            assertEquals("Organizacion1", result.get(0).getNombre());
            assertEquals(3, result.get(0).getNumeroMiembros());

            assertEquals(5L, result.get(1).getId());
            assertEquals("Organizacion2", result.get(1).getNombre());
            assertEquals(5, result.get(1).getNumeroMiembros());

            // Verificación de interacciones
            verify(usuarioService).devolverUsuarioAutenticado();
            verify(organizacionRepository).obtenerOrganizacionesPorUsuarioId(usuario.getId());
            verify(miembroRepository).contarMiembrosPorOrganizacionId(4L);
            verify(miembroRepository).contarMiembrosPorOrganizacionId(5L);
        }
    }
}
