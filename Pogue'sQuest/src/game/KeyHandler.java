package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Update the direction booleans based on which key was pressed
        if (code == KeyEvent.VK_W) {
            upPressed = true;  // Move up
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;  // Move down
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;  // Move left
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;  // Move right
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        // Update the direction booleans based on which key was released
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used in this case
    }
}
