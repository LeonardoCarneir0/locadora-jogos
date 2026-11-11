package locadora.ui;

import javax.swing.SwingUtilities;

/**
 * Ponto de entrada da interface gráfica (Swing).
 */
public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}
