/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador.s.evaluar;
import TablaSimbolos.tablaSimbolos;
/**
 *
 * @author Cristhian
 */
public class evaluar 
 {
    String expresion;
    tablaSimbolos tblSmb;
    
    public evaluar(String expresion, tablaSimbolos tblSmb)
    {
        this.expresion = expresion;
        this.tblSmb = tblSmb;
    }
    
    public int obtenerResultado()
    {
        expresion = obtenerVariables();
        infijo inf = new infijo(expresion);
        String expPostfija = inf.obtenerPostfijo();
        postfijo postf = new postfijo(expPostfija);
        int resultado = postf.obtenerResultado();
        return resultado;
    }
    
    String obtenerVariables()
    {
        String[] datos = expresion.split("");
        String cadena = "";
        String exp = "";
        for (int i = 0; i < datos.length; i++) 
        {
            String dato = datos[i];
            try 
            {
                int aux = Integer.parseInt(dato);
                exp += aux;
                if(!cadena.equals(""))
                {
                    System.out.println("cad1 "+cadena);
                    cadena="";
                }
            } 
            catch (Exception e) 
            {
                if(dato.equals("+")||dato.equals("-")||dato.equals("*")||
                dato.equals("/")||dato.equals("(")||dato.equals(")"))
                {
                    if(!cadena.equals(""))
                    {
                        exp += tblSmb.obtenerTokenSimbolo(cadena).obtenerValor();
                        cadena = "";
                    }
                    exp += dato;
                }
                else
                {
                    cadena += dato;
                }
            }
        }
        return exp;
    }
}
