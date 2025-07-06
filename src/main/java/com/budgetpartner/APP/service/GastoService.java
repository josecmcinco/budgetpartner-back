package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.gasto.GastoDtoPostRequest;
import com.budgetpartner.APP.dto.gasto.GastoDtoResponse;
import com.budgetpartner.APP.dto.gasto.GastoDtoUpdateRequest;
import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.enums.ModoPlan;
import com.budgetpartner.APP.exceptions.BadRequestException;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.GastoMapper;
import com.budgetpartner.APP.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TareaRepository tareaRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private MiembroRepository miembroRepository;
    @Autowired
    private RepartoGastoRepository repartoGastoRepository;

    //ESTRUCTURA GENERAL DE LA LÓGICA DE LOS CONTROLADORES
    //Pasar de DtoRequest a Entity-> Insertar en DB->Pasar de Entity a DtoRequest->Return

    //ENDPOINTS

    //Llamada para Endpoint
    //Crea una Entidad usando el DTO recibido por el usuario
    @Transactional //Interacción con un many to many
    public GastoDtoResponse postGasto(GastoDtoPostRequest gastoDtoReq) {
        //TODO VALIDAR CAMPOS REPETIDOS (DESCRIPCIÓN, MONTO, FECHA, ETC.)

        //Validar Token
        Usuario usuario = usuarioService.devolverUsuarioAutenticado();
        //Ver que permisos ok
        //TODO

        //Obtener elementos necesiarios para insertar el gasto


        Plan plan = planRepository.findById(gastoDtoReq.getPlanId())
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + gastoDtoReq.getPlanId()));

        //Valor por defecto de la tarea asignada
        Tarea tarea = null;

        //Gestión de los planes por tipo
        if(plan.getModoPlan().equals(ModoPlan.simple) && gastoDtoReq.getPlanId() != null){
            throw new BadRequestException("Se está tratando de asignar una tarea a un gasto en un plan simple");}

        else if(gastoDtoReq.getPlanId() != null){
            tarea = tareaRepository.findById(gastoDtoReq.getTareaId())
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + gastoDtoReq.getPlanId()));}


        Miembro pagador = miembroRepository.findById(gastoDtoReq.getPagadorId())
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + gastoDtoReq.getPlanId()));;

        //Crea el gasto
        Gasto gasto = GastoMapper.toEntity(gastoDtoReq, tarea, plan, pagador);
        gastoRepository.save(gasto);

        //Crea las deudas de cada miembro en base al gasto
        List<Long> idEndeudadosList = gastoDtoReq.getListaMiembrosEndeudados();
        postRepartoGastos(idEndeudadosList, gasto, pagador);

        return GastoMapper.toDtoResponse(gasto);
    }

    //Llamada para Endpoint
    //Obtiene una Entidad usando el id recibido por el usuario
    /*DEVUELVE AL USUARIO:

     */
    public GastoDtoResponse getGastoDtoById(Long id) {
        //Obtener gasto usando el id pasado en la llamada
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + id));

        List<Miembro> miembrosEndeudados = gastoRepository.findMiembrosByGastoId(id);
        System.out.println(miembrosEndeudados);

        GastoDtoResponse gastoDtoResp = GastoMapper.toDtoResponse(gasto);
        return gastoDtoResp;

    }

    //Llamada para Endpoint
    //Elimina una Entidad usando el id recibido por el usuario
    public Gasto deleteGastoById(Long id) {
        //Obtener gasto usando el id pasado en la llamada
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + id));

        //Elimina primero la relación muchos a muchos con Miembro
        repartoGastoRepository.eliminarRepartoGastoPorGastoId(id);

        //Elimina el gasto en cascada
        gastoRepository.delete(gasto);
        return gasto;
    }

    //Llamada para Endpoint
    //Actualiza una Entidad usando el id recibido por el usuario
    @Transactional //Interacción con un many to many
    public Gasto patchGasto(GastoDtoUpdateRequest gastoDtoResp, Long id) {
        //Obtener gasto usando el id pasado en la llamada
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + id));

        GastoMapper.updateEntityFromDtoRes(gastoDtoResp, gasto);
        gastoRepository.save(gasto);

        //Crea las deudas de cada miembro en base al gasto
        if(gastoDtoResp.getListaMiembrosEndeudados() != null){
            //Eliminar las reparticiones previas
            repartoGastoRepository.eliminarRepartoGastoPorGastoId(id);

            //Añadir reparticiones nuevas
            List<Long> idEndeudadosList = gastoDtoResp.getListaMiembrosEndeudados();
            postRepartoGastos(idEndeudadosList, gasto, gasto.getPagador());
        }

        return gasto;
    }


    //OTROS MÉTODOS

    //Crea las deudas de cada miembro en base al gasto
    public void postRepartoGastos(List<Long> idEndeudadosList, Gasto gasto, Miembro pagador){

        //Obtener deuda como double con dos decimales
        int prepDeudaSinDecimales = (int) gasto.getCantidad() * 100 / idEndeudadosList.size();
        double deudaPorPersona = (double) prepDeudaSinDecimales / 100 ;

        //Preparar la parte indivisible del gasto para cobrarsela al que hace el gasto
        double picoDelGasto = gasto.getCantidad() - deudaPorPersona * idEndeudadosList.size();

        //Crear un elemento repartoDeuda por cada endeudado y meterlo en la DB
        for (Long idEndeudado: idEndeudadosList){
            Miembro miembro = miembroRepository.findById(idEndeudado)
                    .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + idEndeudado));


            RepartoGasto reparto = new RepartoGasto();
            reparto.setGasto(gasto);
            reparto.setMiembro(miembro);

            //Asignar gasto de pico al pagador
            if(Objects.equals(pagador.getId(), idEndeudado)){reparto.setCantidad(gasto.getCantidad() - deudaPorPersona + picoDelGasto);}
            else{reparto.setCantidad(-deudaPorPersona);}

            reparto.setId(new RepartoGastoId(gasto.getId(), miembro.getId()));

            repartoGastoRepository.save(reparto);
        }

    }

}
