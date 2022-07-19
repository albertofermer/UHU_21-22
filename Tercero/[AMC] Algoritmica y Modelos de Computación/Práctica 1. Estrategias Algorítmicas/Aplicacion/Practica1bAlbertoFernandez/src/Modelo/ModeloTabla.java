package Modelo;

import Controlador.Controlador;
import Controlador.ControladorTabla;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Alberto Fernández
 */
public class ModeloTabla {

    private ControladorTabla ct;
    private JTable tablaEvolucion;
    private JTable tablaAristas;
    private Controlador c;
    private TableColumnModel tcmSolucion;
    private DefaultTableModel dtmSolucion;
    private Algoritmos a;
    private TableColumnModel tcmAristas;
    private DefaultTableModel dtmAristas;
    private String[] fila;

    public ModeloTabla(Algoritmos a, ControladorTabla ct) {
        this.ct = ct;
        this.a = a;
        tablaEvolucion = ct.getTablaSolucion();
        tablaAristas = ct.getTablaAristas();
        a.setModeloTabla(this);

        dtmSolucion = (DefaultTableModel) tablaEvolucion.getModel();
        dtmAristas = (DefaultTableModel) tablaAristas.getModel();
    }

    public void añadirFilaEvolucion(String paso, String v, String c, String d) {
        dtmSolucion.addRow(new Object[]{paso, v, c, d});
    }

    public void añadirFilaAristas(String[] fila) {
        dtmAristas.addRow(fila);
    }

    public void añadirColumnaAristas(Object columna) {

        dtmAristas.addColumn(columna);

    }

    public void eliminarDatosSolucion() {
        dtmSolucion.setNumRows(0);
    }
    public void eliminarDatosAristas() {
        dtmAristas.setNumRows(0);
        dtmAristas.setColumnCount(0);
    }

    void rellenarTablaAristas(Double[][] LongitudAristas) {
        eliminarDatosAristas();
        añadirColumnaAristas("L");
        for (int i = 0; i < a.getNumPuntos(); i++) {
            añadirColumnaAristas(i);
        }

        fila = new String[a.getNumPuntos()+1];
        for (int i = 0; i < fila.length; i++) {
            fila[i] = String.format("%.0f", -1d);
        }
        for (int i = 0; i < a.getNumPuntos(); i++) {
            for (int j = 1; j < fila.length; j++) {
                fila[j] = String.format("%.0f",LongitudAristas[i][j-1]);
                if (LongitudAristas[i][j-1].isInfinite())
                    fila[j] = "\u221E";
            }
            fila[0] = String.format("%.0f",(double)i);
            añadirFilaAristas(fila);
            //System.out.println(ConvertirVectorCadena(fila));

        }

    }

    private String ConvertirVectorCadena(Integer[] Vector) {
        String vector = "[";
        for (int i = 0; i < Vector.length - 1; i++) {

            vector += Vector[i].toString() + ",";
        }
        vector += Vector[Vector.length - 1].toString() + "]";

        return vector;
    }

}
