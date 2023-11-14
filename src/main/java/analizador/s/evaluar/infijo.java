/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador.s.evaluar;

import java.util.Stack;

/**
 *
 * @author Cristhian
 */
public class infijo 
{
    String expInfija;
    
    public infijo(String expInfija)
    {
        this.expInfija = expInfija;
    }
    
    public String obtenerPostfijo()
    {
        String resultado = "";
        //Depurar la expresión algebraica
        String expresion = depurar(expInfija);
        String[] arrayInfijo = expresion.split(" ");
        //Declaración de pilas 
        Stack<String> entrada = new Stack<String>(); // Pila entrada
	Stack<String> tempOp = new Stack<String>();  // Pila temporal para operadores
	Stack<String> salida = new Stack<String>();  //Pila salida
        //Añadir el array a la pila de entrada
        for(int i = arrayInfijo.length - 1; i >= 0; i--)
        {
            entrada.push(arrayInfijo[i]);
        }
        try
        {
            while(!entrada.isEmpty())
            {
                switch(preferencia(entrada.peek()))
                {
                    case 1:
                        tempOp.push(entrada.pop());
                        break;
                    case 3:
                    case 4:
                        while(preferencia(tempOp.peek()) >= preferencia(entrada.peek()))
                        {
                            salida.push(tempOp.pop());
                        }
                        tempOp.push(entrada.pop());
                        break;
                    case 2:
                        while(!tempOp.peek().equals("("))
                        {
                            salida.push(tempOp.pop());
                        }
                        tempOp.pop();
                        entrada.pop();
                        break;
                    default:
                        salida.push(entrada.pop());
                }
            }
            String infijo = salida.toString().replaceAll("[\\]\\[,]", "");
            resultado = infijo;
        }
        catch (Exception ex)
        {
            System.out.println("Error en la expresión algebraica");
            System.err.println("");
        }
        return resultado;
    }
    
    //Depurar expresión algebraica 
    public String depurar(String s)
    {
        s = s.replaceAll("\\s", "");
        s = "(" + s + ")";
        String simbolos = "+-*/()";
        String str = "";
        //Coloca espacioes entre operadores
        for (int i = 0; i < s.length(); i++)
        {
            if (simbolos.contains("" + s.charAt(i))) {
                str += " " + s.charAt(i) + " ";
            } else {
                str += s.charAt(i);
            }
        }
        return str.replaceAll("\\s+", " ").trim();
    }
    
    //Jerarquía de operadores
    public int preferencia(String op)
    {
        int pref = 99;
        if (op.equals("^")) {
            pref = 5;
        }
        if (op.equals("*") || op.equals("/")) {
            pref = 4;
        }
        if (op.equals("+") || op.equals("-")) {
            pref = 3;
        }
        if (op.equals(")")) {
            pref = 2;
        }
        if (op.equals("(")) {
            pref = 1;
        }
        return pref;
    }
}
