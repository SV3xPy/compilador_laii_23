/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador.l;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author COMPUTOCKS
 */
public class lexico_lexema {
    String[] getLexemas(String linea){
    List<String> listLexemas = new ArrayList<String>();
    String[] auxiliar = linea.split(" ");
    for(String a: auxiliar)
      if(!a.equals(""))
        listLexemas.add(a);
    return listLexemas.toArray(new String[0]);
   }
}

