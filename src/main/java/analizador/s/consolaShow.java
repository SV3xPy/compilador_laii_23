/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador.s;

/**
 *
 * @author Cristhian
 */
public class consolaShow 
{
    String contConsola = "";
    
    public String obtenerContConsola()
    {
        return contConsola;
    }
    
    public void agregarContConsola(String texto)
    {
        contConsola += "\n" + texto;
    }
    
    public void vaciar()
    {
        contConsola = "";
    }
}
