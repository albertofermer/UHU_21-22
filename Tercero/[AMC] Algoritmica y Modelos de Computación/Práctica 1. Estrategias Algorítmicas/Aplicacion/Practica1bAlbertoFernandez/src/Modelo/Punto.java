package Modelo;

import static java.lang.Math.*;

/**
 *
 * @author Alberto Fernández
 */
public class Punto implements Cloneable {

    int id;
    private double x;
    private double y;

    public Punto(double x, double y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    @Override
    public Punto clone() {

        return this;
    }

    public Punto(int id) {
        this.x = 0;
        this.y = 0;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getx() {
        return this.x;
    }

    public double gety() {
        return this.y;
    }

    public void setx(double x) {
        this.x = x;
    }

    public void sety(double y) {
        this.y = y;
    }

    public static double distancia(Punto p, Punto q) {
        double distancia;
        distancia = sqrt((pow(p.getx() - q.getx(), 2)) + (pow(p.gety() - q.gety(), 2)));

        return distancia;
    }
    
    public static int calcularPeso(Punto i, Punto j){
        return ((((int) (distancia(i, j) * 100)) % 100) + 1);
    }

    @Override
    public String toString() {
        String texto = Integer.toString(id);
        //String texto = "\t X:" + df.format(this.getx()) + "\t Y: " + df.format(this.gety());
        return texto;

    }

    public boolean esMayorX(Punto p) {
        return (this.x > p.getx());
    }

    public boolean esMayorY(Punto p) {
        return (this.y > p.gety());
    }
}
