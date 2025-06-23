package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.gasto.GastoDtoResponse;
import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoPostRequest;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoResponse;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoUpdateRequest;
import com.budgetpartner.APP.dto.plan.PlanDtoResponse;
import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.enums.NombreRol;
import com.budgetpartner.APP.mapper.GastoMapper;
import com.budgetpartner.APP.mapper.MiembroMapper;
import com.budgetpartner.APP.mapper.OrganizacionMapper;
import com.budgetpartner.APP.mapper.PlanMapper;
import com.budgetpartner.APP.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budgetpartner.APP.exceptions.NotFoundException;

import java.util.List;

@Service
public class OrganizacionService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private OrganizacionRepository organizacionRepository;
    @Autowired
    private MiembroRepository miembroRepository;
    @Autowired
    private GastoRepository gastoRepository;
    @Autowired
    private TareaRepository tareaRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private RolRepository rolRepository;


    //ENDPOINTS

    //Llamada para Endpoint
    //Crea una Entidad usando el DTO recibido por el usuario
    public Organizacion postOrganizacion(OrganizacionDtoPostRequest organizacionDtoReq) {
        //TODO VARIABLES REPETIDAS (NOMBRE, CÓDIGO, ETC. según tu lógica de negocio)

        Organizacion organizacion = OrganizacionMapper.toEntity(organizacionDtoReq);
        organizacionRepository.save(organizacion);

        //Obtener el rol para poder meter el Miembro en la DB

        Rol rol = rolRepository.obtenerRolPorNombre(NombreRol.ROLE_ADMIN)
                .orElseThrow(() -> new NotFoundException("ERROR INTERNO: Miembro no encontrada con el nombre: ROLE_ADMIN"));

        Miembro miembro = new Miembro(organizacion, rol, organizacionDtoReq.getNickMiembroCreador(), true);

        //Asociar el usuario al miembro
        Usuario usuario = usuarioService.devolverUsuarioAutenticado();
        miembro.asociarUsuario(usuario);

        //Guardar miembro en la DB recién creada
        miembroRepository.save(miembro);

        return organizacion;
    }

    //Llamada para Endpoint
    //Obtiene una Entidad usando el id recibido por el usuario
        /*DEVUELVE AL USUARIO:

    */
    public OrganizacionDtoResponse getOrganizacionDtoById(Long id) {
        //Obtener organización usando el id pasado en la llamada
        Organizacion organizacion = organizacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organizacion no encontrada con id: " + id));

        //Transformación de Entity a DtoResponse
        OrganizacionDtoResponse organizacionDto = OrganizacionMapper.toDtoResponse(organizacion);


        //Se añade a OrganizacionDtoResponse la lista de miembros como Dtos
        List<Miembro> miembros = miembroRepository.obtenerMiembrosPorOrganizacionId(organizacionDto.getId());
        List<MiembroDtoResponse> ListMiembroDto = MiembroMapper.toDtoResponseListMiembro(miembros);
        organizacionDto.setMiembros(ListMiembroDto);


        //Se añade a OrganizacionDtoResponse la lista de Planes como Dtos (antes hay que meter los gastos en los planes)
        List<Plan> planes = planRepository.obtenerPlanesPorOrganizacionId(organizacionDto.getId());
        List<PlanDtoResponse> ListPlanDto = PlanMapper.toDtoResponseListPlan(planes);


        //Se añade a PlanDtoResponse la lista de gastos como Dtos para cada gasto
        for ( PlanDtoResponse planDto: ListPlanDto) {

            List<Gasto> gastos = gastoRepository.obtenerGastosPorPlanId(planDto.getId());
            List<GastoDtoResponse> ListGastoDto = GastoMapper.toDtoResponseListGasto(gastos);
            planDto.setGastos(ListGastoDto);
        }

        organizacionDto.setPlanes(ListPlanDto);


        return organizacionDto;
    }

    //Llamada para Endpoint
    //Elimina una Entidad usando el id recibido por el usuario
    public Organizacion deleteOrganizacionById(Long id) {
        //Obtener organización usando el id pasado en la llamada
        Organizacion organizacion = organizacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + id));

        //Borrado en cascada de organizaciones
        organizacionRepository.delete(organizacion);
        return organizacion;
    }

    //Llamada para Endpoint
    //Actualiza una Entidad usando el id recibido por el usuario
    public OrganizacionDtoResponse patchOrganizacion(OrganizacionDtoUpdateRequest dto, Long id) {
        //Obtener organización usando el id pasado en la llamada
        Organizacion organizacion = organizacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + id));

        OrganizacionMapper.updateEntityFromDtoRes(dto, organizacion);
        organizacionRepository.save(organizacion);
        return OrganizacionMapper.toDtoResponse(organizacion);
    }

    //Llamada para Endpoint
    //Obtiene una lista de Entidades usando el id recibido por el usuario
    /*DEVUELVE:
            |-OrganizacionDto
                |-numeroMiembros
     */
    public List<OrganizacionDtoResponse> getOrganizacionesDtoByUsuarioId() {


        Usuario usuario = usuarioService.devolverUsuarioAutenticado();
        List<Organizacion> organizaciones = organizacionRepository.obtenerOrganizacionesPorUsuarioId(usuario.getId());

        //TODO gestionar sin organizacion

        List<OrganizacionDtoResponse> ListaDtoOrganizacionDto = OrganizacionMapper.toDtoResponseListOrganizacion(organizaciones);

        //Introducir número de mimebros de cada organización en el DTO correspondiente
        for (OrganizacionDtoResponse organizacionDto : ListaDtoOrganizacionDto) {
            int numeroMiembros = miembroRepository.contarMiembrosPorOrganizacionId(organizacionDto.getId());
            organizacionDto.setNumeroMiembros(numeroMiembros);
        }

        return  ListaDtoOrganizacionDto;
    }

    //OTROS MÉTODOS

}
