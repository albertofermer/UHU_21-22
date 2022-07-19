package Controlador;

import Modelo.Algoritmos;
import Vista.Aleatorio;
import Vista.InformacionAvanzada;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Vista.VentanaPrincipal;
import Vista.Mensaje;
import Modelo.Punto;
import Vista.SolucionDetallada;
import Vista.SolucionTSP;
import java.awt.Canvas;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Alberto Fernández
 */
public class Controlador implements ActionListener {

    private int REPETICION = 10;
    private Algoritmos a;
    private VentanaPrincipal UIView;
    static private Aleatorio vAleatorio;
    static private InformacionAvanzada InformacionAvanzada;
    static private Mensaje vMensaje = null;
    private ArrayList<Punto> T;
    private Punto Origen;
    private int maximo;
    private int talla;
    private Integer[] LongitudesCaminosMinimos;
    private Canvas canvas;
    private long TiempoInicio;
    private long TiempoFin;
    boolean darkMode = false;
    private JFileChooser selector;
    private SolucionDetallada solucion;
    private ControladorTabla ct;
    private SolucionTSP tsp;

    public Controlador() {
        vAleatorio = new Aleatorio();
        InformacionAvanzada = new InformacionAvanzada();
        vMensaje = new Mensaje();

        UIView = new VentanaPrincipal(this, InformacionAvanzada);
        canvas = UIView.getCanvas();

        addListener();

        UIView.setLocationRelativeTo(null);
        UIView.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        UIView.setVisible(true);
        UIView.BotonBuscar.setEnabled(false);
        UIView.BotonInformacionAvanzada.setEnabled(false);
        UIView.GuardarFichero.setEnabled(false);

        a = UIView.getAlgoritmo();
        ct = new ControladorTabla(this, a);
        
        tsp = new SolucionTSP(a);
    }

    public VentanaPrincipal getvPrincipal() {
        return UIView;
    }

    public void setRepeticion(int r) {
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
        UIView.BotonAleatorio.addActionListener(this);
        UIView.FicheroAleatorio.addActionListener(this);
        UIView.BotonInformacionAvanzada.addActionListener(this);
        vAleatorio.BotonSalir.addActionListener(this);
        vAleatorio.BotonAceptar.addActionListener(this);
        InformacionAvanzada.InformacionAvanzadaAceptar.addActionListener(this);
        UIView.CargarFichero.addActionListener(this);
        UIView.GuardarFichero.addActionListener(this);
        UIView.BotonModoOscuro.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "Salir":
                System.exit(0);
                break;

            case "Buscar":
                if (Integer.parseInt(UIView.PuntoOrigen.getText()) >= T.size()) {
                    vMensaje.Message("error", "El punto de origen debe encontrarse\n en la lista.");
                } else {
                    if (T.size() > 10) {
                        ct.getLongAristas().dispose();
                        ct.getSolucion().dispose();
                    }
                    switch ((String) UIView.Algoritmo.getSelectedItem()) {
                        case "Dijkstra":
                            a.setPuntoOrigen(Integer.parseInt(UIView.PuntoOrigen.getText()));
                            Origen = a.buscarPunto(Integer.parseInt(UIView.PuntoOrigen.getText()));
                            if (!UIView.esExperimental.isSelected()) {
                                UIView.setPuntoOrigen(Integer.parseInt(UIView.PuntoOrigen.getText()));
                                Origen = a.buscarPunto(Integer.parseInt(UIView.PuntoOrigen.getText()));
                                TiempoInicio = System.nanoTime();
                                LongitudesCaminosMinimos = a.Dijkstra(Origen);
                                TiempoFin = System.nanoTime() - TiempoInicio;
                                a.setEncontrado(true);
                                a.actualizaPredecesores();
                                a.pintarCanvas();

                                vMensaje.Message("info", "Camino encontrado correctamente");
                                //UIView.escribirPuntos(LongitudesCaminosMinimos);
                                UIView.escribirEtiquetasSuma(LongitudesCaminosMinimos);
                                UIView.escribirEtiquetasSolucion(LongitudesCaminosMinimos);
                                UIView.escribirEtiquetaPuntos(a.getPuntosSolucion());
                                UIView.escribirInformacionAvanzada("Dijkstra", TiempoFin, 1);

                            } else { // si está seleccionada la casilla Experimental

                                REPETICION = Integer.parseInt(UIView.Repeticiones.getText());
                                UIView.setPuntoOrigen(Integer.parseInt(UIView.PuntoOrigen.getText()));
                                Origen = a.buscarPunto(Integer.parseInt(UIView.PuntoOrigen.getText()));
                                TiempoInicio = System.nanoTime();

                                for (int i = 0; i < REPETICION; i++) {
                                    a.puntosAleatorios(maximo, talla);
                                    LongitudesCaminosMinimos = a.Dijkstra(Origen);
                                }

                                TiempoFin = System.nanoTime() - TiempoInicio;
                                a.setEncontrado(true);
                                a.actualizaPredecesores();
                                a.pintarCanvas();

                                vMensaje.Message("info", "Camino encontrado correctamente");
                                //UIView.escribirPuntos(LongitudesCaminosMinimos);
                                UIView.escribirEtiquetasSuma(LongitudesCaminosMinimos);
                                UIView.escribirEtiquetasSolucion(LongitudesCaminosMinimos);
                                UIView.escribirEtiquetaPuntos(a.getPuntosSolucion());
                                UIView.escribirInformacionAvanzada("Dijkstra", TiempoFin, 10);
                            }
                            break;
                        default:
                            throw new AssertionError();
                    }
                    UIView.GuardarFichero.setEnabled(true);
                }
                tsp.setTSP();
                System.out.println(a.mostrarTSP());
                
                break;
            case "Aleatorio":

                vAleatorio.setLocationRelativeTo(null);
                vAleatorio.setVisible(true);

                break;
            case "ElegirFichero":
                a.setEncontrado(false);
                a.leerFichero();
                a.setEncontrado(false);
                a.pintarCanvas();

                if (UIView.BotonInformacionAvanzada.isEnabled()) {
                    UIView.BotonInformacionAvanzada.setEnabled(false);
                }
                T = a.getPuntos();
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
            case "Guardar":
                String ruta = seleccionarRuta();
                Path path = Paths.get(ruta);
                String nombre = path.getFileName().toString();
                String datos = a.crearTSP(nombre);

                File fichero = escribirFichero(ruta, datos);

                break;
            case "ModoOscuro":
                canvas.repaint();
                break;

            default:
                throw new AssertionError();
        }

    }

    public void setAleatorio(Aleatorio vAleatorio) {

        this.vAleatorio = vAleatorio;
    }

    public String seleccionarRuta() {
        String guardarRuta = "null";
        selector = new JFileChooser();
        selector.setCurrentDirectory(new java.io.File("user.dir"));
        selector.setDialogTitle("Guardar Fichero");
        selector.setDialogType(JFileChooser.SAVE_DIALOG);
        selector.setSelectedFile(new File("MisPuntos.tsp"));
        selector.setFileFilter(new FileNameExtensionFilter("tsp file", "tsp"));

        selector.setAcceptAllFileFilterUsed(true);
        int saveDialog = selector.showSaveDialog(null);

        if (saveDialog == JFileChooser.APPROVE_OPTION) {

            guardarRuta = selector.getSelectedFile().toString();
            if (!guardarRuta.endsWith(".tsp")) {
                guardarRuta += ".tsp";
            }

        } else {
            vMensaje.Message("error", "No se ha seleccionado ninguna ruta.");
        }

        return guardarRuta;
    }

    private File escribirFichero(String ruta, String linea) {
        File fichero = new File(ruta);

        try {
            if (fichero.createNewFile()) {
                vMensaje.Message("info", "Fichero creado correctamente. \nEn la ruta: " + ruta);
            } else {
                vMensaje.Message("error", "El fichero ya existe");
            }
        } catch (IOException ex) {
            vMensaje.Message("error", ex.getMessage());
        }

        OutputStream os = null;
        try {
            os = new FileOutputStream(fichero);
            os.write(linea.getBytes(), 0, linea.length());
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
        }

        return fichero;
    }
}
