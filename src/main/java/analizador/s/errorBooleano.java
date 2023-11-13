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
public class errorBooleano {
    tablaSimbolos tblSmb;
    simbolos smb;
    pilaErrores plErr;
    cadena cdna;
    numero num;
    String s;
    int linea;
    int inicioIndex = 0;
    int finIndex = 0;
    boolean estado = false;
    
    public errorBooleano(tablaSimbolos tblSmb, String s, pilaErrores plErr, int linea)
    {
        this.tblSmb = tblSmb;
        this.s = s;
        this.plErr = plErr;
        this.linea = linea;
    }
    
    public boolean inicio()
    {
        q0();
        return estado;
    }
    
    void q0()
    {
        finIndex = obtenerFinIndex(s);
        String aux = s.substring(inicioIndex, finIndex);
        inicioIndex = finIndex;
        if(aux.equals("verdad") || aux.equals("falso") || esVarBool(aux))
        {
            q1();
        }
        else
        {
            cdna = new cadena(aux);
            num = new numero(aux);
            if(num.inicio() || cdna.inicio() || esVarEntero(aux) || esVarTexto(aux))
            {
                q2();
            }
            else
            {
                estado = false;
                plErr.push(new errores(String.valueOf(linea + 1), "Error de sintaxis: La expresión booleana está mal construida.", "104"));
            }
        }
    }
    
    void q1()
    {
        if(inicioIndex == s.length())
        {
            qf();
        }
        else
        {
            if(esOpLog())
            {
                q0();
            }
            else
            {
                plErr.push(new errores(String.valueOf(linea + 1), "Error de sintaxis: Operador no reconocido.", "105"));
            }
        }
    }
    
    void q2()
    {
        if(esOpComp())
        {
            q3();
        }
        else
        {
            plErr.push(new errores(String.valueOf(linea + 1), "Error de sintaxis: Operador no reconocido.", "105"));
        }
    }
    
    void q3()
    {
        finIndex = obtenerFinIndex(s);
        String aux = s.substring(inicioIndex, finIndex);
        inicioIndex = finIndex;
        cdna = new cadena(aux);
        num = new numero(aux);
        if(num.inicio() || cdna.inicio() || esVarEntero(aux) || esVarTexto(aux))
        {
            q1();
        }
        else
        {
            plErr.push(new errores(String.valueOf(linea + 1), "Error de sintaxis: La expresión booleana está mal construida.", "106"));
        }
    }
    
    void qf()
    {
        estado = true;
    }
    
    int obtenerFinIndex(String s)
    {
        int i = inicioIndex;
        boolean b = false;
        for(; s.length() > i && !b; i++)
        {
            if(esFinalIndex(s.charAt(i)))
            {
                i--;
                b = true;
            }
        }
        System.out.println("i:" + i);
        return i;
    }
    
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
            simbolos Simbolo = tblSmb.obtenerSimbolo(i);
            if (Simbolo.obtenerToken().equals("VARBOOLEANO") && Simbolo.obtenerDesc().equals(s))
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
    
    boolean esOpLog()
    {
        try {
            String opLog = s.substring(inicioIndex, inicioIndex + 2);
            System.out.println("ope" + opLog);
            if (opLog.equals("&") || opLog.equals("|") || opLog.equals("==")) 
            {
                inicioIndex += 2;
                return true;
            }
            else
            {
                return false;
            }
        } 
        catch(Exception e) 
        {
            return false;
        }
    }
    
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
                    System.out.println("ExpAri Valida");
                    aux.add(obtenerExpComp(expresion.get(i-1), expresion.get(i), expresion.get(i+1))+"");
                }
                else
                {
                    System.out.println("ERROR SEMANTICOOOO 1");
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
        System.out.println("exxx "+expresion);
        System.out.println("auxx "+aux);
        // Expresiones logicas
        while(aux.size() > 1)
        {
            if(validarExpLog(aux.get(0), aux.get(1), aux.get(2)))
            {
                System.out.println("ITERACION");
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
    
    boolean validarExpComp(String varIzq, String varDer)
    {
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
    
    boolean obtenerExpComp(String varIzq, String simbolo, String varDer)
    {
        //System.out.println("bueeeee");        
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
    
    boolean validarExpLog(String varIzq, String simbolo, String varDer)
    {
        if((varIzq.equals("true") || varIzq.equals("false") || tblSmb.obtenerTokenSimbolo(varIzq).obtenerToken().equals("VARBOOLEANO")) &&
           (varDer.equals("true") || varDer.equals("false") || tblSmb.obtenerTokenSimbolo(varDer).obtenerToken().equals("VARBOOLEANO")))
        {
            System.out.println("Bien construida");
            return true;
        }
        else
        {
            System.out.println("FFFFFFFFF");
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
                System.out.println("ERROR SEMANTICOOOoOo 2");
            }
        }
        else
        {
            plErr.push(new errores(String.valueOf(linea + 1),"Error semántic: Revisar expresión lógica (izq)" , "311"));
            System.out.println("ERROR SEMANTICOOOoOo 3");
        }
        // Clasificacion de lado derecho de la expresion
        if(varDer.equals("true"))
        {
            der = true;
        }else if(varDer.equals("false"))
        {
            der = false;
        }else if(tblSmb.obtenerTokenSimbolo(varDer).obtenerToken().equals("VARBOOLEANO"))
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
                System.out.println("ERROR SEMANTICOOOoOo 4");
            }
        }
        else
        {
            plErr.push(new errores(String.valueOf(linea + 1),"Error semántico: Revisar expresión lógica (der)" , "313"));
                System.out.println("ERROR SEMANTICOOOoOo 5");
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
            System.out.println("ERROR SINTACTICOOOO 6");
            return false;
        }
    }
}
