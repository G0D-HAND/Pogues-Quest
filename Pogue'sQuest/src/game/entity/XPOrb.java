package game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class XPOrb {
    private int x, y;  // Position of the XP orb
    private int xpValue;  // XP value the orb gives to the player
    private int size = 10;  // Size of the XP orb

    // Constructor
    public XPOrb(int x, int y, int xpValue) {
        this.x = x;
        this.y = y;
        this.xpValue = xpValue;
    }

    // Method to get the XP value of the orb
    public int getXPValue() {
        return xpValue;
    }

    // Get the bounds of the XP orb (used for collision detection)
    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }

    // Draw the XP orb
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, size, size);  // Draw the XP orb as a yellow circle
    }
}
