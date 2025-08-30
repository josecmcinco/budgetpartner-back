package com.budgetpartner.APP.service.AiService;

import com.budgetpartner.APP.aiTools.ToolRegistry;
import com.budgetpartner.APP.dto.api.ChatbotQuery;
import com.budgetpartner.APP.dto.api.OllamaAgentInstruction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OllamaAgentServiceTest {

    @Spy
    @InjectMocks
    private OllamaAgentService ollamaAgentService;
    @Mock
    private ToolRegistry toolRegistry;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testProcessUserMessage_whenAskingForMiembroById3_returnsExpectedResponse() throws Exception {
        // given
        ChatbotQuery query = new ChatbotQuery("Dame los datos del miembro de id 3", true);

        // Mock instrucción 1: pide herramienta
        OllamaAgentInstruction firstInstruction = new OllamaAgentInstruction();
        firstInstruction.setToolName("MiembroTools.obtenerMiembroPorId");
        firstInstruction.setArguments(java.util.List.of("3"));
        firstInstruction.setFinished(false);

        // Mock instrucción 2: respuesta final
        OllamaAgentInstruction secondInstruction = new OllamaAgentInstruction();
        secondInstruction.setToolName("");
        secondInstruction.setArguments(java.util.List.of());
        secondInstruction.setFinished(true);
        secondInstruction.setFinalResponse("El miembro con ID 3 es cmartinez1.");


        doReturn(firstInstruction)  // primera llamada a querry()
                .doReturn(secondInstruction) // segunda llamada a querry()
                .when(ollamaAgentService).querry(any(), any());

        // 4. Mock de ToolRegistry (ejecución de herramienta)
        when(toolRegistry.invokeTool(eq("MiembroTools.obtenerMiembroPorId"), any()))
                .thenReturn(new Object() {
                    public final int id = 3;
                    public final String username = "cmartinez1";
                    @Override public String toString() { return username; }
                });

        // 5. Ejecutamos el proceso completo
        String result = ollamaAgentService.processUserMessage(query, "mockModel");

        // 6. Validamos que termina con la respuesta esperada
        assertEquals("El miembro con ID 3 es cmartinez1.", result);

        // Verificamos que:
        // - querry() fue llamado dos veces (antes y después de usar la herramienta)
        verify(ollamaAgentService, times(2)).querry(any(), any());
        // - la herramienta se invocó correctamente
        verify(toolRegistry, times(1))
                .invokeTool(eq("MiembroTools.obtenerMiembroPorId"), eq(new Object[]{"3"}));
    }



}
