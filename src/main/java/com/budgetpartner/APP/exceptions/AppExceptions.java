package com.budgetpartner.APP.exceptions;

public class AppExceptions {

    public static class UsuarioNotFoundException extends RuntimeException {
        public UsuarioNotFoundException(String message) {
            super(message);
        }
    }

    public static class GastoNotFoundException extends RuntimeException {
        public GastoNotFoundException(String message) {
            super(message);
        }
    }

    public static class MiembroNotFoundException extends RuntimeException {
        public MiembroNotFoundException(String message) {
            super(message);
        }
    }

    public static class OrganizacionNotFoundException extends RuntimeException {
        public OrganizacionNotFoundException(String message) {
            super(message);
        }
    }

    public static class PlanNotFoundException extends RuntimeException {
        public PlanNotFoundException(String message) {
            super(message);
        }
    }

    public static class RolNotFoundException extends RuntimeException {
        public RolNotFoundException(String message) {
            super(message);
        }
    }

    public static class TareaNotFoundException extends RuntimeException {
        public TareaNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidRequestException extends RuntimeException {
        public InvalidRequestException(String message) {
            super(message);
        }
    }
}
