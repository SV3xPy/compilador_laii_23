/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
  Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package interfaz;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import analizador.l.lexico_alfabeto;
import analizador.l.lexico_lexema;
import PilaErrores.pilaErrores;
import PilaErrores.errores;
import analizador.l.lexico_tokens;
import static java.awt.Color.green;
import static java.awt.Color.yellow;
import static java.awt.Color.red;

import TablaSimbolos.simbolos;

import java.io.OutputStreamWriter;
import java.util.HashMap;

import analizador.s.pilaBloques;
import analizador.s.analizadorSintactico;
import analizador.s.consolaShow;
import TablaSimbolos.tablaSimbolos;
import java.awt.Color;

import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;

/**
 *
 * @author COMPUTOCKS
 */
public class NewJFrame extends JFrame implements ActionListener {

    private Timer timer;
    private File openedFile;
    private Map<String, simbolos> tablaSimbolos = new HashMap<>();
    private pilaErrores PilaError;
    NumeroLinea numeroLinea;
    private boolean resaltando = false; // Bandera para evitar bucle infinito

    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        initComponents();
        this.setLocationRelativeTo(null);
        DefaultTableModel model = new DefaultTableModel();
        tblTokens.setModel(model);
        txtSalida.setEditable(false);

        model.addColumn("ID");
        model.addColumn("No. Token");
        model.addColumn("Token");
        model.addColumn("Descripción");
        model.addColumn("Lexema");
        //Codigo para contar las lineas en el scroll
        numeroLinea = new NumeroLinea(editorCodigo);
        jScrollPane3.setRowHeaderView(numeroLinea);
        btnCompilar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn_Lex.setBackground(red);
                btn_Sin.setBackground(red);
                btn_Sem.setBackground(red);
                txtSalida.setText("");
                model.setRowCount(0);
                PilaError = new pilaErrores();
                tablaSimbolos tblSmb = new tablaSimbolos();
                pilaBloques plBloq = new pilaBloques();
                consolaShow consola = new consolaShow();

                // Se obtiene lo escrito
                String codigo = editorCodigo.getText();
                lexico_tokens token = new lexico_tokens();
                lexico_alfabeto alfabeto = new lexico_alfabeto();
                Map<Character, Integer> caracteresErrores = new HashMap<>();

                // Obtener el modelo de la tabla existente
                // Realizar análisis léxico
                // Realizar análisis léxico
                String[] lineas = codigo.split("\\R");
                StringBuilder sb = new StringBuilder();
                boolean inicio = false;
                for (int i = 0; i < lineas.length; i++) {
                    if (lineas[i].contains("@#")) {
                        inicio = true;
                    }
                    if (inicio) {
                        sb.append("*");
                        sb.append(lineas[i]);
                        if (i != lineas.length - 1) {
                            sb.append("\n");
                        }
                        if (lineas[i].contains("#@")) {
                            break;
                        }
                    }
                }
                String resultado = sb.toString();
                boolean bandAlf = true;

                for (int li = 0; lineas.length > li && bandAlf; li++) { //Primer breakpoint
                    caracteresErrores = alfabeto.caracteresConErrores(lineas[li]);
                    if (!caracteresErrores.isEmpty()) {
                        // Procesar caracteres no permitidos y sus códigos de error
                        for (Map.Entry<Character, Integer> entry : caracteresErrores.entrySet()) {
                            char caracter = entry.getKey();
                            int codigoError = entry.getValue();
                            txtSalida.append("Línea " + (li + 1) + "\n" + "Carácter no permitido [" + caracter + "] con código de error: " + codigoError);
                        }
                        return;
                    }

                    String[] lexemas = token.getListTokens(lineas[li], resultado);

                    for (String lexema : lexemas) {
                        String resultadoToken = token.getToken(lexema);
                        // Obtener la información del token
                        String[] info = resultadoToken.split(",");
                        // Verificar si info tiene al menos 8 elementos
                        if (info.length >= 8) {
                            // Agregar la información del token a la tabla
                            System.out.print(resultadoToken);
                            model.addRow(new Object[]{info[3], info[5], info[4], info[6], info[7]});
                        }
                    }
                }

                // Si no hay errores de alfabeto, mostrar mensaje de éxito
                if (bandAlf) {
                    txtSalida.append("\nAnálisis léxico exitoso");
                    btn_Lex.setBackground(green);
                } else {
                    txtSalida.append("\nAnálisis léxico fallido");
                    btn_Lex.setBackground(yellow);
                    return;
                }

                analizadorSintactico sintactico = new analizadorSintactico(lineas, token, tblSmb, PilaError, plBloq, consola);
                sintactico.analisisSintactico(resultado);
                String show = "";
                show = consola.obtenerContConsola();
                consola.vaciar();
                txtSalida.append(show);

                // Aquí es donde se verifica la pila de errores
                if (PilaError.estaVacia()) {
                    // El programa se ejecutó sin errores
                    txtSalida.append("\nAnálisis sintáctico exitoso");
                    btn_Sin.setBackground(green);
                    txtSalida.append("\nAnálisis semántico exitoso");
                    btn_Sem.setBackground(green);
                } else {
                    // Errores en tiempo de compilación
                    String text = "";
                    while (!PilaError.estaVacia()) {
                        errores error = (errores) PilaError.pop();
                        String l = error.obtenerLineaErr();
                        String D = error.obtenerDescErr();
                        String C = error.obtenerCodigoErr();
                        text = text + "Línea: " + l + "\nDescripción: " + D + "\nCódigo del error: " + C + "\n\n";
                    }
                    txtSalida.append("\n\n" + text);
                    txtSalida.append("\nAnálisis sintáctico fallido");
                    btn_Sin.setBackground(yellow);
                    txtSalida.append("\nAnálisis semántico fallido");
                    btn_Sem.setBackground(yellow);
                }
            }
        });
        editorCodigo.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!resaltando) {
                    resaltando = true;
                    // Espera 500 milisegundos antes de realizar la actualización
                    if (timer != null) {
                        timer.stop();
                    }
                    timer = new Timer(500, evt -> actualizarResaltado());
                    timer.setRepeats(false);
                    timer.start();
                    resaltando = false;
                }

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!resaltando) {
                    resaltando = true;
                    // Espera 500 milisegundos antes de realizar la actualización
                    if (timer != null) {
                        timer.stop();
                    }
                    timer = new Timer(500, evt -> actualizarResaltado());
                    timer.setRepeats(false);
                    timer.start();
                    resaltando = false;
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!resaltando) {
                    resaltando = true;
                    // Espera 500 milisegundos antes de realizar la actualización
                    if (timer != null) {
                        timer.stop();
                    }
                    timer = new Timer(500, evt -> actualizarResaltado());
                    timer.setRepeats(false);
                    timer.start();
                    resaltando = false;
                }
            }
        });
    }

    //CLASES PARA APLICAR EL COLOREADO A LAS PALABRAS RESERVADAS
    private void actualizarResaltado() {
        Document document = editorCodigo.getDocument();
        try {
            String texto = document.getText(0, document.getLength());
            int ultimaLineaInicio = texto.lastIndexOf('\n') + 1;
            //Llamada a resaltarPalabrasReservadas
            resaltarPalabrasReservadas("int", Color.BLUE, texto);
            resaltarPalabrasReservadas("point", Color.BLUE, texto);
            resaltarPalabrasReservadas("text", Color.BLUE, texto);
            resaltarPalabrasReservadas("if", Color.GREEN, texto);
            resaltarPalabrasReservadas("else", Color.GREEN, texto);
            resaltarPalabrasReservadas("aslong", Color.RED, texto);
            resaltarPalabrasReservadas("@@", Color.GRAY, texto);
            resaltarPalabrasReservadas("@#", Color.GRAY, texto);
            resaltarPalabrasReservadas("#@", Color.GRAY, texto);
            resaltarPalabrasReservadas("show", Color.MAGENTA, texto);
            resaltarPalabrasReservadas("booleano", Color.BLUE, texto);
            resaltarPalabrasReservadas("principal", Color.CYAN, texto);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void abertura_Archivo(String rutaArchivoPredefinido) {
        try {
            openedFile = new File(rutaArchivoPredefinido);
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(openedFile), "UTF-8"));
            String line = in.readLine();
            editorCodigo.setText("");
            while (line != null) {
                Document doc = editorCodigo.getDocument();
                doc.insertString(doc.getLength(), line + "\n", null);
                line = in.readLine();
            }
            in.close();
            String filePath = openedFile.getAbsolutePath();
            mostrarRuta.setText(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resaltarPalabrasReservadas(String palabraReser, Color color, String texto) {
        StyledDocument doc = editorCodigo.getStyledDocument();
        int pos = 0;
        while ((pos = texto.indexOf(palabraReser, pos)) >= 0) {
            final int inicio = pos;
            final int longitud = palabraReser.length();
            SwingUtilities.invokeLater(() -> {
                try {
                    doc.setCharacterAttributes(inicio, longitud, getEstilo(color), true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            pos += longitud;
        }
    }

    private SimpleAttributeSet getEstilo(Color color) {
        SimpleAttributeSet estilo = new SimpleAttributeSet();
        StyleConstants.setForeground(estilo, color);
        StyleConstants.setBold(estilo, true);
        return estilo;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblTokens = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtSalida = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        editorCodigo = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        mostrarRuta = new javax.swing.JTextField();
        btnCompilar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btn_Lex = new javax.swing.JButton();
        btn_Sin = new javax.swing.JButton();
        btn_Sem = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        Abrir = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        ejemplo_1 = new javax.swing.JMenuItem();
        ejemplo_2 = new javax.swing.JMenuItem();
        ejemplo_3 = new javax.swing.JMenuItem();
        Guardar = new javax.swing.JMenuItem();
        guardarComo = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        limpiarEditorCodigo = new javax.swing.JMenuItem();
        limpiarTablaSimbolos = new javax.swing.JMenuItem();
        limpiarConsola = new javax.swing.JMenuItem();
        limpiarTodo = new javax.swing.JMenuItem();
        Ayuda = new javax.swing.JMenu();
        docALex = new javax.swing.JMenuItem();
        docASin = new javax.swing.JMenuItem();
        docASem = new javax.swing.JMenuItem();
        docComp = new javax.swing.JMenuItem();
        aboutMenu = new javax.swing.JMenu();
        aboutTeam = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(238, 240, 235));

        jScrollPane1.setBackground(new java.awt.Color(238, 240, 235));
        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        tblTokens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "No. Token", "Token", "Descripción", "Valor"
            }
        ));
        jScrollPane1.setViewportView(tblTokens);

        jScrollPane2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        txtSalida.setColumns(20);
        txtSalida.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtSalida.setRows(5);
        jScrollPane2.setViewportView(txtSalida);

        jScrollPane3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        editorCodigo.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jScrollPane3.setViewportView(editorCodigo);

        jLabel1.setFont(new java.awt.Font("Engravers MT", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(21, 50, 67));
        jLabel1.setText("Proyecto - LAII");

        jLabel2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        jLabel2.setText("Tabla de símbolos:");

        jLabel3.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        jLabel3.setText("Salida:");

        jLabel4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        jLabel4.setText("Código:");

        jLabel5.setText("Código fuente:");

        mostrarRuta.setEditable(false);

        btnCompilar.setBackground(new java.awt.Color(40, 75, 99));
        btnCompilar.setForeground(new java.awt.Color(244, 249, 233));
        btnCompilar.setText("Compilar");
        btnCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompilarActionPerformed(evt);
            }
        });

        jLabel6.setText("LÉXICO");

        jLabel7.setText("SINTÁCTICO");

        jLabel8.setText("SEMÁNTICO");

        btn_Lex.setBackground(new java.awt.Color(255, 0, 0));

        btn_Sin.setBackground(new java.awt.Color(255, 0, 0));

        btn_Sem.setBackground(new java.awt.Color(255, 0, 0));

        jMenuBar1.setBackground(new java.awt.Color(180, 184, 171));

        jMenu1.setText("Archivo");

        Abrir.setText("Abrir");
        Abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirActionPerformed(evt);
            }
        });
        jMenu1.add(Abrir);

        jMenu4.setText("Ejemplos");

        ejemplo_1.setText("Ejemplo 1");
        ejemplo_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ejemplo_1ActionPerformed(evt);
            }
        });
        jMenu4.add(ejemplo_1);

        ejemplo_2.setText("Ejemplo 2");
        ejemplo_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ejemplo_2ActionPerformed(evt);
            }
        });
        jMenu4.add(ejemplo_2);

        ejemplo_3.setText("Ejemplo 3");
        ejemplo_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ejemplo_3ActionPerformed(evt);
            }
        });
        jMenu4.add(ejemplo_3);

        jMenu1.add(jMenu4);

        Guardar.setText("Guardar");
        Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarActionPerformed(evt);
            }
        });
        jMenu1.add(Guardar);

        guardarComo.setText("Guardar como...");
        guardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarComoActionPerformed(evt);
            }
        });
        jMenu1.add(guardarComo);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Limpiar");

        limpiarEditorCodigo.setText("Limpiar editor de código");
        limpiarEditorCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarEditorCodigoActionPerformed(evt);
            }
        });
        jMenu3.add(limpiarEditorCodigo);

        limpiarTablaSimbolos.setText("Limpiar tabla de símbolos");
        limpiarTablaSimbolos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarTablaSimbolosActionPerformed(evt);
            }
        });
        jMenu3.add(limpiarTablaSimbolos);

        limpiarConsola.setText("Limpiar consola");
        limpiarConsola.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarConsolaActionPerformed(evt);
            }
        });
        jMenu3.add(limpiarConsola);

        limpiarTodo.setText("Limpiar todo");
        limpiarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarTodoActionPerformed(evt);
            }
        });
        jMenu3.add(limpiarTodo);

        jMenuBar1.add(jMenu3);

        Ayuda.setText("Ayuda");

        docALex.setText("Análisis Léxico");
        docALex.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                docALexMouseClicked(evt);
            }
        });
        docALex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                docALexActionPerformed(evt);
            }
        });
        Ayuda.add(docALex);

        docASin.setText("Análisis Sintáctico");
        docASin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                docASinActionPerformed(evt);
            }
        });
        Ayuda.add(docASin);

        docASem.setText("Análisis Semántico");
        docASem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                docASemActionPerformed(evt);
            }
        });
        Ayuda.add(docASem);

        docComp.setText("Compilador");
        docComp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                docCompActionPerformed(evt);
            }
        });
        Ayuda.add(docComp);

        jMenuBar1.add(Ayuda);

        aboutMenu.setText("Acerca de");

        aboutTeam.setText("Equipo");
        aboutTeam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutTeamActionPerformed(evt);
            }
        });
        aboutMenu.add(aboutTeam);

        jMenuBar1.add(aboutMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(mostrarRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(btn_Lex, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jLabel7))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addComponent(btn_Sin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel8))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(btn_Sem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(186, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(btnCompilar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_Lex, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_Sin, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_Sem, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mostrarRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCompilar)
                            .addComponent(jLabel4))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(51, 51, 51))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void docALexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_docALexActionPerformed
        File file = new File("src\\main\\java\\resources\\lexico.pdf");
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_docALexActionPerformed

    private void AbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirActionPerformed
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Texto plano", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            openedFile = chooser.getSelectedFile();
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(openedFile), "UTF-8"));
                String line = in.readLine();
                while (line != null) {
                    Document doc = editorCodigo.getDocument();
                    doc.insertString(doc.getLength(), line + "\n", null);
                    line = in.readLine();
                }
                in.close();
                String filePath = openedFile.getAbsolutePath();
                mostrarRuta.setText(filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_AbrirActionPerformed

    private void limpiarEditorCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarEditorCodigoActionPerformed
        editorCodigo.setText("");
    }//GEN-LAST:event_limpiarEditorCodigoActionPerformed

    private void docALexMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_docALexMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_docALexMouseClicked

    private void docASinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_docASinActionPerformed
        File file = new File("src\\main\\java\\resources\\sintactico.pdf");
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_docASinActionPerformed

    private void docASemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_docASemActionPerformed
        File file = new File("src\\main\\java\\resources\\semantico.pdf");
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_docASemActionPerformed

    private void docCompActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_docCompActionPerformed
        File file = new File("src\\main\\java\\resources\\DocumentaciónProyectoFinal-LAII-Equipo2.pdf");
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_docCompActionPerformed

    private void GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarActionPerformed
        if (openedFile != null) {
            // sobrescribir el archivo
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(openedFile));
                out.write(editorCodigo.getText());
                out.close();
                JOptionPane.showMessageDialog(null, "Archivo guardado exitosamente.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (editorCodigo.getText().length() > 0) {
            // crear un nuevo archivo
            JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Texto plano", "txt");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showSaveDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                try {
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
                    out.write(editorCodigo.getText());
                    out.close();
                    JOptionPane.showMessageDialog(null, "Archivo guardado exitosamente.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_GuardarActionPerformed

    private void guardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarComoActionPerformed
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Texto plano", "txt");
        int returnVal = chooser.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            try {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(selectedFile), "UTF-8"));
                out.write(editorCodigo.getText());
                out.close();
                JOptionPane.showMessageDialog(null, "Archivo guardado exitosamente.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_guardarComoActionPerformed

    private void limpiarTablaSimbolosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarTablaSimbolosActionPerformed
        DefaultTableModel modeloTabla = (DefaultTableModel) tblTokens.getModel();
        modeloTabla.setRowCount(0);
    }//GEN-LAST:event_limpiarTablaSimbolosActionPerformed

    private void limpiarConsolaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarConsolaActionPerformed
        txtSalida.setText("");
    }//GEN-LAST:event_limpiarConsolaActionPerformed

    private void limpiarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarTodoActionPerformed
        DefaultTableModel modeloTabla = (DefaultTableModel) tblTokens.getModel();
        modeloTabla.setRowCount(0);
        txtSalida.setText("");
        editorCodigo.setText("");
        mostrarRuta.setText("");
        btn_Lex.setBackground(red);
        btn_Sin.setBackground(red);
        btn_Sem.setBackground(red);
    }//GEN-LAST:event_limpiarTodoActionPerformed

    private void ejemplo_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ejemplo_1ActionPerformed
        String rutaArchivoPredefinido = ("src\\main\\java\\resources\\ejem1.txt");
        abertura_Archivo(rutaArchivoPredefinido);
    }//GEN-LAST:event_ejemplo_1ActionPerformed

    private void ejemplo_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ejemplo_2ActionPerformed
        String rutaArchivoPredefinido = ("src\\main\\java\\resources\\ejem2.txt");
        abertura_Archivo(rutaArchivoPredefinido);
    }//GEN-LAST:event_ejemplo_2ActionPerformed

    private void ejemplo_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ejemplo_3ActionPerformed
        String rutaArchivoPredefinido = ("src\\main\\java\\resources\\ejem3.txt");
        abertura_Archivo(rutaArchivoPredefinido);
    }//GEN-LAST:event_ejemplo_3ActionPerformed

    private void btnCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompilarActionPerformed

    }//GEN-LAST:event_btnCompilarActionPerformed

    private void aboutTeamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutTeamActionPerformed
        new acercaDeTeam(this, true).setVisible(true);
    }//GEN-LAST:event_aboutTeamActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);

            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Abrir;
    private javax.swing.JMenu Ayuda;
    private javax.swing.JMenuItem Guardar;
    private javax.swing.JMenu aboutMenu;
    private javax.swing.JMenuItem aboutTeam;
    private javax.swing.JButton btnCompilar;
    private javax.swing.JButton btn_Lex;
    private javax.swing.JButton btn_Sem;
    private javax.swing.JButton btn_Sin;
    private javax.swing.JMenuItem docALex;
    private javax.swing.JMenuItem docASem;
    private javax.swing.JMenuItem docASin;
    private javax.swing.JMenuItem docComp;
    private javax.swing.JTextPane editorCodigo;
    private javax.swing.JMenuItem ejemplo_1;
    private javax.swing.JMenuItem ejemplo_2;
    private javax.swing.JMenuItem ejemplo_3;
    private javax.swing.JMenuItem guardarComo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JMenuItem limpiarConsola;
    private javax.swing.JMenuItem limpiarEditorCodigo;
    private javax.swing.JMenuItem limpiarTablaSimbolos;
    private javax.swing.JMenuItem limpiarTodo;
    private javax.swing.JTextField mostrarRuta;
    private javax.swing.JTable tblTokens;
    private javax.swing.JTextArea txtSalida;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
