/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package group.buscaminas;
import javax.swing.JFrame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

public class NewJFrameBuscaminas extends javax.swing.JFrame {
    private JMenuBar barraJuego;
    private JMenu menuJuego;
    private JMenu menuAyuda;
    private JPanel panelSuperior;
    private JPanel panelTablero;
    private JPanel panelInferior;
    private JLabel etiquetaTitulo;
    private JLabel etiquetaMinas;
    private JLabel etiquetaResultado;
    private JButton btnReiniciar;
    private boolean partidaTerminada = false;
    private JButton[][] botones;
    private boolean[][] minas;
    private int[][] minasAlrededor;
    private int totalMinas = 10;
    private boolean[][] banderas;
    private int tamanoTablero = 10;
    private JComboBox<String> comboDificultad;
    
    
public NewJFrameBuscaminas() {
    setTitle("Buscaminas");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    setLayout(new GridBagLayout());
    
 

    panelSuperior = new JPanel();
    panelTablero = new JPanel();
    panelInferior = new JPanel();
    
    barraJuego = new JMenuBar();

        menuJuego = new JMenu("Juego");
       menuAyuda = new JMenu("Ayuda");

       barraJuego.add(menuJuego);
       barraJuego.add(menuAyuda);
       setJMenuBar(barraJuego);

       JMenuItem opcionReiniciar = new JMenuItem("Reiniciar");
       menuJuego.add(opcionReiniciar);
       opcionReiniciar.addActionListener(e -> reiniciarPartida());
       
       JMenu menuTamano = new JMenu("Tamaño del tablero");
       menuJuego.add(menuTamano);
       
       JMenuItem tamano8 = new JMenuItem("8x8");
       JMenuItem tamano10 = new JMenuItem("10x10");
       JMenuItem tamano15 = new JMenuItem("15x15");
       
       menuTamano.add(tamano8);
       menuTamano.add(tamano10);
       menuTamano.add(tamano15);
       
       tamano8.addActionListener(e -> cambiarTamanoTablero(8));
       tamano10.addActionListener(e -> cambiarTamanoTablero(10));
       tamano15.addActionListener(e -> cambiarTamanoTablero(15));

       JMenuItem opcionAcercaDe = new JMenuItem("Acerca de");
       menuAyuda.add(opcionAcercaDe);

       opcionAcercaDe.addActionListener(e -> {
           JFrame ventanaAyuda = new JFrame("Acerca de");
           ventanaAyuda.setSize(300, 150);
           ventanaAyuda.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
           ventanaAyuda.add(
               new JLabel("Juego del Buscaminas", SwingConstants.CENTER)
           );
           ventanaAyuda.setLocationRelativeTo(this);
           ventanaAyuda.setVisible(true);
       });
    
    


    etiquetaTitulo = new JLabel("Buscaminas");
    etiquetaMinas = new JLabel("Minas: " + totalMinas);

    panelSuperior.setLayout(new GridBagLayout());
    

    GridBagConstraints gbcEtiqueta = new GridBagConstraints();
    gbcEtiqueta.gridy = 0;

    comboDificultad = new JComboBox<>(
        new String[]{"Facil", "Media", "Dificil"}
    );

    gbcEtiqueta.gridx = 2;
    panelSuperior.add(comboDificultad, gbcEtiqueta);

    gbcEtiqueta.gridx = 0;
    panelSuperior.add(etiquetaTitulo, gbcEtiqueta);

    gbcEtiqueta.gridx = 1;
    gbcEtiqueta.insets = new Insets (0,20,0,0);
    panelSuperior.add(etiquetaMinas, gbcEtiqueta);
    
    comboDificultad.addActionListener(e -> {
        switch (comboDificultad.getSelectedIndex()) {
    case 0: 
        totalMinas = 10;
        break;
    case 1: 
        totalMinas = 20;
        break;
    case 2: 
        totalMinas = 30;
        break;
}
            etiquetaMinas.setText("Minas: " + totalMinas);
            reiniciarPartida();
    });

    GridBagConstraints gbcPanel = new GridBagConstraints();
    gbcPanel.gridx = 0;
    gbcPanel.gridy = 0;
    gbcPanel.weighty = 1.0;
    gbcPanel.fill = GridBagConstraints.HORIZONTAL;
    gbcPanel.anchor = GridBagConstraints.NORTH;

    add(panelSuperior, gbcPanel);
    
    panelTablero.setLayout(new GridBagLayout());
    GridBagConstraints gbcTablero = new GridBagConstraints();
    gbcTablero.gridx = 0;
    gbcTablero.gridy = 1;
    gbcTablero.weightx = 1.0;
    gbcTablero.weighty = 1.0;
    gbcTablero.fill = GridBagConstraints.BOTH;

    add(panelTablero, gbcTablero);
    
    btnReiniciar = new JButton("Reiniciar");
    panelInferior.add(btnReiniciar);
    
    
    btnReiniciar.addActionListener(e -> reiniciarPartida());
    etiquetaResultado = new JLabel("Partida en curso");
    panelInferior.add(etiquetaResultado);
    GridBagConstraints gbcInferior = new GridBagConstraints();
    gbcInferior.gridx = 0;
    gbcInferior.gridy = 2;
    gbcInferior.weightx = 1.0;
    gbcInferior.fill = GridBagConstraints.HORIZONTAL;

    add(panelInferior, gbcInferior);
    cambiarTamanoTablero(tamanoTablero);
    
    
}

    private void colocarMinas(){
        Random random = new Random();
        int minasColocadas = 0;
        
        while(minasColocadas < totalMinas){
            int fila = random.nextInt(tamanoTablero);
            int columna = random.nextInt(tamanoTablero);
            
            if(!minas[fila][columna]){
                minas[fila][columna] = true;
                minasColocadas++;
            }
        }
    }
    
    private void calcularMinasAlrededor() {
    for (int fila = 0; fila < tamanoTablero; fila++) {
        for (int columna = 0; columna < tamanoTablero; columna++) {
            int contador = 0;

            for (int desplazamientoFila = -1; desplazamientoFila <= 1; desplazamientoFila++) {
                for (int desplazamientoColumna = -1; desplazamientoColumna <= 1; desplazamientoColumna++) {
                    int filaVecina = fila + desplazamientoFila;
                    int columnaVecina = columna + desplazamientoColumna;

                    if (filaVecina >= 0 && filaVecina < tamanoTablero
                            && columnaVecina >= 0 && columnaVecina < tamanoTablero
                            && minas[filaVecina][columnaVecina]) {
                        contador++;
                    }
                }
                
            }

            minasAlrededor[fila][columna] = contador;
        }
    }
}
    private void inicializarPartida() {
    colocarMinas();
    calcularMinasAlrededor();
}
    
    private void mostrarTodasLasMinas() {
    for (int fila = 0; fila < tamanoTablero; fila++) {
        for (int columna = 0; columna < tamanoTablero; columna++) {
            if (minas[fila][columna]) {
                botones[fila][columna].setText("*");
            }

            botones[fila][columna].setEnabled(false);
        }
    }
}
    
private void descubrirCasilla(int fila, int columna) {
    if (fila < 0 || fila >= tamanoTablero || columna < 0 || columna >= tamanoTablero) {
        return;
    }

    if (!botones[fila][columna].isEnabled() || minas[fila][columna] || banderas[fila][columna]) {
        return;
    }

    int cantidad = minasAlrededor[fila][columna];
    botones[fila][columna].setEnabled(false);

    if (cantidad > 0) {
        botones[fila][columna].setText(String.valueOf(cantidad));
        return;
    }

    for (int desplazamientoFila = -1; desplazamientoFila <= 1; desplazamientoFila++) {
        for (int desplazamientoColumna = -1; desplazamientoColumna <= 1; desplazamientoColumna++) {
            descubrirCasilla(fila + desplazamientoFila, columna + desplazamientoColumna);
        }
    }
}

private void comprobarVictoria() {
    int casillasSegurasDescubiertas = 0;

    for (int fila = 0; fila < tamanoTablero; fila++) {
        for (int columna = 0; columna < tamanoTablero; columna++) {
            if (!minas[fila][columna] && !botones[fila][columna].isEnabled()) {
                casillasSegurasDescubiertas++;
            }
        }
    }

    if (casillasSegurasDescubiertas == tamanoTablero * tamanoTablero - totalMinas) {
        etiquetaResultado.setText("¡Has ganado!");
        partidaTerminada = true;
        mostrarTodasLasMinas();
    }
}


private void reiniciarPartida() {
    partidaTerminada = false;

    for (int fila = 0; fila < tamanoTablero; fila++) {
    for (int columna = 0; columna < tamanoTablero; columna++) {
        minas[fila][columna] = false;
        minasAlrededor[fila][columna] = 0;
        banderas[fila][columna] = false;

        botones[fila][columna].setText("");
        botones[fila][columna].setEnabled(true);
    }
}

    colocarMinas();
    calcularMinasAlrededor();

    etiquetaResultado.setText("Partida en curso");
}

private void cambiarTamanoTablero(int nuevoTamano) {
    if (nuevoTamano < 1 || (long) nuevoTamano * nuevoTamano <= totalMinas) {
        throw new IllegalArgumentException("El tablero debe tener más casillas que minas.");
    }
    tamanoTablero = nuevoTamano;
    panelTablero.removeAll();
    botones = new JButton[tamanoTablero][tamanoTablero];
    minas = new boolean[tamanoTablero][tamanoTablero];
    minasAlrededor = new int[tamanoTablero][tamanoTablero];
    banderas = new boolean[tamanoTablero][tamanoTablero];
    crearBotonesTablero();
    reiniciarPartida();
    panelTablero.revalidate();
    panelTablero.repaint();
}

private void crearBotonesTablero() {
    GridBagConstraints gbcCasilla = new GridBagConstraints();
    gbcCasilla.fill = GridBagConstraints.BOTH;
    gbcCasilla.weightx = 1.0;
    gbcCasilla.weighty = 1.0;
    for (int fila = 0; fila < tamanoTablero; fila++) {
        for (int columna = 0; columna < tamanoTablero; columna++) {
            JButton boton = new JButton();
            botones[fila][columna] = boton;
            gbcCasilla.gridx = columna;
            gbcCasilla.gridy = fila;
            panelTablero.add(boton, gbcCasilla);
            final int filaBoton = fila;
            final int columnaBoton = columna;
            boton.addActionListener(e -> {
                if (partidaTerminada || banderas[filaBoton][columnaBoton]) {
                    return;
                }
                if (minas[filaBoton][columnaBoton]) {
                    etiquetaResultado.setText("Has perdido");
                    partidaTerminada = true;
                    mostrarTodasLasMinas();
                } else {
                    descubrirCasilla(filaBoton, columnaBoton);
                    comprobarVictoria();
                }
            });
            boton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (!SwingUtilities.isRightMouseButton(e)
                            || partidaTerminada || !boton.isEnabled()) {
                        return;
                    }
                    banderas[filaBoton][columnaBoton] = !banderas[filaBoton][columnaBoton];
                    boton.setText(banderas[filaBoton][columnaBoton] ? "F" : "");
                }
            });
        }
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(NewJFrameBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrameBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrameBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrameBuscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrameBuscaminas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    

}
