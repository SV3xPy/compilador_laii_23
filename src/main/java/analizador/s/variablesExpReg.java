/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador.s;
import analizador.l.lexico_tokens;
import analizador.s.evaluar.evaluar;
import PilaErrores.pilaErrores;
import PilaErrores.errores;
import TablaSimbolos.tablaSimbolos;
import TablaSimbolos.simbolos;
import java.util.Arrays;

/**
 *
 * @author Cristhian
 */
public class variablesExpReg 
{
    lexico_tokens token;
    tablaSimbolos tblSmb;
    pilaErrores plErr;
    String[] lineas;
    String[] desctd;
    String linea;
    int clinea;
    boolean init;
    boolean q0 = false;
    boolean q1 = false;
    boolean q2 = false;
    boolean q3 = false;
    boolean q4 = false;
    
    public variablesExpReg(String[] lineas, String linea, lexico_tokens token, tablaSimbolos tblSmb, pilaErrores plErr, int clinea, boolean init)
    {
        this.lineas = lineas;
        this.linea = linea;
        this.token = token;
        this.tblSmb = tblSmb;
        this.plErr = plErr;
        this.clinea = clinea;
        this.init = init;
    }
    
    public void validarExpReg()
    {
        if(!init)
        {
            // Tipo de dato
            String td = token.getToken(lineas[0]);
            desctd =  td.split(",");
            int numt = Integer.parseInt(desctd[1]);
            if((numt > 10) && (numt < 15) )
            {
                q0 = true;
                q0();
            }
            else
            {
                plErr.push(new errores(String.valueOf(clinea + 1),"Error semántico: Tipo de dato no reconocido." , "303"));
            }
        }
        else if(init)
        {
            // Variables ya declaradas
            //System.out.println("YA DECLARADA");
            try 
            {
                q5();
            } 
            catch (Exception e) 
            {
                plErr.push(new errores(String.valueOf(clinea + 1),"Error semántico: Revisar expresión de la línea "+ linea , "304"));
            }
        }
    }
    
    void q0()
    {
        if(q0)
        {
            // Nombre de la variable
            String nv = token.getToken(lineas[1]);
            String[] descnv = nv.split(",");
            if(descnv[2].equals("Token no especificado"))
            { // No es un token existente
                q1 = true;
                q1();
            }
            else
            {
                plErr.push(new errores(String.valueOf(clinea+1),"Error semántico: La variable a declarar ya es una palabra reservada." , "305"));
            }
        }
    }
    
    void q1()
    {
        if(q0 && q1)
        {
            // Signo de Igualacion
            String si = token.getToken(lineas[2]);
            String[] descsi =  si.split(",");
            if(descsi[2].equals("Operador de asignación"))
            {
                q2 = true;
                q2();
            }
            else
            {
                plErr.push(new errores(String.valueOf(clinea + 1),"Error sintáctico: No se agregó el símbolo de asignación." , "113"));
            }
        }
    }
    
    void q2()
    {
        if(q0 && q1 && q2)
        {
            // Contenido de la variable
            String cv = token.getToken(lineas[3]);
            String[] desccv =  cv.split(",");
            // ANALISIS SEMANTICO
            if(desctd[0].equals("ENTERO"))
            {
                if(desccv[0].equals("NUMERO"))
                {
                    q3 = true;
                }
            }
            else if(desctd[0].equals("TEXTO"))
            {
                if(desccv[0].equals("CADENA"))
                {
                    q3 = true;
                }
            }
            else if(desctd[0].equals("BOOLEANO"))
            {
                if(desccv[0].equals("TDBOOLEANO"))
                {
                    q3 = true;
                }
            }
            if(!q3)
            {
                plErr.push(new errores(String.valueOf(clinea + 1),"Error semántico: El tipo de dato no coincide con su contenido" , "306"));
            }
            else
            {
                q3();
            }
        } 
    }
    
    void q3()
    {
        if(q0 && q1 && q2 && q3)
        {
            // Delimitador ;
            String pc = token.getToken(lineas[4]);
            String[] descpc =  pc.split(",");
            if(descpc[2].equals("Delimitador"))
            {
                q4 = true;
                q4();
            }
            else
            {
                plErr.push(new errores(String.valueOf(clinea + 1),"Error sintáctico: Falta delimitador." , "114"));
            }
        }
        System.out.println("------ ER Variable ------");
        System.out.println("Tipo de dato : "+lineas[0]+" -> "+q0);
        System.out.println("Nombre       : "+lineas[1]+" -> "+q1);
        System.out.println("Asignacion   : "+lineas[2]+" -> "+q2);
        System.out.println("Contenido    : "+lineas[3]+" -> "+q3);
        System.out.println("Delimitador  : "+lineas[4]+" -> "+q4); 
    }
    
    void q4()
    {
        if(q0 && q1 && q2 && q3 && q4)
        {
            token.variables.add(lineas[1]);
            if(lineas[0].equals("entero"))
            {
                this.tblSmb.agregarSimbolo(new simbolos(("VARENTERO 75 " + lineas[1] + " " + lineas[3]).split(" ")));
            }
            if(lineas[0].equals("texto"))
            {
                this.tblSmb.agregarSimbolo(new simbolos(("VARTEXTO 76 " + lineas[1] + " " + lineas[3]).split(" ")));
            }
            if(lineas[0].equals("booleano"))
            {
                this.tblSmb.agregarSimbolo(new simbolos(("VARBOOLEANO 77 " + lineas[1] + " " + lineas[3]).split(" ")));
            }
            System.out.println("VARIABLE DECLARADA");
        }
        else
        {
            System.out.println("NO FUE POSIBLE DECLARAR LA VARIABLE");
            plErr.push(new errores(String.valueOf(clinea + 1),"Error semántico: La variable no fue declarada." , "307"));
        }
        //System.out.println("Variables: "+t.variables);
    }
    
    void q5(){
        String[] contenido = linea.trim().split("=");
        String[] separado = contenido[1].trim().split("\\Q+\\E");
        if(!lineas[1].equals("="))
        {
            System.out.println("LA ASIGNACION DE LA VARIABLE NO CONTIENE SIMBOLO (=)");
            plErr.push(new errores(String.valueOf(clinea + 1),"Error semántico: la variable a declarar no cuenta con el simbolo de asignación." , "308"));
        }
        else if(!lineas[lineas.length-1].equals(";"))
        {
            System.out.println("FALTA EL DELIMITADOR EN LA ASIGNACION DE LA VARIABLE");
            plErr.push(new errores(String.valueOf(clinea + 1),"Error sintáctico: Falta delimitador en la asignación de la variable." , "115"));
        }
        else
        {
            // Validar la expresion
            //System.out.println("    Contenido -> "+Arrays.toString(contenido));
            if(tblSmb.obtenerTokenSimbolo(lineas[0]).obtenerToken().equals("VARENTERO"))
            {
                // Evaluar expresion infija
                //System.out.println("Expresion INFIJA");
                evaluar ev = new evaluar(lineas[2],tblSmb);
                String resultado = "" + ev.obtenerResultado();
                System.out.println("Resuuuu "+resultado);

                // tipoSimbolo, id, descripcion, valor
                String[] simbol = {"VARENTERO","75",lineas[0],resultado};
                tblSmb.agregarSimbolo(new simbolos(simbol));
            }
            else
            {
                System.out.println("Separado  -> "+Arrays.toString(separado));
            }
        }
    }
}
