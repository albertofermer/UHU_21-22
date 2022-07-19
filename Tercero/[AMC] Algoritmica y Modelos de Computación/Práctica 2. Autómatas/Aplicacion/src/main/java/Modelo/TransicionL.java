package Modelo;

import java.util.ArrayList;

/**
 *
 * @author Alberto Fernández Merchán & Juan Manuel Ruíz Pérez
 */
public class TransicionL {

    private int estadoOrigen;
    private ArrayList<Integer> estadosDestino = new ArrayList();
/**
 * Constructor de la clase. Crea una &lambda;-transición para un autómata no-determinista.
 * @param estadoOrigen Estado origen de la &lambda;-transición.
 * @param estadoDestino Lista de estados destino de la &lambda;-transición.
 */
    public TransicionL(int estadoOrigen, ArrayList<Integer> estadoDestino) {
        this.estadoOrigen = estadoOrigen;
        this.estadosDestino.addAll(estadoDestino);
    }
/**
 * Devuelve el estado origen de la &lambda;-transición.
 * @return 
 */
    public int getEstadoOrigen() {
        return estadoOrigen;
    }
/**
 * Devuelve la lista de estados destino de la &lambda;-transición.
 * @return 
 */
    public ArrayList<Integer> getEstadoDestino() {
        return estadosDestino;
    }
/**
 * Establece el estado origen de la &lambda;-transición.
 * @param estadoOrigen Estado que queremos establecer como estado origen de la
 * &lambda;-transición
 */
    public void setEstadoOrigen(int estadoOrigen) {
        this.estadoOrigen = estadoOrigen;
    }
/**
 * Establece la lista de destinos de la &lambda;-transición.
 * @param estadoDestino lista de estados que queremos establecer como la lista de
 * estados destino de la &lambda;-transición;
 */
    public void setEstadoDestino(ArrayList<Integer> estadoDestino) {
        this.estadosDestino = estadoDestino;
    }
/**
 * Añade un estado a la lista de destinos de la &lambda;-transición.
 * @param e2 Lista de estados destino que queremos añadir a la lista de los estados
 * destino de la &lambda;-transición.
 */
    void addEstadosDestino(ArrayList<Integer> e2) {
        for (int i = 0; i < e2.size(); i++) {
            //Inserta solo los estados que no están en el conjunto de la transicion.
            if (!estadosDestino.contains(e2.get(i))) {
                estadosDestino.addAll(e2);
            }
        }
    }
/**
 * <p>Transforma a la &lambda;-transición a un objeto String de la forma:</p>
 * estadoOrigen &emsp; 'lambda' &emsp; [estadosDestino]
 * @return 
 */
    @Override
    public String toString() {
        String s = estadoOrigen + "\t'lambda'\t" + estadosDestino;
        return s;
    }

}
