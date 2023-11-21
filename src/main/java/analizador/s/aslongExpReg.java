/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador.s;
import PilaErrores.pilaErrores;
import PilaErrores.errores;
import TablaSimbolos.tablaSimbolos;

/**
 *
 * @author Cristhian
 */

/* 
    * aslong
    * (
    *   expBool
    * )
    * {
    *   Contenido
    * }
*/
public class aslongExpReg 
{
    tablaSimbolos tblSmb;
    booleanoExpReg errBool;
    pilaErrores plErr;
    String cadena;
    int linea;
    int inicioIndex;
    boolean estado = false;
    
    public aslongExpReg(tablaSimbolos tblSmb, String cadena, pilaErrores plErr, int linea)
    {
        this.tblSmb = tblSmb;
        this.cadena = cadena;
        this.plErr = plErr;
        this.linea = linea;
    }
    
    public boolean inicio()
    {
        eliminarEspacios();
        q0();
        return estado;
    }
    
    public boolean obtenerResultado()
    {
        return errBool.validarExpBool(cadena.substring(7, cadena.length() - 2));
    }
    
    void q0()
    {
        if(cadena.substring(0, 7).equals("aslong("))
        {
            q1(obtenerExpBool());
        }
        else
        {
            plErr.push(new errores(String.valueOf(linea + 1), "Error sintáctico: Revisar estructura aslong", "109"));
        }
    }
    
    void q1(String expBool)
    {
        if(expBool == null)
        {
            plErr.push(new errores(String.valueOf(linea + 1), "Error sintáctico: Falta paréntesis de cierre", "110"));
        }
        else
        {
            errBool = new booleanoExpReg(this.tblSmb, expBool, this.plErr, linea);
            estado = true;
        }
    }
    
    String obtenerExpBool()
    {
        for(inicioIndex = 7; cadena.length() > inicioIndex; inicioIndex++)
        {
            if(cadena.charAt(inicioIndex) == ')')
            {
                return cadena.substring(7, inicioIndex);
            }
        }            
        return null;
    }
    
    void eliminarEspacios()
    {
        String[] data = cadena.split(" ");
        cadena = "";
        for(String e : data)
        {
            if(!e.equals(""))
            {
                cadena += e;
            }
        }
    }
}
