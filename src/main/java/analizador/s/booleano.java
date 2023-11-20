/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador.s;

/**
 *
 * @author COMPUTOCKS
 */
public class booleano {
    
    String cadena;
    int position = 0;
    boolean acept = false;
  
    public booleano(String cadena){
        this.cadena = cadena;
    }
    
    public boolean inicio(){
        if(cadena.length() > 0)
            q0(cadena);
        if(acept)
            return true;
        else
            return false;
    }

    void q0(String valor){
        String v = valor.trim();
        if(v.equals("true") || v.equals("false"))
            acept = true; 
        
    }
}
