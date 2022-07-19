package Vista;

import Modelo.Algoritmos;
import Modelo.Punto;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;

/**
 *
 * @author Alberto Fernández
 */
public class CanvasPuntos extends Canvas {

    private Algoritmos a;
    private ArrayList<Punto> T;
    private final Dimension dimensionPantalla = Toolkit.getDefaultToolkit().getScreenSize();
    Punto[] Puntos;
    int[] indice;
    private final int ANCHO = 2;
    private final int ALTO = 2;
    private double FACTORX = 10;
    private double FACTORY = 10;

    public CanvasPuntos(Algoritmos a) {
        this.Puntos = new Punto[]{new Punto(), new Punto(), new Punto()};
        this.a = a;
        T = a.getPuntos();

    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);
        Graphics2D draw = (Graphics2D) g;
        draw.setColor(Color.black);
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        FACTORX = (a.getMayorX().getx() / (dimensionPantalla.width - 10));
        FACTORY = (a.getMayorY().gety() / (getHeight() - 10));
        for (int i = 0; i < T.size(); i++) {

            draw.fillOval((int) (T.get(i).getx() / FACTORX), (int) (T.get(i).gety() / FACTORY), ANCHO, ALTO);

        }

        if (a.getEncontrado()) {
            //Dibuja las lineas que unen los puntos...
            T = a.getPuntos(); //Obtenemos el vector ordenado

            indice = a.getIndice(); //Obtenemos el índice de los 3 puntos ya ordenados
            draw.setColor(Color.red);

            draw.drawLine((int) (T.get(indice[0] - 1).getx() / FACTORX), (int) (T.get(indice[0] - 1).gety() / FACTORY), (int) (T.get(indice[1] - 1).getx() / FACTORX), (int) (T.get(indice[1] - 1).gety() / FACTORY));
            draw.drawLine((int) (T.get(indice[0] - 1).getx() / FACTORX), (int) (T.get(indice[0] - 1).gety() / FACTORY), (int) (T.get(indice[2] - 1).getx() / FACTORX), (int) (T.get(indice[2] - 1).gety() / FACTORY));

        }

    }
}
