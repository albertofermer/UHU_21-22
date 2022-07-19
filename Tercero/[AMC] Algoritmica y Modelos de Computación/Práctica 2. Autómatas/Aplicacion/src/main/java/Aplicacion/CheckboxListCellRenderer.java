package Aplicacion;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Alberto Fernández
 */
public class CheckboxListCellRenderer extends JCheckBox implements ListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, 
            boolean isSelected, boolean cellHasFocus) {

        setComponentOrientation(list.getComponentOrientation());
        setFont(list.getFont());
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        setSelected(isSelected);
        setEnabled(list.isEnabled());

        setText(value == null ? "" : value.toString());  

        return this;
    }
}
