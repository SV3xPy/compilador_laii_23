/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador.s;

import java.util.Stack;

/**
 *
 * @author Cristhian
 */
public class pilaBloques 
{
    Stack<String> pila = null;

    public pilaBloques()
    {
        pila = new Stack<String>();
    }

    public boolean estaVacia()
    {
        return pila.isEmpty();
    }

    int obtenerTamaño()
    {
        return pila.size();
    }
}
