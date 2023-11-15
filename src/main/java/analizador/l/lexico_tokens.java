/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador.l;
import java.util.ArrayList;

import analizador.s.booleano;
import analizador.s.cadena;
import analizador.s.numero;
/**
 *
 * @author COMPUTOCKS
 */
public class lexico_tokens {
    public ArrayList<String> variables = new ArrayList<>();
    cadena AutCadenas;
    numero AutNumeros;
    booleano AutBooleana;
    lexico_lexema lex;
    private int nextId = 1; // Inicializar el contador de ID

    public String getToken(String lexema){
        String id = Integer.toString(nextId++); // Generar un ID único

        if(lexema.equals("if"))
            return id + ",IF,1,Palabra reservada,"+lexema;
        if(lexema.equals("else"))
            return id + ",ELSE,2,Palabra reservada,"+lexema;    
        if(lexema.equals("aslong"))
            return id + ",ASLONG,3,Palabra reservada,"+lexema;    
        if(lexema.equals("show"))
            return id + ",SHOW,4,Palabra reservada,"+lexema;    
        if(lexema.equals("int"))
            return id + ",INT,11,Palabra reservada,"+lexema;    
        if(lexema.equals("point"))
            return id + ",POINT,12,Palabra reservada,"+lexema;   
        if(lexema.equals("booleano"))
            return id + ",BOOLEANO,22,Palabra reservada,"+lexema;    
        if(lexema.equals("text"))
            return id + ",TEXT,13,Palabra reservada,"+lexema;
        if(esOpArtimetico(lexema))
            return id + ",OPARITMETICO,21,Operador aritmético,"+lexema;    
        if(esOpLogico(lexema))
            return id + ",OPLOGICO,31,Operador lógico,"+lexema;       
        if(esOpComparacion(lexema))
            return id + ",OPCOMPARACION,41,Operador de comparación,"+lexema;    
        if(lexema.equals("="))
            return id + ",OPASIGNACION,51,Operador de asignación,"+lexema;    
        if(lexema.equals(";"))
            return id + ",DELIMITADOR,60,Delimitador,"+lexema;
        if(lexema.equals("{"))
            return id + ",BLOQUEAPERTURA,61,Bloque apertura,"+lexema;
        if(lexema.equals("}"))
            return id + ",BLOQUECIERRE,62,Bloque cierre,"+lexema;
        if(lexema.equals("("))
            return id + ",PARENTESISAPERTURA,63,Paréntesis apertura,"+lexema;
        if(lexema.equals(")"))
            return id + ",PARENTESISCIERRE,64,Paréntesis cierre,"+lexema;
        if(variables.contains(lexema))
            return id + ",VARIABLE,75,Variable,"+lexema;
        if(lexema.equals("@@"))
            return id + ",COMENTARIO,80,Comentario de una línea,"+lexema;
        if(lexema.equals("#@"))
            return id + ",COMENTARIOINICIO,81,Comentario de más de dos líneas inicio,"+lexema;
        if(lexema.equals("@#"))
            return id + ",COMENTARIOFIN,82,Comentario de más de dos líneas fin,"+lexema;
        if(lexema.equals("show"))
            return id + ",SHOW,100,Imprimir,"+lexema;
        String buscToken = buscarToken(lexema);
        if(buscToken!=null)
            return buscToken;
        return "TOKEN NO ENCONTRADO,-1,Token no especificado,"+lexema;
    }
    
    boolean esOpArtimetico(String lexema){
        if(lexema.equals("+") || lexema.equals("*") || lexema.equals("/") || lexema.equals("-") || lexema.equals("%"))
            return true;
        return false;
    }

    boolean esOpLogico(String lexema){
        if(lexema.equals("&") || lexema.equals("|") || lexema.equals("¬"))
            return true;
        return false;
    }

    boolean esOpComparacion(String lexema){
        if(lexema.equals("==") || lexema.equals("!=") || lexema.equals("<") 
        || lexema.equals(">") || lexema.equals(">=") || lexema.equals("<="))
            return true;
        return false;
    }

    String buscarToken(String lexema){
        AutCadenas = new cadena(lexema);
        if(AutCadenas.inicio())
            return "CADENA,70,Tipo de dato Cadena,"+lexema;
        AutNumeros = new numero(lexema);
        if(AutNumeros.inicio())
            return "NUMERO,71,Tipo de dato Numero,"+lexema;
        AutBooleana = new booleano(lexema);
        if(AutBooleana.inicio())
            return "TDBOOLEANO,72,Tipo de dato booleano,"+lexema;
        return null;
    }

    public String[] getListTokens(String linea){
    lex = new lexico_lexema();
    String[] lexemas = lex.getLexemas(linea);
    String[] tokens = new String[lexemas.length];
    for(int i=0;i<lexemas.length;i++){
        System.out.println("Lexema: '" + lexemas[i] + "'");
        tokens[i] = getToken(lexemas[i]);
    }
    return tokens;
}

}
