/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador.s;

/**
 *
 * @author COMPUTOCKS
 */
public class numero {
    String cadena;
    int position = 0;
    boolean acept = false;

    public numero(String cadena){
        this.cadena = cadena;
    }

    public boolean start(){
        if(cadena.length() > 0)
            q0(cadena.charAt(0));
        if(acept)
            return true;
        else
            return false;
    }

    void q0(char c){
        if(c >= 48 && c <= 57){
            position ++;
            if(cadena.length() > position)
                q1(cadena.charAt(position));
            else 
                acept = true;
        }
    }

    void q1(char c){
        if(c >=48 && c<=57){
            position ++;
            if(cadena.length() > position)
                q1(cadena.charAt(position));
            else 
                acept = true;
        }
    }
}
