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

public class NewJFrameBuscaminas extends javax.swing.JFrame {
    private JPanel panelSuperior;
    private JPanel panelTablero;
    private JPanel panelInferior;
    private JLabel etiquetaTitulo;
    private JLabel etiquetaMinas;
    private JLabel etiquetaResultado;
    private JButton btnReiniciar;
    private boolean partidaTerminada = false;
    private JButton[][] botones = new JButton[10][10];
    private boolean[][] minas = new boolean[10][10];
    private int[][] minasAlrededor = new int[10][10];
    private int totalMinas = 10;
    private boolean[][] banderas = new boolean[10][10];
    
public NewJFrameBuscaminas() {
    setTitle("Buscaminas");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    setLayout(new GridBagLayout());

    panelSuperior = new JPanel();
    panelTablero = new JPanel();
    panelInferior = new JPanel();

    etiquetaTitulo = new JLabel("Buscaminas");
    etiquetaMinas = new JLabel("Minas: 10");

    panelSuperior.setLayout(new GridBagLayout());

    GridBagConstraints gbcEtiqueta = new GridBagConstraints();
    gbcEtiqueta.gridy = 0;

    gbcEtiqueta.gridx = 0;
    panelSuperior.add(etiquetaTitulo, gbcEtiqueta);

    gbcEtiqueta.gridx = 1;
    gbcEtiqueta.insets = new Insets (0,20,0,0);
    panelSuperior.add(etiquetaMinas, gbcEtiqueta);

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
    
    GridBagConstraints gbcCasilla = new GridBagConstraints();
    gbcCasilla.fill = GridBagConstraints.BOTH;
    gbcCasilla.weightx = 1.0;
    gbcCasilla.weighty = 1.0;
    
    for(int fila = 0; fila<10; fila++){
        for(int columna = 0; columna < 10; columna++){
            botones[fila][columna] = new JButton();
 
            gbcCasilla.gridx = columna;
            gbcCasilla.gridy = fila;

            panelTablero.add(botones[fila][columna], gbcCasilla);
                
            final int filaBoton = fila;
            final int columnaBoton = columna;

                botones[filaBoton][columnaBoton].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (!SwingUtilities.isRightMouseButton(e)
                            || partidaTerminada
                            || !botones[filaBoton][columnaBoton].isEnabled()) {
                        return;
                    }

                    banderas[filaBoton][columnaBoton] =
                            !banderas[filaBoton][columnaBoton];

                    if (banderas[filaBoton][columnaBoton]) {
                        botones[filaBoton][columnaBoton].setText("F");
                    } else {
                        botones[filaBoton][columnaBoton].setText("");
                    }
                }
            });
        }
    }
    
    inicializarPartida();
    
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
    
    
}

    private void colocarMinas(){
        Random random = new Random();
        int minasColocadas = 0;
        
        while(minasColocadas < totalMinas){
            int fila = random.nextInt(10);
            int columna = random.nextInt(10);
            
            if(!minas[fila][columna]){
                minas[fila][columna] = true;
                minasColocadas++;
            }
        }
    }
    
    private void calcularMinasAlrededor() {
    for (int fila = 0; fila < 10; fila++) {
        for (int columna = 0; columna < 10; columna++) {
            int contador = 0;

            for (int desplazamientoFila = -1; desplazamientoFila <= 1; desplazamientoFila++) {
                for (int desplazamientoColumna = -1; desplazamientoColumna <= 1; desplazamientoColumna++) {
                    int filaVecina = fila + desplazamientoFila;
                    int columnaVecina = columna + desplazamientoColumna;

                    if (filaVecina >= 0 && filaVecina < 10
                            && columnaVecina >= 0 && columnaVecina < 10
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
    for (int fila = 0; fila < 10; fila++) {
        for (int columna = 0; columna < 10; columna++) {
            if (minas[fila][columna]) {
                botones[fila][columna].setText("*");
            }

            botones[fila][columna].setEnabled(false);
        }
    }
}
    
private void descubrirCasilla(int fila, int columna) {
    if (fila < 0 || fila >= 10 || columna < 0 || columna >= 10) {
        return;
    }

    if (!botones[fila][columna].isEnabled() || minas[fila][columna]) {
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

    for (int fila = 0; fila < 10; fila++) {
        for (int columna = 0; columna < 10; columna++) {
            if (!minas[fila][columna] && !botones[fila][columna].isEnabled()) {
                casillasSegurasDescubiertas++;
            }
        }
    }

    if (casillasSegurasDescubiertas == 100 - totalMinas) {
        etiquetaResultado.setText("¡Has ganado!");
        partidaTerminada = true;
        mostrarTodasLasMinas();
    }
}


private void reiniciarPartida() {
    partidaTerminada = false;

    for (int fila = 0; fila < 10; fila++) {
    for (int columna = 0; columna < 10; columna++) {
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
