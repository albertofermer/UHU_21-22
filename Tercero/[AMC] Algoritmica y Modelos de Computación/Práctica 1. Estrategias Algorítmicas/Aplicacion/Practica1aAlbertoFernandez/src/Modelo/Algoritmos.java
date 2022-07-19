package Modelo;

import Controlador.Controlador;
import static Modelo.Punto.*;
import Vista.Aleatorio;
import Vista.InformacionAvanzada;
import Vista.InterfazGrafica;
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
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Algoritmos {

    private Controlador c;

    private static final JFileChooser filechooser = new JFileChooser();
    private ArrayList<Punto> T = new ArrayList();

    private final int izq = 0;
    private int dcha;

    private int maximo;
    private int talla;

    //DecimalFormat df = new DecimalFormat("####0.00");
    private final int indice[] = {0, 0, 0};
    private static double dmin = 0;

    private InterfazGrafica UIView;
    //private Aleatorio vAleatorio;
    //private InformacionAvanzada InformacionAvanzada;
    private Mensaje vMensaje = null;
    private Canvas canvas;

    private boolean PuntoEncontrado = false;

    public Algoritmos(InterfazGrafica UIView, Aleatorio vAleatorio, InformacionAvanzada ia, Mensaje vMensaje) {
        this.UIView = UIView;
        //this.vAleatorio = vAleatorio;
        //this.InformacionAvanzada = ia;
        this.vMensaje = vMensaje;
    }

    public Algoritmos() {
    }

    public void setCanvas(Canvas cv) {
        canvas = cv;
    }

    public void puntosAleatorios(int max, int talla) //Genera puntos aleatorios.
    {
        T.clear();
        for (int i = 0; i < talla; i++) {

            double x = ((random() * max));
            double y = (random() * max);

            Punto p = new Punto(x, y);
            T.add(p);

            dcha = T.size() - 1;

        }
    }

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

                        while ((lineaActual = br.readLine()) != null) {
                            lineasLeidas++;
                            String[] linea = lineaActual.split(" ");
                            if (lineasLeidas > 6 && !(lineaActual.equals("") || lineaActual.equals("EOF"))) {
                                if (!linea[0].equals("EOF")) {
                                    T.add(new Punto(Double.parseDouble(linea[1]), Double.parseDouble(linea[2])));
                                }
                            }
                        }
                    }
                    if (UIView != null) {
                        UIView.Fichero.setText(fichero.getName());
                        UIView.Fichero.setForeground(Color.white);
                        UIView.BotonBuscar.setEnabled(true);
                        pintarCanvas();
                    }

                    //System.out.println("Fichero " + fichero.getName() + " leido correctamente");
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

        dcha = T.size() - 1;

        return T;
    }

    public void ver() {
        DecimalFormat df = new DecimalFormat("####0.00");
        for (int i = 0; i < T.size(); i++) {
            System.out.println("T[" + (i + 1) + "]:\t X: " + df.format(T.get(i).getx()) + "\t Y: " + df.format(T.get(i).gety()) + "");
        }

    }

    public double exhaustivo(int izq, int der) {

        dmin = Double.POSITIVE_INFINITY;
        for (int i = izq; i <= der; i++) {
            for (int j = i + 1; j <= der; j++) {
                for (int l = j + 1; l <= der; l++) {
                    if ((distancia(T.get(i), T.get(j)) + distancia(T.get(i), T.get(l))) < dmin) {
                        dmin = (distancia(T.get(i), T.get(j)) + distancia(T.get(i), T.get(l)));
                        indice[0] = i + 1;
                        indice[1] = j + 1;
                        indice[2] = l + 1;

                    }
                    if ((distancia(T.get(j), T.get(i)) + distancia(T.get(j), T.get(l))) < dmin) {
                        dmin = (distancia(T.get(j), T.get(i)) + distancia(T.get(j), T.get(l)));
                        indice[0] = j + 1;
                        indice[1] = i + 1;
                        indice[2] = l + 1;
                    }
                    if ((distancia(T.get(l), T.get(i)) + distancia(T.get(l), T.get(j))) < dmin) {
                        dmin = (distancia(T.get(l), T.get(i)) + distancia(T.get(l), T.get(j)));
                        indice[0] = l + 1;
                        indice[1] = i + 1;
                        indice[2] = j + 1;
                    }
                }
            }
        }
        return dmin;
    }

    private void ordenarX(ArrayList<Punto> T) {
        Punto arr[] = new Punto[T.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = T.get(i);
        }

        int n = T.size();

        for (int i = n / 2 - 1; i >= 0; i--) {
            creaMonticuloX(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            Punto temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            creaMonticuloX(arr, i, 0);
        }

        T.clear();
        T.addAll(Arrays.asList(arr));
        //ver(T);
    }

    private static void creaMonticuloX(Punto[] arr, int n, int i) {
        int mayor = i; // El mayor es la raiz
        int izq = 2 * i + 1; // izquierda = 2*i + 1
        int der = 2 * i + 2; // derecha = 2*i + 2

        // Si el hijo izq es mayor que la raiz
        if (izq < n && arr[izq].getx() > arr[mayor].getx()) {
            mayor = izq;
        } else if (izq < n && arr[izq].getx() == arr[mayor].getx() && arr[izq].gety() > arr[mayor].gety()) {
            mayor = izq;
        }
        // Si hijo derecho es mayor que la raiz
        if (der < n && arr[der].getx() > arr[mayor].getx()) {
            mayor = der;
        } else if (der < n && arr[der].getx() == arr[mayor].getx() && arr[der].gety() > arr[mayor].gety()) {
            mayor = der;
        }
        // Si el mayor no es la raiz
        if (mayor != i) {
            Punto aux = arr[i];
            arr[i] = arr[mayor];
            arr[mayor] = aux;

            // Llamada recursiva para llamar al subarbol del hijo mayor.
            creaMonticuloX(arr, n, mayor);
        }
    }

    private static void creaMonticuloY(Punto[] arr, int n, int i) {
        int mayor = i; // El mayor es la raiz
        int izq = 2 * i + 1; // izquierda = 2*i + 1
        int der = 2 * i + 2; // derecha = 2*i + 2

        // Si el hijo izq es mayor que la raiz
        if (izq < n && arr[izq].gety() > arr[mayor].gety()) {
            mayor = izq;
        } else if (izq < n && arr[izq].gety() == arr[mayor].gety() && arr[izq].getx() > arr[mayor].getx()) {
            mayor = izq;
        }
        // Si hijo derecho es mayor que la raiz
        if (der < n && arr[der].gety() > arr[mayor].gety()) {
            mayor = der;
        } else if (der < n && arr[der].gety() == arr[mayor].gety() && arr[der].getx() > arr[mayor].getx()) {
            mayor = der;
        }
        // Si el mayor no es la raiz
        if (mayor != i) {
            Punto aux = arr[i];
            arr[i] = arr[mayor];
            arr[mayor] = aux;

            // Llamada recursiva para llamar al subarbol del hijo mayor.
            creaMonticuloY(arr, n, mayor);
        }
    }

    public Punto getMayorX() { //Devuelve el punto con la X mayor
        Punto mayorX = new Punto(10, 10);
        ArrayList<Punto> Ordenado = new ArrayList<>();

        for (int i = 0; i < T.size(); i++) {
            Ordenado.add(T.get(i));
        }

        ordenarX(Ordenado);
        if (Ordenado.size() > 0) {
            mayorX = Ordenado.get(Ordenado.size() - 1);
        }

        return mayorX;
    }

    public Punto getMayorY() { //Devuelve el punto con la Y mayor
        Punto mayorY = new Punto(10, 10);
        ArrayList<Punto> Ordenado = new ArrayList<>();

        for (int i = 0; i < T.size(); i++) {
            Ordenado.add(T.get(i));
        }

        ordenarY(Ordenado);
        if (Ordenado.size() > 0) {
            mayorY = Ordenado.get(Ordenado.size() - 1);
        }

        return mayorY;
    }

    private void ordenarY(ArrayList<Punto> T) {
        Punto arr[] = new Punto[T.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = T.get(i);
        }

        int n = T.size();

        for (int i = n / 2 - 1; i >= 0; i--) {
            creaMonticuloY(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            Punto temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            creaMonticuloY(arr, i, 0);
        }

        T.clear();
        T.addAll(Arrays.asList(arr));
    }

    public double dyv(int i, int d) {
        ordenarX(T);
        dmin = dyv(i, d, indice);
        return dmin;
    }

    private double dyv(int izq, int dcha, int[] index) {
        if (dcha - izq + 1 >= 4) {
            int[] indiceI = {0, 0, 0};
            int[] indiceD = {0, 0, 0};
            int xm = (izq + dcha) / 2;
            int a1, a2;
            boolean fin;
            dmin = Double.POSITIVE_INFINITY;
            //Dividimos el problema en 2 subproblemas de tamaño mitad.
            double di = dyv(izq, xm, index);
            for (int i = 0; i < 3; i++) { // Guardo los indices de los puntos de la parte izquierda para no perderlos.
                indiceI[i] = index[i];
            }
            double dd = dyv(xm + 1, dcha, index);
            for (int i = 0; i < 3; i++) {  // Guardo los indices de los puntos de la parte derecha para no perderlos.
                indiceD[i] = index[i];
            }
            //Nos quedamos con la distancia minima de ambas partes.
            dmin = minimo(di, dd);
            if (dmin == di) { //Si la distancia minima resulta ser la de la izquierda.
                for (int i = 0; i < 3; i++) {
                    index[i] = indiceI[i];
                }
            } else { //Si la distancia mínima resulta ser la de la derecha
                for (int i = 0; i < 3; i++) {
                    index[i] = indiceD[i];
                }
            }
//Selecciona la distancia minima que hay entre el punto medio y el extremo izquierdo

            a1 = xm;
            fin = false;
            while (a1 >= izq && !fin) {
                if (((T.get(xm + 1).getx() - T.get(a1).getx())) > dmin) {
                    fin = true;
                } else {
                    a1--;
                }
            }
            a2 = xm + 1;
            fin = false;
            while (a2 <= dcha && !fin) {
                if ((T.get(a2).getx() - T.get(xm).getx()) > dmin) {
                    fin = true;
                } else {
                    a2++;
                }
            }
//Compara 1 de la izquierda con 2 de la derecha
            for (int a3 = a1 + 1; a3 <= xm; a3++) {
                for (int a4 = xm + 1; a4 <= a2 - 1; a4++) {
                    for (int a5 = a4 + 1; a5 <= dcha; a5++) {
                        if (distancia(T.get(a3), T.get(a4)) + distancia(T.get(a3), T.get(a5)) < dmin) {
                            dmin = distancia(T.get(a3), T.get(a4)) + distancia(T.get(a3), T.get(a5));
                            index[0] = a3 + 1;
                            index[1] = a4 + 1;
                            index[2] = a5 + 1;

                        }
                        if (distancia(T.get(a4), T.get(a3)) + distancia(T.get(a4), T.get(a5)) < dmin) {
                            dmin = distancia(T.get(a4), T.get(a3)) + distancia(T.get(a4), T.get(a5));
                            index[0] = a4 + 1;
                            index[1] = a3 + 1;
                            index[2] = a5 + 1;

                        }
                        if (distancia(T.get(a5), T.get(a3)) + distancia(T.get(a5), T.get(a4)) < dmin) {
                            dmin = distancia(T.get(a5), T.get(a3)) + distancia(T.get(a5), T.get(a4));
                            index[0] = a5 + 1;
                            index[1] = a3 + 1;
                            index[2] = a4 + 1;

                        }
                    }
                }
            }
//Compara 2 de la izquierda con 1 de la derecha
            for (int a3 = a1 + 1; a3 <= xm; a3++) {
                for (int a4 = a3 + 1; a4 <= xm; a4++) {
                    for (int a5 = xm + 1; a5 <= a2 - 1; a5++) { //
                        if (distancia(T.get(a3), T.get(a4)) + distancia(T.get(a3), T.get(a5)) < dmin) {
                            dmin = distancia(T.get(a3), T.get(a4)) + distancia(T.get(a3), T.get(a5));
                            index[0] = a3 + 1;
                            index[1] = a4 + 1;
                            index[2] = a5 + 1;

                        }
                        if (distancia(T.get(a4), T.get(a3)) + distancia(T.get(a4), T.get(a5)) < dmin) {
                            dmin = distancia(T.get(a4), T.get(a3)) + distancia(T.get(a4), T.get(a5));
                            index[0] = a4 + 1;
                            index[1] = a3 + 1;
                            index[2] = a5 + 1;

                        }
                        if (distancia(T.get(a5), T.get(a3)) + distancia(T.get(a5), T.get(a4)) < dmin) {
                            dmin = distancia(T.get(a5), T.get(a3)) + distancia(T.get(a5), T.get(a4));
                            index[0] = a5 + 1;
                            index[1] = a3 + 1;
                            index[2] = a4 + 1;

                        }
                    }
                }
            }
            return dmin;
        } else {
            return exhaustivo(izq, dcha);
        }
    }

    private static double minimo(double a, double b) {
        double minimo = b;

        if (a < b) {
            minimo = a;
        }

        return minimo;
    }

    public void pintarCanvas() {
        canvas.repaint();

    }

    public ArrayList<Punto> getPuntos() {
        return T;
    }

    public ArrayList<Punto> setPuntos(ArrayList<Punto> T) {
        this.T = T;
        return this.T;
    }

    public int[] getIndice() {
        return indice;
    }

    public boolean getEncontrado() {
        return PuntoEncontrado;
    }

    public void setEncontrado(boolean encontrado) {
        PuntoEncontrado = encontrado;
    }

}
