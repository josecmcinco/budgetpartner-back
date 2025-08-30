package com.budgetpartner.APP.aiTools;

import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ToolRegistry {

    // Mapa clave: "Clase.metodo"
    private final Map<String, Method> toolMethods = new HashMap<>();

    // Mapa clave: "Clase.metodo" -> Instancia del bean
    private final Map<String, Object> toolInstances = new HashMap<>();

    /**
     * Registra todos los métodos @Tool de la instancia pasada.
     * Puedes llamarlo varias veces para distintos beans.
     */
    public void registerTools(Object instance) {
        Class<?> clazz = instance.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Tool.class)) {
                String key = clazz.getSimpleName() + "." + method.getName();
                toolMethods.put(key, method);
                toolInstances.put(key, instance);

            }
        }
    }

    public String getToolDescriptionsForPrompt() {
        StringBuilder sb = new StringBuilder("Tienes acceso a las siguientes herramientas:\n\n");

        for (String key : toolMethods.keySet()) {
            Method method = toolMethods.get(key);
            Tool toolAnnotation = method.getAnnotation(Tool.class);

            sb.append("- ").append(key).append("(");

            Class<?>[] paramTypes = method.getParameterTypes();
            Parameter[] params = method.getParameters();

            for (int i = 0; i < params.length; i++) {
                ToolParam paramAnnotation = params[i].getAnnotation(ToolParam.class);
                String paramName = params[i].getName();
                String paramDesc = paramAnnotation != null ? paramAnnotation.description() : "";
                sb.append(paramName).append(": ").append(paramTypes[i].getSimpleName());
                if (!paramDesc.isEmpty()) {
                    sb.append(" (").append(paramDesc).append(")");
                }
                if (i < params.length - 1) sb.append(", ");
            }

            sb.append(")\n  - Descripción: ").append(toolAnnotation.description()).append("\n\n");
        }

        return sb.toString();
    }

    /**
     * Invoca la herramienta por nombre completo "Clase.metodo" con parámetros.
     */
    public Object invokeTool(String fullMethodName, Object... args) throws Exception {
        Method method = toolMethods.get(fullMethodName);
        Object instance = toolInstances.get(fullMethodName);

        if (method == null || instance == null) {
            throw new RuntimeException("Herramienta no registrada: " + fullMethodName);
        }

        Class<?>[] paramTypes = method.getParameterTypes();

        if (paramTypes.length != args.length) {
            throw new RuntimeException("Número de argumentos incorrecto. Esperado: " +
                    paramTypes.length + ", recibido: " + args.length);
        }

        Object[] convertedArgs = new Object[args.length];

        for (int i = 0; i < args.length; i++) {
            Object rawArg = args[i];

            // Solo convertimos si viene como String
            if (rawArg instanceof String stringArg) {
                Class<?> targetType = paramTypes[i];

                convertedArgs[i] = convertStringToType(stringArg, targetType);
            } else {
                convertedArgs[i] = rawArg; // ya es un tipo válido
            }
        }

        method.setAccessible(true);
        return method.invoke(instance, convertedArgs);
    }

    private Object convertStringToType(String value, Class<?> targetType) {
        if (targetType.equals(String.class)) return value;
        if (targetType.equals(Long.class) || targetType.equals(long.class)) return Long.parseLong(value);
        if (targetType.equals(Integer.class) || targetType.equals(int.class)) return Integer.parseInt(value);
        if (targetType.equals(Float.class) || targetType.equals(float.class)) return Float.parseFloat(value);
        if (targetType.equals(Double.class) || targetType.equals(double.class)) return Double.parseDouble(value);
        if (targetType.equals(Boolean.class) || targetType.equals(boolean.class)) return Boolean.parseBoolean(value);

        // Puedes añadir más tipos según tu caso (Date, Enum, etc.)

        throw new IllegalArgumentException("No se puede convertir el valor '" + value + "' al tipo: " + targetType.getName());
    }

    /**
     * Obtiene todos los nombres de herramientas registradas.
     */
    public Set<String> getRegisteredTools() {
        return Collections.unmodifiableSet(toolMethods.keySet());
    }
}