package game;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create the game window
                GameWindow window = new GameWindow();

                // Create the game panel
                GamePanel panel = new GamePanel();

                // Add the game panel to the window
                window.addPanel(panel);

                // Start the game loop
                panel.startGameThread();
            }
        });
    }
}
