package Modelo;

import java.util.ArrayList;

/**
 *
 * @author Alberto Fernández Merchán & Juan Manuel Ruíz Pérez
 */
public class Transicion {

    private int estadoOrigen;
    private ArrayList<Integer> estadosDestino = new ArrayList<>();
    private String simbolo;

    /**
     * Constructor de la clase. Crea una transición normal para un autómata.
     *
     * @param e1 Estado de origen.
     * @param simbolo Símbolo que provoca la transición.
     * @param e2 Estado de destino.
     */
    public Transicion(int e1, String simbolo, ArrayList<Integer> e2) {
        estadoOrigen = e1;
        this.simbolo = simbolo;
        estadosDestino.addAll(e2);
    }

    /**
     * Devuelve el estado de origen de la transicion
     *
     * @return
     */
    public int getEstadoOrigen() {
        return estadoOrigen;
    }

    /**
     * Obtiene una lista con los estados de destino de la transición
     *
     * @return
     */
    public ArrayList<Integer> getEstadosDestino() {
        return estadosDestino;
    }

    /**
     * Obtiene el símbolo de la transición
     *
     * @return
     */
    public String getSimbolo() {
        return simbolo;
    }

    /**
     * Establece el estado de origen de la transición.
     *
     * @param estadoOrigen Estado que queremos establecer como origen.
     */
    public void setEstadoOrigen(int estadoOrigen) {
        this.estadoOrigen = estadoOrigen;
    }

    /**
     * Establece la lista de estados destino de la transición.
     *
     * @param estadosDestino Lista de estados destino de la transición.
     */
    public void setEstadosDestino(ArrayList<Integer> estadosDestino) {
        this.estadosDestino = estadosDestino;
    }

    /**
     * Añade un estado destino a la transición sin borrar el resto.
     *
     * @param eDestino Estado destino que queremos añadir a la lista.
     */
    public void addEstadoDestino(ArrayList<Integer> eDestino) {
        for (int i = 0; i < eDestino.size(); i++) {
            //Inserta solo los estados que no están en el conjunto de la transicion.
            if (!estadosDestino.contains(eDestino.get(i))) {
                estadosDestino.addAll(eDestino);
            }
        }
    }

    /**
     * Establece el símbolo de la transición.
     *
     * @param simbolo Símbolo que se establecerá como símbolo de la transición.
     */
    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
/**
 * <p> Transforma la transición a un objeto tipo String de la forma: </p>
 * estadoOrigen &emsp; 'simbolo' &emsp; [estadosDestino]
 * @return 
 */
    @Override
    public String toString() {
        String s = estadoOrigen + "\t'" + simbolo + "'\t" + estadosDestino;
        return s;        
        
    }
}
