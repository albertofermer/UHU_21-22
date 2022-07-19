package Controlador;

import Modelo.Algoritmos;
import Vista.Aleatorio;
import Vista.AutoAyuda;
import Vista.InformacionAvanzada;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Vista.InterfazGrafica;
import Vista.Mensaje;
import Modelo.Punto;
import java.awt.Canvas;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author Alberto Fernández
 */
public class Controlador implements ActionListener {

    private int REPETICION = 10;
    private Algoritmos a;
    private InterfazGrafica UIView;
    static private Aleatorio vAleatorio;
    static private InformacionAvanzada InformacionAvanzada;
    static private Mensaje vMensaje = null;
    private ArrayList<Punto> T;
    private AutoAyuda ayuda;
    private static Punto[] P = {new Punto(), new Punto(), new Punto()};
    private int maximo;
    private int talla;
    DecimalFormat df = new DecimalFormat("####0.00");
    private static int indice[] = {0, 0, 0};
    private static double dmin = 0;
    private Canvas canvas;
    private long TiempoInicio;
    private long TiempoFin;

    public Controlador() {
        vAleatorio = new Aleatorio();
        InformacionAvanzada = new InformacionAvanzada();
        vMensaje = new Mensaje();
        ayuda = new AutoAyuda();
        UIView = new InterfazGrafica(this,InformacionAvanzada);
        canvas = UIView.getCanvas();

        addListener();
        
        UIView.setLocationRelativeTo(null);
        UIView.setExtendedState(JFrame.MAXIMIZED_BOTH);
        UIView.setVisible(true);
        UIView.BotonBuscar.setEnabled(false);
        UIView.BotonInformacionAvanzada.setEnabled(false);
        
        a = UIView.getAlgoritmo();
    }
    
    public void setRepeticion(int r){
        REPETICION = r;
    }
    public static Aleatorio getvAleatorio() {
        return vAleatorio;
    }

    public static InformacionAvanzada getInformacionAvanzada() {
        return InformacionAvanzada;
    }

    public static Mensaje getvMensaje() {
        return vMensaje;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    private void addListener() {
        UIView.BotonSalir.addActionListener(this);
        UIView.BotonBuscar.addActionListener(this);
        UIView.BotonFichero.addActionListener(this);
        UIView.BotonAleatorio.addActionListener(this);
        UIView.BotonAyuda.addActionListener(this);
        UIView.BotonInformacionAvanzada.addActionListener(this);
        vAleatorio.BotonSalir.addActionListener(this);
        vAleatorio.BotonAceptar.addActionListener(this);
        InformacionAvanzada.InformacionAvanzadaAceptar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "Salir":
                System.exit(0);
                break;

            case "Buscar":
                switch ((String) UIView.Algoritmo.getSelectedItem()) {
                    case "Exhaustivo":
                        if (!UIView.esExperimental.isSelected()) {
                            TiempoInicio = System.nanoTime();
                            dmin = a.exhaustivo(0, T.size() - 1);
                            indice = a.getIndice();
                            TiempoFin = System.nanoTime() - TiempoInicio;
                            a.setEncontrado(true);
                            a.pintarCanvas();
                            vMensaje.Message("info", "Distancia encontrada correctamente");
                            UIView.escribirPuntos(indice);
                            UIView.escribirEtiquetasPuntos(T.get(indice[0] - 1), T.get(indice[1] - 1), T.get(indice[2] - 1));
                            UIView.escribirEtiquetasDistancia(T.get(indice[0] - 1), T.get(indice[1] - 1), T.get(indice[2] - 1));
                            UIView.escribirInformacionAvanzada("Exhaustivo", TiempoFin,1);
                        } else { // si está seleccionada la casilla Experimental
                            REPETICION = Integer.parseInt(UIView.Repeticiones.getText());
                            TiempoInicio = System.nanoTime();
                            for (int i = 0; i < REPETICION; i++) {
                                a.puntosAleatorios(maximo, talla);
                                dmin = a.exhaustivo(0, T.size() - 1);
                                indice = a.getIndice();
                            }
                            TiempoFin = System.nanoTime() - TiempoInicio;
                            a.setEncontrado(true);
                            a.pintarCanvas();
                            vMensaje.Message("info", "Distancia encontrada correctamente");
                            UIView.escribirPuntos(indice);
                            UIView.escribirEtiquetasPuntos(T.get(indice[0]-1),T.get(indice[1]-1),T.get(indice[2]-1));
                            UIView.escribirEtiquetasDistancia(T.get(indice[0]-1),T.get(indice[1]-1),T.get(indice[2]-1));
                            UIView.escribirInformacionAvanzada("Exhaustivo",TiempoFin,REPETICION);
                        }
                        break;

                    case "Divide y Vencerás":
                        if (!UIView.esExperimental.isSelected()) {
                            TiempoInicio = System.nanoTime();
                            dmin = a.dyv(0, T.size() - 1);
                            indice = a.getIndice();
                            TiempoFin = System.nanoTime() - TiempoInicio;
                            a.setEncontrado(true);
                            a.pintarCanvas();
                            vMensaje.Message("info", "Distancia encontrada correctamente");
                            UIView.escribirPuntos(indice);
                            UIView.escribirEtiquetasPuntos(T.get(indice[0] - 1), T.get(indice[1] - 1), T.get(indice[2] - 1));
                            UIView.escribirEtiquetasDistancia(T.get(indice[0] - 1), T.get(indice[1] - 1), T.get(indice[2] - 1));
                            UIView.escribirInformacionAvanzada("Divide y Vencerás", TiempoFin,1);


                        } else {
                            REPETICION = Integer.parseInt(UIView.Repeticiones.getText());
                            TiempoInicio = System.nanoTime();
                            for (int i = 0; i < REPETICION; i++) {
                                a.puntosAleatorios(maximo, talla);
                                dmin = a.dyv(0, T.size() - 1);
                                indice = a.getIndice();
                            }

                            TiempoFin = System.nanoTime() - TiempoInicio;

                            a.setEncontrado(true);
                            a.pintarCanvas();
                            vMensaje.Message("info", "Distancia encontrada correctamente");
                            UIView.escribirPuntos(indice);
                            UIView.escribirEtiquetasPuntos(T.get(indice[0]-1),T.get(indice[1]-1),T.get(indice[2]-1));
                            UIView.escribirEtiquetasDistancia(T.get(indice[0]-1),T.get(indice[1]-1),T.get(indice[2]-1));
                            UIView.escribirInformacionAvanzada("Divide y Vencerás",TiempoFin,REPETICION);
                        }
                        break;
                    default:
                        throw new AssertionError();
                }
                break;
            case "ElegirFichero":
                a.leerFichero();
                a.setEncontrado(false);
                a.pintarCanvas();

                if (UIView.BotonInformacionAvanzada.isEnabled()) {
                    UIView.BotonInformacionAvanzada.setEnabled(false);
                }
                T = a.getPuntos();
                break;
            case "Aleatorio":

                vAleatorio.setLocationRelativeTo(null);
                vAleatorio.setVisible(true);

                break;
            case "Ayuda":
                ayuda.setVisible(true);

                break;

            case "SalirAleatorio":
                vAleatorio.dispose();
                break;

            case "Aceptar": //menu de puntos aleatorios

                boolean datosOK = false;

                try {
                    maximo = Integer.parseInt(vAleatorio.max.getText());
                    talla = Integer.parseInt(vAleatorio.talla.getText());
                    datosOK = true;
                } catch (NumberFormatException ex) {
                    vMensaje.Message("error", ex.getMessage());
                    datosOK = false;
                }

                if (datosOK) {

                    a.puntosAleatorios(maximo, talla);
                    a.setEncontrado(false);
                    a.pintarCanvas();
                    vAleatorio.dispose();
                    UIView.Fichero.setText("Fichero aleatorio");
                    UIView.Fichero.setForeground(Color.white);

                    if (!UIView.BotonBuscar.isEnabled()) {
                        UIView.BotonBuscar.setEnabled(true);

                    }
                    if (UIView.BotonInformacionAvanzada.isEnabled()) {
                        UIView.BotonInformacionAvanzada.setEnabled(false);
                    }

                    T = a.getPuntos();

                }

                break;

            case "Información Avanzada":
                InformacionAvanzada.setLocationRelativeTo(null);
                InformacionAvanzada.setVisible(true);

                break;

            case "AceptarInformacionAvanzada":
                InformacionAvanzada.dispose();
                break;
            default:
                throw new AssertionError();
        }

        UIView.dmin.setText("" + df.format(this.dmin));
    }
}
