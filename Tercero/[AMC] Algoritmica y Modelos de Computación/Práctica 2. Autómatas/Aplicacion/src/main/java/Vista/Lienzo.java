package Vista;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alberto Fernández Merchán & Juan Manuel Ruíz Pérez
 */
public class Lienzo extends Canvas {

    private VentanaPrincipal vp;

    public Lienzo(VentanaPrincipal vp) {
        this.vp = vp;
        setBackground(java.awt.Color.decode("#99bdbd"));
    }

    /**
     * Dibuja en el canvas la imagen del grafo del fichero .dot
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        if (!vp.TextoArea.getText().isEmpty()) {
            g.drawImage(drawAutomata(), (int) 0, 0, null);
        }
    }

    /**
     * Dibuja el grafo que describe el fichero .dot en la carpeta de resources.
     *
     * @return
     */
    private Image drawAutomata() {
        Image imgn = null;

        try (InputStream dot = getClass().getResourceAsStream("/automata.dot")) { //Tiene que existir la carpeta resources...
            MutableGraph gr = new Parser().read(dot);
            gr.graphAttrs()
                    .add(Color.rgb("99bdbd").background());

            imgn = Graphviz.fromGraph(gr).width(getWidth()).render(Format.PNG).toImage(); //Guardar en memoria una imagen

        } catch (IOException ex) {
            Logger.getLogger(Lienzo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imgn;
    }

}
