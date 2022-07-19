package Aplicación;

import Controlador.Controlador;
import Vista.InterfazGrafica;

/**
 *
 * @author Alberto Fernández
 */
public class Principal {
    public static void main(String[] args) {
        
            java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Controlador empezar = new Controlador();
            }
        });
        
    }
}
