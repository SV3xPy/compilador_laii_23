/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador.s;
import PilaErrores.pilaErrores;
import PilaErrores.errores;
import TablaSimbolos.tablaSimbolos;
import TablaSimbolos.simbolos;
/**
 *
 * @author Cristhian
 */
public class showExpReg 
{
    tablaSimbolos tblSmb;
    pilaErrores plErr;
    consolaShow consola;
    booleanoExpReg errBoo;
    String[] datos;
    String[] contenido;
    String[] separado;
    String lineas;
    String cadena = "";
    String token = "";
    int numLinea;
    boolean estado = false;
    
    public showExpReg(tablaSimbolos tblSmb, String lineas, pilaErrores plErr, int numLinea, consolaShow consola)
    {
        this.tblSmb = tblSmb;
        this.lineas = lineas;
        this.plErr = plErr;
        this.numLinea = numLinea;
        this.consola = consola;
    }
    
    public void validarExpReg()
    {
        String ultimo = lineas.trim().substring(lineas.trim().length()-1);
        if(ultimo.equals(";")){
            try {
                q0();
            } catch (Exception e) {
                plErr.push(new errores(String.valueOf(numLinea),"Error sintáctico:  Falla en la impresión." , "107"));
            }
        }else{
            System.out.println("ERROR, FALTO AGREGAR EL DELIMITADOR");
            plErr.push(new errores(String.valueOf(numLinea),"Error sintáctico: Falta delimitador" , "108"));
        }
    }
    
    void q0()
    {
        cadena = "";
        datos = lineas.trim().split("show");
        for (int i = 1; i < datos.length; i++) 
        {
            contenido = datos[i].trim().split(";");
            q1();
        }
        System.out.println("Imprimir -> "+cadena);
        consola.agregarContConsola(cadena);
    }
    
    void q1()
    {
        for (int j = 0; j < contenido.length; j++) 
        {
            separado = contenido[j].trim().split("\\Q+\\E"); // Simbolo +
            q2();
        }
    }
    
    void q2()
    {
        for (int k = 0; k < separado.length; k++) 
        {
            token = separado[k];
            if(token.substring(0, 1).equals("\""))
            {
                q3();
            }
            else
            {
                q4();
            }
        }
    }
    
    void q3()
    {
        // Texto directo
        if(token.substring(token.length()-1).equals("\""))
        {
            cadena += token.substring(1, token.length()-1);
        }
    }
    
    void q4()
    {
        // Validar variable
        simbolos smb = tblSmb.obtenerTokenSimbolo(token);
        if(smb != null){
            // Asignacion del valor a la cadena
            if(smb.obtenerToken().equals("VARTEXTO")){
                String aux = smb.obtenerValor().trim().substring(1, smb.obtenerValor().length()-1);
                cadena += aux;
            }else{
                cadena += smb.obtenerValor();
            }
        }
    }
}
