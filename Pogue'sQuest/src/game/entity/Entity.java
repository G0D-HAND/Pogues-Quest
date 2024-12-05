package game.entity;

import java.awt.Rectangle;

public class Entity {
    protected int x, y;  // Position
    protected int health;
    protected int speed;
    protected int width = 40;  // Default width for entities
    protected int height = 40;  // Default height for entities

    public Entity(int x, int y, int health, int speed) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.speed = speed;
    }

    // Get the bounding box of the entity for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int dx, int dy) {
        this.x += dx * speed;
        this.y += dy * speed;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }
    
}
