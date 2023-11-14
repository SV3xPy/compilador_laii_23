/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador.s;
import PilaErrores.errores;
import PilaErrores.pilaErrores;
import TablaSimbolos.simbolos;
import TablaSimbolos.tablaSimbolos;
import java.util.ArrayList;
/**
 *
 * @author Cristhian
 */
public class booleanoExpReg {
    tablaSimbolos tblSmb;     //Crea una tabla de símbolos
    simbolos smb;             //Representa un símbolo
    pilaErrores plErr;        //Crea una pila de errores
    cadena cdna;              //Cadena de caracteres
    numero num;               //Representa un número
    String s;                 //Cadena de texto
    int linea;                //Almacena el número de línea
    int inicioIndex = 0;      //Índice de inicio
    int finIndex = 0;         //Índice de fin
    boolean estado = false;   //Almacena un estado
    
    //Constructor
    public booleanoExpReg(tablaSimbolos tblSmb, String s, pilaErrores plErr, int linea)
    {
        this.tblSmb = tblSmb;
        this.s = s;
        this.plErr = plErr;
        this.linea = linea;
    }
    
    
    //Comienza recorrido del automáta
    public boolean inicio()
    {
        q0();
        return estado;
    }
    
    //Estado inicial del autómata: 
    void q0()
    {
        finIndex = obtenerFinIndex(s);
        String aux = s.substring(inicioIndex, finIndex);
        inicioIndex = finIndex;
        if(aux.equals("verdad") || aux.equals("falso") || esVarBool(aux)) //Comprueba si la subcadena es igual a verdad, falso o una variable booleana
        {
            q1(); //Se pasa al siguiente estado del autómata.
        }
        else
        {
            cdna = new cadena(aux);
            num = new numero(aux);
            if(num.inicio() || cdna.inicio() || esVarEntero(aux) || esVarTexto(aux)) //Comprueba si la subcadena es un número válido, una cadena válida, una variable entera o de texto.
            {
                q2(); //Cambia a otro estado del autómata
            }
            else
            {
                estado = false;
                plErr.push(new errores(String.valueOf(linea + 1), "Error de sintaxis: La expresión booleana está mal construida.", "104")); //Se arroja mensaje de error.
            }
        }
    }
    
    //Estado uno del autómata
    void q1()
    {
        if(inicioIndex == s.length())
        {
            qf(); //Se pasa al estado final del autómata
        }
        else
        {
            if(esOpLog()) //Comprueba si es un operador lógico
            {
                q0(); //Regresa al estado inicial
            }
            else
            {
                plErr.push(new errores(String.valueOf(linea + 1), "Error de sintaxis: Operador no reconocido.", "105")); //Se arroja mensaje de error
            }
        }
    }
    
    //Estado dos del autómata
    void q2()
    {
        if(esOpComp()) //Comprueba si es un operador de comparación 
        {
            q3(); //Pasa al estado tres del autómata
        }
        else
        {
            plErr.push(new errores(String.valueOf(linea + 1), "Error de sintaxis: Operador no reconocido.", "105")); //Arroja mensaje de error.
        }
    }
    
    void q3()
    {
        finIndex = obtenerFinIndex(s);
        String aux = s.substring(inicioIndex, finIndex);
        inicioIndex = finIndex;
        cdna = new cadena(aux);
        num = new numero(aux);
        if(num.inicio() || cdna.inicio() || esVarEntero(aux) || esVarTexto(aux)) //Comprueba si la subcadena es un número válido, una cadena válida, una variable entera o de texto.
        {
            q1(); //Pasa al estado uno del autómata
        }
        else
        {
            plErr.push(new errores(String.valueOf(linea + 1), "Error de sintaxis: La expresión booleana está mal construida.", "106")); //Arroja mensaje de error
        }
    }
    
    //Estado final del autómata
    void qf()
    {
        estado = true;
    }
    
    //Encontrar el índice final de una subcadena en la cadena "s"
    int obtenerFinIndex(String s)
    {
        int i = inicioIndex;
        boolean b = false;
        for(; s.length() > i && !b; i++)
        {
            if(esFinalIndex(s.charAt(i))) //Comprueba si el carácter en la posición "i" de la cadena "s" es un carácter que indica el final de la subcadena.
            {
                i--;
                b = true;
            }
        }
        System.out.println("i:" + i);
        return i;
    }
    
    //Verifica si un carácter es uno que indican el final de la subcadena.
    boolean esFinalIndex(char c)
    {
        if(c == '<' || c == '>' || c == '=' || c == '&' || c == '|' || c == '¬')
        {
            return true;
        }
        return false;
    }
    
    boolean esVarBool(String s)
    {
        for (int i = 0; this.tblSmb.tamanio() > i; i++) {
            simbolos Simbolo = tblSmb.obtenerSimbolo(i); //Obtiene el símbolo en la posición "i" de la tabla de símbolos y lo asigna a la variable "Simbolo".
            if (Simbolo.obtenerToken().equals("VARBOOLEANO") && Simbolo.obtenerDesc().equals(s)) //Comprueba si el token del símbolo es igual a "VARBOOLEANO" y la descripción del símbolo es igual a la cadena "s"
                return true;
        }
        return false;
    }
    
    boolean esVarEntero(String s)
    {
        for (int i = 0; this.tblSmb.tamanio() > i; i++) {
            simbolos Simbolo = tblSmb.obtenerSimbolo(i); 
            if (Simbolo.obtenerToken().equals("VARENTERO") && Simbolo.obtenerDesc().equals(s))
                return true;
        }
        return false;
    }
    
    boolean esVarTexto(String s)
    {
        for (int i = 0; this.tblSmb.tamanio() > i; i++) {
            simbolos Simbolo = tblSmb.obtenerSimbolo(i);
            if (Simbolo.obtenerToken().equals("VARTEXTO") && Simbolo.obtenerDesc().equals(s))
                return true;
        }
        return false;
    }
    
    //Verifica si la subcadena corresponde a un operador lógico
    boolean esOpLog()
    {
        try {
            String opLog = s.substring(inicioIndex, inicioIndex + 2);
            System.out.println("ope" + opLog);
            if (opLog.equals("==")) 
            {
                inicioIndex += 2;
                return true;
            }
            else
            {
                opLog = s.substring(inicioIndex, inicioIndex + 1);
                if (opLog.equals("&") || opLog.equals("|")) 
                {
                    inicioIndex += 1;
                    return true;
                }
                return false;
            }
        } 
        catch(Exception e) 
        {
            return false;
        }
    }
    
    //Verifica si la subcadena corresponde a un operador de comparación
    boolean esOpComp()
    {
        try 
        {
            String opComp = s.substring(inicioIndex, inicioIndex + 2);
            System.out.println("op c: " + opComp);
            if (opComp.equals(">=") || opComp.equals("<=") || opComp.equals("==") || opComp.equals("¬=")) 
            {
                inicioIndex += 2;
                return true;
            } 
            else 
            {
                opComp = s.substring(inicioIndex, inicioIndex + 1);
                System.out.println("ope1" + opComp);
                if (opComp.equals(">") || opComp.equals("<")) 
                {
                    inicioIndex += 1;
                    return true;
                }
            }
            return false;
        } 
        catch (Exception e) 
        {
            System.out.println("ex: " + e);
            return false;
        }
    }
    
    //Valida la expresión booleana
    public Boolean validarExpBool(String exp)
    {
        ArrayList <String> expresion = new ArrayList <String> ();
        String op = "";
        String cadena = "";
        System.out.println("\nEXP -> "+exp);
        String[] datos = exp.split("");
        for (int i = 0; i < datos.length; i++) 
        {
            char c = datos[i].charAt(0);
            if(esFinalIndex(c))
            { // Encuentra un simbolo de comparacion
                char c2 = datos[i+1].charAt(0);
                expresion.add(cadena);
                if(esFinalIndex(c2))
                { // Operador doble
                    op = datos[i]+datos[i+1];
                    expresion.add(op);
                    i++;
                }
                else
                { // Operador simple
                    op = String.valueOf(c);
                    expresion.add(op);
                }
                cadena = "";
            }
            else
            {
                cadena+=c;
            }
        }
        if(!cadena.equals(""))
        {
            expresion.add(cadena);
        }
        System.out.println(expresion);
        return evaluarExpBool(expresion);
    }
    
    //Recorre la lista de elementos de la expresión booleana y realiza las operaciones de comparación y lógicas correspondientes
    boolean evaluarExpBool(ArrayList <String> expresion)
    {
        ArrayList <String> aux = new ArrayList <String> ();
        for (int i = 0; i < expresion.size(); i++) 
        {
            // Expresiones aritmeticas
            if(expresion.get(i).equals(">") || 
            expresion.get(i).equals("<") || 
            expresion.get(i).equals(">=") || 
            expresion.get(i).equals("<=") || 
            expresion.get(i).equals("=="))
            {
                if(validarExpComp(expresion.get(i-1), expresion.get(i+1)))
                {
                    aux.add(obtenerExpComp(expresion.get(i-1), expresion.get(i), expresion.get(i+1))+"");
                }
                else
                {
                    plErr.push(new errores(String.valueOf(linea + 1),"Error semántico: Revisar expresión de comparación." , "302"));
                }
            // Expresiones logicas
            }
            else if(expresion.get(i).equals("&&"))
            {
                aux.add(expresion.get(i));
            }
            else if(expresion.get(i).equals("||"))
            {
                aux.add(expresion.get(i));
            }
            else
            {
                //aux.add(expresion.get(i));
                if(tblSmb.obtenerTokenSimbolo(expresion.get(i)).obtenerToken().equals("VARBOOLEANO"))
                {
                    aux.add(expresion.get(i));
                }
            }
        }
        // Expresiones logicas
        while(aux.size() > 1)
        {
            if(validarExpLog(aux.get(0), aux.get(1), aux.get(2)))
            {
                aux.set(2, obtenerExpLog(aux.get(0), aux.get(1), aux.get(2))+"");
                aux.remove(0);
                aux.remove(0);
            }
        }
        System.out.println("! BAN => "+aux.get(0));
        if(aux.get(0).equals("true"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //Valida una expresión de comparación entre dos variables. Aquí está la explicación del código
    boolean validarExpComp(String varIzq, String varDer)
    {
        //Si ambos tokens son "VARENTERO", significa que ambas variables son de tipo entero y la expresión de comparación es válida.
        if(tblSmb.obtenerTokenSimbolo(varIzq).obtenerToken().equals("VARENTERO") &&
           tblSmb.obtenerTokenSimbolo(varDer).obtenerToken().equals("VARENTERO")) 
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //Obtiene el resultado de una expresión de comparación entre dos variables enteras. 
    boolean obtenerExpComp(String varIzq, String simbolo, String varDer)
    {        
        int vIzq = Integer.parseInt(tblSmb.obtenerTokenSimbolo(varIzq).obtenerValor());
        int vDer = Integer.parseInt(tblSmb.obtenerTokenSimbolo(varDer).obtenerValor());
        boolean res = false;
        if(simbolo.equals(">"))
        {
            if(vIzq > vDer)
            {
                res = true;
            }
        }else if(simbolo.equals("<"))
        {
            if(vIzq < vDer)
            {
                res = true;
            }
        }else if(simbolo.equals(">="))
        {
            if(vIzq >= vDer)
            {
                res = true;
            }
        }else if(simbolo.equals("<="))
        {
            if(vIzq <= vDer)
            {
                res = true;
            }
        }else if(simbolo.equals("=="))
        {
            if(vIzq == vDer)
                res = true;
        }
        return res;
    }
    
    //Valida una expresión lógica entre dos variables booleanas.
    boolean validarExpLog(String varIzq, String simbolo, String varDer)
    {
        //Verifica si ambas variables son booleanas
        if((varIzq.equals("true") || varIzq.equals("false") || tblSmb.obtenerTokenSimbolo(varIzq).obtenerToken().equals("VARBOOLEANO")) &&
           (varDer.equals("true") || varDer.equals("false") || tblSmb.obtenerTokenSimbolo(varDer).obtenerToken().equals("VARBOOLEANO")))
        {
            return true;
        }
        else
        {
            plErr.push(new errores(String.valueOf(linea + 1),"Error semántico: Revisar expresión lógica" , "309"));
            return false;
        }
    }
    
    boolean obtenerExpLog(String varIzq, String simbolo, String varDer)
    {
        boolean izq = false;
        boolean der = false;
        // Clasificacion de lado izquierdo de la expresion
        if(varIzq.equals("true"))
        {
            izq = true;
        }
        else if(varIzq.equals("false"))
        {
            izq = false;
        }
        else if(tblSmb.obtenerTokenSimbolo(varIzq).obtenerToken().equals("VARBOOLEANO"))
        {
            String aux = tblSmb.obtenerTokenSimbolo(varIzq).obtenerValor();
            if(aux.equals("verdad"))
            {
                izq = true;
            }
            else if(aux.equals("falso"))
            {
                izq = false;
            }
            else
            {
                plErr.push(new errores(String.valueOf(linea + 1),"Error semántico: Revizar expresión lógica (izq)" , "310"));
            }
        }
        else
        {
            plErr.push(new errores(String.valueOf(linea + 1),"Error semántic: Revisar expresión lógica (izq)" , "311"));
        }
        // Clasificacion de lado derecho de la expresion
        if(varDer.equals("true"))
        {
            der = true;
        }
        else if(varDer.equals("false"))
        {
            der = false;
        }
        else if(tblSmb.obtenerTokenSimbolo(varDer).obtenerToken().equals("VARBOOLEANO"))
        {
            String aux = tblSmb.obtenerTokenSimbolo(varDer).obtenerValor();
            if(aux.equals("verdad"))
            {
                der = true;
            }
            else if(aux.equals("falso"))
            {
                der = false;
            }
            else
            {
                plErr.push(new errores(String.valueOf(linea + 1),"Error semántico: Revisar expresión lógica (der)" , "312"));
            }
        }
        else
        {
            plErr.push(new errores(String.valueOf(linea + 1),"Error semántico: Revisar expresión lógica (der)" , "313"));
        }
        // Clasificacion de simbolos de comparacion
        if(simbolo.equals("&"))
        {
            if(izq && der)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else if(simbolo.equals("|"))
        {
            if(izq || der)
            {
                return true;
            }
            else
            {
                return false;
            }
        }else{
            plErr.push(new errores(String.valueOf(linea + 1),"Error sintáctico: Revisar estructura de la expresion logica" , "314"));
            return false;
        }
    }
}
