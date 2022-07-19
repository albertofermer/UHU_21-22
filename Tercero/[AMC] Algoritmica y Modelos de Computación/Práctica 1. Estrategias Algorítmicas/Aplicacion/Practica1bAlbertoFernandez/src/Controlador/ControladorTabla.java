package Controlador;

import Modelo.Algoritmos;
import Modelo.ModeloTabla;
import Vista.LongitudAristas;
import Vista.Mensaje;
import Vista.SolucionDetallada;
import Vista.VentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;

/**
 *
 * @author Alberto Fernández
 */
public class ControladorTabla implements ActionListener {

    private SolucionDetallada solucion;
    private LongitudAristas longAristas;
    private VentanaPrincipal v;
    private Mensaje vMensaje;
    private Algoritmos a;
    private ModeloTabla mt;
    private final int MAX = 10;

    public ControladorTabla(Controlador c, Algoritmos a) {
        v = c.getvPrincipal();
        solucion = new SolucionDetallada();
        longAristas = new LongitudAristas();
        vMensaje = new Mensaje();
        this.a = a;
        mt = new ModeloTabla(a, this);

        addListener();
    }

    public SolucionDetallada getSolucion() {
        return solucion;
    }

    public LongitudAristas getLongAristas() {
        return longAristas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Pesos":
                if (a.getNumPuntos() <= MAX) {
                    longAristas.setVisible(true);
                } else {
                    vMensaje.Message("error", "Solo se muestra para tallas menores de 10.");
                }
                break;
            case "Evolucion":
                if (a.getNumPuntos() <= MAX) {
                    solucion.setVisible(true);
                } else {
                    vMensaje.Message("error", "Solo se muestra para tallas menores de 10.");
                }
                break;
            default:
                throw new AssertionError();
        }
    }

    private void addListener() {
        v.TablaEvolucion.addActionListener(this);
        v.TablaPesos.addActionListener(this);
    }

    public JTable getTablaSolucion() {
        return solucion.getTabla();
    }

    public JTable getTablaAristas() {
        return longAristas.getTabla();
    }
}
