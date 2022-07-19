package Controlador;

import static Modelo.Automata.crearDOT;
import Modelo.Automata;
import Vista.Mensaje;
import Vista.VentanaAyuda;
import Vista.VentanaPrincipal;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Alberto Fernández Merchán & Juan Manuel Ruíz Pérez
 */
public class ControladorPrincipal implements ActionListener {

    Automata a = null;
    private VentanaPrincipal vp = null;
    private JFileChooser selector;
    private String guardarRuta = null;
    private Mensaje vMensaje = null;
    private int pasoActual = 0;
    private ArrayList<Integer> status = null;
    private ArrayList<Integer> visitados = null;
    private String[] lectura;

    public ControladorPrincipal() {
        a = new Automata();
        status = new ArrayList();
        visitados = new ArrayList();
        vMensaje = new Mensaje();
        vp = new VentanaPrincipal();
        vp.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vp.setLocationRelativeTo(null);
        vp.setVisible(true);
        vp.EstadoCadena.setText("");
        addListener();

    }

    /**
     * Realiza las funciones de los botones de la ventana principal
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "GuardarComo":
                guardarRuta = seleccionarRuta();
                guardarFichero(guardarRuta, vp.TextoArea.getText());
                break;
            case "Cargar":
                vp.TextoArea.setText("");
                leerFichero();

                break;
            case "Guardar":
                if (guardarRuta == null) {
                    guardarRuta = seleccionarRuta();
                    guardarFichero(guardarRuta, vp.TextoArea.getText());
                } else {
                    guardarFichero(guardarRuta, vp.TextoArea.getText());
                }
                break;
            case "Generar":
                lectura = vp.cadena.getText().split(",");
                a = new Automata();
                a.leerFichero(vp.TextoArea.getText());
                status.clear();
                visitados.clear();
                status.addAll(a.lambdaClausura(a.getEstadoInicial(), visitados));
                System.out.println("INICIALMENTE: " + status);
                pasoActual = 0;
                crearDOT(a, status, lectura, pasoActual);
                status.clear();
                visitados.clear();
                try {
                    sleep(1750); //Espera para que cargue el motor del graphviz
                } catch (InterruptedException ex) {
                }
                vp.lienzo.repaint();

                break;

            case "Reconocer Cadena":
                if (a != null) {
                    if (a.reconocer(vp.cadena.getText())) {
                        vp.EstadoCadena.setText("ACEPTADA");
                        vp.EstadoCadena.setForeground(Color.green);
                    } else {
                        vp.EstadoCadena.setText("RECHAZADA");
                        vp.EstadoCadena.setForeground(Color.red);
                    }

                } else {
                    vMensaje.Mensaje("error", "Primero debe generar el autómata.");
                }

                break;
            case "PasoAPaso":
                lectura = vp.cadena.getText().split(",");
                if (pasoActual == lectura.length) { //Si el paso es igual a la longitud de la cadena: empieza de nuevo
                    pasoActual = 0;
                    if (a.reconocer(vp.cadena.getText())) {
                        System.out.println("La cadena es válida");

                    } else {
                        System.out.println("La cadena no es válida");

                    }

                    status.clear();
                    visitados.clear();
                    status.add(a.getEstadoInicial());
                    status.addAll(a.lambdaClausura(a.getEstadoInicial(), visitados));
                    crearDOT(a, status, lectura, pasoActual);
                    try {
                        sleep(1750); //Espera para que cargue el motor del graphviz
                    } catch (InterruptedException ex) {
                    }
                    vp.lienzo.repaint();
                    pasoActual = 0;
                } else {

                    ArrayList<Integer> aux = new ArrayList();
                    if (pasoActual == 0) {
                        status.addAll(a.lambdaClausura(a.getEstadoInicial(), visitados));
                    }

                    aux.addAll(a.siguientePaso(lectura[pasoActual], pasoActual, status));
                    status.clear();
                    visitados.clear();
                    status.addAll(aux);
                    crearDOT(a, status, lectura, (pasoActual + 1));
                    try {
                        sleep(1750); //Espera para que cargue el motor del graphviz
                    } catch (InterruptedException ex) {
                    }

                    vp.lienzo.repaint();

                    pasoActual++;
                }
                break;
            case "Paso Anterior":
                String[] lecturaA = vp.cadena.getText().split(",");
                pasoActual--;
                if (pasoActual < 0) {
                    pasoActual = 0;
                }
                status.clear();
                visitados.clear();
                status.addAll(a.lambdaClausura(a.getEstadoInicial(), visitados));
                ArrayList<Integer> aux = new ArrayList();
                for (int i = 0; i < pasoActual; i++) {
                    aux.clear();
                    aux.addAll(a.siguientePaso(lecturaA[i], i, status));
                    status.clear();
                    status.addAll(aux);
                }
                crearDOT(a, status, lecturaA, (pasoActual));
                try {
                    sleep(1750); //Espera para que cargue el motor del graphviz
                } catch (InterruptedException ex) {
                }

                vp.lienzo.repaint();
                break;
            case "CrearAutómata":
                ControladorMenu menu = new ControladorMenu(vp);
                break;
            case "creditos":
                vMensaje.Mensaje("info", "Autores: Alberto Fernández Merchán y Juan M. Ruíz Pérez\nAsignatura: Algorítmica y Modelos de Computación\nCurso: 2021-2022");
                break;
            case "ayuda":
                VentanaAyuda vAyuda = new VentanaAyuda();
                vAyuda.setVisible(true);
                break;
            default:
                throw new AssertionError();
        }
    }

    /**
     * Añade ActionListeners a los componentes de la ventana principal.
     */
    public void addListener() {
        vp.CargarEditor.addActionListener(this);
        vp.GuardarComoEditor.addActionListener(this);
        vp.GuardarEditor.addActionListener(this);
        vp.GenerarFichero.addActionListener(this);
        vp.reconocerCadena.addActionListener(this);
        vp.Cargar.addActionListener(this);
        vp.GenerarGrafico.addActionListener(this);
        vp.PasoAPaso.addActionListener(this);
        vp.pasoAnterior.addActionListener(this);
        vp.BotonCrearAutomata.addActionListener(this);
        vp.creditosMenu.addActionListener(this);
        vp.ayudaMenu.addActionListener(this);
    }

    /**
     * Seleciona la ruta donde guardar el fichero.
     * @return
     */
    public String seleccionarRuta() {
        selector = new JFileChooser();
        selector.setCurrentDirectory(new java.io.File("user.dir"));
        selector.setDialogTitle("Guardar Fichero");
        selector.setDialogType(JFileChooser.SAVE_DIALOG);
        selector.setSelectedFile(new File("MiAutomata.auto"));
        selector.setFileFilter(new FileNameExtensionFilter("auto file", "auto"));

        selector.setAcceptAllFileFilterUsed(true);
        int saveDialog = selector.showSaveDialog(null);

        if (saveDialog == JFileChooser.APPROVE_OPTION) {

            guardarRuta = selector.getSelectedFile().toString();
            if (!guardarRuta.endsWith(".auto")) {
                guardarRuta += ".auto";
            }
            return guardarRuta;
        } else {
            System.out.println("No se ha seleccionado ninguna ruta.");
        }

        return null;
    }

    /**
     * Guarda el fichero con la ruta pasada por parámetro.
     *
     * @param ruta Ruta donde se quiere guardar el fichero
     * @param texto Texto que contiene el fichero que queremos guardar.
     * @return
     */
    private File guardarFichero(String ruta, String texto) {
        if (ruta == null) {
            System.out.println("Ruta nula. No se puede generar el fichero");
            return null;
        }
        File fichero = new File(ruta);

        try {
            if (fichero.createNewFile() && ruta != null) {
                System.out.println("Fichero creado correctamente. \nEn la ruta: " + ruta);
            } else {
                System.out.println("El fichero ya existe");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        OutputStream os = null;
        try {
            os = new FileOutputStream(fichero);
            os.write(texto.getBytes(), 0, texto.length());
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

    /**
     * Lee un fichero .auto del disco duro y lo escribe en el panel de edición
     * de la ventana principal.
     */
    public void leerFichero() //Lee un fichero del disco duro
    {
        selector = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("AUTO", "auto");
        selector.setFileFilter(filter);

        selector.setCurrentDirectory(new File(System.getProperty("user.dir")));

        int result = selector.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File fichero = selector.getSelectedFile();
            guardarRuta = fichero.getAbsolutePath(); //¿CanonicalPath?
            String[] nombre = fichero.getName().split("\\.");

            if (nombre[1].equals("auto")) {

                try {
                    try (FileReader fr = new FileReader(fichero); BufferedReader br = new BufferedReader(fr)) {
                        String lineaActual;
                        while ((lineaActual = br.readLine()) != null) {
                            vp.TextoArea.append(lineaActual + "\n");
                        }
                    }

                } catch (IOException | NumberFormatException e) {
                    vMensaje.Mensaje("error", e.getMessage());
                    System.out.println(e.getMessage());
                }
            }
        } else if (result == JFileChooser.CANCEL_OPTION) {
            vMensaje.Mensaje("error", "No se ha seleccionado ningún fichero.");
        }
    }

    public void setAutomata(Automata aut) {
        this.a = aut;
    }

}
