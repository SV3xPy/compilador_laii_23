/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PilaErrores;
import java.util.Stack;

/**
 *
 * @author Cristhian
 */
public class pilaErrores {
    Stack <errores> erroresPila; //Pila que almacena los errores
    
    //Constructor
    public pilaErrores()
    {
        erroresPila = new Stack <errores> ();
    }
    
    //Inserta un error a la pila
    public void push(errores error)
    {
        erroresPila.push(error);
    }
    
    //Elimina un error de la pila y devuelve su valor
    public Object pop()
    {
        return erroresPila.pop();
    }
    
    //Verifica si la pila está vacía
    public boolean estaVacia()
    {
        return erroresPila.isEmpty();
    }
}
