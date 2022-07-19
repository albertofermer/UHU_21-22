package Vista;

import Controlador.Controlador;
import Modelo.Algoritmos;
import Modelo.Punto;
import static Modelo.Punto.calcularPeso;
import static Modelo.Punto.distancia;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

/**
 *
 * @author Alberto Fernández
 */
public class CanvasPuntos extends Canvas {

    Controlador c;
    private Algoritmos a;
    private ArrayList<Punto> T;
    Punto[] Puntos;
    Integer[][] LongitudAristas;
    ArrayList<Integer> indice;
    Punto[] Predecesor;
    private final int ANCHO = 5;
    private final int ALTO = 5;
    private double FACTORX = 10;
    private double FACTORY = 10;
    Punto v, w;
    boolean encontrado;
    private final Dimension dimensionPantalla = Toolkit.getDefaultToolkit().getScreenSize();
    private int puntoOrigen;

    public CanvasPuntos(Algoritmos a) {

        this.a = a;
        T = a.getPuntos();

    }

    public void actualiza(Punto v, Punto w) {
        this.v = v;
        this.w = w;
        encontrado = true;
        repaint();
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        Image img = createImage(this.getWidth(), this.getHeight());
        Graphics og = img.getGraphics();
        if (a.isDark()) {
            super.paint(g);
            setBackground(Color.black);
            og.setColor(Color.white);
            if (a.getNumPuntos() > 0) {
                FACTORX = (a.getMayorX().getx() / (dimensionPantalla.width - 10));
                FACTORY = (a.getMayorY().gety() / (getHeight() - 10));
            }
            for (int i = 0; i < T.size(); i++) {
                og.drawString(Integer.toString(T.get(i).getId()), (int) (T.get(i).getx() / FACTORX), (int) (T.get(i).gety() / FACTORY));
                og.fillOval((int) (T.get(i).getx() / FACTORX), (int) (T.get(i).gety() / FACTORY), ANCHO, ALTO);
            }
            for (int i = 0; i < T.size(); i++) {
                for (int j = 0; j < T.size(); j++) {
                    if (i != j && !a.getEncontrado()) {
                        int puntoMedioX = (int) (((T.get(i).getx() / FACTORX) + (T.get(j).getx() / FACTORX)) / 2);
                        int puntoMedioY = (int) (((T.get(i).gety() / FACTORY) + (T.get(j).gety() / FACTORY))) / 2;
                        og.setColor(Color.red.brighter());
                        og.drawString(Integer.toString(calcularPeso(T.get(i),T.get(j))), puntoMedioX, puntoMedioY);
                        og.setColor(Color.gray.brighter());
                        og.drawLine((int) (T.get(i).getx() / FACTORX), (int) (T.get(i).gety() / FACTORY), (int) (T.get(j).getx() / FACTORX), (int) (T.get(j).gety() / FACTORY));
                    }
                }
            }

            if (a.getEncontrado()) {
                puntoOrigen = a.getPuntoOrigen();
                for (int i = 0; i < Predecesor.length; i++) {
                    if (i != puntoOrigen) {
                        og.setColor(Color.red);
                        int puntoMedioX = (int) (((Predecesor[i].getx() / FACTORX) + (T.get(i).getx() / FACTORX)) / 2);
                        int puntoMedioY = (int) (((Predecesor[i].gety() / FACTORY) + (T.get(i).gety() / FACTORY))) / 2;
                        og.drawLine((int) (T.get(Predecesor[i].getId()).getx() / FACTORX), (int) (T.get(Predecesor[i].getId()).gety() / FACTORY), (int) (T.get(i).getx() / FACTORX), (int) (T.get(i).gety() / FACTORY));
                        og.setColor(Color.red.darker());
                        og.drawString(Integer.toString(calcularPeso(Predecesor[i], T.get(i))), puntoMedioX, puntoMedioY);
                    }
                }
            }

        } else {
            setBackground(Color.white);
            super.paint(g);
            og.setColor(Color.black);
            if (a.getNumPuntos() > 0) {
                FACTORX = (a.getMayorX().getx() / (dimensionPantalla.width - 10));
                FACTORY = (a.getMayorY().gety() / (getHeight() - 10));
            }

            for (int i = 0; i < T.size(); i++) {
                og.drawString(Integer.toString(T.get(i).getId()), (int) (T.get(i).getx() / FACTORX), (int) (T.get(i).gety() / FACTORY));
                og.fillOval((int) (T.get(i).getx() / FACTORX), (int) (T.get(i).gety() / FACTORY), ANCHO, ALTO);

            }

            for (int i = 0; i < T.size(); i++) {
                for (int j = 0; j < T.size(); j++) {
                    if (i != j && !a.getEncontrado()) {
                        int puntoMedioX = (int) (((T.get(i).getx() / FACTORX) + (T.get(j).getx() / FACTORX)) / 2);
                        int puntoMedioY = (int) (((T.get(i).gety() / FACTORY) + (T.get(j).gety() / FACTORY))) / 2;
                        og.setColor(Color.red.darker());
                        og.drawString(Integer.toString(calcularPeso(T.get(i),T.get(j))), puntoMedioX, puntoMedioY);
                        og.setColor(Color.gray.brighter());
                        og.drawLine((int) (T.get(i).getx() / FACTORX), (int) (T.get(i).gety() / FACTORY), (int) (T.get(j).getx() / FACTORX), (int) (T.get(j).gety() / FACTORY));
                    }
                }
            }

            if (a.getEncontrado()) {
                //a.setEncontrado(false);
                puntoOrigen = a.getPuntoOrigen();
                for (int i = 0; i < Predecesor.length; i++) {
                    if (i != puntoOrigen) {
                        og.setColor(Color.red);
                        int puntoMedioX = (int) (((Predecesor[i].getx() / FACTORX) + (T.get(i).getx() / FACTORX)) / 2);
                        int puntoMedioY = (int) (((Predecesor[i].gety() / FACTORY) + (T.get(i).gety() / FACTORY))) / 2;
                        og.drawLine((int) (T.get(Predecesor[i].getId()).getx() / FACTORX), (int) (T.get(Predecesor[i].getId()).gety() / FACTORY), (int) (T.get(i).getx() / FACTORX), (int) (T.get(i).gety() / FACTORY));
                        og.setColor(Color.red.darker());
                        og.drawString(Integer.toString(calcularPeso(Predecesor[i], T.get(i))), puntoMedioX, puntoMedioY);
                    }
                }
            }
        }

        g.drawImage(img, 0, 0, null);

    }

    public void actualiza(Punto[] Predecesor) {
        this.Predecesor = Predecesor;
    }
}
