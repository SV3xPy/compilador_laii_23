/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PilaErrores;

/**
 *
 * @author Cristhian
 */
public class errores {
    String lineaError;   //Almacena la línea donde ocurrió el error
    String descError;    //Almacena la descripción del error
    String codigoError;  //Almacena el código del error
    
    //Constructor
    public errores(String lineaError, String descError, String codigoError)
    {
        this.lineaError = lineaError;
        this.descError = descError;
        this.codigoError = codigoError;
    }
    
    //Devuelve línea del error
    public String obtenerLineaErr()
    {
        return this.lineaError;
    }
    
    //Devuelve descripción del error
    public String obtenerDescErr()
    {
        return this.descError;
    }
    
    //Devuelve código del error
    public String obtenerCodigoErr()
    {
        return this.codigoError;
    }
    //Devuelve línea del error
    public String getLineaError()
    {
        return this.lineaError;
    }
    
    //Devuelve descripción del error
    public String getDescError()
    {
        return this.descError;
    }
    
    //Devuelve código del error
    public String getCodigoError()
    {
        return this.codigoError;
    }
}
