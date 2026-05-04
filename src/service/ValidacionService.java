package service;

public class ValidacionService {

    public boolean estaVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    public boolean esEmailValido(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public boolean esTelefonoValido(String telefono) {
        return telefono != null && telefono.matches("^[0-9]{9}$");
    }

    public boolean esDniValido(String dni) {
        if (dni == null || !dni.matches("^[0-9]{8}[A-Za-z]$")) {
            return false;
        }
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numero = Integer.parseInt(dni.substring(0, 8));
        char letraCalculada = letras.charAt(numero % 23);
        return Character.toUpperCase(dni.charAt(8)) == letraCalculada;
    }

    public boolean esNumeroPositivo(double numero) {
        return numero > 0;
    }

    public boolean esEnteroNoNegativo(int numero) {
        return numero >= 0;
    }

    public String normalizarTexto(String valor) {
        return valor == null ? "" : valor.trim();
    }
}
