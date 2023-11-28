/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador.s;

import analizador.l.lexico_tokens;
import PilaErrores.pilaErrores;
import PilaErrores.errores;
import TablaSimbolos.tablaSimbolos;

/**
 *
 * @author Cristhian
 */
public class analizadorSintactico {

    tablaSimbolos tblSmb;
    pilaErrores plErr;
    pilaBloques plBloq;
    consolaShow consola;
    lexico_tokens token;
    String[] codigo;

    public analizadorSintactico(String[] lineas, lexico_tokens tkn, tablaSimbolos tblSmb, pilaErrores plErr, pilaBloques plBloq, consolaShow consola) {
        codigo = lineas;
        token = tkn;
        this.tblSmb = tblSmb;
        this.plErr = plErr;
        this.plBloq = plBloq;
        this.consola = consola;
    }

    // Evaluacion de cada linea de codigo de manera singular
    public void analisisSintactico(String aux) {
        boolean bloque = false, ban = true;
        int i;
        for (i = 0; i < codigo.length; i++) {
            codigo[i] = codigo[i].trim();
            if (!codigo[i].equals("")) {
                String ultimo = codigo[i].substring(codigo[i].length() - 1);
                //System.out.println("Ultimo -> "+ultimo);
                //System.out.println("Linea: "+codigo[i]);
                String[] datos = valiString(codigo[i]);
                String tkn = token.getToken(datos[0]);
                String vars[] = tkn.split(",");
                if (datos[0].equals("principal") && ultimo.equals("{")) {
                    bloque = true;
                    plBloq.pila.push("principal");
                } else if ((datos[0].equals("if") || datos[0].equals("else")) && ultimo.equals("{")) {
                    if (ban) {
                        ifelseExpReg erif = new ifelseExpReg(tblSmb, codigo[i], this.plErr, i);
                        if (erif.inicio()) { // Sintaxis correcta
                            try {
                                //System.out.println("RESUUUUU SI" + ersi.getResu());
                                ban = erif.obtenerResu();
                            } catch (Exception e) {
                                System.out.println("ERROR SEMANTICOOOO 1");
                                plErr.push(new errores(String.valueOf(i + 1), "Error semántico: Revisar estructura if", "300"));
                            }
                        } else {
                            System.out.println("INCORRECTAAAAAAA");
                        }
                        plBloq.pila.push("if");
                    } else {
                        System.out.println("CONDICION FALSA si " + i);
                    }
                } else if (datos[0].equals("aslong") && ultimo.equals("{")) {
                    if (ban) {
                        aslongExpReg eral = new aslongExpReg(tblSmb, codigo[i], this.plErr, i);
                        plBloq.pila.push("aslong " + i);
                        if (eral.inicio()) { // Sintaxis correcta
                            try {
                                //System.out.println("RESUUUUU MMMM -> " + erm.getResu());
                                ban = eral.obtenerResultado();
                            } catch (Exception e) {
                                System.out.println("ERROR SEMANTICOOOO 2");
                                plErr.push(new errores(String.valueOf(i + 1), "Error semántico: Revisar estructura aslong ", "301"));
                                ban = false;
                            }
                        } else {
                            System.out.println("INCORRECTAAAAAAA");
                        }
                    } else {
                        System.out.println("CONDICION FALSA mientras " + i);
                    }
                } /*else if (datos[0].equals("hacer") && ultimo.equals("{")) 
                {
                    if (ban) {
                        PilaBloques.pila.push("hacer");
                    } else {
                        System.out.println("CONDICION FALSA hacer " + i);
                    }
                }*/ else if ((datos[0].equals("int") || (datos[0].equals("text")) || (datos[0].equals("booleano")) || (datos[0].equals("point"))) // Declaracion de variables
                        || (vars[1].equals("VARIABLE"))) { // Validacion de variable ya declarada
                    if (ban) {
                        try {
                            variablesExpReg erv;
                            if ((vars[1].equals("VARIABLE"))) { // Iniciazada
                                erv = new variablesExpReg(datos, codigo[i], token, this.tblSmb, this.plErr, i, true);
                            } else { // A declarar
                                erv = new variablesExpReg(datos, codigo[i], token, this.tblSmb, this.plErr, i, false);
                            }
                            erv.validarExpReg(); //Breakpoint 2
                        } catch (Exception e) {
                            System.out.println("Error Sintactico");
                            plErr.push(new errores(String.valueOf(i + 1), "Error sintáctico: Al declarar el valor de la variable.", "116"));
                        }
                    } else {
                        System.out.println("CONDICION FALSA var " + i);
                    }
                } else if (datos[0].equals("")) { // Variables ya declaradas
                    System.out.println("¿Error?");
                } else if (datos[0].equals("}")) { // Cierre de bloque
                    if (plBloq.estaVacia()) {
                        System.out.println("ERROR en las llaves");
                        plErr.push(new errores(String.valueOf(i + 1), "Error sintáctico: Faltó cerrar un bloque de código.", "100"));
                    } else {
                        //System.out.println("PILA Bloques ->" + PilaBloques.pila);
                        //System.out.println("Ult Bloq -> " + PilaBloques.pila.get(PilaBloques.pila.size() - 1));
                        if (plBloq.pila.get(plBloq.pila.size() - 1).equals("if")) {
                            if (!ban) {
                                ban = true;
                            }
                        } else if (plBloq.pila.get(plBloq.pila.size() - 1).substring(0, 6).equals("aslong")) {
                            if (ban) {
                                //System.out.println("Si era mientras " + i);
                                //System.out.println("Volver a la linea " + PilaBloques.pila.get(PilaBloques.pila.size() - 1).substring(9));
                                if (plErr.estaVacia()) {
                                    i = Integer.parseInt(plBloq.pila.get(plBloq.pila.size() - 1).substring(7)) - 1;
                                } else {
                                    plErr.push(new errores(String.valueOf(i + 1), "Error sintáctico: En el bloque del ciclo", "117"));
                                }
                            } else {
                                ban = true;
                            }
                        }
                        plBloq.pila.pop();
                        if (bloque) {
                            System.out.println("Bloque correcto");
                        }
                    }
                } else if (datos[0].equals("@@")) { // Cometarios
                    // *NO HACE NADA* 
                } else if (datos[0].equals("show")) { // Cometarios
                    if (ban) {
                        showExpReg ershow = new showExpReg(tblSmb, codigo[i], this.plErr, i + 1, consola);
                        ershow.validarExpReg();
                    } else {
                        System.out.println("CONDICION FALSA imprimir " + i);
                    }
                } else {
                    if (Character.isLetter(datos[0].charAt(0))) {
                        plErr.push(new errores(String.valueOf(i + 1), "Error sintáctico: Error uso de variable, mal definida", "104"));

                    }
                }
            }
        }
        //System.out.println("Pila Bloques -> " + PilaBloques.getLong());
        if (!bloque) {
            plErr.push(new errores(String.valueOf(i + 1), "Error sintáctico: Falta el bloque de código principal.", "102"));
        }
        if (!plBloq.estaVacia()) {
            plErr.push(new errores(String.valueOf(i + 1), "Error sintáctico: Falta cerrar un bloque de código.", "103"));
        }
    }

    // Toma de parametro una cadena y la devuelve como un arrelo String separado por espacios
    String[] valiString(String p_dato) {
        String[] v_datos = null;
        int v_con, v_con2;
        try {
            int v_cont = 0, v_cont2 = 0;
            String[] v_aux = p_dato.split(" ");
            for (v_con = 0; v_con < v_aux.length; v_con++) {
                if (!v_aux[v_con].equals("")) {
                    v_cont++;
                }
            }
            v_datos = new String[v_cont];
            for (v_con2 = 0; v_con2 < v_aux.length; v_con2++) {
                if (!v_aux[v_con2].equals("")) {
                    v_datos[v_cont2] = v_aux[v_con2];
                    v_cont2++;
                }
            }
            return v_datos;
        } catch (Exception e) {
            return v_datos;
        }
    }
}
