package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.gasto.GastoDtoPostRequest;
import com.budgetpartner.APP.dto.gasto.GastoDtoResponse;
import com.budgetpartner.APP.dto.gasto.GastoDtoUpdateRequest;
import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.GastoMapper;
import com.budgetpartner.APP.repository.GastoRepository;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.PlanRepository;
import com.budgetpartner.APP.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    TareaRepository tareaRepository;
    @Autowired
    PlanRepository planRepository;
    @Autowired
    MiembroRepository miembroRepository;

    //ESTRUCTURA GENERAL DE LA LÓGICA DE LOS CONTROLADORES
    //Pasar de DtoRequest a Entity-> Insertar en DB->Pasar de Entity a DtoRequest->Return

    //ENDPOINTS

    //Llamada para Endpoint
    //Crea una Entidad usando el DTO recibido por el usuario
    public Gasto postGasto(GastoDtoPostRequest gastoDtoReq) {
        //TODO VALIDAR CAMPOS REPETIDOS (DESCRIPCIÓN, MONTO, FECHA, ETC.)

        //Validar Token
        Usuario usuario = usuarioService.devolverUsuarioAutenticado();
        //Ver que permisos ok

        //Crear

        Tarea tarea = tareaRepository.findById(gastoDtoReq.getPlanId())
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + gastoDtoReq.getPlanId()));
        Plan plan = planRepository.findById(gastoDtoReq.getPlanId())
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + gastoDtoReq.getPlanId()));;
        Miembro pagador = miembroRepository.findById(gastoDtoReq.getPagadorId())
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + gastoDtoReq.getPlanId()));;

        Gasto gasto = GastoMapper.toEntity(gastoDtoReq, tarea, plan, pagador);
        gastoRepository.save(gasto);
        return gasto;
    }

    //Llamada para Endpoint
    //Obtiene una Entidad usando el id recibido por el usuario
    /*DEVUELVE AL USUARIO:

     */
    public GastoDtoResponse getGastoDtoById(Long id) {
        //Obtener gasto usando el id pasado en la llamada
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + id));

        GastoDtoResponse gastoDtoResp = GastoMapper.toDtoResponse(gasto);
        return gastoDtoResp;

    }

    //Llamada para Endpoint
    //Elimina una Entidad usando el id recibido por el usuario
    public Gasto deleteGastoById(Long id) {
        //Obtener gasto usando el id pasado en la llamada
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + id));

        gastoRepository.delete(gasto);

        //TODO AJUSTAR DEPENDENCIAS DE BORRADO SI EXISTEN (referencias desde otras entidades)
        return gasto;
    }

    //Llamada para Endpoint
    //Actualiza una Entidad usando el id recibido por el usuario
    public Gasto patchGasto(GastoDtoUpdateRequest dto) {
        //Obtener gasto usando el id pasado en la llamada
        Gasto gasto = gastoRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + dto.getId()));

        GastoMapper.updateEntityFromDtoRes(dto, gasto);
        gastoRepository.save(gasto);
        return gasto;
    }


    //OTROS MÉTODOS


}
