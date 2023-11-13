/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TablaSimbolos;

import java.util.ArrayList;

/**
 *
 * @author crist
 */
public class tablaSimbolos 
{
    ArrayList <simbolos> simbolosLista; //Lista para almacenar objetos de la clase "simbolos"
    
    //Constructor
    public tablaSimbolos()
    {
        simbolosLista = new ArrayList <> ();
    }
    
    //Agrega un símbolo a la lista
    public void agregarSimbolo(simbolos p_simbolo)
    {
        simbolosLista.add(p_simbolo);
    }
    
    //Obtiene una posición y devuelve el objeto que se encuentre en esa posición
    public simbolos obtenerSimbolo(int posicion)
    {
        return simbolosLista.get(posicion);
    }
    
    //Devuelve tamaño de la lista
    public int tamanio()
    {
        return simbolosLista.size();
    }
    
    //Crea una nueva lista para "limpiar" la tabla
    public void limpiarTabla ()
    {
        simbolosLista = new ArrayList <> ();
    }
    
    //Busca en la lista una descripción igual a la proporcionada. Si es igual, devuelve ese objeto.
    public simbolos obtenerTokenSimbolo(String descripcion)
    {
        simbolos simb = null;
        for(int i = 0; i < simbolosLista.size(); i++)
        {
            if(simbolosLista.get(i).obtenerDesc().equals(descripcion))
            {
                simb = simbolosLista.get(i);
            }
        }
        return simb;
    }
}
