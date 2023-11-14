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
public class postfijo 
{

    String expPostfija;

    public postfijo(String expPostfija) 
    {
        this.expPostfija = expPostfija;
    }

    public int obtenerResultado() 
    {
        String[] arrayPostfijo = expPostfija.split(" ");
        Stack<String> entrada = new Stack<String>(); // Pila entrada
        Stack<String> tempOp = new Stack<String>();  //Pila de operandos
        for (int i = arrayPostfijo.length - 1; i >= 0; i--) 
        {
            entrada.push(arrayPostfijo[i]);
        }
        // Algoritmo de Evaluación Postfija
        String operadores = "+-*/%";
        while (!entrada.isEmpty()) 
        {
            if (operadores.contains("" + entrada.peek())) 
            {
                tempOp.push(evaluar(tempOp.pop(), tempOp.pop(), tempOp.pop()) + "");
            } 
            else 
            {
                tempOp.push(entrada.pop());
            }
        }
        // Mostrar resultados:
        int res = Integer.parseInt(tempOp.peek());
        return res;
    }

    public int evaluar(String op, String n2, String n1) 
    {
        int num1 = Integer.parseInt(n1);
        int num2 = Integer.parseInt(n2);
        if (op.equals("+")) 
        {
            return (num1 + num2);
        }
        if (op.equals("-")) 
        {
            return (num1 - num2);
        }
        if (op.equals("*")) 
        {
            return (num1 * num2);
        }
        if (op.equals("/")) 
        {
            return (num1 / num2);
        }
        if (op.equals("%")) 
        {
            return (num1 % num2);
        }
        return 0;
    }
}
