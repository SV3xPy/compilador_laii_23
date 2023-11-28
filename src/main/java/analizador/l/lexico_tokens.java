/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador.l;

import java.util.ArrayList;

import analizador.s.booleano;
import analizador.s.cadena;
import analizador.s.numero;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author COMPUTOCKS
 */
public class lexico_tokens {

    public ArrayList<String> variables = new ArrayList<>();
    private Set<String> variablesEncontradas = new HashSet<>();
    cadena AutCadenas;
    numero AutNumeros;
    booleano AutBooleana;
    lexico_lexema lex;
    private int nextId = 1; // Inicializar el contador de ID

    public String getToken(String lexema) {
        String id = Integer.toString(nextId++); // Generar un ID único

        if (lexema.equals("if")) {
            return id + ",IF,1,Palabra reservada," + lexema;
        }
        if (lexema.equals("else")) {
            return id + ",ELSE,2,Palabra reservada," + lexema;
        }
        if (lexema.equals("aslong")) {
            return id + ",ASLONG,3,Palabra reservada," + lexema;
        }
        if (lexema.equals("show")) {
            return id + ",SHOW,4,Palabra reservada," + lexema;
        }
        if (lexema.equals("int")) {
            return id + ",INT,11,Palabra reservada," + lexema;
        }
        if (lexema.equals("point")) {
            return id + ",POINT,12,Palabra reservada," + lexema;
        }
        if (lexema.equals("booleano")) {
            return id + ",BOOLEANO,13,Palabra reservada," + lexema;
        }
        if (lexema.equals("text")) {
            return id + ",TEXT,14,Palabra reservada," + lexema;
        }
        if (lexema.equals("principal")) {
            return id + ",PRINCIPAL,20,Palabra reservada," + lexema;
        }
        if (esOpAritmetico(lexema)) {
            return id + ",OP ARITMETICO,21,Operador aritmético," + lexema;
        }
        if (esOpLogico(lexema)) {
            return id + ",OPLOGICO,31,Operador lógico," + lexema;
        }
        if (esOpComparacion(lexema)) {
            return id + ",OPCOMPARACION,41,Operador de comparación," + lexema;
        }
        if (lexema.equals("=")) {
            return id + ",OPASIGNACION,51,Operador de asignación," + lexema;
        }
        if (lexema.equals(";")) {
            return id + ",DELIMITADOR,60,Delimitador," + lexema;
        }
        if (lexema.equals("{")) {
            return id + ",BLOQUEAPERTURA,61,Bloque apertura," + lexema;
        }
        if (lexema.equals("}")) {
            return id + ",BLOQUECIERRE,62,Bloque cierre," + lexema;
        }
        if (lexema.equals("(")) {
            return id + ",PARENTESISAPERTURA,63,Paréntesis apertura," + lexema;
        }
        if (lexema.equals(")")) {
            return id + ",PARENTESISCIERRE,64,Paréntesis cierre," + lexema;
        }
        if (variables.contains(lexema)) {
            return id + ",VARIABLE,75,Variable," + lexema;
        }
        if (lexema.equals("@@")) {
            return id + ",COMENTARIO,80,Comentario de una línea," + lexema;
        }
        if (lexema.equals("@#")) {
            return id + ",COMENTARIOINICIO,81,Comentario de más de una línea inicio," + lexema;
        }
        if (lexema.equals("#@")) {
            return id + ",COMENTARIOFIN,82,Comentario de más de una línea fin," + lexema;
        }
        if (lexema.equals("show")) {
            return id + ",SHOW,100,Imprimir," + lexema;
        }
        if (esOpLogico(lexema)) {
            return id + "OPLOGICO,31,Operador lógico," + lexema;
        }
        if (esOpComparacion(lexema)) {
            return id + "OPCOMPARACION,41,Operador de comparación," + lexema;
        }

        // Verificar si el lexema es una variable y si no ha sido añadida previamente
        if (esVariable(lexema) && !variablesEncontradas.contains(lexema)) {
            variablesEncontradas.add(lexema);
            return id + ",VARIABLE,75,Variable," + lexema;
        }

        String buscToken = buscarToken(lexema);
        if (buscToken != null) {
            return buscToken;
        }
        return "TOKEN NO ENCONTRADO,-1,Token no especificado," + lexema;
    }

    public boolean esOpAritmetico(String lexema) {
        // Definir un conjunto que contenga los operadores aritméticos
        Set<String> operadoresAritmeticos = new HashSet<>();
        operadoresAritmeticos.add("+");
        operadoresAritmeticos.add("*");
        operadoresAritmeticos.add("/");
        operadoresAritmeticos.add("-");
        operadoresAritmeticos.add("%");

        return operadoresAritmeticos.contains(lexema);
    }

    boolean esOpLogico(String lexema) {
        if (lexema.equals("&") || lexema.equals("|") || lexema.equals("¬")) {
            return true;
        }
        return false;
    }

    boolean esOpComparacion(String lexema) {
        if (lexema.equals("==") || lexema.equals("!=") || lexema.equals("<")
                || lexema.equals(">") || lexema.equals(">=") || lexema.equals("<=")) {
            return true;
        }
        return false;
    }

    String buscarToken(String lexema) {
        AutCadenas = new cadena(lexema);
        if (AutCadenas.inicio()) {
            return "CADENA,70,Tipo de dato Cadena," + lexema;
        }
        AutNumeros = new numero(lexema);
        if (AutNumeros.inicio()) {
            return "NUMERO,71,Tipo de dato Numero," + lexema;
        }
        AutBooleana = new booleano(lexema);
        if (AutBooleana.inicio()) {
            return "TDBOOLEANO,72,Tipo de dato booleano," + lexema;
        }
        return null;
    }

    public String[] getListTokens(String linea, String aux) {
        lex = new lexico_lexema();
        String[] lexemas = lex.getLexemas(linea);
        ArrayList<String> tokensList = new ArrayList<>();

        boolean ignoreSingleLineComment = false;
        boolean ignoreMultilineComment = false;

        for (String lexema : lexemas) {
            if (ignoreSingleLineComment || ignoreMultilineComment) {
                // Ignorar comentario de una sola línea o varias líneas
                if (lexema.equals("#@")) {
                    ignoreMultilineComment = false;
                    continue;// Fin del comentario de una línea
                }
                continue; // Saltar procesamiento de tokens
            }

            if (lexema.equals("@@")) {
                ignoreSingleLineComment = true;
                continue;
            }
            /*if (aux.contains(lexema) && (aux.contains("@#") || aux.contains("#@"))) {
                ignoreMultilineComment = true;
                continue;
            }
            /*if (lexema.equals("@#") && aux.contains("*")) {
                    ignoreMultilineComment = true;
                    continue;
                } */

            if (!ignoreMultilineComment) {
                tokensList.add(getToken(lexema));
            }
        }
        String[] tokens = tokensList.toArray(new String[0]);
        return tokens;
    }

// Método para verificar si el lexema es una variable
    private boolean esVariable(String lexema) {
        String regexVariable = "[a-zA-Z]+[0-9]*_?[0-9]*";
        return lexema.matches(regexVariable);
    }
}
