/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador.l;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    public Map<Character, Integer> caracteresConErrores(String linea){
        Map<Character, Integer> erroresCaracteres = new HashMap<>();
        int tamanio = linea.length();
        char c;
        for(int i = 0; i < tamanio; i++){
            c = linea.charAt(i); 
            if(!((c >= 10 && c <= 125) || c == 168 || c == 179)) {
                int codigoError = obtenerCodigoError(c);
                erroresCaracteres.put(c, codigoError);
            }
        }
        return erroresCaracteres;
    }

    private int obtenerCodigoError(char caracter) {
        // Asignación de códigos basados en valores ASCII
        int codigo = (int) caracter;
        return codigo % 100; // Ajustar al rango de 0 a 100
    }
}
