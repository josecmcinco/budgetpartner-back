package com.budgetpartner.APP.repository;

import com.budgetpartner.APP.entity.Gasto;
import com.budgetpartner.APP.entity.Invitacion;
import com.budgetpartner.config.PobladorTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Import(PobladorTestConfig.class)
public class InvitacionRepositoryTest {

    @Autowired
    private InvitacionRepository invitacionRepository;

    private static String tokenValido = "a4d6bbda-93a7-47bc-9f38-5b6ec62a9478";;

    @Test
    public void testObtenerInvitacionPorOrganizacionId() {
        //La organizacion 1 tiene una invitacion
        Long organizacionId = 1L; // Plan A de la Organización 1
        Invitacion invitacion = invitacionRepository.obtenerInvitacionPorOrganizacionId(organizacionId)
                .orElseThrow();

        assertThat(invitacion).isNotNull();
        assertThat(invitacion.getToken()).isEqualTo(tokenValido);


        ;
    }
}
