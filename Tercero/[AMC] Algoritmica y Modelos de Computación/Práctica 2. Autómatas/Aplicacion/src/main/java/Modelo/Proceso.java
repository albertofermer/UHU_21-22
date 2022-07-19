package Modelo;

/**
 *
 * @author Alberto Fernández Merchán & Juan Manuel Ruíz Pérez
 */
public interface Proceso {
    public abstract boolean esFinal(int estado);
    public abstract boolean reconocer(String cadena);
    @Override
    public abstract String toString();
}
