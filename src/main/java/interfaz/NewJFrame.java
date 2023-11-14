/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
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
import analizador.l.lexico_tokens;
import static java.awt.Color.green;
import static java.awt.Color.red;

import java.io.OutputStreamWriter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.Document;

/**
 *
 * @author COMPUTOCKS
 */
public class NewJFrame extends JFrame implements ActionListener {

    private File openedFile;

    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        initComponents();

        btnCompilar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Se obtiene lo escrito
                String codigo = editorCodigo.getText();

                // Se inicializa la instancia de la clase para el análisis léxico
                lexico_tokens token = new lexico_tokens();
                lexico_alfabeto alfabeto = new lexico_alfabeto();

                // Realizar análisis léxico
                String[] lineas = codigo.split("\n");
                boolean bandAlf = true;

                for (int li = 0; lineas.length > li && bandAlf; li++) {
                    if (!alfabeto.validar(lineas[li])) {
                        bandAlf = false;
                        lblSalida.setText("Error en el análisis léxico. Caracteres no permitidos en la línea " + (li + 1));
                        lblLestado.setForeground(red);
                        lblLestado.setText("O");
                        break;
                    }

                    String[] lexemas = token.getListTokens(lineas[li]);

                    for (String lexema : lexemas) {
                        String resultadoToken = token.getToken(lexema);
                        // Puedes agregar lógica adicional para manejar el resultado del token aquí
                        System.out.println(resultadoToken);
                    }
                }

                // Si no hay errores de alfabeto, mostrar mensaje de éxito
                if (bandAlf) {
                    lblSalida.setText("Análisis léxico exitoso");
                    lblLestado.setForeground(green);
                    lblLestado.setText("O");
                }

            }
        });

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
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        lblSalida = new javax.swing.JLabel();
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
        lblLestado = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        Abrir = new javax.swing.JMenuItem();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(238, 240, 235));

        jScrollPane1.setBackground(new java.awt.Color(238, 240, 235));
        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        jScrollPane2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lblSalida.setBackground(new java.awt.Color(255, 255, 255));
        lblSalida.setForeground(new java.awt.Color(21, 50, 67));
        jScrollPane2.setViewportView(lblSalida);

        jScrollPane3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
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

        jLabel6.setText("LÉXICO ESTADO: ");

        lblLestado.setBackground(new java.awt.Color(102, 102, 102));

        jMenuBar1.setBackground(new java.awt.Color(180, 184, 171));

        jMenu1.setText("Archivo");

        Abrir.setText("Abrir");
        Abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirActionPerformed(evt);
            }
        });
        jMenu1.add(Abrir);

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
        jMenu3.add(limpiarTablaSimbolos);

        limpiarConsola.setText("Limpiar consola");
        jMenu3.add(limpiarConsola);

        limpiarTodo.setText("Limpiar todo");
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLestado, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(btnCompilar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(143, 143, 143))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(46, 46, 46)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(632, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lblLestado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6)))
                        .addGap(16, 16, 16)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mostrarRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCompilar)
                            .addComponent(jLabel4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(294, 294, 294)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(45, 45, 45))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(101, 101, 101)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(203, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void docALexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_docALexActionPerformed
        File file = new File("src\\main\\java\\resources\\blank.txt");
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
            //File file = chooser.getSelectedFile();
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
        // TODO add your handling code here:
    }//GEN-LAST:event_limpiarEditorCodigoActionPerformed

    private void docALexMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_docALexMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_docALexMouseClicked

    private void docASinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_docASinActionPerformed
        File file = new File("src\\main\\java\\resources\\DocumentacionExamen.pdf");
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_docASinActionPerformed

    private void docASemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_docASemActionPerformed
        File file = new File("src\\main\\java\\resources\\DocumentacionExamen.pdf");
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_docASemActionPerformed

    private void docCompActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_docCompActionPerformed
        File file = new File("src\\main\\java\\resources\\blank.txt");
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
    private javax.swing.JButton btnCompilar;
    private javax.swing.JMenuItem docALex;
    private javax.swing.JMenuItem docASem;
    private javax.swing.JMenuItem docASin;
    private javax.swing.JMenuItem docComp;
    private javax.swing.JTextPane editorCodigo;
    private javax.swing.JMenuItem guardarComo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblLestado;
    private javax.swing.JLabel lblSalida;
    private javax.swing.JMenuItem limpiarConsola;
    private javax.swing.JMenuItem limpiarEditorCodigo;
    private javax.swing.JMenuItem limpiarTablaSimbolos;
    private javax.swing.JMenuItem limpiarTodo;
    private javax.swing.JTextField mostrarRuta;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
