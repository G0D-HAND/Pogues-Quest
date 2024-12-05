package game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class PowerUp {
    protected int x, y;
    protected int width, height;

    public PowerUp(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 30;
        this.height = 30;
    }

    // Abstract method for applying the power-up effect to the player
    public abstract void applyEffect(Player player);

    // Draw the power-up on the screen
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);  // Default power-up color, can vary by type
        g.fillRect(x, y, width, height);
    }

    // Get the bounding box for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
