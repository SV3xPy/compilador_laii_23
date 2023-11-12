/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador.l;

/**
 *
 * @author COMPUTOCKS
 */
public class lexico_alfabeto {
    public boolean validar(String linea){
        int tamanio = linea.length();
        char c;
        for(int i=0; tamanio > i; i++){
            c = linea.charAt(i); 
            if(!((c >= 10 && c <=125 ) || c == 168 || c==179))
                return false;
        }
        return true;
    }
}
