/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Automata;
import Modelo.Transicion;
import Modelo.TransicionL;
import Vista.Mensaje;
import Vista.VentanaAutomata;
import Vista.VentanaPrincipal;
import Vista.VentanaTransiciones;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alberto Fernández & Juan Manuel Ruíz Pérez
 */
public class ControladorMenu implements ActionListener {

    private VentanaAutomata vAutomata = null;
    private VentanaPrincipal vp = null;
    private VentanaTransiciones vTransiciones = null;
    private Mensaje vMensaje = null;
    private DefaultListModel lm = null;
    Automata aut = new Automata();

    public DefaultTableModel modeloTabla = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public ControladorMenu(VentanaPrincipal vp) {
        this.vp = vp;
        vMensaje = new Mensaje();
        vAutomata = new VentanaAutomata();
        vAutomata.setLocationRelativeTo(null);
        vTransiciones = new VentanaTransiciones();
        vTransiciones.setLocationRelativeTo(null);
        vAutomata.setVisible(true);
        addListener();
    }

    public void addListener() {
        vAutomata.Anadirtransicion.addActionListener(this);
        vAutomata.Guardarautomata.addActionListener(this);
        vTransiciones.TransicionLista.addActionListener(this);
        vAutomata.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                vAutomataMouseClicked(evt);
            }
        });
        vAutomata.BotonBorrarTransicion.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Anadir transiciones":
                vAutomata.setVisible(false);
                dibujarTabla(vAutomata);
                vTransiciones.setVisible(true);

                break;
            case "Guardar autómata":
                boolean ok = false;
                //Añadir Estados:
                for (int i = 0; i < Integer.parseInt(vAutomata.Numerodeestados.getText()); i++) {
                    aut.agregarEstado(i);
                }
                //Establecer estado inicial:
                aut.setEstadoInicial(Integer.parseInt(vAutomata.Selectorinicial.getSelectedItem().toString()));
                //Establecer estados finales:
                for (int i = 0; i < vAutomata.listaEstados.getModel().getSize(); i++) {
                    if (vAutomata.listaEstados.isSelectedIndex(i)) {
                        aut.agregarFinal(i);
                    }
                }
                //Establecer transiciones:

                //Escribir automata en la JTextBox:
                try {
                    vp.TextoArea.setText(aut.toString());
                    ok = true;
                } catch (Exception ex) {
                    vMensaje.Mensaje("error", "Revisa que el autómata esté bien formado.");
                }

                if (ok) {
                    vAutomata.dispose();
                }
                break;
            case "Ok":
                if (vTransiciones.ComboBoxEstadoOrigen.getSelectedItem() == null || vTransiciones.CampoTextoTransicion.getText() == null || vTransiciones.ComboBoxEstadoDestino.getSelectedItem() == null) {
                    vMensaje.Mensaje("error", "Inserta los datos correctamente.");
                } else if (vTransiciones.CampoTextoTransicion.getText().equals("lambda")) {
                    ArrayList<Integer> destinos = new ArrayList();
                    destinos.add(Integer.parseInt(vTransiciones.ComboBoxEstadoDestino.getSelectedItem().toString()));
                    aut.agregarL_Transicion(Integer.parseInt(vTransiciones.ComboBoxEstadoOrigen.getSelectedItem().toString()), destinos);

                } else {
                    ArrayList<Integer> destinos = new ArrayList();
                    destinos.add(Integer.parseInt(vTransiciones.ComboBoxEstadoDestino.getSelectedItem().toString()));

                    aut.agregarTransicion(Integer.parseInt(vTransiciones.ComboBoxEstadoOrigen.getSelectedItem().toString()), vTransiciones.CampoTextoTransicion.getText(), destinos);

                }
                vTransiciones.dispose();
                dibujarTabla(vAutomata);
                rellenarTabla(aut.getTransiciones(), aut.getTransicionesL());
                vAutomata.setVisible(true);
                break;
            case "Borrar transición":
                int fila = vAutomata.Tablatransiciones.getSelectedRow();
                ArrayList<Integer> destinosborrar = new ArrayList();
                String[] estadosdestino = vAutomata.Tablatransiciones.getValueAt(fila, 2).toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
                for (int i = 0; i < estadosdestino.length; i++) {
                    destinosborrar.add(Integer.parseInt(estadosdestino[i]));
                }
                //destinosborrar.add(Integer.parseInt(vAutomata.Tablatransiciones.getValueAt(fila, 2).toString()));
                aut.borrarTransicion(Integer.parseInt(vAutomata.Tablatransiciones.getValueAt(fila, 0).toString()), vAutomata.Tablatransiciones.getValueAt(fila, 1).toString(), destinosborrar);
                rellenarTabla(aut.getTransiciones(), aut.getTransicionesL());
                break;
            default:
                throw new AssertionError();
        }
    }

    public void dibujarTabla(VentanaAutomata vAutomata) {
        vAutomata.Tablatransiciones.setModel(modeloTabla);

        String[] columnasTabla = {"Origen", "Símbolo", "Destino"};
        modeloTabla.setColumnIdentifiers(columnasTabla);

        vAutomata.Tablatransiciones.getTableHeader().setResizingAllowed(false);
        vAutomata.Tablatransiciones.getTableHeader().setReorderingAllowed(false);
        vAutomata.Tablatransiciones.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        vAutomata.Tablatransiciones.getColumnModel().getColumn(0).setPreferredWidth(30);
        vAutomata.Tablatransiciones.getColumnModel().getColumn(1).setPreferredWidth(30);
        vAutomata.Tablatransiciones.getColumnModel().getColumn(2).setPreferredWidth(30);

    }

    public void rellenarTabla(ArrayList<Transicion> Transiciones, ArrayList<TransicionL> LTransiciones) {
        Object[] fila = new Object[3];
        vaciarTabla();
        for (int i = 0; i < Transiciones.size(); i++) {
            fila[0] = Transiciones.get(i).getEstadoOrigen();
            fila[1] = Transiciones.get(i).getSimbolo();
            fila[2] = Transiciones.get(i).getEstadosDestino();
            modeloTabla.addRow(fila);
            //vMensaje.Mensaje("info", "Añadido correctamente" + fila[0] + "" + fila[1] + "" + fila[2]);
        }
        //Añade tambien a la tabla las lambda transiciones
        for (int i = 0; i < LTransiciones.size(); i++) {
            fila[0] = LTransiciones.get(i).getEstadoOrigen();
            fila[1] = "lambda";
            fila[2] = LTransiciones.get(i).getEstadoDestino();
            modeloTabla.addRow(fila);
            //vMensaje.Mensaje("info", "Añadido correctamente" + fila[0] + "" + fila[1] + "" + fila[2]);
        }
    }

    public void vAutomataMouseClicked(MouseEvent evt) {
        String seleccionado = "0";
        if (vAutomata.Selectorinicial.getItemCount() != 0) {
            seleccionado = vAutomata.Selectorinicial.getSelectedItem().toString();
        }
        vAutomata.Selectorinicial.removeAllItems();
        vTransiciones.ComboBoxEstadoOrigen.removeAllItems();
        vTransiciones.ComboBoxEstadoDestino.removeAllItems();
        for (int i = 0; i < Integer.parseInt(vAutomata.Numerodeestados.getText()); i++) {
            vAutomata.Selectorinicial.addItem("" + i + "");
            vTransiciones.ComboBoxEstadoOrigen.addItem("" + i + "");
            vTransiciones.ComboBoxEstadoDestino.addItem("" + i + "");
            aut.agregarEstado(i);
        }

        if (Integer.parseInt(seleccionado) < Integer.parseInt(vAutomata.Numerodeestados.getText())) {
            vAutomata.Selectorinicial.setSelectedItem(seleccionado);
        }
        //Establece el modelo de la lista de checkbox
        if (lm == null) {
            lm = new DefaultListModel();
            for (Integer e1 : aut.getEstados()) {
                lm.addElement(e1);
            }

            vAutomata.listaEstados.setModel(lm);
        }

    }

    public void vaciarTabla() {
        while (modeloTabla.getRowCount() > 0) {
            modeloTabla.removeRow(0);
        }
    }
}
