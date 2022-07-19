
package Modelo;

import static java.lang.Math.*;
import java.text.DecimalFormat;
/**
 *
 * @author Alberto Fernández
 */
public class Punto implements Cloneable {
    private double x;
    private double y;
    
    
    public Punto(double x, double y)
    {
     this.x = x;
     this.y = y;
    }
    
 public Punto clone(){
 
     return this;
 }
    
    public Punto()
    {
    this.x = 0;
    this.y = 0;
    }
    
    public double getx()
    {
    return this.x;
    }
    
    public double gety()
    {
    return this.y;
    }
    public void setx(double x)
    {
    this.x = x;
    }
    
    public void sety(double y)
    {
    this.y=y;
    }
    

    public static double distancia(Punto p, Punto q)
    {
        double distancia;
        distancia = sqrt(abs((pow(p.getx()-q.getx(),2))+(pow(p.gety()-q.gety(),2))));

        return distancia;
    }
    
    @Override
    public String toString(){
         DecimalFormat df = new DecimalFormat("####0.00");       
        String texto = "\t X:" + df.format(this.getx()) + "\t Y: " + df.format(this.gety());
        return texto;
        
    }
    
}
