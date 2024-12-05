package game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Enemy extends Entity {
    private String type;
    private int xpValue;  // XP the enemy drops when defeated

    public Enemy(String type, int x, int y, int health, int speed) {
        super(x, y, health, speed);
        this.type = type;
        this.xpValue = 20;  // Example XP value (adjust per enemy type)
    }

    // Move towards the player's position
    public void moveTowardsPlayer(int playerX, int playerY) {
        if (x < playerX) {
            x += speed;
        } else if (x > playerX) {
            x -= speed;
        }

        if (y < playerY) {
            y += speed;
        } else if (y > playerY) {
            y -= speed;
        }
    }
    
    

    // Draw enemy with health bar
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 40, 40);

        // Draw health bar above the enemy
        g.setColor(Color.GREEN);
        g.fillRect(x, y - 10, (int) ((double) health / 50 * 40), 5);
    }

    // Get the hitbox of the enemy
    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 40);
    }

    // Return the XP value when the enemy dies
    public int getXPValue() {
        return xpValue;
    }
}
