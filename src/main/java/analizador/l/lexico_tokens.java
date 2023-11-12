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

    public String getToken(String lexema){
        if(lexema.equals("if"))
            return "IF,1,Palabra reservada,"+lexema;
        if(lexema.equals("else"))
            return "ELSE,2,Palabra reservada,"+lexema;    
        if(lexema.equals("aslong"))
            return "ASLONG,3,Palabra reservada,"+lexema;    
        if(lexema.equals("show"))
            return "SHOW,4,Palabra reservada,"+lexema;    
        if(lexema.equals("int"))
            return "INT,11,Palabra reservada,"+lexema;    
        if(lexema.equals("point"))
            return "POINT,12,Palabra reservada,"+lexema;    
        if(lexema.equals("text"))
            return "TEXT,13,Palabra reservada,"+lexema;
        if(esOpArtimetico(lexema))
            return "OPARITMETICO,21,Operador aritmético,"+lexema;    
        if(esOpLogico(lexema))
            return "OPLOGICO,31,Operador lógico,"+lexema;       
        if(esOpComparacion(lexema))
            return "OPCOMPARACION,41,Operador de comparación,"+lexema;    
        if(lexema.equals("="))
            return "OPASIGNACION,51,Operador de asignación,"+lexema;    
        if(lexema.equals(";"))
            return "DELIMITADOR,60,Delimitador,"+lexema;
        if(lexema.equals("{"))
            return "BLOQUEAPERTURA,61,Bloque apertura,"+lexema;
        if(lexema.equals("}"))
            return "BLOQUECIERRE,62,Bloque cierre,"+lexema;
        if(lexema.equals("("))
            return "PARENTESISAPERTURA,63,Paréntesis apertura,"+lexema;
        if(lexema.equals(")"))
            return "PARENTESISCIERRE,64,Paréntesis cierre,"+lexema;
        if(variables.contains(lexema))
            return "VARIABLE,75,Variable,"+lexema;
        if(lexema.equals("@@"))
            return "COMENTARIO,80,Comentario de una línea,"+lexema;
        if(lexema.equals("#@"))
            return "COMENTARIOINICIO,81,Comentario de más de dos líneas inicio,"+lexema;
        if(lexema.equals("@#"))
            return "COMENTARIOFIN,82,Comentario de más de dos líneas fin,"+lexema;
        if(lexema.equals("show"))
            return "SHOW,100,Imprimir,"+lexema;
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
        if(AutCadenas.start())
            return "CADENA,70,Tipo de dato Cadena,"+lexema;
        AutNumeros = new numero(lexema);
        if(AutNumeros.start())
            return "NUMERO,71,Tipo de dato Numero,"+lexema;
        AutBooleana = new booleano(lexema);
        if(AutBooleana.start())
            return "TDBOOLEANO,72,Tipo de dato booleano,"+lexema;
        return null;
    }

    public String[] getListTokens(String linea){
        lex = new lexico_lexema();
        String[] lexemas = lex.getLexemas(linea);
        String[] tokens = new String[lexemas.length];
        for(int i=0;lexemas.length>i;i++){
            tokens[i] = getToken(lexemas[i]);
        }
        return tokens;
    }
}
