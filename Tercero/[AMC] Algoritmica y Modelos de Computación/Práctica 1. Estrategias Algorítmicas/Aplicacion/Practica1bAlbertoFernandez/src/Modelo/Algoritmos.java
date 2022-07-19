package Modelo;

import static Modelo.Punto.*;
import Vista.Aleatorio;
import Vista.CanvasPuntos;
import Vista.InformacionAvanzada;
import Vista.VentanaPrincipal;
import Vista.Mensaje;
import java.awt.Canvas;
import java.awt.Color;
import java.io.BufferedReader;
import static java.lang.Math.random;
import java.text.DecimalFormat;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Algoritmos {

    private ModeloTabla mt;
    private static final JFileChooser filechooser = new JFileChooser();
    private ArrayList<Punto> T = new ArrayList();
    private ArrayList<Punto> PuntosSolucion = new ArrayList();
    private Double[][] LongitudAristas; //Matriz de Adyacencia, no simétrica.
    Integer[] Distancias;
    private VentanaPrincipal UIView;
    private Mensaje vMensaje = null;
    private CanvasPuntos canvas;
    private boolean PuntoEncontrado = false;
    private Punto[] Predecesor;
    private int puntoOrigen;
    private final int MAX = 10;

    public Algoritmos(VentanaPrincipal UIView, Aleatorio vAleatorio, InformacionAvanzada ia, Mensaje vMensaje) {
        this.UIView = UIView;
        this.vMensaje = vMensaje;
    }

    public void setModeloTabla(ModeloTabla modelo) {
        mt = modelo;
    }

    public boolean isDark() {
        return UIView.BotonModoOscuro.isSelected();
    }

    public void setCanvas(Canvas cv) {
        canvas = (CanvasPuntos) cv;
    }

    public ArrayList<Punto> getPuntosSolucion() {
        return this.PuntosSolucion;
    }

    public Double[][] getPesos() {
        return LongitudAristas;
    }

    /**
     * Método que genera un vector de puntos aleatorios y los almacena en T.
     *
     * @param max: Número máximo que alcanzarán los puntos.
     * @param talla: Tamaño del vector de puntos.
     *
     */
    public void puntosAleatorios(int max, int talla) //Genera puntos aleatorios.
    {
        T.clear();
        for (int i = 0; i < talla; i++) {

            double x = ((random() * max));
            double y = (random() * max);

            Punto p = new Punto(x, y, i);
            T.add(p);
        }
        InicializarLongitudAristas();
        InicializarCaminoPredecesores();
        if (T.size() <= MAX) {
            mt.rellenarTablaAristas(LongitudAristas);
        }
    }

    /**
     * Método que lee un fichero .tsp del disco duro y almacena los puntos en la
     * lista de puntos T.
     *
     * @return T
     */
    public ArrayList<Punto> leerFichero() //Lee un fichero del disco duro
    {
        T.clear();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TSP", "tsp");
        filechooser.setFileFilter(filter);

        filechooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        int result = filechooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File fichero = filechooser.getSelectedFile();
            String[] str = fichero.getName().split("\\.");

            if (str[1].equals("tsp")) {

                try {
                    try (FileReader fr = new FileReader(fichero); BufferedReader br = new BufferedReader(fr)) {
                        String lineaActual;
                        int lineasLeidas = 0;

                        int id = 0; //identificador del punto

                        while ((lineaActual = br.readLine()) != null) {
                            lineasLeidas++;
                            String[] linea = lineaActual.split(" ");
                            if (lineasLeidas > 6 && !(lineaActual.equals("") || lineaActual.equals("EOF"))) {
                                if (!linea[0].equals("EOF")) {
                                    T.add(new Punto(Double.parseDouble(linea[1]), Double.parseDouble(linea[2]), id));
                                    id++;
                                }
                            }
                        }
                        InicializarLongitudAristas();
                        InicializarCaminoPredecesores();
                        if (T.size() <= 10) {
                            mt.rellenarTablaAristas(LongitudAristas);
                        }
                    }
                    if (UIView != null) {
                        UIView.Fichero.setText(fichero.getName());
                        UIView.Fichero.setForeground(Color.white);
                        UIView.BotonBuscar.setEnabled(true);

                        pintarCanvas();
                    }
                } catch (IOException | NumberFormatException e) {
                    vMensaje.Message("error", e.getMessage());
                    System.out.println(e.getMessage());
                }
            }
        } else if (result == JFileChooser.CANCEL_OPTION) {

            if (UIView != null) {
                UIView.BotonBuscar.setEnabled(false);
                vMensaje.Message("error", "No se ha seleccionado ningun fichero");
                UIView.Fichero.setText("No se ha seleccionado ningun fichero");
                UIView.Fichero.setForeground(Color.red);
            }
        }
        return T;
    }

    /**
     * Muestra por consola todos los puntos que están contenidos en el vector de
     * puntos.
     *
     */
    public void ver() {
        DecimalFormat df = new DecimalFormat("####0.00");
        for (int i = 0; i < T.size(); i++) {
            System.out.println("T[" + (i + 1) + "]:\t X: " + df.format(T.get(i).getx()) + "\t Y: " + df.format(T.get(i).gety()) + "");
        }

    }

    /**
     *
     * Calcula el camino minimo desde un punto de origen hasta el resto de
     * puntos.
     *
     * @param puntoOrigen
     * @return vector de distancias minimas.
     */
    public Integer[] Dijkstra(Punto puntoOrigen) {
        //COMIENZA LA INICIALIZACION
        PuntosSolucion.clear();
        Distancias = new Integer[T.size()];
        for (int i = 0; i < Distancias.length; i++) {
            Distancias[i] = 0;
        }
        ArrayList<Punto> Candidatos = new ArrayList();
        Punto v;
        for (int i = 0; i < T.size(); i++) {
            if (puntoOrigen != T.get(i)) {
                Candidatos.add(T.get(i)); //Los puntos candidatos serán todos menos el origen.
            }
        }

        PuntosSolucion.add(puntoOrigen); //Inicializamos el vector solucion con el elemento origen.

        for (int i = 0; i < T.size(); i++) {
            Predecesor[i] = puntoOrigen;//En un principio, el predecesor será el origen
        }

        for (int i = 0; i < T.size(); i++) {
            if (i != puntoOrigen.getId()) {
                Distancias[i] = LongitudAristas[puntoOrigen.getId()][i].intValue(); // Inicializo el vector de distancias al coste de la arista directa.
            }
        }

        if (T.size() <= 10) {
            mt.eliminarDatosSolucion();
            mt.añadirFilaEvolucion("inicial", "-", Candidatos.toString(), ConvertirDistanciasCadena(Distancias));
        }
        //FIN DE LA INICIALIZACIONC
        //COMIENZA EL BUCLE VORAZ
        for (int i = 0; i < T.size() - 2; i++) { // El algoritmo termina después de n-2 iteraciones
            v = elegirNodo(Candidatos, Distancias); //Elijo el nodo con la distancia minia (exceptuando el Distancias[0])
            Candidatos.remove(v);  // Lo elimino de la lista
            PuntosSolucion.add(v);
            for (int j = 0; j < Candidatos.size(); j++) {
                int aux = Distancias[Candidatos.get(j).getId()];
                Distancias[Candidatos.get(j).getId()] = (int) minimo(Distancias[Candidatos.get(j).getId()], (Distancias[T.indexOf(v)] + LongitudAristas[T.indexOf(v)][Candidatos.get(j).getId()]));
                if (Distancias[Candidatos.get(j).getId()] != aux) {
                    Predecesor[Candidatos.get(j).getId()] = (v);
                }
            }
            if (T.size() <= MAX) {
                mt.añadirFilaEvolucion(Integer.toString(i + 1), v.toString(), Candidatos.toString(), ConvertirDistanciasCadena(Distancias));
            }
        }
        PuntosSolucion.add(Candidatos.get(0)); // Inserto el ultimo nodo candidato para tener todos los nodos en el vector de Solucion
        canvas.actualiza(Predecesor);
        return Distancias;
    }

    public void actualizaPredecesores() {
        canvas.actualiza(Predecesor);
    }

    /**
     * Obtiene el punto con mayor coordenada X
     *
     * @return
     */
    public Punto getMayorX() { //Devuelve el punto con la X mayor

        Punto mayorX = T.get(0);
        for (int i = 1; i < T.size(); i++) {
            if (T.get(i).esMayorX(mayorX)) {
                mayorX = T.get(i);
            }
        }
        return mayorX;
    }

    /**
     * Obtiene el punto con mayor coordenada Y
     *
     * @return
     */
    public Punto getMayorY() { //Devuelve el punto con la Y mayor

        Punto mayorY = T.get(0);
        for (int j = 1; j < T.size(); j++) {
            if (T.get(j).esMayorY(mayorY)) {
                mayorY = T.get(j);
            }
        }

        return mayorY;
    }

    /**
     * Calcula el minimo entre dos numeros
     *
     * @param a
     * @param b
     * @return
     */
    public double minimo(double a, double b) {
        double minimo = b;

        if (a < b) {
            minimo = a;
        }

        return minimo;
    }

    /**
     * Vuelve a pintar el canvas
     */
    public void pintarCanvas() {
        canvas.repaint();

    }

    /**
     * Obtiene la lista de puntos.
     *
     * @return
     */
    public ArrayList<Punto> getPuntos() {
        return T;
    }

    public int getNumPuntos() {
        return T.size();
    }

    /**
     * Devuelve si ha encontrado correctamente la solución al problema.
     *
     * @return
     */
    public boolean getEncontrado() {
        return PuntoEncontrado;
    }

    /**
     * Coloca si se ha encontrado la solución al problema.
     *
     * @param encontrado
     */
    public void setEncontrado(boolean encontrado) {
        PuntoEncontrado = encontrado;
    }

    /**
     * Método para inicializar la tabla de longitud de las aristas.
     */
    private void InicializarLongitudAristas() {
        LongitudAristas = new Double[T.size()][T.size()];
        for (int i = 0; i < T.size(); i++) {
            for (int j = 0; j <= i; j++) { //Como la tabla es simetrica podemos reducir el bucle
                if (i == j) {
                    LongitudAristas[i][j] = Double.POSITIVE_INFINITY;
                } else {
                    //LongitudAristas[i][j] = (double) ((((int) (distancia(T.get(i), T.get(j)) * 100)) % 100) + 1);
                    LongitudAristas[i][j] = (double) (calcularPeso(T.get(i), T.get(j)));
                    //LongitudAristas[j][i] = (double) ((((int) (disttancia(T.get(j), T.get(i)) * 100)) % 100) + 1);
                    LongitudAristas[j][i] = (double) (calcularPeso(T.get(j), T.get(i)));
                }
            }
        }
    }

    /**
     * Método para decidir qué nodo deberá ser el siguiente a elegir en el
     * algoritmo de Dijkstra
     *
     * @param Candidatos
     * @param Distancias
     * @return
     */
    private Punto elegirNodo(ArrayList<Punto> Candidatos, Integer[] Distancias) {
        Punto v;
        int indiceElegido;
        int distanciaMinima;
        distanciaMinima = minimo(Distancias, Candidatos); //Encontrar el minimo en distancias y que este en candidatos
        indiceElegido = buscarEnDistancias(distanciaMinima, Distancias, Candidatos);
        //Buscar en Candidatos el punto con el identificador == indiceElegido
        v = buscarEnCandidatos(indiceElegido, Candidatos);
        return v;
    }

    /**
     * Calcula la distancia minima del vector distancias de los puntos que se
     * encuentren en Candidatos.
     *
     * @param Distancias
     * @param Candidatos
     * @return
     */
    public int minimo(Integer[] Distancias, ArrayList<Punto> Candidatos) { //Selecciona la distancia minima del vector distancias
        //Distancias[0] = 0 (por comodidad)
        int minimo = Integer.MAX_VALUE;
        for (int i = 0; i < Distancias.length; i++) {
            if (Distancias[i] < minimo && Candidatos.contains(buscarPunto(i))) {
                minimo = Distancias[i];
            }
        }

        return minimo;
    }

    /**
     * Busca en el vector de distancias el punto con la distancia minima
     * correspondiente
     *
     * @param distanciaMinima
     * @param Distancias
     * @param Candidatos
     * @return
     */
    private int buscarEnDistancias(int distanciaMinima, Integer[] Distancias, ArrayList<Punto> Candidatos) {
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < Distancias.length) {
            if ((distanciaMinima == Distancias[i]) && Candidatos.contains(buscarPunto(i))) {
                encontrado = true;
            } else {
                i++;
            }
        }
        return i;
    }

    /**
     * Busca en la lista de candidatos el punto con el indice indiceElegido
     *
     * @param indiceElegido
     * @param Candidatos
     * @return
     */
    private Punto buscarEnCandidatos(int indiceElegido, ArrayList<Punto> Candidatos) {
        int i = 0;
        Punto v = new Punto(0, 0, -1);
        boolean encontrado = false;

        while (!encontrado && i < Candidatos.size()) {
            if (Candidatos.get(i).getId() == indiceElegido) {
                encontrado = true;
                v = Candidatos.get(i);
            } else {
                i++;
            }
        }

        return v;
    }

    /**
     * Inicializa el vector Predecesor para saber el camino minimo
     */
    private void InicializarCaminoPredecesores() {
        Predecesor = new Punto[T.size()];
        for (int i = 0; i < T.size(); i++) {
            Predecesor[i] = new Punto(0, 0, 0);
        }
    }

    public Punto buscarPunto(int id) {
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < T.size()) {
            if (T.get(i).getId() == id) {
                encontrado = true;
            } else {
                i++;
            }
        }

        return T.get(i);
    }

    public int getPuntoOrigen() {
        return puntoOrigen;
    }

    public void setPuntoOrigen(int id) {
        puntoOrigen = id;
    }

    public String crearTSP(String nombre) {
        String datos = "NAME : " + nombre
                + "\nTYPE : TOUR\n"
                + "DIMENSION : " + T.size()
                + "\nSOLUTION: " + calcularSuma()
                + "\nTOUR_SECTION\n";

        for (int i = 0; i < Distancias.length; i++) {
            ArrayList<Integer> aux = new ArrayList();
            if (i != puntoOrigen) {
                datos += Distancias[i] + " - ";
                int id = i;

                while (Predecesor[id].getId() != puntoOrigen) {
                    int k = 0;
                    aux.add(Predecesor[id].getId());// + ",";
                    id = Predecesor[id].getId();
                    k++;
                }

                aux.add(puntoOrigen);
                datos += invertirVector(aux) + "\n";
            }
        }

        datos += "-1\n"
                + "EOF";
        return datos;
    }

    public String mostrarTSP() {
        String datos = "DIMENSION : " + T.size()
                + "\nSOLUTION: " + calcularSuma()
                + "\nTOUR_SECTION\n";

        for (int i = 0; i < Distancias.length; i++) {
            ArrayList<Integer> aux = new ArrayList();
            if (i != puntoOrigen) {
                datos += Distancias[i] + " - ";
                int id = i;

                while (Predecesor[id].getId() != puntoOrigen) {
                    int k = 0;
                    aux.add(Predecesor[id].getId());// + ",";
                    id = Predecesor[id].getId();
                    k++;
                }

                aux.add(puntoOrigen);
                datos += invertirVector(aux) + "\n";
            }
        }

        datos += "-1\n"
                + "EOF";
        return datos;
    }

    private int calcularSuma() {
        int suma = 0;
        for (int i = 0; i < Distancias.length; i++) {
            suma += Distancias[i];
        }

        return suma;

    }

    private String invertirVector(ArrayList<Integer> Distancias) {
        String distanciasInvertidas = "";

        for (int j = Distancias.size() - 1; j > 0; j--) {
            distanciasInvertidas += Integer.toString(Distancias.get(j)) + ",";
        }
        distanciasInvertidas += Integer.toString(Distancias.get(0));

        return distanciasInvertidas;

    }

    private String ConvertirDistanciasCadena(Integer[] Distancias) {

        String distancias = "[";
        for (int i = 0; i < Distancias.length - 1; i++) {

            distancias += Distancias[i].toString() + ",";
        }
        distancias += Distancias[Distancias.length - 1].toString() + "]";

        return distancias;
    }
}
