/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import javax.swing.JOptionPane;

/**
 *
 * @author Alberto Fernández
 */
public class Mensaje {
        public void Message(String type, String text) {
            
        switch (type) {
            case "info":
                JOptionPane.showMessageDialog(null, text, null, JOptionPane.INFORMATION_MESSAGE);
                break;
            case "error":
                JOptionPane.showMessageDialog(null, text, null, JOptionPane.ERROR_MESSAGE);
                break;
            case "creditos":
                JOptionPane.showMessageDialog(null, text, null, JOptionPane.INFORMATION_MESSAGE);
                break;
            default:

        }
    }
}
