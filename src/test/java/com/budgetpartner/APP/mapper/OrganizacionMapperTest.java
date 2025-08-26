package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoPostRequest;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoResponse;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoUpdateRequest;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.mapper.OrganizacionMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrganizacionMapperTest {

    @Test
    void testToDtoResponse_NullOrganizacion() {
        assertNull(OrganizacionMapper.toDtoResponse(null));
    }

    @Test
    void testToDtoResponse_WithAllFields() {
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 12, 0);
        Organizacion org = new Organizacion(1L, "OrgPrueba", "DescripcionPrueba", MonedasDisponibles.EUR, date, date);

        OrganizacionDtoResponse dto = OrganizacionMapper.toDtoResponse(org);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("OrgPrueba", dto.getNombre());
        assertEquals("DescripcionPrueba", dto.getDescripcion());
        assertEquals(MonedasDisponibles.EUR, dto.getMoneda());
    }

    @Test
    void testToEntity_NullDto() {
        assertNull(OrganizacionMapper.toEntity(null));
    }

    @Test
    void testToEntity_WithValidDto() {
        OrganizacionDtoPostRequest dto = new OrganizacionDtoPostRequest("OrgNueva", "DescripcionNueva", "Nick1", MonedasDisponibles.USD);

        Organizacion org = OrganizacionMapper.toEntity(dto);

        assertNotNull(org);
        assertEquals("OrgNueva", org.getNombre());
        assertEquals("DescripcionNueva", org.getDescripcion());
        assertEquals(MonedasDisponibles.USD, org.getMoneda());
    }

    @Test
    void testUpdateEntityFromDtoRes_NullDtoOrOrganizacion() {
        OrganizacionMapper.updateEntityFromDtoRes(null, null);
        // No lanza excepción
    }

    @Test
    void testUpdateEntityFromDtoRes_UpdateFields() {
        Organizacion org = new Organizacion();
        org.setNombre("OrgVieja");
        org.setDescripcion("DescVieja");

        OrganizacionDtoUpdateRequest dto = new OrganizacionDtoUpdateRequest("OrgNueva", "DescNueva",  MonedasDisponibles.EUR);

        OrganizacionMapper.updateEntityFromDtoRes(dto, org);

        assertEquals("OrgNueva", org.getNombre());
        assertEquals("DescNueva", org.getDescripcion());
    }

    @Test
    void testUpdateEntityFromDtoRes_PartialUpdate() {
        Organizacion org = new Organizacion();
        org.setNombre("OrgVieja");
        org.setDescripcion("DescVieja");

        // Solo cambia el nombre
        OrganizacionDtoUpdateRequest dto = new OrganizacionDtoUpdateRequest("OrgNueva", null, MonedasDisponibles.EUR);

        OrganizacionMapper.updateEntityFromDtoRes(dto, org);

        assertEquals("OrgNueva", org.getNombre());
        assertEquals("DescVieja", org.getDescripcion());
    }

    @Test
    void testToDtoResponseListOrganizacion_NullOrEmptyList() {
        assertTrue(OrganizacionMapper.toDtoResponseListOrganizacion(null).isEmpty());
        assertTrue(OrganizacionMapper.toDtoResponseListOrganizacion(Collections.emptyList()).isEmpty());
    }

    @Test
    void testToDtoResponseListOrganizacion_WithElements() {
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 12, 0);
        Organizacion org1 = new Organizacion(1L, "Org1", "Desc1", MonedasDisponibles.EUR, date, date);
        Organizacion org2 = new Organizacion(2L, "Org2", "Desc2", MonedasDisponibles.USD, date, date);

        List<OrganizacionDtoResponse> result = OrganizacionMapper.toDtoResponseListOrganizacion(Arrays.asList(org1, org2));

        assertEquals(2, result.size());
        assertEquals("Org1", result.get(0).getNombre());
        assertEquals("Org2", result.get(1).getNombre());
        assertEquals(MonedasDisponibles.EUR, result.get(0).getMoneda());
        assertEquals(MonedasDisponibles.USD, result.get(1).getMoneda());
    }
}
