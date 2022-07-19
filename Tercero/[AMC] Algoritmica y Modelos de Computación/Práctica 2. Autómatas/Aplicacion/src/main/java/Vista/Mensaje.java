
package Vista;

import javax.swing.JOptionPane;

/**
 *
 * @author Alberto Fernández Merchán & Juan Manuel Ruíz Pérez
 */
public class Mensaje {
    /**
     * Genera una ventana con un mensaje pasado por parámetro
     * @param tipo
     * <p>info</p> Genera un mensaje de información.
     * <p>error</p> Genera un mensaje de error.
     * @param mensaje 
     */
        public void Mensaje(String tipo, String mensaje) {
            
        switch (tipo) {
            case "info":
                JOptionPane.showMessageDialog(null, mensaje, null, JOptionPane.INFORMATION_MESSAGE);
                break;
            case "error":
                JOptionPane.showMessageDialog(null, mensaje, null, JOptionPane.ERROR_MESSAGE);
                break;
            default:

        }
    }
}
