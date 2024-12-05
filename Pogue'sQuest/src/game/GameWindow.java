package game;

import javax.swing.JFrame;

public class GameWindow {
    private JFrame frame;

    public GameWindow() {
        frame = new JFrame("Pogue's Quest");  // Set the title of the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Ensure the window closes when exiting
        frame.setResizable(false);  // Prevent resizing
        frame.setSize(800, 600);  // Set window dimensions (adjustable)
        frame.setLocationRelativeTo(null);  // Center the window on the screen
        frame.setVisible(true);  // Make the window visible
    }

    public void addPanel(GamePanel panel) {
        frame.add(panel);  // Add the GamePanel to the window
        frame.pack();  // Adjusts window size based on its components
    }
}
