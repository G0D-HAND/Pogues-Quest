package game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Projectile {
    private int x, y;  // Position
    private int speed = 10;  // Projectile speed
    private int dx, dy;  // Direction of movement
    private int size = 10;  // Size of the projectile

    // Constructor
    public Projectile(int x, int y, int targetX, int targetY) {
        this.x = x;
        this.y = y;

        // Calculate the direction using basic trigonometry
        double angle = Math.atan2(targetY - y, targetX - x);
        this.dx = (int) (Math.cos(angle) * speed);
        this.dy = (int) (Math.sin(angle) * speed);
    }

    // Update the projectile's position
    public void update() {
        x += dx;
        y += dy;
    }

    // Render the projectile
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, size, size);
    }

    // Get the hitbox of the projectile
    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}
