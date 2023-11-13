/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TablaSimbolos;

/**
 *
 * @author Cristhian
 */
public class simbolos {
    public String tipoSimbolo; //Tipo del símbolo
    public String id;          //ID del símbolo
    public String desc;        //Descripción del símbolo
    public String valor;       //Valor del símbolo
    public String nombre;      //Nombre del símbolo
    public String tipo;        //Tipo de dato del símbolo
    public String tamanio;     //Tamaño del símbolo
    public String dir;         //Dirección del símbolo
    
    //Constructor
    public simbolos (String[] simbolosArray)
    {
        tipoSimbolo = simbolosArray[0];
        id = simbolosArray[1];
        desc = simbolosArray[2];
        valor = simbolosArray[3];
    }
    
    //Obtiene el tipo del símbolo
    public String obtenerToken()
    {
        return this.tipoSimbolo;
    }
    
    //Obtiene identificador del símbolo
    public String obtenerID()
    {
        return this.id;
    }
    
    //Obtiene una descripción del símbolo
    public String obtenerDesc()
    {
        return this.desc;
    }
    
    //Obtiene el valor del símbolo
    public String obtenerValor()
    {
        return this.valor;
    }
}
