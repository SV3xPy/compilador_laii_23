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
public class ifelseExpReg 
{
    tablaSimbolos tblSmb;
    pilaErrores plErr;
    booleanoExpReg boolER;
    String cadena;
    boolean estado = false;
    int linea;
    int inicioIndex;
    
    public ifelseExpReg (tablaSimbolos tblSmb, String cadena, pilaErrores plErr, int linea)
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
    
    public boolean obtenerResu()
    {
        return boolER.validarExpBool(cadena.substring(3, cadena.length()-2));
    }
    
    void q0()
    {
        if(cadena.substring(0, 3).equals("if("))
        {
            //System.out.println("q1");
            q1(obtenerExpBool());
        }
        else
        {
            plErr.push(new errores(String.valueOf(linea + 1),"Error sintáctico: Fallo en la estructura if" , "111"));
        }
    }
    
    void q1(String expBool)
    {
        if(expBool== null)
        {
            plErr.push(new errores(String.valueOf(linea + 1),"Error sintáctico: Falta el parentesis de cierre" , "112"));
        }else{
            boolER = new booleanoExpReg(this.tblSmb, expBool, this.plErr, linea);
            estado = true;
        }
    }
    
    String obtenerExpBool()
    {
        for(inicioIndex = 3; cadena.length() > inicioIndex; inicioIndex++)
        {
            if(cadena.charAt(inicioIndex)==')')
            {
                //System.out.println("c: " +cadena.substring(3, beginIndex));
                return cadena.substring(3, inicioIndex);
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
